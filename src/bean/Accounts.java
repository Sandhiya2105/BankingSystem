package bean;
import exception.InvalidAccountException;
import exception.InsufficientFundException;
import exception.OverDraftLimitExceededException;
public class Accounts {
    private static long lastAccNo = 1000;

    private String accountNumber;
    private String accountType; // Savings or Current
    protected double accountBalance;
    private Customer customer; // Association: Account "has a" bean.Customer

    // Default Constructor
    public Accounts() {
        this.accountNumber = "";
        this.accountType = "";
        this.accountBalance = 0.0;
        this.customer = null;
    }

    // Parameterized Constructor (with customer)
    public Accounts(String accountNumber, String accountType, double accountBalance, Customer customer) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.accountBalance = accountBalance;
        this.customer = customer;
    }
    @Override
    public String toString() {
        return "Account Details:\n" +
                "Account Number: " + accountNumber + "\n" +
                "Name          : " + customer + "\n" +
                "Balance       : " + accountBalance;
    }
    private String generateAccountNumber() {
        return String.valueOf(++lastAccNo);
    }

    // Getters and Setters
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public double getAccountBalance() { return accountBalance; }
    public void setAccountBalance(double accountBalance) { this.accountBalance = accountBalance; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    // Print Account Info
    public void printAccountInfo() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Type  : " + accountType);
        System.out.println("Balance       : $" + accountBalance);
        if (customer != null) {
            System.out.println("Name          : " + customer.getFirstName() + " " + customer.getLastName());
            System.out.println("Email         : " + customer.getEmailAddress());
            System.out.println("Phone         : " + customer.getPhoneNumber());
            System.out.println("Address       : " + customer.getAddress());
        } else {
            System.out.println("No customer linked to this account.");
        }
    }


    // Deposit
    public void deposit(double amount) {
        if (amount > 0) {
            accountBalance += amount;
            System.out.println("Deposited: $" + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    // Overloaded withdraw methods
    public void withdraw(float amount) throws InsufficientFundException,OverDraftLimitExceededException {
        withdraw((double) amount);
    }

    // Withdraw
    public void withdraw(double amount) throws InsufficientFundException,OverDraftLimitExceededException {
        if (amount <= accountBalance && amount > 0) {
            accountBalance -= amount;
            System.out.println("Withdrawn: $" + amount);
        } else {
            throw new InsufficientFundException("Insufficient balance or invalid amount.");
        }
    }
    // Calculate Interest (4.5%)
    public void calculateInterest() {
        if (accountType.equalsIgnoreCase("Savings")) {
            double interest = accountBalance * 0.045;
            accountBalance += interest;
            System.out.println("Interest of $" + interest + " added. New Balance: $" + accountBalance);
        } else {
            System.out.println("Interest only applicable for Savings accounts.");
        }
    }
    public static long getLastAccNo() {
        return lastAccNo;
    }
    public static void setLastAccNo(long accNo) {
        lastAccNo = accNo;
    }
}


