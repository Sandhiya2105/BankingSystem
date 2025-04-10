package app;

import bean.Customer;
import bean.Transaction;
import repository.BankRepositoryImpl;
import service.IBankRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class BankApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        IBankRepository bankRepo = new BankRepositoryImpl();

        while (true) {
            try {
                System.out.println("\n=========  BANK MENU =========");
                System.out.println("1. Create Account");
                System.out.println("2. Deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. Get Account Balance");
                System.out.println("5. Transfer Funds");
                System.out.println("6. Get Account Details");
                System.out.println("7. List Accounts");
                System.out.println("8. Get Transactions");
                System.out.println("9. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1: // Create Account
                        System.out.print("Enter Customer ID: ");
                        String id = sc.nextLine();
                        System.out.print("First Name: ");
                        String fName = sc.nextLine();
                        System.out.print("Last Name: ");
                        String lName = sc.nextLine();

                        String email;
                        while (true) {
                            System.out.print("Email Address: ");
                            email = sc.nextLine();
                            if (email.matches("^(.+)@(.+)$")) break;
                            System.out.println("Invalid email. Try again.");
                        }

                        String phone;
                        while (true) {
                            System.out.print("Phone Number (10 digits): ");
                            phone = sc.nextLine();
                            if (phone.matches("\\d{10}")) break;
                            System.out.println("Invalid phone number. Try again.");
                        }

                        System.out.print("Address: ");
                        String address = sc.nextLine();

                        Customer customer = new Customer(id, fName, lName, email, phone, address);

                        System.out.print("Choose Account Type (Savings / Current / ZeroBalance): ");
                        String accType = sc.nextLine();
                        System.out.print("Initial Deposit Amount: ");
                        float bal = sc.nextFloat();
                        sc.nextLine();

                        long accNo = System.currentTimeMillis(); // temporary account number generation
                        bankRepo.createAccount(customer, accNo, accType, bal);
                        System.out.println(" Account created successfully! Your Account Number is: " + accNo);
                        break;

                    case 2: // Deposit
                        System.out.print("Enter Account Number: ");
                        long depAcc = sc.nextLong();
                        System.out.print("Enter amount to deposit: ");
                        float depAmount = sc.nextFloat();
                        double updatedBal = bankRepo.deposit(depAcc, depAmount);
                        System.out.println("Deposit successful. Updated Balance: ₹" + updatedBal);
                        break;

                    case 3: // Withdraw
                        System.out.print("Enter Account Number: ");
                        long witAcc = sc.nextLong();
                        System.out.print("Enter amount to withdraw: ");
                        float witAmount = sc.nextFloat();
                        double newBal = bankRepo.withdraw(witAcc, witAmount);
                        System.out.println("Withdrawal successful. New Balance: ₹" + newBal);
                        break;

                    case 4: // Get Balance
                        System.out.print("Enter Account Number: ");
                        long balAcc = sc.nextLong();
                        double balance = bankRepo.getAccountBalance(balAcc);
                        System.out.println("Current Balance: ₹" + balance);
                        break;

                    case 5: // Transfer
                        System.out.print("From Account Number: ");
                        long fromAcc = sc.nextLong();
                        System.out.print("To Account Number: ");
                        long toAcc = sc.nextLong();
                        System.out.print("Amount to transfer: ");
                        float amount = sc.nextFloat();
                        bankRepo.transfer(fromAcc, toAcc, amount);
                        System.out.println("Transfer successful.");
                        break;

                    case 6: // Get Account Details
                        System.out.print("Enter Account Number: ");
                        long accDetails = sc.nextLong();
                        System.out.println(bankRepo.getAccountDetails(accDetails));
                        break;

                    case 7: // List All Accounts
                        bankRepo.listAccounts().forEach(a -> a.printAccountInfo());
                        break;
                    case 8: // Get Transactions
                        System.out.print("Enter Account Number: ");
                        long accTx = sc.nextLong();
                        sc.nextLine();

                        System.out.print("From Date (yyyy-mm-dd): ");
                        String fromDateStr = sc.nextLine();
                        LocalDateTime fromDateTime = LocalDate.parse(fromDateStr).atStartOfDay();
                        Timestamp from = Timestamp.valueOf(fromDateTime);

                        System.out.print("To Date (yyyy-mm-dd): ");
                        String toDateStr = sc.nextLine();

                        LocalDateTime toDateTime = LocalDate.parse(toDateStr).atTime(23, 59, 59);
                        Timestamp to = Timestamp.valueOf(toDateTime);

                        List<Transaction> txns = bankRepo.getTransactions(accTx, from, to);
                        if (txns.isEmpty()) {
                            System.out.println("ℹ No transactions found between the given dates.");
                        } else {
                            System.out.println("Transaction History:");
                            for (Transaction t : txns) {
                                System.out.println("──────────────────────────────");
                                System.out.println("Type       : " + t.getTransactionType());
                                System.out.println("Amount     : ₹" + t.getTransactionAmount());
                                System.out.println("Date & Time: " + t.getDateTime());
                                System.out.println("Note       : " + t.getDescription());
                            }
                            System.out.println("──────────────────────────────");
                        }
                        break;



                    case 9:
                        System.out.println("Thank you for using the Banking System!");
                        sc.close();
                        System.exit(0);

                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}


