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
import javafx.concurrent.Task;

public class DatabaseManager {
    private final String URL = "jdbc:mysql://localhost:3306/space_shooter";
    private final String USER = "javafx_user";
    private final String PASSWORD = "your_password";

    private String username = "player" + (int) (Math.random() * 1500);
    private boolean isLoggedIn = false;

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public String getUsername() {
        return username;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void saveScore(int score) {
        Task<Void> saveTask = new Task<>() {
            @Override
            protected Void call() {
                String query = "INSERT INTO highscore (Username, Highscore) VALUES (?, ?) ON DUPLICATE KEY UPDATE Highscore = GREATEST(Highscore, ?)";
                try (Connection conn = getConnection();
                        PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, username);
                    stmt.setInt(2, score);
                    stmt.setInt(3, score);
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    System.err.println("Database error: " + e.getMessage());
                }
                return null;
            }
        };
        new Thread(saveTask).start();
    }

    public void Register(String username, String password) {
        Task<Void> registerTask = new Task<>() {
            @Override
            protected Void call() {
                String query = "INSERT INTO player (Username, Password) VALUES (?, ?)";
                try (Connection conn = getConnection();
                        PreparedStatement stmt = conn.prepareStatement(query)) {

                    stmt.setString(1, username);
                    stmt.setString(2, password);
                    stmt.execute();

                } catch (SQLException e) {
                    System.err.println("Database error: " + e.getMessage());
                }
                return null;
            }
        };
        new Thread(registerTask).start();
    }

    public void login(String username, String password) {
        Task<Void> loginTask = new Task<Void>() {
            @Override
            protected Void call() {
                String query = "SELECT * FROM player WHERE Username = ? AND Password = ?";
                try (Connection conn = getConnection();
                        PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, username);
                    stmt.setString(2, password);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        System.out.println("Login successful!");
                        DatabaseManager.this.username = username;
                        DatabaseManager.this.isLoggedIn = true;
                    } else {
                        System.out.println("Invalid username or password.");
                    }

                } catch (SQLException e) {
                    System.err.println("Database error: " + e.getMessage());
                }
                return null;
            }
        };
        new Thread(loginTask).start();

    }

    public ObservableList<LeaderboardEntry> getLeaderboardData() {
        List<LeaderboardEntry> leaderboard = new ArrayList<>();
        String query = "SELECT Username, Highscore FROM highscore ORDER BY Highscore DESC";
        try (PreparedStatement statement = getConnection().prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            int rank = 1;
            while (resultSet.next()) {
                String username1 = resultSet.getString("Username");
                int score = resultSet.getInt("Highscore");

                leaderboard.add(new LeaderboardEntry(username1, score, rank));
                rank++;
            }
        } catch (SQLException e) {
            System.err.println("Error fetching leaderboard data: " + e.getMessage());
        }
        ObservableList<LeaderboardEntry> data = FXCollections.observableArrayList(leaderboard);
        return data;
    }
}
