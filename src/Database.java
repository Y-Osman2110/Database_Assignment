import java.sql.*;

public class Database {
    private Connection connection;
    private Insert insert = new Insert();
    private Select select = new Select();
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
        } catch(SQLException e){

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
}