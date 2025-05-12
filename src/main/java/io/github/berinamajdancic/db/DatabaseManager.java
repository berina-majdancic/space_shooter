package io.github.berinamajdancic.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatabaseManager {
    private final String URL = "jdbc:mysql://localhost:3306/space_shooter";
    private final String USER = "javafx_user";
    private final String PASSWORD = "your_password";
    private String username = "player";
    private boolean isLoggedIn = false;

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void saveScore(int score) {
        String query = "INSERT INTO players (username, high_score) VALUES (?, ?) ON DUPLICATE KEY UPDATE high_score = GREATEST(high_score, ?)";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setInt(2, score);
            stmt.setInt(3, score);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    public void Register(String username, String password) {
        String query = "INSERT INTO players (username, high_score) VALUES (?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setInt(2, 300);
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

    public void login(String username, String password) {
        String query = "SELECT * FROM players WHERE username = ? AND password = ?";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Login successful!");
                this.username = username;
                this.isLoggedIn = true;
            } else {
                System.out.println("Invalid username or password.");
            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    public ObservableList<LeaderboardEntry> getLeaderboardData() {
        List<LeaderboardEntry> leaderboard = new ArrayList<>();
        String query = "SELECT username, high_score FROM players ORDER BY high_score DESC";
        try (PreparedStatement statement = getConnection().prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            int rank = 1;
            while (resultSet.next()) {
                String username1 = resultSet.getString("username");
                int score = resultSet.getInt("high_score");

                leaderboard.add(new LeaderboardEntry(username1, rank, score));
                rank++;
            }
        } catch (SQLException e) {
            System.err.println("Error fetching leaderboard data: " + e.getMessage());
        }
        ObservableList<LeaderboardEntry> data = FXCollections.observableArrayList(leaderboard);
        return data;
    }
}
