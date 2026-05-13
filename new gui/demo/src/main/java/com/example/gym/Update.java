package com.example.gym;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Update {
    // ══════════════════════════════════════════════
    // UPDATE #1
    // Update a Member's email by memberID
    // ══════════════════════════════════════════════

    public  void updateMemberEmail(Connection conn, int memberId, String newEmail) {
        String sql = "UPDATE Member SET memberEmail = ? WHERE memberId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newEmail);
            stmt.setInt(2, memberId);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Member #" + memberId + " email updated to: " + newEmail);
            } else {
                System.out.println("No member found with ID " + memberId + ".");
            }
        } catch (SQLException e) {
            System.out.println("Error updating member email: " + e.getMessage());
        }
    }

    // ══════════════════════════════════════════════
    // UPDATE #2
    // Update a Subscription's status by subscriptionID
    // Valid statuses: 'Active', 'Expired', 'Cancelled'
    // ══════════════════════════════════════════════
    public  void updateSubscriptionStatus(Connection conn, int subscriptionId, String newStatus) {
        String sql = "UPDATE Subscription SET status = ? WHERE subscriptionId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, subscriptionId);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Subscription #" + subscriptionId + " status updated to: " + newStatus);
            } else {
                System.out.println("No subscription found with ID " + subscriptionId + ".");
            }
        } catch (SQLException e) {
            System.out.println("Error updating subscription status: " + e.getMessage());
        }
    }
}
