import java.util.Scanner;

public class CompoundInterest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of customers: ");
        int n = sc.nextInt();

        for (int i = 1; i <= n; i++) {
            System.out.println("\nbean.Customer " + i);
            System.out.print("Initial balance: ");
            double balance = sc.nextDouble();
            System.out.print("Interest rate (%): ");
            double rate = sc.nextDouble();
            System.out.print("Years: ");
            int years = sc.nextInt();

            double futureBalance = balance * Math.pow(1 + rate / 100, years);
            System.out.printf("Future Balance: %.2f\n", futureBalance);
        }

        sc.close();
    }
}

