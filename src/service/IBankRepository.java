package service;

import bean.Accounts;
import bean.Customer;
import bean.Transaction;

import java.sql.Date;
import java.util.List;
import java.sql.Timestamp;

public interface IBankRepository {
    void createAccount(Customer customer, long accNo, String accType, float balance) throws Exception;

    List<Accounts> listAccounts() throws Exception;

    void calculateInterest() throws Exception;

    double getAccountBalance(long accountNumber) throws Exception;

    double deposit(long accountNumber, float amount) throws Exception;

    double withdraw(long accountNumber, float amount) throws Exception;

    void transfer(long fromAccount, long toAccount, float amount) throws Exception;

    Accounts getAccountDetails(long accountNumber) throws Exception;

    List<Transaction> getTransactions(long accountNumber, Timestamp fromDate, Timestamp toDate) throws Exception;
}
