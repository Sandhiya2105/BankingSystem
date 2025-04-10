package bean;

import java.time.LocalDateTime;

public class Transaction {
    private Accounts account;
    private String description;
    private LocalDateTime dateTime;
    private String transactionType;
    private double transactionAmount;


    public Transaction() {
    }


    public Transaction(Accounts account, String description, String transactionType, double transactionAmount) {
        this.account = account;
        this.description = description;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.dateTime = LocalDateTime.now();
    }

    public Accounts getAccount() { return account; }
    public void setAccount(Accounts account) { this.account = account; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    public double getTransactionAmount() { return transactionAmount; }
    public void setTransactionAmount(double transactionAmount) { this.transactionAmount = transactionAmount; }

    @Override
    public String toString() {
        return "Transaction [" + transactionType + " of ₹" + transactionAmount +
                " on " + dateTime + "]";
    }
}



