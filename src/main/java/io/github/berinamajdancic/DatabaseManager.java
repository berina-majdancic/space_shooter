package io.github.berinamajdancic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:1443/space_shooter";
    private static final String USER = "javafx_user";
    private static final String PASSWORD = "your_password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void saveScore(String playerName, int score) {
        String query = "INSERT INTO players (username, high_score) VALUES (?, ?) ON DUPLICATE KEY UPDATE high_score = GREATEST(high_score, ?)";

        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, playerName);
            stmt.setInt(2, score);
            stmt.setInt(3, score);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    public static void Register(String username, String password) {
        String query = "INSERT INTO players (username, password) VALUES (?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Login successful!");
            } else {
                System.out.println("Invalid username or password.");
            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    public static void login(String username, String password) {
        String query = "SELECT * FROM players WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Login successful!");
            } else {
                System.out.println("Invalid username or password.");
            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }
}
