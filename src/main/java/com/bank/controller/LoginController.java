package com.bank.controller;

import com.bank.database.DatabaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * LoginController — Handles all user interactions on the login screen.
 *
 * Responsibilities:
 *  - Validates that username and password fields are not blank.
 *  - Delegates credential verification to DatabaseManager.kullaniciKontrol().
 *  - On success: loads main.fxml and swaps the current scene.
 *  - On failure: shows an error Alert and does not navigate away.
 */
public class LoginController {

    /** Path to the main application FXML resource. */
    private static final String MAIN_FXML = "/com/bank/view/main.fxml";

    @FXML private TextField     txtKullaniciAdi;
    @FXML private PasswordField txtSifre;

    /**
     * Invoked when the "GİRİŞ YAP" button is clicked (or Enter is pressed on the form).
     */
    @FXML
    private void handleLogin(ActionEvent event) {
        String kullaniciAdi = txtKullaniciAdi.getText().trim();
        String sifre        = txtSifre.getText().trim();

        // ── Blank-field guard ─────────────────────────────────────────────────
        if (kullaniciAdi.isEmpty() || sifre.isEmpty()) {
            showAlert(AlertType.WARNING,
                    "Eksik Bilgi",
                    "Kullanıcı adı ve şifre alanları boş bırakılamaz.");
            return;
        }

        // ── Credential verification ───────────────────────────────────────────
        if (DatabaseManager.kullaniciKontrol(kullaniciAdi, sifre)) {
            navigateToMain();
        } else {
            showAlert(AlertType.ERROR,
                    "Giriş Başarısız",
                    "Kullanıcı adı veya şifre hatalı.\nLütfen tekrar deneyiniz.");
            txtSifre.clear();
            txtSifre.requestFocus();
        }
    }

    /**
     * Loads main.fxml and replaces the current stage scene.
     */
    private void navigateToMain() {
        try {
            Parent root = FXMLLoader.load(
                    Objects.requireNonNull(getClass().getResource(MAIN_FXML),
                            "main.fxml bulunamadı: " + MAIN_FXML));

            Stage stage = (Stage) txtKullaniciAdi.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Faiz Hesaplama — Ana Ekran");
            stage.setResizable(false);
            stage.centerOnScreen();

        } catch (IOException e) {
            System.err.println("[Login] Ana ekran yüklenemedi: " + e.getMessage());
            showAlert(AlertType.ERROR,
                    "Sistem Hatası",
                    "Ana ekran yüklenirken bir hata oluştu:\n" + e.getMessage());
        }
    }

    /**
     * Utility method to display a modal Alert dialog.
     *
     * @param type    The severity type of the alert
     * @param title   Window title
     * @param message Body text shown to the user
     */
    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
