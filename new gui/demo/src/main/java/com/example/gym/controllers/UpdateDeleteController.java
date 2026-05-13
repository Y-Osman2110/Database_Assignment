package com.example.gym.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import com.example.gym.Database;

public class UpdateDeleteController {

    @FXML private TextField updateMemberIdField;
    @FXML private TextField newEmailField;
    @FXML private TextField updateSubIdField;
    @FXML private ComboBox<String> statusComboBox;
    @FXML private TextField deleteResIdField;
    @FXML private TextField deleteTrainerIdField;
    @FXML private VBox udNotificationBox;
    @FXML private Label udNotificationLabel;

    private Database db;

    @FXML public void initialize() {
        db = new Database();
        statusComboBox.getItems().addAll("Active", "Expired", "Cancelled");
        statusComboBox.setPromptText("Select status...");
    }

    @FXML
    private void updateMemberEmail() {
        try {
            int memberId = Integer.parseInt(updateMemberIdField.getText().trim());
            String newEmail = newEmailField.getText().trim();
            if (newEmail.isEmpty()) { showNotification("❌ Please enter a new email address!", false); return; }
            db.updateMemberEmail(memberId, newEmail);
            showNotification("✅ Member email updated successfully! 📧", true);
            updateMemberIdField.clear(); newEmailField.clear();
        } catch (NumberFormatException e) { showNotification("❌ Please enter a valid Member ID!", false);
        } catch (Exception e) { showNotification("❌ Error: " + e.getMessage(), false); }
    }

    @FXML
    private void updateSubscriptionStatus() {
        try {
            int subId = Integer.parseInt(updateSubIdField.getText().trim());
            String status = statusComboBox.getValue();
            if (status == null || status.isEmpty()) { showNotification("❌ Please select a status!", false); return; }
            db.updateSubscriptionStatus(subId, status);
            showNotification("✅ Subscription status updated to: " + status + " 🔄", true);
            updateSubIdField.clear(); statusComboBox.setValue(null);
        } catch (NumberFormatException e) { showNotification("❌ Please enter a valid Subscription ID!", false);
        } catch (Exception e) { showNotification("❌ Error: " + e.getMessage(), false); }
    }

    @FXML
    private void deleteReservation() {
        try {
            int resId = Integer.parseInt(deleteResIdField.getText().trim());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Delete Reservation #" + resId);
            alert.setContentText("This action cannot be undone. Are you sure?");
            if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                db.deleteReservation(resId);
                showNotification("✅ Reservation #" + resId + " deleted successfully! 🗑️", true);
                deleteResIdField.clear();
            }
        } catch (NumberFormatException e) { showNotification("❌ Please enter a valid Reservation ID!", false);
        } catch (Exception e) { showNotification("❌ Error: " + e.getMessage(), false); }
    }

    @FXML
    private void deleteTrainer() {
        try {
            int trainerId = Integer.parseInt(deleteTrainerIdField.getText().trim());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Delete Trainer #" + trainerId);
            alert.setContentText("This will permanently remove the trainer. Are you sure?");
            if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                db.deleteTrainer(trainerId);
                showNotification("✅ Trainer #" + trainerId + " deleted successfully! 🗑️", true);
                deleteTrainerIdField.clear();
            }
        } catch (NumberFormatException e) { showNotification("❌ Please enter a valid Trainer ID!", false);
        } catch (Exception e) { showNotification("❌ Error: " + e.getMessage(), false); }
    }

    private void showNotification(String message, boolean isSuccess) {
        udNotificationLabel.setText(message);
        udNotificationBox.getStyleClass().clear();
        udNotificationBox.getStyleClass().add(isSuccess ? "notification-success" : "notification-error");
        udNotificationBox.setVisible(true); udNotificationBox.setManaged(true);
        FadeTransition ft = new FadeTransition(Duration.millis(300), udNotificationBox);
        ft.setFromValue(0.0); ft.setToValue(1.0); ft.play();
        new Thread(() -> {
            try {
                Thread.sleep(4000);
                javafx.application.Platform.runLater(() -> {
                    FadeTransition hide = new FadeTransition(Duration.millis(500), udNotificationBox);
                    hide.setFromValue(1.0); hide.setToValue(0.0);
                    hide.setOnFinished(e -> { udNotificationBox.setVisible(false); udNotificationBox.setManaged(false); });
                    hide.play();
                });
            } catch (InterruptedException e) { e.printStackTrace(); }
        }).start();
    }
}
