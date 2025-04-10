import java.util.ArrayList;
import java.util.Scanner;

public class TransactionHistory {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> transactions = new ArrayList<>();

        while (true) {
            System.out.print("Enter transaction type (deposit/withdraw/exit): ");
            String type = sc.next().toLowerCase();

            if (type.equals("exit")) break;

            if (type.equals("deposit") || type.equals("withdraw")) {
                System.out.print("Enter amount: ");
                double amt = sc.nextDouble();
                transactions.add(type + " of $" + amt);
            } else {
                System.out.println("Invalid transaction type.");
            }
        }

        System.out.println("\n Transaction History:");
        for (String t : transactions) {
            System.out.println("- " + t);
        }

        sc.close();
    }
}

