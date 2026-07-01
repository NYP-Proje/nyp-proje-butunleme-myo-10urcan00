package com.bank.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DatabaseManager — Singleton-style utility class for all SQLite database operations.
 *
 * Responsibilities:
 *  - Creates and maintains the 'banka.db' SQLite database file.
 *  - Initialises the 'kullanicilar' table on first run.
 *  - Seeds the default admin account (INSERT OR IGNORE ensures idempotency).
 *  - Provides a secure, PreparedStatement-based login verification method.
 */
public class DatabaseManager {

    /** SQLite connection URL — database file is created in the working directory. */
    private static final String DB_URL = "jdbc:sqlite:banka.db";

    // Private constructor: prevent instantiation — all methods are static utilities.
    private DatabaseManager() {}

    /**
     * Initialises the database schema and seeds default credentials.
     * Safe to call multiple times; uses CREATE TABLE IF NOT EXISTS and INSERT OR IGNORE.
     */
    public static void veritabaniOlustur() {
        String createTable = """
                CREATE TABLE IF NOT EXISTS kullanicilar (
                    id            INTEGER PRIMARY KEY AUTOINCREMENT,
                    kullaniciAdi  TEXT    NOT NULL UNIQUE,
                    sifre         TEXT    NOT NULL
                )
                """;

        // INSERT OR IGNORE guarantees the admin row is never duplicated.
        String insertAdmin = """
                INSERT OR IGNORE INTO kullanicilar (kullaniciAdi, sifre)
                VALUES ('admin', '1234')
                """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement  stmt = conn.createStatement()) {

            stmt.execute(createTable);
            stmt.execute(insertAdmin);
            System.out.println("[DB] Veritabanı başarıyla hazırlandı: banka.db");

        } catch (SQLException e) {
            System.err.println("[DB] Veritabanı oluşturulurken hata: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Verifies user credentials against the 'kullanicilar' table using a PreparedStatement
     * to prevent SQL injection.
     *
     * @param kullaniciAdi Username entered by the user
     * @param sifre        Password entered by the user
     * @return {@code true} if a matching record is found; {@code false} otherwise
     */
    public static boolean kullaniciKontrol(String kullaniciAdi, String sifre) {
        String sql = "SELECT 1 FROM kullanicilar WHERE kullaniciAdi = ? AND sifre = ? LIMIT 1";

        try (Connection      conn  = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, kullaniciAdi);
            pstmt.setString(2, sifre);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // true if at least one row matched
            }

        } catch (SQLException e) {
            System.err.println("[DB] Kullanıcı doğrulama hatası: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
