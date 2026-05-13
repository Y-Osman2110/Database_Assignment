package com.example.gym.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import com.example.gym.Database;

public class InsertController {

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private TextArea addressField;
    @FXML private TextField healthMemberIdField;
    @FXML private TextField ageField;
    @FXML private TextField heightField;
    @FXML private TextField weightField;
    @FXML private TextArea healthDescField;
    @FXML private VBox notificationBox;
    @FXML private Label notificationLabel;

    private Database db;

    @FXML public void initialize() { db = new Database(); }

    @FXML
    private void enrollMember() {
        try {
            String fName = firstNameField.getText().trim();
            String lName = lastNameField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();
            String address = addressField.getText().trim();

            if (fName.isEmpty() || lName.isEmpty() || phone.isEmpty()) {
                showNotification("❌ Please fill in all required fields!", false);
                return;
            }

            db.addMember(fName, lName, phone, email, address);
            showNotification("✅ Member enrolled successfully! Welcome to the gym! 💪", true);
            clearMemberForm();

        } catch (Exception e) {
            showNotification("❌ Error: " + e.getMessage(), false);
        }
    }

    @FXML
    private void saveHealthProfile() {
        try {
            int memberId = Integer.parseInt(healthMemberIdField.getText().trim());
            int age = Integer.parseInt(ageField.getText().trim());
            int height = Integer.parseInt(heightField.getText().trim());
            int weight = Integer.parseInt(weightField.getText().trim());
            String desc = healthDescField.getText().trim();

            db.setHealthProfile(age, height, weight, desc, memberId);
            showNotification("✅ Health profile saved successfully! ❤️", true);
            clearHealthForm();

        } catch (NumberFormatException e) {
            showNotification("❌ Please enter valid numbers for ID, Age, Height, and Weight!", false);
        } catch (Exception e) {
            showNotification("❌ Error: " + e.getMessage(), false);
        }
    }

    @FXML private void clearMemberForm() {
        firstNameField.clear(); lastNameField.clear(); phoneField.clear();
        emailField.clear(); addressField.clear();
    }

    @FXML private void clearHealthForm() {
        healthMemberIdField.clear(); ageField.clear(); heightField.clear();
        weightField.clear(); healthDescField.clear();
    }

    private void showNotification(String message, boolean isSuccess) {
        notificationLabel.setText(message);
        notificationBox.getStyleClass().clear();
        notificationBox.getStyleClass().add(isSuccess ? "notification-success" : "notification-error");
        notificationBox.setVisible(true);
        notificationBox.setManaged(true);

        FadeTransition ft = new FadeTransition(Duration.millis(300), notificationBox);
        ft.setFromValue(0.0); ft.setToValue(1.0); ft.play();

        new Thread(() -> {
            try {
                Thread.sleep(4000);
                javafx.application.Platform.runLater(() -> {
                    FadeTransition hide = new FadeTransition(Duration.millis(500), notificationBox);
                    hide.setFromValue(1.0); hide.setToValue(0.0);
                    hide.setOnFinished(e -> { notificationBox.setVisible(false); notificationBox.setManaged(false); });
                    hide.play();
                });
            } catch (InterruptedException e) { e.printStackTrace(); }
        }).start();
    }
}
