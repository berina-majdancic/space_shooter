package io.github.berinamajdancic.db;

public class LeaderboardEntry {
    private String username;
    private int score;
    private int rank;

    LeaderboardEntry(String username, int score, int rank) {
        this.username = username;
        this.score = score;
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }
}
