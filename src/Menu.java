package src;

public class Menu {
    public void mainMenu(){
        System.out.println("\n========================================");
        System.out.println("   GYM MANAGEMENT SYSTEM");
        System.out.println("========================================");
        System.out.println("1. Insert Operations");
        System.out.println("2. Select Operations");
        System.out.println("3. Update & Delete");
        System.out.println("0. Go back");
    }
    public void insert(){
        System.out.println("\n========================================");
        System.out.println("   INERT INTO THE DATABASE");
        System.out.println("========================================");
        System.out.println("1. Enroll new member");
        System.out.println("2. Add health profile");
        System.out.println("0. Go back");

    }
    public void select(){
        System.out.println("\n========================================");
        System.out.println("   SELECT FROM THE DATABASE");
        System.out.println("========================================");
        System.out.println("1.  Find member by phone");
        System.out.println("2.  Find trainers by specialty");
        System.out.println("3.  Get members with expired subscriptions");
        System.out.println("4.  Get all trainers' schedules");
        System.out.println("5.  Get discipline with max reservations");
        System.out.println("6.  Get sessions with no reservations in the last month");
        System.out.println("7.  Get the trainer with highest member attendance");
        System.out.println("8.  Get Active members with no check-in last month");
        System.out.println("9.  Get Sessions per zone in the last month");
        System.out.println("10. Get Members total reservations");
        System.out.println("0.  Go back");

    }
    public void updateAndDelete(){
        System.out.println("\n========================================");
        System.out.println("   UPDATE OR DELETE FROM THE DATABASE");
        System.out.println("========================================");
        System.out.println("1. Update a member's email            [Update]");
        System.out.println("2. Update a member's subscription     [Update]");
        System.out.println("3. Delete a reservation               [Delete]");
        System.out.println("4. Delete a Trainer                   [Delete]");
        System.out.println("0. Go back");

    }
}
