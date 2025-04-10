import java.util.Scanner;

public class PasswordValidator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter password: ");
        String pwd = sc.next();

        boolean hasUpper = false, hasDigit = false;

        if (pwd.length() >= 8) {
            for (char ch : pwd.toCharArray()) {
                if (Character.isUpperCase(ch)) hasUpper = true;
                if (Character.isDigit(ch)) hasDigit = true;
            }

            if (hasUpper && hasDigit) {
                System.out.println("Valid password.");
            } else {
                System.out.println("Must include uppercase and digit.");
            }
        } else {
            System.out.println("Password must be at least 8 characters.");
        }

        sc.close();
    }
}

