package src;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Database db = new Database();
        String choice;
        while (true) {
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
            choice = input.nextLine();
            if (choice.equals("0")) {
                System.out.println("Exiting the system...");
                db.close();
                input.close();
                break;
            }
            else if(choice.equals("1")) {
                System.out.println("Enter first name: ");
                String f = input.nextLine();
                
                System.out.println("Enter last name: ");
                String l = input.nextLine();
                
                System.out.println("Enter phone: ");
                String p = input.nextLine();
                
                System.out.println("Enter email: ");
                String e = input.nextLine();
                
                System.out.println("Enter address: ");
                String a = input.nextLine();
                
                db.addMember(f, l, p, e, a);
            }

            else if(choice.equals("2")) {
                System.out.println("Enter age: ");
                int a = input.nextInt(); input.nextLine();
                
                System.out.println("Enter height in CMs: ");
                int h = input.nextInt(); input.nextLine();
                
                System.out.println("Enter weight in KGs: ");
                int w = input.nextInt(); input.nextLine();
                
                System.out.println("Enter description: ");
                String d = input.nextLine(); 
                
                System.out.println("Enter member's id: ");
                int m = input.nextInt(); input.nextLine();
                
                db.setHealthProfile(a, h, w, d, m);
            }
            else if(choice.equals("3")) {
                System.out.println("Enter phone number: ");
                String p = input.nextLine();

                db.findMemberByPhone(p);
            }
            else if(choice.equals("4")) {
                System.out.println("Enter specialty: ");
                String s = input.nextLine();

                db.findATrainerBySpecialty(s);
            }
            else if(choice.equals("5")) {
                db.getMembersWithExpiredSubscriptions();
            }
            else if(choice.equals("6")) {
                db.getTrainerSchedule();
            }
            else if (choice.equals("7")) {
                System.out.println("Enter member id: ");
                int m = input.nextInt(); input.nextLine();
                
                System.out.println("Enter new email: ");
                String e = input.nextLine();

                db.updateMemberEmail(m, e);
            }
            else if (choice.equals("8")) {
                System.out.println("Enter subscription id: ");
                int s = input.nextInt(); input.nextLine();
                
                System.out.println("Enter new status (Active / Expired / Cancelled): ");
                String st = input.nextLine();

                db.updateSubscriptionStatus(s, st);
            }
            else if (choice.equals("9")) {
                System.out.println("Enter reservation id to delete: ");
                int r = input.nextInt(); input.nextLine();

                db.deleteReservation(r);
            }
            else if (choice.equals("10")) {
                System.out.println("Enter trainer id to delete: ");
                int t = input.nextInt(); input.nextLine();

                db.deleteTrainer(t);
            }
            else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

