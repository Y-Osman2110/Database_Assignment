
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String choice;
        while (true) {
            System.out.println("\n========================================");
            System.out.println("   FITNESS CLUB MANAGEMENT SYSTEM");
            System.out.println("========================================");
            System.out.println("0. Exit");
            System.out.println("1. Insert new member");
            System.out.println("2. Set member's health profile");
            System.out.println("3. Find member by phone number");
            System.out.println("4. Find trainers by specialty");
            System.out.println("5. Find members with expired subscriptions");
            System.out.println("6. Get all trainers' schedules");
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
                
                Insert.registerMember(f, l, p, e, a);
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
                
                Insert.setMemberHealthProfile(a, h, w, d, m);
            }
            else if(choice.equals("3")) {
                System.out.println("Enter phone number: ");
                String p = input.nextLine();

                Select.findMemeberByPhone(p);
            }
            else if(choice.equals("4")) {
                System.out.println("Enter specialty: ");
                String s = input.nextLine();

                Select.findMemeberByPhone(s);
            }
            else if(choice.equals("5")) {
                Select.findMembersWithExpiredSubscriptions();
            }
            else if(choice.equals("6")) {
                Select.getTrainerSchedule();
            }
        }
    }
}

