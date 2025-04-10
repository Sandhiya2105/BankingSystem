package bean;

import service.ICustomerServiceProvider;
import exception.InsufficientFundException;
import exception.OverDraftLimitExceededException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import bean.Transaction;

public class CustomerServiceProviderImpl implements ICustomerServiceProvider {

    protected List<Accounts> accountList = new ArrayList<>();
    protected List<Transaction> transactionList = new ArrayList<>();

    protected Accounts findAccount(long accNo) {
        for (Accounts acc : accountList) {
            if (acc.getAccountNumber().equals(String.valueOf(accNo))) {
                return acc;
            }
        }
        return null;
    }

    public double get_account_balance(long accountNumber) {
        Accounts acc = findAccount(accountNumber);
        if (acc != null) return acc.getAccountBalance();
        System.out.println("Account not found.");
        return -1;
    }

    public double deposit(long accountNumber, float amount) {
        Accounts acc = findAccount(accountNumber);
        if (acc != null) {
            acc.deposit(amount);
            transactionList.add(new Transaction(acc, "Deposit", "Deposit", amount));
            return acc.getAccountBalance();
        }
        System.out.println("Account not found.");
        return -1;
    }

    public double withdraw(long accountNumber, float amount)
            throws InsufficientFundException, OverDraftLimitExceededException {
        Accounts acc = findAccount(accountNumber);
        if (acc != null) {
            acc.withdraw(amount); // may throw exceptions
            transactionList.add(new Transaction(acc, "Withdrawal", "Withdraw", amount));
            return acc.getAccountBalance();
        }
        System.out.println("Account not found.");
        return -1;
    }

    public void transfer(long fromAccount, int toAccount, float amount)
            throws InsufficientFundException, OverDraftLimitExceededException {
        Accounts from = findAccount(fromAccount);
        Accounts to = findAccount(toAccount);
        if (from != null && to != null) {
            if (from.getAccountBalance() >= amount) {
                from.withdraw(amount);
                to.deposit(amount);
                transactionList.add(new Transaction(from, "Transfer to " + to.getAccountNumber(), "Transfer", amount));
                transactionList.add(new Transaction(to, "Transfer from " + from.getAccountNumber(), "Transfer", amount));
                System.out.println("Transfer successful.");
            } else {
                throw new InsufficientFundException("Insufficient funds.");
            }
        } else {
            System.out.println("One or both accounts not found.");
        }
    }

    public void printAccountDetails(long accountNumber) {
        Accounts acc = findAccount(accountNumber);
        if (acc != null) acc.printAccountInfo();
        else System.out.println("Account not found.");
    }

    public List<Transaction> getTransations(long accountNumber, java.util.Date fromDate, java.util.Date toDate) {
        return transactionList.stream()
                .filter(t -> t.getAccount().getAccountNumber().equals(String.valueOf(accountNumber)))
                .filter(t -> {
                    Date txnDate = java.sql.Timestamp.valueOf(t.getDateTime());
                    return !txnDate.before(fromDate) && !txnDate.after(toDate);
                })
                .collect(Collectors.toList());
    }
    @Override
    public List<Transaction> getTransactions(long accountNumber, Date fromDate, Date toDate) {
        System.out.println("Transaction retrieval is not supported in local implementation.");
        return new ArrayList<>(); // or return Collections.emptyList();
    }
}



