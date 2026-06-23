package com.bank.controller;

import com.bank.model.Hesap;
import com.bank.model.KrediHesabi;
import com.bank.model.VadeliHesap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * MainController — Controller for the main interest calculation screen.
 *
 * Demonstrates Polymorphism: the local variable {@code hesap} is typed as the
 * base class {@link Hesap}, but at runtime it holds either a {@link VadeliHesap}
 * or {@link KrediHesabi} instance depending on the user's ComboBox selection.
 * The call {@code hesap.faizHesapla()} dispatches to the correct override automatically.
 */
public class MainController {

    /** ComboBox option labels — must match FXML and switch logic exactly. */
    private static final String OPT_VADELI = "Mevduat Vadeli Hesap";
    private static final String OPT_KREDI  = "Kredi Faiz Maliyeti";

    /** Path back to the login FXML resource. */
    private static final String LOGIN_FXML = "/com/bank/view/login.fxml";

    // ── FXML-injected controls ────────────────────────────────────────────────

    @FXML private TextField          txtMusteriAdi;
    @FXML private TextField          txtAnaPara;
    @FXML private TextField          txtVadeAy;
    @FXML private TextField          txtFaizOrani;
    @FXML private ComboBox<String>   cmbHesapTuru;
    @FXML private Label              lblSonuc;

    // ── Lifecycle ─────────────────────────────────────────────────────────────

    /**
     * Called automatically by the FXMLLoader after all @FXML fields are injected.
     * Populates the ComboBox items and sets a sensible default selection.
     */
    @FXML
    public void initialize() {
        cmbHesapTuru.getItems().addAll(OPT_VADELI, OPT_KREDI);
        cmbHesapTuru.getSelectionModel().selectFirst();
    }

    // ── Event Handlers ────────────────────────────────────────────────────────

    /**
     * Handles the "Hesapla" button click.
     *
     * Reads all input fields, validates them, instantiates the appropriate
     * {@link Hesap} subclass, calls {@code faizHesapla()}, and updates the result label.
     * Wraps parsing in a try-catch for {@link NumberFormatException}.
     */
    @FXML
    private void handleHesapla(ActionEvent event) {
        try {
            // ── Read and validate string field ────────────────────────────────
            String musteriAdi = txtMusteriAdi.getText().trim();
            if (musteriAdi.isEmpty()) {
                showAlert(AlertType.WARNING, "Eksik Bilgi", "Müşteri adı boş bırakılamaz.");
                txtMusteriAdi.requestFocus();
                return;
            }

            // ── Parse numeric fields (throws NumberFormatException if invalid) ──
            double anaPara   = Double.parseDouble(txtAnaPara.getText().trim());
            int    vadeAy    = Integer.parseInt(txtVadeAy.getText().trim());
            double faizOrani = Double.parseDouble(txtFaizOrani.getText().trim());

            // ── Business-rule validation ──────────────────────────────────────
            if (anaPara <= 0 || vadeAy <= 0 || faizOrani <= 0) {
                showAlert(AlertType.WARNING, "Geçersiz Değer",
                        "Ana para, vade ve faiz oranı sıfırdan büyük olmalıdır.");
                return;
            }

            // ── Polymorphic instantiation based on ComboBox selection ─────────
            String secilen = cmbHesapTuru.getSelectionModel().getSelectedItem();
            Hesap hesap;

            if (OPT_VADELI.equals(secilen)) {
                hesap = new VadeliHesap(musteriAdi, anaPara, vadeAy, faizOrani);
            } else {
                hesap = new KrediHesabi(musteriAdi, anaPara, vadeAy, faizOrani);
            }

            // ── Calculate and display result ───────────────────────────────────
            double faiz       = hesap.faizHesapla();
            double toplamTutar = anaPara + faiz;

            lblSonuc.setText(String.format(
                    "👤 Müşteri      : %s\n" +
                    "📋 Hesap Türü   : %s\n" +
                    "💰 Ana Para     : %,.2f TL\n" +
                    "📈 Toplam Faiz  : %,.2f TL\n" +
                    "🏦 Toplam Tutar : %,.2f TL",
                    musteriAdi, secilen, anaPara, faiz, toplamTutar));

        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Sayısal Hata",
                    "Ana para, vade ve faiz oranı alanlarına yalnızca geçerli sayısal değerler giriniz.\n" +
                    "Örnek: 10000 | 12 | 25.5");
        }
    }

    /**
     * Clears all input fields and resets the ComboBox and result label to defaults.
     */
    @FXML
    private void handleTemizle(ActionEvent event) {
        txtMusteriAdi.clear();
        txtAnaPara.clear();
        txtVadeAy.clear();
        txtFaizOrani.clear();
        cmbHesapTuru.getSelectionModel().selectFirst();
        lblSonuc.setText("Hesaplamak için formu doldurunuz.");
        txtMusteriAdi.requestFocus();
    }

    /**
     * Logs the user out by navigating back to the login screen.
     */
    @FXML
    private void handleCikis(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(
                    Objects.requireNonNull(getClass().getResource(LOGIN_FXML),
                            "login.fxml bulunamadı: " + LOGIN_FXML));

            Stage stage = (Stage) txtMusteriAdi.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Banka Faiz Hesaplama Sistemi");
            stage.setResizable(false);
            stage.centerOnScreen();

        } catch (IOException e) {
            System.err.println("[Main] Giriş ekranı yüklenemedi: " + e.getMessage());
            showAlert(AlertType.ERROR, "Sistem Hatası",
                    "Giriş ekranı yüklenirken bir hata oluştu:\n" + e.getMessage());
        }
    }

    // ── Utility ───────────────────────────────────────────────────────────────

    /**
     * Displays a modal Alert dialog.
     *
     * @param type    Severity type
     * @param title   Window title
     * @param message Content text shown to the user
     */
    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
