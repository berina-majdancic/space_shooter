package io.github.berinamajdancic.db;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class LeaderboardEntry {
    private SimpleStringProperty username;
    private SimpleIntegerProperty score;
    private SimpleIntegerProperty rank;

    LeaderboardEntry(String username, int score, int rank) {
        this.rank = new SimpleIntegerProperty(rank);
        this.username = new SimpleStringProperty(username);
        this.score = new SimpleIntegerProperty(score);
    }

    public int getRank() {
        return rank.get();
    }

    public String getUsername() {
        return username.get();
    }

    public int getScore() {
        return score.get();
    }

    public SimpleIntegerProperty rankProperty() {
        return rank;
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public SimpleIntegerProperty scoreProperty() {
        return score;
    }
}
