package bean;

public class ZeroBalanceAccount extends Accounts {

    public ZeroBalanceAccount(String accountNumber, String accountType, double balance, Customer customer) {
        super(accountNumber, accountType, balance, customer);
    }
@Override
    public void calculateInterest() {
        System.out.println("ℹ No interest for Zero Balance Account.");
    }
}

