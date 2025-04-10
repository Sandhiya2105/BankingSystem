import java.util.Scanner;

public class AccountChecker {
    public static void main(String[] args) {
        String[] accountNumbers = {"1001", "1002", "1003"};
        double[] balances = {2500.50, 5600.00, 920.75};

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("Enter account number: ");
            String acc = sc.next();
            boolean found = false;

            for (int i = 0; i < accountNumbers.length; i++) {
                if (acc.equals(accountNumbers[i])) {
                    System.out.println("Balance: $" + balances[i]);
                    found = true;
                    break;
                }
            }

            if (found) {
                break;
            } else {
                System.out.println("Invalid account number. Try again.");
            }
        }

        sc.close();
    }
}
