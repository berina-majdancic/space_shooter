package io.github.berinamajdancic;

import java.sql.*;

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
}
