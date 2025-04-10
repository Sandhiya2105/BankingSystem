import java.util.Scanner;

public class LoanEligibility {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter your credit score: ");
        int creditScore = sc.nextInt();

        System.out.print("Enter your annual income: ");
        double annualIncome = sc.nextDouble();

        if (creditScore > 700 && annualIncome >= 50000) {
            System.out.println("You are eligible for a loan.");
        } else {
            System.out.println("Sorry, you are not eligible for a loan.");
        }

        sc.close();
    }
}

