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
            System.out.println("1. Enroll new member                  [Insert]");
            System.out.println("2. Add health profile                 [Insert]");
            System.out.println("3. Find member by phone               [Select]");
            System.out.println("4. Find trainers by specialty         [Select]");
            System.out.println("5. Members with expired subscriptions [Select]");
            System.out.println("6. Get all trainers' schedules        [Select]");
            System.out.println("0. Exit");
            choice = input.nextLine();
            if (choice.equals("0")) {
                System.out.println("Exiting the system...");
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
                int a = input.nextInt();
                
                System.out.println("Enter height: ");
                int h = input.nextInt();
                
                System.out.println("Enter weight: ");
                int w = input.nextInt();
                
                System.out.println("Enter description: ");
                String d = input.nextLine();
                
                System.out.println("Enter member's id: ");
                int m = input.nextInt();
                
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
        }
    }
}

