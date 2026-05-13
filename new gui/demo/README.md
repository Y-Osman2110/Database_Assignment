# 🏋️ Gym Management System - JavaFX GUI (Maven/Gradle)

## 📁 Project Structure

```
demo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── gym/
│   │   │               ├── GymApp.java              ← Main entry point
│   │   │               ├── Database.java            ← Your existing DB class
│   │   │               ├── Insert.java              ← Your existing insert
│   │   │               ├── Select.java              ← Your existing select
│   │   │               ├── Update.java              ← Your existing update
│   │   │               ├── Delete.java              ← Your existing delete
│   │   │               └── controllers/
│   │   │                   ├── MainController.java
│   │   │                   ├── DashboardController.java
│   │   │                   ├── InsertController.java
│   │   │                   ├── SelectController.java
│   │   │                   ├── AnalyticsController.java
│   │   │                   └── UpdateDeleteController.java
│   │   └── resources/
│   │       └── com/
│   │           └── example/
│   │               └── gym/
│   │                   ├── views/
│   │                   │   ├── MainView.fxml
│   │                   │   ├── DashboardView.fxml
│   │                   │   ├── InsertView.fxml
│   │                   │   ├── SelectView.fxml
│   │                   │   ├── AnalyticsView.fxml
│   │                   │   └── UpdateDeleteView.fxml
│   │                   └── styles/
│   │                       └── gym-theme.css
│   └── test/
├── pom.xml (or build.gradle)
└── module-info.java (optional)
```

## 🔧 Setup Steps

### 1. Copy Your Existing Files
Move your existing `Database.java`, `Insert.java`, `Select.java`, `Update.java`, `Delete.java` into:
```
src/main/java/com/example/gym/
```

### 2. Add Dependencies

**pom.xml:**
```xml
<dependencies>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>17.0.2</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-fxml</artifactId>
        <version>17.0.2</version>
    </dependency>
    <dependency>
        <groupId>com.microsoft.sqlserver</groupId>
        <artifactId>mssql-jdbc</artifactId>
        <version>12.4.2.jre11</version>
    </dependency>
</dependencies>
```

**build.gradle:**
```groovy
dependencies {
    implementation 'org.openjfx:javafx-controls:17.0.2'
    implementation 'org.openjfx:javafx-fxml:17.0.2'
    implementation 'com.microsoft.sqlserver:mssql-jdbc:12.4.2.jre11'
}
```

### 3. Run the App
```bash
mvn javafx:run
```
or
```bash
./gradlew run
```

## 🎨 Features
- Dark gradient theme with coral accents
- Undecorated draggable window
- Fade animations on page transitions
- Card-based layouts with hover effects
- Emoji icons throughout
- Auto-hiding notifications
- Confirmation dialogs for deletions

## ⚠️ Important Notes

1. **Package Declaration**: All new files use `package com.example.gym;` or `package com.example.gym.controllers;`
2. **FXML paths** use absolute resource paths: `/com/example/gym/views/...`
3. **CSS path** in GymApp.java: `getClass().getResource("styles/gym-theme.css")`
4. Your existing `Database.java` stays in `com.example.gym` package — no changes needed

## 🎨 Color Palette
| Color | Hex | Usage |
|-------|-----|-------|
| Deep Blue | `#1a1a2e` | Background |
| Coral Red | `#e94560` | Primary accent |
| Gold | `#ffd700` | Highlights |
| Success Green | `#00d26a` | Success states |
| Danger Red | `#ff4757` | Delete/warning |
