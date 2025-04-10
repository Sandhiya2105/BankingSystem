package bean;

import java.util.HashMap;
import java.util.Map;
import exception.InvalidAccountException;
import exception.InsufficientFundException;
import exception.OverDraftLimitExceededException;


public class HMBank {
    private Map<Long, Accounts> accountsMap;
    private static long nextAccountNumber = 1001;

    public HMBank() {
        accountsMap = new HashMap<>();
    }

    // Create Account
    public long create_account(Customer customer, String accType, float balance) {
        long accNo = nextAccountNumber++;
        String accNumStr = String.valueOf(accNo);

        Accounts account;
        if (accType.equalsIgnoreCase("Savings")) {
            account = new SavingsAccount(accNumStr, accType, balance, customer, 4.5);
        } else if (accType.equalsIgnoreCase("Current")) {
            account = new CurrentAccount(accNumStr, accType, balance, customer, 500.0);
        } else {
            System.out.println("Invalid account type. Account not created.");
            return -1;
        }

        account.setCustomer(customer);
        accountsMap.put(accNo, account);
        System.out.println("Account created with Account Number: " + accNo);
        return accNo;
    }

    // Get Account Balance
    public double get_account_balance(long accountNumber) throws InvalidAccountException {
        Accounts account = accountsMap.get(accountNumber);
        if (account != null) return account.getAccountBalance();
        throw new InvalidAccountException("Account not found.");
    }

    // Deposit
    public double deposit(long accountNumber, float amount) {
        Accounts account = accountsMap.get(accountNumber);
        if (account != null) {
            account.deposit(amount);
            return account.getAccountBalance();
        } else {
            System.out.println("Account not found.");
            return -1;
        }
    }

    // Withdraw
    public double withdraw(long accountNumber, float amount)
            throws InvalidAccountException, InsufficientFundException, OverDraftLimitExceededException {
        Accounts account = accountsMap.get(accountNumber);
        if (account == null) {
            throw new InvalidAccountException("Account number " + accountNumber + " not found.");
        }

        if (account instanceof CurrentAccount) {
            ((CurrentAccount) account).withdraw(amount);
        } else {
            account.withdraw(amount);
        }

        return account.getAccountBalance();
    }


    // Transfer
    public void transfer(long fromAccount, long toAccount, float amount)
            throws InvalidAccountException, InsufficientFundException, OverDraftLimitExceededException {
        Accounts fromAcc = accountsMap.get(fromAccount);
        Accounts toAcc = accountsMap.get(toAccount);

        if (fromAcc == null || toAcc == null) {
            throw new InvalidAccountException("One or both accounts not found.");
        }

        if (fromAcc instanceof CurrentAccount) {
            ((CurrentAccount) fromAcc).withdraw(amount);
        } else {
            fromAcc.withdraw(amount);
        }

        toAcc.deposit(amount);
        System.out.println("Transferred ₹" + amount + " from " + fromAccount + " to " + toAccount);
    }


    // Get Account + bean.Customer Details
    public void getAccountDetails(long accountNumber) throws InvalidAccountException {
        Accounts account = accountsMap.get(accountNumber);
        if (account != null) {
            account.printAccountInfo();
        } else {
            throw new InvalidAccountException("Account not found.");
        }
    }
}

