package com.bank;

import com.bank.database.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Main — JavaFX application entry point.
 *
 * Responsibilities:
 *  1. Initialise the SQLite database (schema + seed data) before any UI is shown.
 *  2. Load the login FXML and display the primary stage.
 */
public class Main extends Application {

    /** Path to the login FXML resource. */
    private static final String LOGIN_FXML = "/com/bank/view/login.fxml";

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Step 1: Ensure the database and default admin user exist.
        DatabaseManager.veritabaniOlustur();

        // Step 2: Load the login screen.
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource(LOGIN_FXML),
                        "login.fxml bulunamadı: " + LOGIN_FXML));

        // Step 3: Configure and display the primary stage.
        primaryStage.setTitle("Banka Faiz Hesaplama Sistemi");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    /**
     * Standard Java entry point — delegates to JavaFX launch machinery.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
