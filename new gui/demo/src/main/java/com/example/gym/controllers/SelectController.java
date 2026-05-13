package com.example.gym.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.gym.Database;

public class SelectController {

    @FXML private TextField phoneSearchField;
    @FXML private TextArea phoneResultArea;
    @FXML private TextField specialtySearchField;
    @FXML private TextArea specialtyResultArea;
    @FXML private TableView<ObservableList<String>> resultsTable;

    private Database db;

    @FXML public void initialize() {
        db = new Database();
        resultsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    @FXML private void findMemberByPhone() {
        String phone = phoneSearchField.getText().trim();
        if (phone.isEmpty()) { phoneResultArea.setText("❌ Please enter a phone number!"); return; }

        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        java.io.PrintStream ps = new java.io.PrintStream(baos);
        java.io.PrintStream old = System.out; System.setOut(ps);
        db.findMemberByPhone(phone);
        System.out.flush(); System.setOut(old);

        String result = baos.toString().trim();
        phoneResultArea.setText(result.isEmpty() ? "❌ No member found with phone: " + phone : "✅ " + result);
    }

    @FXML private void findTrainerBySpecialty() {
        String specialty = specialtySearchField.getText().trim();
        if (specialty.isEmpty()) { specialtyResultArea.setText("❌ Please enter a specialty!"); return; }

        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        java.io.PrintStream ps = new java.io.PrintStream(baos);
        java.io.PrintStream old = System.out; System.setOut(ps);
        db.findATrainerBySpecialty(specialty);
        System.out.flush(); System.setOut(old);

        String result = baos.toString().trim();
        specialtyResultArea.setText(result.isEmpty() ? "❌ No trainers found with specialty: " + specialty : "✅ " + result);
    }

    @FXML private void getExpiredSubscriptions() { runQueryToTable(() -> db.getMembersWithExpiredSubscriptions(), "Expired Subscriptions"); }
    @FXML private void getTrainerSchedule() { runQueryToTable(() -> db.getTrainerSchedule(), "Trainer Schedule"); }
    @FXML private void getDisciplineMaxReservation() { runQueryToTable(() -> db.getDisciplineMaxReservation(), "Top Discipline"); }
    @FXML private void getSessionsNoReservations() { runQueryToTable(() -> db.sessionsWithNoReservations(), "Empty Sessions"); }
    @FXML private void getTopTrainer() { runQueryToTable(() -> db.trainerWithTheHighestMembers(), "Top Trainer"); }
    @FXML private void getInactiveActiveMembers() { runQueryToTable(() -> db.activeMembersWithNoCheckin(), "Inactive Members"); }
    @FXML private void getSessionsPerZone() { runQueryToTable(() -> db.sessionsPerZone(), "Sessions Per Zone"); }
    @FXML private void getMembersTotalReservations() { runQueryToTable(() -> db.membersTotalReservations(), "Total Reservations"); }

    private void runQueryToTable(Runnable query, String title) {
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        java.io.PrintStream ps = new java.io.PrintStream(baos);
        java.io.PrintStream old = System.out; System.setOut(ps);
        query.run();
        System.out.flush(); System.setOut(old);

        String result = baos.toString().trim();
        resultsTable.getColumns().clear();
        resultsTable.getItems().clear();

        if (result.isEmpty() || result.contains("No ") || result.contains("Couldn't")) {
            resultsTable.setPlaceholder(new Label("📭 " + title + ": No data found"));
            return;
        }

        String[] lines = result.split("\\n");
        if (lines.length > 0) {
            String[] firstLineParts = lines[0].split("\\|");
            for (int i = 0; i < firstLineParts.length; i++) {
                final int colIndex = i;
                TableColumn<ObservableList<String>, String> col = new TableColumn<>("Column " + (i + 1));
                col.setCellValueFactory(param -> new javafx.beans.property.SimpleStringProperty(param.getValue().get(colIndex)));
                col.setPrefWidth(150);
                resultsTable.getColumns().add(col);
            }
            for (String line : lines) {
                String[] parts = line.split("\\|");
                resultsTable.getItems().add(FXCollections.observableArrayList(parts));
            }
        }
    }
}
