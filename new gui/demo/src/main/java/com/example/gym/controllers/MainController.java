package com.example.gym.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

import java.io.IOException;

public class MainController {

    @FXML private StackPane contentContainer;
    @FXML private Label pageTitle;
    @FXML private Button btnDashboard;
    @FXML private Button btnInsert;
    @FXML private Button btnSelect;
    @FXML private Button btnAnalytics;
    @FXML private Button btnUpdateDelete;

    private Node dashboardView;
    private Node insertView;
    private Node selectView;
    private Node analyticsView;
    private Node updateDeleteView;

    @FXML
    public void initialize() {
        try {
            dashboardView = FXMLLoader.load(getClass().getResource("/com/example/gym/views/DashboardView.fxml"));
            insertView = FXMLLoader.load(getClass().getResource("/com/example/gym/views/InsertView.fxml"));
            selectView = FXMLLoader.load(getClass().getResource("/com/example/gym/views/SelectView.fxml"));
            analyticsView = FXMLLoader.load(getClass().getResource("/com/example/gym/views/AnalyticsView.fxml"));
            updateDeleteView = FXMLLoader.load(getClass().getResource("/com/example/gym/views/UpdateDeleteView.fxml"));

            contentContainer.getChildren().addAll(insertView, selectView, analyticsView, updateDeleteView);
            insertView.setVisible(false);
            selectView.setVisible(false);
            analyticsView.setVisible(false);
            updateDeleteView.setVisible(false);

            contentContainer.getChildren().add(0, dashboardView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void switchView(Node viewToShow, String title, Button activeButton) {
        btnDashboard.getStyleClass().remove("active");
        btnInsert.getStyleClass().remove("active");
        btnSelect.getStyleClass().remove("active");
        btnAnalytics.getStyleClass().remove("active");
        btnUpdateDelete.getStyleClass().remove("active");

        if (!activeButton.getStyleClass().contains("active")) {
            activeButton.getStyleClass().add("active");
        }

        pageTitle.setText(title);

        for (Node child : contentContainer.getChildren()) {
            child.setVisible(false);
        }
        viewToShow.setVisible(true);

        FadeTransition ft = new FadeTransition(Duration.millis(300), viewToShow);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }

    @FXML private void showDashboard() { switchView(dashboardView, "Dashboard", btnDashboard); }
    @FXML private void showInsert() { switchView(insertView, "Insert Operations", btnInsert); }
    @FXML private void showSelect() { switchView(selectView, "Search & Query", btnSelect); }
    @FXML private void showAnalytics() { switchView(analyticsView, "Analytics", btnAnalytics); }
    @FXML private void showUpdateDelete() { switchView(updateDeleteView, "Update & Delete", btnUpdateDelete); }

    @FXML private void closeWindow() {
        Stage stage = (Stage) pageTitle.getScene().getWindow();
        stage.close();
    }

    @FXML private void minimizeWindow() {
        Stage stage = (Stage) pageTitle.getScene().getWindow();
        stage.setIconified(true);
    }
}
