package src;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.function.Consumer;

public class GymGUI extends Application {

    private Database db;
    private TextArea outputArea;
    private TabPane tabPane;

    @Override
    public void start(Stage primaryStage) {
        db = new Database();
        outputArea = createConsoleArea();

        tabPane = createMainTabs();

        BorderPane root = new BorderPane();
        root.getStyleClass().add("app-root");
        root.setTop(createHeader());
        root.setLeft(createSidebar());

        VBox centerShell = new VBox(18, createOverviewStrip(), tabPane);
        centerShell.setPadding(new Insets(22));
        VBox.setVgrow(tabPane, Priority.ALWAYS);
        root.setCenter(centerShell);

        root.setBottom(createConsolePanel());

        Scene scene = new Scene(root, 1420, 940);
        URL css = getClass().getResource("/src/style.css");
        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        }

        primaryStage.setTitle("Gym Management Studio");
        primaryStage.setScene(scene);
        primaryStage.show();

        ConsoleRedirector.redirect(outputArea);
        System.out.println("GUI ready. Database connected: " + (db != null));
    }

    private Node createHeader() {
        VBox header = new VBox(10);
        header.getStyleClass().add("hero-bar");
        header.setPadding(new Insets(22, 28, 22, 28));

        HBox titleRow = new HBox(18);
        titleRow.setAlignment(Pos.CENTER_LEFT);

        VBox titleBlock = new VBox(4);
        Label titleLabel = new Label("Gym Management Studio");
        titleLabel.getStyleClass().add("hero-title");
        Label subtitle = new Label("A cleaner control center for member operations, reporting, and maintenance.");
        subtitle.getStyleClass().add("hero-subtitle");
        titleBlock.getChildren().addAll(titleLabel, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label liveBadge = new Label("Database online");
        liveBadge.getStyleClass().add("status-chip");

        titleRow.getChildren().addAll(titleBlock, spacer, liveBadge);

        HBox metaRow = new HBox(12);
        metaRow.getChildren().addAll(
                createBadge("Insert", "Member onboarding and health profiles"),
                createBadge("Search", "Lookup and reporting tools"),
                createBadge("Manage", "Updates and deletions"));

        header.getChildren().addAll(titleRow, metaRow);
        return header;
    }

    private VBox createSidebar() {
        VBox sidebar = new VBox(16);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(280);

        VBox intro = new VBox(8);
        intro.getStyleClass().add("sidebar-card");
        Label introTitle = new Label("Workspace");
        introTitle.getStyleClass().add("sidebar-title");
        Label introText = new Label("Navigate the major workflows without losing the current console output.");
        introText.getStyleClass().add("sidebar-text");
        introText.setWrapText(true);
        intro.getChildren().addAll(introTitle, introText);

        VBox nav = new VBox(10);
        nav.getStyleClass().add("sidebar-card");
        Label navTitle = new Label("Quick Navigation");
        navTitle.getStyleClass().add("sidebar-title");

        Button insertButton = createNavButton("Insert", 0);
        Button searchButton = createNavButton("Search", 1);
        Button manageButton = createNavButton("Manage", 2);

        nav.getChildren().addAll(navTitle, insertButton, searchButton, manageButton);

        VBox tips = new VBox(8);
        tips.getStyleClass().add("sidebar-card");
        Label tipsTitle = new Label("Usage Notes");
        tipsTitle.getStyleClass().add("sidebar-title");
        Label tip1 = new Label("Inputs clear after successful actions.");
        Label tip2 = new Label("Console output stays docked at the bottom.");
        Label tip3 = new Label("No database logic was changed.");
        tip1.getStyleClass().add("sidebar-text");
        tip2.getStyleClass().add("sidebar-text");
        tip3.getStyleClass().add("sidebar-text");
        tips.getChildren().addAll(tipsTitle, tip1, tip2, tip3);

        sidebar.getChildren().addAll(intro, nav, tips);
        return sidebar;
    }

    private Node createOverviewStrip() {
        HBox strip = new HBox(14);
        strip.getChildren().addAll(
                createOverviewCard("Member Onboarding",
                        "Register a new member and attach a health profile in a single flow."),
                createOverviewCard("Search & Reports", "Run lookups and operational reports with one click."),
                createOverviewCard("Update & Cleanup", "Edit member data and remove records from dedicated controls."));
        return strip;
    }

    private VBox createOverviewCard(String title, String text) {
        VBox card = new VBox(8);
        card.getStyleClass().add("overview-card");
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("overview-card-title");
        Label body = new Label(text);
        body.getStyleClass().add("overview-card-text");
        body.setWrapText(true);
        card.getChildren().addAll(titleLabel, body);
        HBox.setHgrow(card, Priority.ALWAYS);
        return card;
    }

    private TabPane createMainTabs() {
        TabPane tabs = new TabPane();
        tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabs.getTabs().addAll(createInsertTab(), createSelectTab(), createUpdateDeleteTab());
        return tabs;
    }

    private VBox createConsolePanel() {
        VBox consolePanel = new VBox(10);
        consolePanel.getStyleClass().add("console-panel");
        consolePanel.setPadding(new Insets(16, 22, 18, 22));

        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("Console");
        title.getStyleClass().add("console-title");

        Label meta = new Label("Live output from the database layer");
        meta.getStyleClass().add("console-meta");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button clearButton = createGhostButton("Clear output");
        clearButton.setOnAction(e -> outputArea.clear());

        header.getChildren().addAll(title, meta, spacer, clearButton);
        consolePanel.getChildren().addAll(header, outputArea);
        VBox.setVgrow(outputArea, Priority.ALWAYS);
        return consolePanel;
    }

    private TextArea createConsoleArea() {
        TextArea area = new TextArea();
        area.setEditable(false);
        area.setWrapText(false);
        area.setPrefRowCount(8);
        area.getStyleClass().add("console-area");
        return area;
    }

    private Button createNavButton(String text, int index) {
        Button button = new Button(text);
        button.getStyleClass().add("nav-button");
        button.setMaxWidth(Double.MAX_VALUE);
        button.setOnAction(e -> tabPane.getSelectionModel().select(index));
        return button;
    }

    private Tab createInsertTab() {
        Tab tab = new Tab("Insert");
        tab.setClosable(false);

        VBox content = new VBox(16);
        content.setPadding(new Insets(18));

        VBox memberCard = createPanelCard("New Member Registration",
                "Capture the core member record before attaching wellness data.");
        GridPane memberGrid = createFormGrid();

        TextField fName = createStyledTextField("First name");
        TextField lName = createStyledTextField("Last name");
        TextField phone = createStyledTextField("Phone");
        TextField email = createStyledTextField("Email");
        TextField address = createStyledTextField("Address");
        Button btnAddMember = createPrimaryButton("Register Member");

        addFieldRow(memberGrid, 0, "First Name", fName, "Last Name", lName);
        addFieldRow(memberGrid, 1, "Phone", phone, "Email", email);
        addSingleFieldRow(memberGrid, 2, "Address", address);

        HBox memberActions = createActionBar();
        memberActions.getChildren().add(btnAddMember);
        memberCard.getChildren().addAll(memberGrid, memberActions);

        VBox healthCard = createPanelCard("Health Profile",
                "Attach measurable data and a short description to an existing member.");
        GridPane healthGrid = createFormGrid();

        TextField age = createStyledTextField("Age");
        TextField height = createStyledTextField("Height (cm)");
        TextField weight = createStyledTextField("Weight (kg)");
        TextField desc = createStyledTextField("Description");
        TextField memberId = createStyledTextField("Member ID");
        Button btnHealth = createPrimaryButton("Save Health Profile");

        addFieldRow(healthGrid, 0, "Age", age, "Height", height);
        addFieldRow(healthGrid, 1, "Weight", weight, "Member ID", memberId);
        addSingleFieldRow(healthGrid, 2, "Description", desc);

        HBox healthActions = createActionBar();
        healthActions.getChildren().add(btnHealth);
        healthCard.getChildren().addAll(healthGrid, healthActions);

        btnAddMember.setOnAction(e -> {
            db.addMember(fName.getText(), lName.getText(), phone.getText(), email.getText(), address.getText());
            clearFields(fName, lName, phone, email, address);
        });

        btnHealth.setOnAction(e -> {
            try {
                int a = Integer.parseInt(age.getText());
                int h = Integer.parseInt(height.getText());
                int w = Integer.parseInt(weight.getText());
                int m = Integer.parseInt(memberId.getText());
                db.setHealthProfile(a, h, w, desc.getText(), m);
                clearFields(age, height, weight, desc, memberId);
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number input: " + ex.getMessage());
            }
        });

        HBox formsRow = new HBox(18, memberCard, healthCard);
        formsRow.setFillHeight(true);
        HBox.setHgrow(memberCard, Priority.ALWAYS);
        HBox.setHgrow(healthCard, Priority.ALWAYS);
        content.getChildren().add(formsRow);

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("workspace-scroll");
        tab.setContent(scrollPane);
        return tab;
    }

    private Tab createSelectTab() {
        Tab tab = new Tab("Search");
        tab.setClosable(false);

        VBox content = new VBox(16);
        content.setPadding(new Insets(18));

        HBox searchRow = new HBox(18);
        searchRow.getChildren().addAll(
                createSearchCard("Find Member by Phone", "Look up a member record using the phone number.",
                        createStyledTextField("Phone"), phoneField -> db.findMemberByPhone(phoneField.getText())),
                createSearchCard("Find Trainer by Specialty", "Search trainers with a matching specialty.",
                        createStyledTextField("Specialty"),
                        specField -> db.findATrainerBySpecialty(specField.getText())));

        VBox reportsCard = createPanelCard("Reports", "Operational queries for fast monitoring and review.");
        TilePane reportGrid = new TilePane();
        reportGrid.setHgap(12);
        reportGrid.setVgap(12);
        reportGrid.setPrefColumns(2);

        Button[] buttons = {
                createReportButton("Members with Expired Subscriptions", db::getMembersWithExpiredSubscriptions),
                createReportButton("Trainer Schedule", db::getTrainerSchedule),
                createReportButton("Discipline with Max Reservations", db::getDisciplineMaxReservation),
                createReportButton("Sessions with No Reservations", db::sessionsWithNoReservations),
                createReportButton("Trainer with Highest Member Attendance", db::trainerWithTheHighestMembers),
                createReportButton("Active Members with No Check-in", db::activeMembersWithNoCheckin),
                createReportButton("Sessions per Zone", db::sessionsPerZone),
                createReportButton("Members Total Reservations", db::membersTotalReservations)
        };

        reportGrid.getChildren().addAll(buttons);
        reportsCard.getChildren().add(reportGrid);

        content.getChildren().addAll(searchRow, reportsCard);

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("workspace-scroll");
        tab.setContent(scrollPane);
        return tab;
    }

    private Tab createUpdateDeleteTab() {
        Tab tab = new Tab("Manage");
        tab.setClosable(false);

        VBox content = new VBox(16);
        content.setPadding(new Insets(18));

        HBox cards = new HBox(18);

        VBox updateCard = createPanelCard("Update Records", "Edit contact and subscription details.");
        GridPane updateGrid = createFormGrid();
        TextField updateMemberId = createStyledTextField("Member ID");
        TextField newEmail = createStyledTextField("New Email");
        Button btnUpdateEmail = createWarningButton("Update Email");

        TextField subId = createStyledTextField("Subscription ID");
        TextField newStatus = createStyledTextField("Active/Expired/Cancelled");
        Button btnUpdateSub = createWarningButton("Update Status");

        addFieldRow(updateGrid, 0, "Member ID", updateMemberId, "New Email", newEmail);
        addFieldRow(updateGrid, 1, "Subscription ID", subId, "New Status", newStatus);
        HBox updateActions = createActionBar();
        updateActions.getChildren().addAll(btnUpdateEmail, btnUpdateSub);
        updateCard.getChildren().addAll(updateGrid, updateActions);

        VBox deleteCard = createPanelCard("Delete Records", "Remove reservations or trainer entries.");
        GridPane deleteGrid = createFormGrid();
        TextField resId = createStyledTextField("Reservation ID");
        Button btnDelRes = createDangerButton("Delete Reservation");

        TextField trainerId = createStyledTextField("Trainer ID");
        Button btnDelTrainer = createDangerButton("Delete Trainer");

        addSingleFieldRow(deleteGrid, 0, "Reservation ID", resId);
        addSingleFieldRow(deleteGrid, 1, "Trainer ID", trainerId);
        HBox deleteActions = createActionBar();
        deleteActions.getChildren().addAll(btnDelRes, btnDelTrainer);
        deleteCard.getChildren().addAll(deleteGrid, deleteActions);

        cards.getChildren().addAll(updateCard, deleteCard);
        HBox.setHgrow(updateCard, Priority.ALWAYS);
        HBox.setHgrow(deleteCard, Priority.ALWAYS);
        content.getChildren().add(cards);

        btnUpdateEmail.setOnAction(e -> {
            try {
                int id = Integer.parseInt(updateMemberId.getText());
                db.updateMemberEmail(id, newEmail.getText());
                clearFields(updateMemberId, newEmail);
            } catch (NumberFormatException ex) {
                System.out.println("Invalid Member ID");
            }
        });

        btnUpdateSub.setOnAction(e -> {
            try {
                int id = Integer.parseInt(subId.getText());
                db.updateSubscriptionStatus(id, newStatus.getText());
                clearFields(subId, newStatus);
            } catch (NumberFormatException ex) {
                System.out.println("Invalid Subscription ID");
            }
        });

        btnDelRes.setOnAction(e -> {
            try {
                int id = Integer.parseInt(resId.getText());
                db.deleteReservation(id);
                clearFields(resId);
            } catch (NumberFormatException ex) {
                System.out.println("Invalid Reservation ID");
            }
        });

        btnDelTrainer.setOnAction(e -> {
            try {
                int id = Integer.parseInt(trainerId.getText());
                db.deleteTrainer(id);
                clearFields(trainerId);
            } catch (NumberFormatException ex) {
                System.out.println("Invalid Trainer ID");
            }
        });

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("workspace-scroll");
        tab.setContent(scrollPane);
        return tab;
    }

    private VBox createPanelCard(String titleText, String subtitleText) {
        VBox card = new VBox(14);
        card.getStyleClass().add("panel-card");

        Label title = new Label(titleText);
        title.getStyleClass().add("panel-title");

        Label subtitle = new Label(subtitleText);
        subtitle.getStyleClass().add("panel-subtitle");
        subtitle.setWrapText(true);

        card.getChildren().addAll(title, subtitle);
        return card;
    }

    private GridPane createFormGrid() {
        GridPane grid = new GridPane();
        grid.getStyleClass().add("form-grid");
        grid.setHgap(12);
        grid.setVgap(12);

        ColumnConstraints left = new ColumnConstraints();
        left.setPercentWidth(18);
        ColumnConstraints leftField = new ColumnConstraints();
        leftField.setPercentWidth(32);
        ColumnConstraints right = new ColumnConstraints();
        right.setPercentWidth(18);
        ColumnConstraints rightField = new ColumnConstraints();
        rightField.setPercentWidth(32);
        grid.getColumnConstraints().addAll(left, leftField, right, rightField);
        return grid;
    }

    private void addFieldRow(GridPane grid, int row, String leftLabel, TextField leftField, String rightLabel,
            TextField rightField) {
        grid.add(createFieldLabel(leftLabel), 0, row);
        grid.add(leftField, 1, row);
        grid.add(createFieldLabel(rightLabel), 2, row);
        grid.add(rightField, 3, row);
    }

    private void addSingleFieldRow(GridPane grid, int row, String label, TextField field) {
        grid.add(createFieldLabel(label), 0, row);
        grid.add(field, 1, row, 3, 1);
    }

    private Label createFieldLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("form-label");
        return label;
    }

    private HBox createActionBar() {
        HBox bar = new HBox(10);
        bar.setAlignment(Pos.CENTER_RIGHT);
        bar.getStyleClass().add("action-bar");
        return bar;
    }

    private TextField createStyledTextField(String prompt) {
        TextField field = new TextField();
        field.setPromptText(prompt);
        field.getStyleClass().add("input-field");
        field.setMaxWidth(Double.MAX_VALUE);
        return field;
    }

    private Button createPrimaryButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("action-button");
        return button;
    }

    private Button createWarningButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("warning-button");
        return button;
    }

    private Button createDangerButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("danger-button");
        return button;
    }

    private Button createGhostButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("ghost-button");
        return button;
    }

    private Button createReportButton(String text, Runnable action) {
        Button button = new Button(text);
        button.getStyleClass().add("report-button");
        button.setMaxWidth(Double.MAX_VALUE);
        button.setOnAction(e -> action.run());
        return button;
    }

    private VBox createSearchCard(String title, String subtitle, TextField inputField, Consumer<TextField> action) {
        VBox card = createPanelCard(title, subtitle);
        card.setMaxWidth(Double.MAX_VALUE);

        VBox body = new VBox(10, inputField);
        Button searchBtn = createPrimaryButton("Search");
        searchBtn.setOnAction(e -> action.accept(inputField));

        HBox actions = createActionBar();
        actions.getChildren().add(searchBtn);

        card.getChildren().addAll(body, actions);
        return card;
    }

    private Label createBadge(String title, String text) {
        Label badge = new Label(title + "  " + text);
        badge.getStyleClass().add("hero-badge");
        return badge;
    }

    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }

    @Override
    public void stop() {
        if (db != null) {
            db.close();
        }
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
