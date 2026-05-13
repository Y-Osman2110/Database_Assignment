package src;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Database db = new Database();
        Menu menu = new Menu();
        String choice, subChoice;
        while (true) {
            menu.mainMenu();
            choice = input.nextLine();
            if (choice.equals("0")) {
                System.out.println("Exiting the system...");
                db.close();
                input.close();
                break;
            } else if (choice.equals("1")) {
                while (true) {
                    menu.insert();
                    subChoice = input.nextLine();
                    if (subChoice.equals("0")) {
                        break;
                    } else if (subChoice.equals("1")) {
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
                    } else if (subChoice.equals("2")) {
                        System.out.println("Enter age: ");
                        int a = input.nextInt();
                        input.nextLine();

                        System.out.println("Enter height in CMs: ");
                        int h = input.nextInt();
                        input.nextLine();

                        System.out.println("Enter weight in KGs: ");
                        int w = input.nextInt();
                        input.nextLine();

                        System.out.println("Enter description: ");
                        String d = input.nextLine();

                        System.out.println("Enter member's id: ");
                        int m = input.nextInt();
                        input.nextLine();

                        db.setHealthProfile(a, h, w, d, m);
                    }
                }

            }

            else if (choice.equals("2")) {
                while (true) {
                    menu.select();
                    subChoice = input.nextLine();
                    if (subChoice.equals("0")) {
                        break;
                    } else if (subChoice.equals("1")) {
                        System.out.println("Enter phone number: ");
                        String p = input.nextLine();
                        db.findMemberByPhone(p);
                    } else if (subChoice.equals("2")) {
                        System.out.println("Enter specialty: ");
                        String s = input.nextLine();
                        db.findATrainerBySpecialty(s);
                    } else if (subChoice.equals("3")) {
                        db.getMembersWithExpiredSubscriptions();
                    } else if (subChoice.equals("4")) {
                        db.getTrainerSchedule();
                    } else if (subChoice.equals("5")) {
                        db.getDisciplineMaxReservation();
                    } else if (subChoice.equals("6")) {
                        db.sessionsWithNoReservations();
                    } else if (subChoice.equals("7")) {
                        db.trainerWithTheHighestMembers();
                    } else if (subChoice.equals("8")) {
                        db.activeMembersWithNoCheckin();
                    } else if (subChoice.equals("9")) {
                        db.sessionsPerZone();
                    } else if (subChoice.equals("10")) {
                        db.membersTotalReservations();
                    }
                }
            } else if (choice.equals("3")) {
                while (true) {
                    menu.updateAndDelete();
                    subChoice = input.nextLine();
                    if (subChoice.equals("0")) {
                        break;
                    } else if (subChoice.equals("1")) {
                        System.out.println("Enter member id: ");
                        int m = input.nextInt();
                        input.nextLine();

                        System.out.println("Enter new email: ");
                        String e = input.nextLine();
                        db.updateMemberEmail(m, e);

                    } else if (subChoice.equals("2")) {
                        System.out.println("Enter subscription id: ");
                        int s = input.nextInt();
                        input.nextLine();
                        System.out.println("Enter new status (Active / Expired / Cancelled): ");
                        String st = input.nextLine();
                        db.updateSubscriptionStatus(s, st);
                    } else if (subChoice.equals("3")) {
                        System.out.println("Enter reservation id to delete: ");
                        int r = input.nextInt();
                        input.nextLine();
                        db.deleteReservation(r);
                    } else if (subChoice.equals("4")) {
                        System.out.println("Enter trainer id to delete: ");
                        int t = input.nextInt();
                        input.nextLine();
                        db.deleteTrainer(t);
                    }
                }
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
