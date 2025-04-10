package bean;
import exception.OverDraftLimitExceededException;

public class CurrentAccount extends Accounts {
    private double overdraftLimit;

    public CurrentAccount(String accountNumber, String accountType, double balance, Customer customer, double overdraftLimit) {
        super(accountNumber, accountType, balance, customer);
        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftLimit() { return overdraftLimit; }
    public void setOverdraftLimit(double overdraftLimit) { this.overdraftLimit = overdraftLimit; }

    @Override
    public void withdraw(double amount) throws OverDraftLimitExceededException {
        if (amount <= accountBalance + overdraftLimit) {
            accountBalance -= amount;
            System.out.println("Withdrawal of ₹" + amount + " successful. New balance: ₹" + accountBalance);
        } else {
            throw new OverDraftLimitExceededException("Withdrawal failed. Amount exceeds overdraft limit.");
        }
    }

    @Override
    public void calculateInterest() {
        System.out.println("ℹ No interest is applicable on Current Account.");
    }
}
