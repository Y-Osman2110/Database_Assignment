
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Insert {

    Insert() {};
    
    public static void registerMember(String fName, String lName, String phone, String email, String address) {
        String sql = "INSERT INTO Member VALUES (?, ?, ?, ?, ?)";

        try(Connection conn = Database.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, fName);
                statement.setString(2, lName);
                statement.setString(3, phone);
                statement.setString(4, email);
                statement.setString(5, address);

                statement.executeUpdate();
                System.out.println("Inserted member to database~ ");
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void setMemberHealthProfile(int age, int height, int weight, String description, int memberId) {
        String sql = "INSERT INTO [Health Profile] VALUES (?, ?, ?, ?, ?)";

        try(Connection conn = Database.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, age);
                statement.setInt(2, height);
                statement.setInt(3, weight);
                statement.setString(4, description);
                statement.setInt(5, memberId);

                statement.executeUpdate();
                System.out.println("Set member health profile~ ");
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
