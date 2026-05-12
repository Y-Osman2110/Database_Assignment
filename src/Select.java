import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Select {
    // ============single table selects
    public void findMemberByPhone(Connection connection, String phone) {
        String sql = "SELECT * FROM Member WHERE memberPhone = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, phone);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                System.out.println("Found Member~");
                int id = rs.getInt(1);
                String fname = rs.getString(2);
                String lname = rs.getString(3);
                String email = rs.getString(5);
                String address = rs.getString(6);

                System.err.println("Id: " + id + ", Name: " + fname + " " + lname + ", email: "
                        + email + ", address: " + address);
            } else
                System.out.println("Couldn't find member with phone number: " + phone);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // ------------------------------//
    public void findTrainersBySpecialty(Connection connection, String specialty) {
        String sql = "SELECT * FROM Trainer WHERE trainerSpecialtiy = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, specialty);

            ResultSet rs = statement.executeQuery();

            boolean found = false;
            while (rs.next()) {
                if (!found) {
                    System.out.println("Found Trainer(s)~");
                    found = true;
                }
                int id = rs.getInt(1);
                String fname = rs.getString(2);
                String lname = rs.getString(3);
                String duration = rs.getString(4);
                String desc = rs.getString(5);
                int member = rs.getInt(6);

                System.out.println("Id: " + id + ", Name: " + fname + " " + lname + ", duration: "
                        + duration + ", description: " + desc + ", member's id (that they coach): " + member);
            }

            if (!found)
                System.out.println("Couldn't find trainer with specialty: " + specialty);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    // ==============================
    // ==================multiple tables (joins)

    public void findMembersWithExpiredSubscriptions(Connection connection) {
        String sql = "SELECT m.Fname, m.Lname, m.memberPhone, m.memberEmail, m.memberAddress  FROM Member m Inner Join Subscription s ON "
                + "m.memberId = s.memberId WHERE s.EndDate < GETDATE();";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet rs = statement.executeQuery();
            boolean found = false;

            while (rs.next()) {
                if (!found) {
                    System.out.println("Found Member(s)~");
                    found = true;
                }

                String fname = rs.getString(1);
                String lname = rs.getString(2);
                String phone = rs.getString(3);
                String email = rs.getString(4);
                String address = rs.getString(5);

                System.out.println("Name: " + fname + " " + lname + ", phone: "
                        + phone + ", email: " + email + ", address: " + address);
            }
            if (!found)
                System.out.println("There's no member with expired subscriptions currently.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getTrainerSchedule(Connection connection) {
        String sql = "SELECT t.trainerFName, t.trainerLName, t.trainerSpecialtiy, s.sessionName, s.discipline, s.sessionCapacity, s.startTime, s.endTime, z.location AS zoneLocation, "
                + "COUNT(r.reservationId) AS bookedCapacity FROM Trainer t INNER JOIN Session s ON t.trainerId = s.trainerId INNER JOIN Zone z ON s.zoneNum = z.zoneNum"
                + " LEFT JOIN Reservation r ON s.sessionId = r.sessionId WHERE s.startTime > GETDATE() GROUP BY  t.trainerFName, t.trainerLName, t.trainerSpecialtiy, "
                + "s.sessionName, s.discipline, s.sessionCapacity, s.startTime, s.endTime, z.location ORDER BY s.startTime;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet rs = statement.executeQuery();
            boolean found = false;

            while (rs.next()) {
                if (!found) {
                    System.out.println("Trainers' schedules: ");
                    found = true;
                }

                String fname = rs.getString(1);
                String lname = rs.getString(2);
                String specialty = rs.getString(3);
                String sessionName = rs.getString(4);
                String discipline = rs.getString(5);
                int capacity = rs.getInt(6);
                java.sql.Time startTime = rs.getTime(7);
                java.sql.Time endTime = rs.getTime(8);
                String zoneLoc = rs.getString(9);
                int booked = rs.getInt(10);

                System.out.println("Trainer: " + fname + " " + lname + " (" + specialty + ")");
                System.out.println("  Session: " + sessionName + " | " + discipline);
                System.out.println("  Time: " + startTime + " - " + endTime);
                System.out.println("  Location: " + zoneLoc);
                System.out.println("  Capacity: " + booked + "/" + capacity + " booked");
                System.out.println();
            }
            if (!found)
                System.out.println("No upcoming trainer schedules found.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
