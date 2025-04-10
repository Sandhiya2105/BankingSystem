import java.util.Scanner;

public class ATMSimulation {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double balance = 5000;

        System.out.println("Welcome to ATM!");
        System.out.println("1. Check Balance\n2. Withdraw\n3. Deposit");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();

        if (choice == 1) {
            System.out.println("Balance: $" + balance);
        } else if (choice == 2) {
            System.out.print("Enter amount to withdraw: ");
            double amount = sc.nextDouble();
            if (amount <= balance) {
                if (amount % 100 == 0 || amount % 500 == 0) {
                    balance -= amount;
                    System.out.println("Withdrawal successful. Balance: $" + balance);
                } else {
                    System.out.println("Amount must be multiple of 100 or 500.");
                }
            } else {
                System.out.println("Insufficient funds.");
            }
        } else if (choice == 3) {
            System.out.print("Enter amount to deposit: ");
            double amount = sc.nextDouble();
            balance += amount;
            System.out.println("Deposit successful. Balance: $" + balance);
        } else {
            System.out.println("Invalid choice.");
        }

        sc.close();
    }
}

