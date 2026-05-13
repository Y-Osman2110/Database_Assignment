package src;

public class Menu {
    public void mainMenu() {
        System.out.println("\n========================================");
        System.out.println("   FITNESS CLUB MANAGEMENT SYSTEM");
        System.out.println("========================================");
        System.out.println("1.  Enroll new member                  [Insert]");
        System.out.println("2.  Add health profile                 [Insert]");
        System.out.println("3.  Find member by phone               [Select]");
        System.out.println("4.  Find trainers by specialty         [Select]");
        System.out.println("5.  Members with expired subscriptions [Select]");
        System.out.println("6.  Get all trainers' schedules        [Select]");
        System.out.println("7.  Update a member's email            [Update]");
        System.out.println("8.  Update a member's subscription     [Update]");
        System.out.println("9.  Delete a reservation               [Delete]");
        System.out.println("10. Delete a Trainer                   [Delete]");
        System.out.println("0. Exit");
    }
}
