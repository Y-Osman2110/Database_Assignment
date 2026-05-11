
package src;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Database database = new Database();
        Scanner input = new Scanner(System.in);
        String choice;
        while (true) {
            System.out.println("\n========================================");
            System.out.println("   FITNESS CLUB MANAGEMENT SYSTEM");
            System.out.println("========================================");
            System.out.println("0. Exit");
            choice = input.nextLine();
            if (choice.equals("0")) {
                System.out.println("Exiting the system...");
                input.close();
                break;
            }
        }
    }
}
