package repository;

import bean.*;
import service.IBankRepository;
import util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BankRepositoryImpl implements IBankRepository {

    @Override
    public void createAccount(Customer customer, long accNo, String accType, float balance) throws Exception {
        Connection conn = DBConnUtil.getConnection();

        String insertCustomer = "INSERT INTO customers (customer_id, first_name, last_name, email, phone_number, address) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement cs = conn.prepareStatement(insertCustomer);
        cs.setString(1, customer.getCustomerID());
        cs.setString(2, customer.getFirstName());
        cs.setString(3, customer.getLastName());
        cs.setString(4, customer.getEmailAddress());
        cs.setString(5, customer.getPhoneNumber());
        cs.setString(6, customer.getAddress());
        cs.executeUpdate();

        String insertAccount = "INSERT INTO accounts (account_id, account_type, balance, customer_id) VALUES (?, ?, ?, ?)";
        PreparedStatement as = conn.prepareStatement(insertAccount);
        as.setLong(1, accNo);
        as.setString(2, accType);
        as.setFloat(3, balance);
        as.setString(4, customer.getCustomerID());
        as.executeUpdate();

        conn.close();
    }

    @Override
    public List<Accounts> listAccounts() throws Exception {
        List<Accounts> list = new ArrayList<>();
        Connection conn = DBConnUtil.getConnection();

        String query = "SELECT a.*, c.first_name, c.last_name, c.email, c.phone_number, c.address, c.customer_id " +
                "FROM accounts a JOIN customers c ON a.customer_id = c.customer_id";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            Accounts acc = new Accounts();
            acc.setAccountNumber(String.valueOf(rs.getLong("account_id")));
            acc.setAccountType(rs.getString("account_type"));
            acc.setAccountBalance(rs.getDouble("balance"));

            Customer c = new Customer(
                    rs.getString("customer_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("phone_number"),
                    rs.getString("address")
            );

            acc.setCustomer(c);
            list.add(acc);
        }

        conn.close();
        return list;
    }

    @Override
    public void calculateInterest() throws Exception {
        Connection conn = DBConnUtil.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM accounts WHERE account_type='Savings'");

        while (rs.next()) {
            long accNo = rs.getLong("account_id");
            double balance = rs.getDouble("balance");
            double interest = balance * 0.045;
            double newBal = balance + interest;

            PreparedStatement ps = conn.prepareStatement("UPDATE accounts SET balance=? WHERE account_id=?");
            ps.setDouble(1, newBal);
            ps.setLong(2, accNo);
            ps.executeUpdate();
        }

        conn.close();
    }

    @Override
    public double getAccountBalance(long accountNumber) throws Exception {
        Connection conn = DBConnUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT balance FROM accounts WHERE account_id=?");
        ps.setLong(1, accountNumber);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getDouble("balance");
        }

        throw new Exception("Account not found.");
    }

    @Override
    public double deposit(long accountNumber, float amount) throws Exception {
        double newBalance = getAccountBalance(accountNumber) + amount;
        Connection conn = DBConnUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement("UPDATE accounts SET balance=? WHERE account_id=?");
        ps.setDouble(1, newBalance);
        ps.setLong(2, accountNumber);
        ps.executeUpdate();

        recordTransaction(accountNumber, "Deposit", amount);
        return newBalance;
    }

    @Override
    public double withdraw(long accountNumber, float amount) throws Exception {
        double balance = getAccountBalance(accountNumber);
        if (balance < amount) throw new Exception("Insufficient funds.");

        double newBalance = balance - amount;
        Connection conn = DBConnUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement("UPDATE accounts SET balance=? WHERE account_id=?");
        ps.setDouble(1, newBalance);
        ps.setLong(2, accountNumber);
        ps.executeUpdate();

        recordTransaction(accountNumber, "Withdrawal", amount);
        return newBalance;
    }

    @Override
    public void transfer(long fromAccount, long toAccount, float amount) throws Exception {
        withdraw(fromAccount, amount);
        deposit(toAccount, amount);
        recordTransaction(fromAccount, "Transfer", amount);
        recordTransaction(toAccount, "Transfer", amount);
    }

    private void recordTransaction(long accNo, String type, double amount) throws Exception {
        Connection conn = DBConnUtil.getConnection();
        String insertTxn = "INSERT INTO transactions (account_id, transaction_type, amount) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(insertTxn);
        ps.setLong(1, accNo);
        ps.setString(2, type);
        ps.setDouble(3, amount);
        ps.executeUpdate();
        conn.close();
    }

    @Override
    public Accounts getAccountDetails(long accountNumber) throws Exception {
        Connection conn = DBConnUtil.getConnection();

        String sql = "SELECT a.account_id, a.account_type, a.balance, " +
                "c.customer_id, c.first_name, c.last_name, c.email, c.phone_number, c.address " +
                "FROM accounts a JOIN customers c ON a.customer_id = c.customer_id " +
                "WHERE a.account_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, accountNumber);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Accounts acc = new Accounts();
            acc.setAccountNumber(String.valueOf(rs.getLong("account_id")));
            acc.setAccountType(rs.getString("account_type"));
            acc.setAccountBalance(rs.getDouble("balance"));

            Customer cust = new Customer(
                    rs.getString("customer_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("phone_number"),
                    rs.getString("address")
            );
            acc.setCustomer(cust);

            return acc;
        }

        throw new Exception("Account not found.");
    }

    @Override
    public List<Transaction> getTransactions(long accountNumber, Timestamp fromDate, Timestamp toDate) throws Exception {
        List<Transaction> transactions = new ArrayList<>();
        Connection conn = DBConnUtil.getConnection();
        String query = "SELECT * FROM transactions WHERE account_id = ? AND transaction_date BETWEEN ? AND ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setLong(1, accountNumber);
        ps.setTimestamp(2, fromDate);
        ps.setTimestamp(3, toDate);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Transaction t = new Transaction();
            t.setDescription(rs.getString("transaction_type"));
            t.setTransactionType(rs.getString("transaction_type"));
            t.setTransactionAmount(rs.getDouble("amount"));
            t.setDateTime(rs.getTimestamp("transaction_date").toLocalDateTime());
            transactions.add(t);
        }

        conn.close();
        return transactions;
    }
}

