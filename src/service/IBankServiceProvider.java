package service;

import bean.Accounts;
import bean.Customer;
import java.util.List;

public interface IBankServiceProvider {
    void create_account(Customer customer,long accNo,String accType, double balance);
    List<Accounts> listAccounts();
    Accounts getAccountDetails(long accountNumber);
    void calculateInterest();
}

