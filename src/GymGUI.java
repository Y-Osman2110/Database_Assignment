package src;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GymGUI extends Application {

    private Database db = new Database();
    private TextArea outputArea;

    @Override
    public void start(Stage primaryStage) {
        // Connect to database (this may take a moment)
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefHeight(300);

        // Redirect System.out to the TextArea
        ConsoleRedirector.redirect(outputArea);

        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(
                createInsertTab(),
                createSelectTab(),
                createUpdateDeleteTab());

        BorderPane root = new BorderPane();
        root.setCenter(tabPane);
        root.setBottom(outputArea);
        BorderPane.setMargin(outputArea, new Insets(10));

        Scene scene = new Scene(root, 900, 700);
        primaryStage.setTitle("GYM Management System");
        primaryStage.setScene(scene);
        primaryStage.show();

        System.out.println("✅ GUI ready. Database initialized? " + (db != null));
    }

    private Tab createInsertTab() {
        Tab tab = new Tab("Insert");
        tab.setClosable(false);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15));

        // Add Member
        Label lblMember = new Label("Add Member:");
        TextField fName = new TextField();
        fName.setPromptText("First name");
        TextField lName = new TextField();
        lName.setPromptText("Last name");
        TextField phone = new TextField();
        phone.setPromptText("Phone");
        TextField email = new TextField();
        email.setPromptText("Email");
        TextField address = new TextField();
        address.setPromptText("Address");
        Button btnAddMember = new Button("Register Member");

        btnAddMember.setOnAction(e -> {
            db.addMember(fName.getText(), lName.getText(),
                    phone.getText(), email.getText(), address.getText());
        });

        // Health Profile
        Label lblHealth = new Label("Health Profile:");
        TextField age = new TextField();
        age.setPromptText("Age");
        TextField height = new TextField();
        height.setPromptText("Height (cm)");
        TextField weight = new TextField();
        weight.setPromptText("Weight (kg)");
        TextField desc = new TextField();
        desc.setPromptText("Description");
        TextField memberId = new TextField();
        memberId.setPromptText("Member ID");
        Button btnHealth = new Button("Set Health Profile");

        btnHealth.setOnAction(e -> {
            try {
                int a = Integer.parseInt(age.getText());
                int h = Integer.parseInt(height.getText());
                int w = Integer.parseInt(weight.getText());
                int m = Integer.parseInt(memberId.getText());
                db.setHealthProfile(a, h, w, desc.getText(), m);
            } catch (NumberFormatException ex) {
                System.out.println("❌ Invalid number input: " + ex.getMessage());
            }
        });

        grid.add(lblMember, 0, 0);
        grid.add(fName, 1, 0);
        grid.add(lName, 2, 0);
        grid.add(phone, 3, 0);
        grid.add(email, 4, 0);
        grid.add(address, 5, 0);
        grid.add(btnAddMember, 6, 0);

        grid.add(lblHealth, 0, 1);
        grid.add(age, 1, 1);
        grid.add(height, 2, 1);
        grid.add(weight, 3, 1);
        grid.add(desc, 4, 1);
        grid.add(memberId, 5, 1);
        grid.add(btnHealth, 6, 1);

        tab.setContent(grid);
        return tab;
    }

    private Tab createSelectTab() {
        Tab tab = new Tab("Select");
        tab.setClosable(false);

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(15));

        // row1: member by phone
        HBox row1 = new HBox(5);
        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone");
        Button btnFindMember = new Button("Find Member by Phone");
        btnFindMember.setOnAction(e -> db.findMemberByPhone(phoneField.getText()));
        row1.getChildren().addAll(new Label("Phone:"), phoneField, btnFindMember);

        // row2: trainer by specialty
        HBox row2 = new HBox(5);
        TextField specField = new TextField();
        specField.setPromptText("Specialty");
        Button btnFindTrainer = new Button("Find Trainer by Specialty");
        btnFindTrainer.setOnAction(e -> db.findATrainerBySpecialty(specField.getText()));
        row2.getChildren().addAll(new Label("Specialty:"), specField, btnFindTrainer);

        // simple buttons
        Button btnExpired = new Button("Members with Expired Subscriptions");
        btnExpired.setOnAction(e -> db.getMembersWithExpiredSubscriptions());

        Button btnSchedule = new Button("Trainer Schedule");
        btnSchedule.setOnAction(e -> db.getTrainerSchedule());

        Button btnQuery1 = new Button("Discipline with Max Reservations");
        btnQuery1.setOnAction(e -> db.getDisciplineMaxReservation());

        Button btnQuery2 = new Button("Sessions with No Reservations (last month)");
        btnQuery2.setOnAction(e -> db.sessionsWithNoReservations());

        Button btnQuery3 = new Button("Trainer with Highest Member Attendance");
        btnQuery3.setOnAction(e -> db.trainerWithTheHighestMembers());

        Button btnQuery4 = new Button("Active Members with No Check-in (last month)");
        btnQuery4.setOnAction(e -> db.activeMembersWithNoCheckin());

        Button btnQuery5 = new Button("Sessions per Zone (last month)");
        btnQuery5.setOnAction(e -> db.sessionsPerZone());

        Button btnQuery6 = new Button("Members Total Reservations");
        btnQuery6.setOnAction(e -> db.membersTotalReservations());

        vbox.getChildren().addAll(row1, row2,
                btnExpired, btnSchedule, btnQuery1, btnQuery2, btnQuery3,
                btnQuery4, btnQuery5, btnQuery6);

        tab.setContent(vbox);
        return tab;
    }

    private Tab createUpdateDeleteTab() {
        Tab tab = new Tab("Update / Delete");
        tab.setClosable(false);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15));

        // Update email
        TextField updateMemberId = new TextField();
        updateMemberId.setPromptText("Member ID");
        TextField newEmail = new TextField();
        newEmail.setPromptText("New Email");
        Button btnUpdateEmail = new Button("Update Email");
        btnUpdateEmail.setOnAction(e -> {
            try {
                int id = Integer.parseInt(updateMemberId.getText());
                db.updateMemberEmail(id, newEmail.getText());
            } catch (NumberFormatException ex) {
                System.out.println("❌ Invalid Member ID");
            }
        });

        // Update subscription status
        TextField subId = new TextField();
        subId.setPromptText("Subscription ID");
        TextField newStatus = new TextField();
        newStatus.setPromptText("Active/Expired/Cancelled");
        Button btnUpdateSub = new Button("Update Status");
        btnUpdateSub.setOnAction(e -> {
            try {
                int id = Integer.parseInt(subId.getText());
                db.updateSubscriptionStatus(id, newStatus.getText());
            } catch (NumberFormatException ex) {
                System.out.println("❌ Invalid Subscription ID");
            }
        });

        // Delete reservation
        TextField resId = new TextField();
        resId.setPromptText("Reservation ID");
        Button btnDelRes = new Button("Delete Reservation");
        btnDelRes.setOnAction(e -> {
            try {
                int id = Integer.parseInt(resId.getText());
                db.deleteReservation(id);
            } catch (NumberFormatException ex) {
                System.out.println("❌ Invalid Reservation ID");
            }
        });

        // Delete trainer
        TextField trainerId = new TextField();
        trainerId.setPromptText("Trainer ID");
        Button btnDelTrainer = new Button("Delete Trainer");
        btnDelTrainer.setOnAction(e -> {
            try {
                int id = Integer.parseInt(trainerId.getText());
                db.deleteTrainer(id);
            } catch (NumberFormatException ex) {
                System.out.println("❌ Invalid Trainer ID");
            }
        });

        grid.add(new Label("Update Member Email:"), 0, 0);
        grid.add(updateMemberId, 1, 0);
        grid.add(newEmail, 2, 0);
        grid.add(btnUpdateEmail, 3, 0);

        grid.add(new Label("Update Subscription:"), 0, 1);
        grid.add(subId, 1, 1);
        grid.add(newStatus, 2, 1);
        grid.add(btnUpdateSub, 3, 1);

        grid.add(new Label("Delete Reservation:"), 0, 2);
        grid.add(resId, 1, 2);
        grid.add(btnDelRes, 3, 2);

        grid.add(new Label("Delete Trainer:"), 0, 3);
        grid.add(trainerId, 1, 3);
        grid.add(btnDelTrainer, 3, 3);

        tab.setContent(grid);
        return tab;
    }

    @Override
    public void stop() {
        if (db != null)
            db.close();
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}