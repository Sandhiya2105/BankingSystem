package service;
import bean.Accounts;
import java.util.Date;
import java.util.List;
import exception.InsufficientFundException;
import exception.OverDraftLimitExceededException;

public interface ICustomerServiceProvider {
    double get_account_balance(long accountNumber);

    double deposit(long accountNumber, float amount);

    double withdraw(long accountNumber, float amount)
            throws InsufficientFundException, OverDraftLimitExceededException;

    void transfer(long fromAccount, int toAccount, float amount)
            throws InsufficientFundException, OverDraftLimitExceededException;

    void printAccountDetails(long accountNumber); // clearer name
    List<bean.Transaction> getTransactions(long accountNumber, Date from, Date to);
}


