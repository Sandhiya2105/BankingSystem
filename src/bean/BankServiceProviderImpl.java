package bean;

import service.IBankServiceProvider;
import java.util.List;

public class BankServiceProviderImpl extends CustomerServiceProviderImpl implements IBankServiceProvider {
    private String branchName = "Main Branch";
    private String branchAddress = "123 Bank Street";

    @Override
    public void create_account(Customer customer, long accNo, String accType, double balance) {
        Accounts account = null;
        String accNumStr = String.valueOf(accNo);

        switch (accType.toLowerCase()) {
            case "savings":
                if (balance < 500) {
                    System.out.println("Minimum balance for Savings Account is ₹500.");
                    return;
                }
                account = new SavingsAccount(accNumStr, "Savings", balance, customer, 4.5);
                break;

            case "current":
                account = new CurrentAccount(accNumStr, "Current", balance, customer, 10000.0);
                break;

            case "zero":
            case "zerobalance":
                account = new ZeroBalanceAccount(accNumStr, "ZeroBalance", balance, customer);
                break;

            default:
                System.out.println("Invalid account type.");
                return;
        }

        accountList.add(account);
        Accounts.setLastAccNo(accNo);

        System.out.println("Account created successfully. Account Number: " + account.getAccountNumber());
    }

    @Override
    public Accounts getAccountDetails(long accountNumber) {
        for (Accounts acc : accountList) {
            if (Long.parseLong(acc.getAccountNumber()) == accountNumber) {
                return acc;
            }
        }
        System.out.println("Account not found.");
        return null;
    }

    @Override
    public List<Accounts> listAccounts() {
        return accountList;
    }

    @Override
    public void calculateInterest() {
        for (Accounts acc : accountList) {
            acc.calculateInterest();
        }
    }

    @Override
    public void printAccountDetails(long accountNumber) {
        Accounts acc = getAccountDetails(accountNumber);
        if (acc != null) {
            acc.printAccountInfo();
        }
    }
}




