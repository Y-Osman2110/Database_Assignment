package src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;

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

                System.err.println("Id: " + id + ", Name: " + fname + " " + lname + ", phone: "
                        + phone + ", email: " + email + ", address: " + address);
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
                String duration = rs.getString(5);
                String desc = rs.getString(6);

                System.out.println("Id: " + id + ", Name: " + fname + " " + lname + ", duration: "
                        + duration + ", description: " + desc);
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
        String sql = "SELECT m.memberFname, m.memberLname, m.memberPhone, m.memberEmail, m.memberAddress FROM Member m Inner Join Subscription s ON "
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
           + "COUNT(r.reservationId) AS bookedCapacity "
           + "FROM Trainer t "
           + "INNER JOIN Session s ON t.trainerId = s.trainerId "
           + "INNER JOIN Zone z ON s.zoneNum = z.zoneNum "
           + "LEFT JOIN Reservation r ON s.sessionId = r.sessionId "
           + "WHERE s.startTime > GETDATE() "
           + "GROUP BY t.trainerFName, t.trainerLName, t.trainerSpecialtiy, s.sessionName, s.discipline, s.sessionCapacity, s.startTime, s.endTime, z.location "
           + "ORDER BY s.startTime;";
        try (PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery()) {

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
                java.sql.Timestamp startTime = rs.getTimestamp(7);
                java.sql.Timestamp endTime = rs.getTimestamp(8);
                String zoneLoc = rs.getString(9);
                int booked = rs.getInt(10);

                System.out.println("Trainer: " + fname + " " + lname + " (" + specialty + ")");
                System.out.println("  Session: " + sessionName + " | " + discipline);

                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                System.out.println("  Time: " + startTime.toLocalDateTime().format(fmt) + " - " + endTime.toLocalDateTime().format(fmt));
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

    // the 6 inqueries
    private void executeAndPrint(Connection conn, String sql) {
        if (conn == null)
            return;
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    System.out.print(rs.getString(i) + (i < columnsNumber ? " | " : ""));
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    public void query1(Connection connection) {
        String sql = "SELECT TOP 1 s.discipline, COUNT(r.reservationId) AS Total " +
                "FROM Session s JOIN Reservation r ON s.sessionId = r.sessionId " +
                "GROUP BY s.discipline ORDER BY Total DESC;";
        executeAndPrint(connection, sql);
    }

    public void query2(Connection connection) {
        String sql = "SELECT s.sessionId, s.discipline, s.startTime FROM Session s " +
                "WHERE s.startTime >= DATEADD(MONTH, -1, GETDATE()) " +
                "AND s.sessionId NOT IN (SELECT DISTINCT sessionId FROM Reservation WHERE sessionId IS NOT NULL);";
        executeAndPrint(connection, sql);
    }

    public void query3(Connection connection) {
        String sql = "SELECT TOP 1 t.trainerFname, t.trainerLname, COUNT(DISTINCT r.memberId) AS Total " +
                "FROM Trainer t JOIN Session s ON t.trainerId = s.trainerId " +
                "JOIN Reservation r ON s.sessionId = r.sessionId " +
                "GROUP BY t.trainerId, t.trainerFname, t.trainerLname ORDER BY Total DESC;";
        executeAndPrint(connection, sql);
    }

    public void query4(Connection connection) {
        String sql = "SELECT m.memberFname, m.memberLname, m.memberEmail FROM Member m " +
                "JOIN Subscription s ON m.memberId = s.memberId " +
                "WHERE s.status = 'Active' AND m.memberId NOT IN " +
                "(SELECT memberId FROM [Check-in] WHERE timestamp >= DATEADD(MONTH, -1, GETDATE()));";
        executeAndPrint(connection, sql);
    }

    public void query5(Connection connection) {
        String sql = "SELECT z.zoneNum, s.discipline, s.startTime , s.endTime FROM Zone z " +
                "JOIN Session s ON z.zoneNum = s.zoneNum " +
                "WHERE z.timestamp >= DATEADD(MONTH, -1, GETDATE()) ORDER BY z.zoneNum;";
        executeAndPrint(connection, sql);
    }

    public void query6(Connection connection) {
        String sql = "SELECT m.memberFname + ' ' + m.memberLname AS FullName, COUNT(r.reservationId) AS Total " +
                "FROM Member m LEFT JOIN Reservation r ON m.memberId = r.memberId " +
                "GROUP BY m.memberId, m.memberFname, m.memberLname ORDER BY Total DESC;";
        executeAndPrint(connection, sql);
    }

}
