package src;

import java.sql.*;


public class Database {
    private Connection connection;
    private Insert insert = new Insert();
    private Select select = new Select();
    private Update update = new Update();
    private Delete delete = new Delete();
    public Database() {
        try {
            String url = "jdbc:sqlserver://localhost:58927;"
                    + "databaseName=GYM;"
                    + "integratedSecurity=true;"
                    + "encrypt=false;";
            connection = DriverManager.getConnection(url);
            System.out.println("Database Connected successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            System.out.println(e.getMessage());
        }
    }
    public void close(){
        try{
            connection.close();
            System.out.println("Database connection closed successfully.");
        } catch(SQLException e){
            System.out.println("Failed to close the database connection.");
            System.out.println(e.getMessage());
        }
    }
    

    // insert
    public void addMember(String fName, String lName, String phone, String email, String address) {
        insert.registerMember(connection, fName, lName, phone, email, address);
    }

    public void setHealthProfile(int age, int height, int weight, String description, int memberId) {
        insert.setMemberHealthProfile(connection, age, height, weight, description, memberId);
    }
    // select
    public void findMemberByPhone(String phone) {
        select.findMemberByPhone(connection, phone);
    }
    public void findATrainerBySpecialty(String specialty) {
        select.findTrainersBySpecialty(connection, specialty);
    }
    public void getMembersWithExpiredSubscriptions() {
        select.findMembersWithExpiredSubscriptions(connection);
    }
    public void getTrainerSchedule() {
        select.getTrainerSchedule(connection);
    }
    // update 
    public void updateMemberEmail(int memberId, String newEmail) {
        update.updateMemberEmail(connection, memberId, newEmail);
    }
    public void updateSubscriptionStatus(int subscriptionId, String newStatus) {
        update.updateSubscriptionStatus(connection, subscriptionId, newStatus);
    }
    // delete
    public void deleteReservation(int reservationId) {
        delete.deleteReservation(connection, reservationId);
    }
    public void deleteTrainer(int trainerId) {
        delete.deleteTrainer(connection, trainerId);
    }
}