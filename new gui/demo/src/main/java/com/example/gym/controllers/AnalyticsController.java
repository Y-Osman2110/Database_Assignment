package com.example.gym.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import com.example.gym.Database;

public class AnalyticsController {

    @FXML private Label totalRevenueLabel;
    @FXML private Label growthRateLabel;
    @FXML private Label avgAttendanceLabel;
    @FXML private Label retentionRateLabel;
    @FXML private TableView<DisciplineRow> disciplineTable;
    @FXML private TableView<TrainerRow> trainerTable;
    @FXML private TableView<ObservableList<String>> zoneTable;

    private Database db;

    @FXML public void initialize() {
        db = new Database();
        setupTables();
    }

    private void setupTables() {
        TableColumn<DisciplineRow, String> discCol = new TableColumn<>("Discipline");
        discCol.setCellValueFactory(new PropertyValueFactory<>("discipline"));
        discCol.setPrefWidth(200);
        TableColumn<DisciplineRow, String> resCol = new TableColumn<>("Reservations");
        resCol.setCellValueFactory(new PropertyValueFactory<>("reservations"));
        resCol.setPrefWidth(150);
        TableColumn<DisciplineRow, String> trendCol = new TableColumn<>("Trend");
        trendCol.setCellValueFactory(new PropertyValueFactory<>("trend"));
        trendCol.setPrefWidth(100);
        disciplineTable.getColumns().addAll(discCol, resCol, trendCol);

        TableColumn<TrainerRow, String> nameCol = new TableColumn<>("Trainer");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(180);
        TableColumn<TrainerRow, String> memCol = new TableColumn<>("Members");
        memCol.setCellValueFactory(new PropertyValueFactory<>("members"));
        memCol.setPrefWidth(100);
        TableColumn<TrainerRow, String> sessCol = new TableColumn<>("Sessions");
        sessCol.setCellValueFactory(new PropertyValueFactory<>("sessions"));
        sessCol.setPrefWidth(100);
        TableColumn<TrainerRow, String> ratingCol = new TableColumn<>("Rating");
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
        ratingCol.setPrefWidth(100);
        trainerTable.getColumns().addAll(nameCol, memCol, sessCol, ratingCol);
    }

    @FXML
    private void loadAnalytics() {
        animateLabel(totalRevenueLabel, "$48,250");
        animateLabel(growthRateLabel, "+12.5%");
        animateLabel(avgAttendanceLabel, "142");
        animateLabel(retentionRateLabel, "87%");

        disciplineTable.setItems(FXCollections.observableArrayList(
            new DisciplineRow("CrossFit", "342", "📈"),
            new DisciplineRow("Yoga", "287", "📈"),
            new DisciplineRow("HIIT", "198", "📉"),
            new DisciplineRow("Pilates", "156", "📈"),
            new DisciplineRow("Boxing", "134", "➡️")
        ));

        trainerTable.setItems(FXCollections.observableArrayList(
            new TrainerRow("Mike Johnson", "45", "28", "⭐ 4.9"),
            new TrainerRow("Sarah Williams", "38", "24", "⭐ 4.8"),
            new TrainerRow("David Chen", "32", "22", "⭐ 4.7"),
            new TrainerRow("Emma Davis", "29", "20", "⭐ 4.9")
        ));

        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        java.io.PrintStream ps = new java.io.PrintStream(baos);
        java.io.PrintStream old = System.out; System.setOut(ps);
        db.sessionsPerZone();
        System.out.flush(); System.setOut(old);

        String result = baos.toString().trim();
        if (!result.isEmpty()) {
            zoneTable.getColumns().clear();
            String[] lines = result.split("\\n");
            if (lines.length > 0) {
                String[] parts = lines[0].split("\\|");
                for (int i = 0; i < parts.length; i++) {
                    final int idx = i;
                    TableColumn<ObservableList<String>, String> col = new TableColumn<>("Col " + (i+1));
                    col.setCellValueFactory(param -> new javafx.beans.property.SimpleStringProperty(param.getValue().get(idx)));
                    zoneTable.getColumns().add(col);
                }
                ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
                for (String line : lines) {
                    data.add(FXCollections.observableArrayList(line.split("\\|")));
                }
            }
        }
    }

    private void animateLabel(Label label, String finalText) {
        FadeTransition ft = new FadeTransition(Duration.millis(600), label);
        ft.setFromValue(0.0); ft.setToValue(1.0);
        ft.setOnFinished(e -> label.setText(finalText));
        ft.play();
    }

    public static class DisciplineRow {
        private String discipline, reservations, trend;
        public DisciplineRow(String d, String r, String t) { discipline = d; reservations = r; trend = t; }
        public String getDiscipline() { return discipline; }
        public void setDiscipline(String d) { discipline = d; }
        public String getReservations() { return reservations; }
        public void setReservations(String r) { reservations = r; }
        public String getTrend() { return trend; }
        public void setTrend(String t) { trend = t; }
    }

    public static class TrainerRow {
        private String name, members, sessions, rating;
        public TrainerRow(String n, String m, String s, String r) { name = n; members = m; sessions = s; rating = r; }
        public String getName() { return name; }
        public void setName(String n) { name = n; }
        public String getMembers() { return members; }
        public void setMembers(String m) { members = m; }
        public String getSessions() { return sessions; }
        public void setSessions(String s) { sessions = s; }
        public String getRating() { return rating; }
        public void setRating(String r) { rating = r; }
    }
}
