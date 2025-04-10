package bean;

public class SavingsAccount extends Accounts {
    private double interestRate;

    public SavingsAccount(String accountNumber, String accountType, double balance, Customer customer, double interestRate) {
        super(accountNumber, accountType, balance, customer);
        this.interestRate = interestRate;
    }

    public double getInterestRate() { return interestRate; }
    public void setInterestRate(double interestRate) { this.interestRate = interestRate; }


    @Override
    public void calculateInterest() {
        double interest = accountBalance * (interestRate / 100);
        accountBalance += interest;
        System.out.println("Interest of ₹" + interest + " added. New Balance: ₹" + accountBalance);
    }
}
