package com.example.gym.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;
import com.example.gym.Database;

public class DashboardController {

    @FXML private Label totalMembersLabel;
    @FXML private Label activeSubsLabel;
    @FXML private Label totalTrainersLabel;
    @FXML private Label todaySessionsLabel;
    @FXML private Label expiredCountLabel;
    @FXML private Label topDisciplineLabel;
    @FXML private Label bestTrainerLabel;

    private Database db;

    @FXML
    public void initialize() {
        db = new Database();

        animateLabel(totalMembersLabel, "1,247");
        animateLabel(activeSubsLabel, "892");
        animateLabel(totalTrainersLabel, "34");
        animateLabel(todaySessionsLabel, "56");
        animateLabel(expiredCountLabel, "23 members");
        animateLabel(topDisciplineLabel, "CrossFit");
        animateLabel(bestTrainerLabel, "Mike Johnson");
    }

    private void animateLabel(Label label, String finalText) {
        label.setText("0");
        FadeTransition ft = new FadeTransition(Duration.millis(800), label);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setOnFinished(e -> label.setText(finalText));
        ft.play();

        ScaleTransition st = new ScaleTransition(Duration.millis(500), label);
        st.setFromX(0.5);
        st.setFromY(0.5);
        st.setToX(1.0);
        st.setToY(1.0);
        st.play();
    }

    @FXML private void quickAddMember() {}
    @FXML private void quickFindMember() {}
    @FXML private void quickViewAnalytics() {}
    @FXML private void quickUpdateSub() {}
}
