package src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
// ══════════════════════════════════════════════
//                DELETE #1
//  Delete a Reservation by reservationId
// ══════════════════════════════════════════════

public class Delete {
    public  void deleteReservation(Connection conn, int reservationId) {
        String sql = "DELETE FROM Reservation WHERE reservationId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reservationId);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Reservation #" + reservationId + " deleted successfully.");
            } else {
                System.out.println("No reservation found with ID " + reservationId + ".");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting reservation: " + e.getMessage());
        }
    }

    // ══════════════════════════════════════════════
    //                DELETE #2
    //  Delete a Trainer by trainerID
    // ══════════════════════════════════════════════
    public  void deleteTrainer(Connection conn, int trainerId) {
        String sql = "DELETE FROM Trainer WHERE trainerId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, trainerId);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Trainer #" + trainerId + " deleted successfully.");
            } else {
                System.out.println("No trainer found with ID " + trainerId + ".");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting trainer: " + e.getMessage());
        }
    }
}
