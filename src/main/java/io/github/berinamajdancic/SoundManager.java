package io.github.berinamajdancic;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SoundManager {
    private final Map<String, MediaPlayer> soundPlayers = new HashMap<>();

    public SoundManager() {
        loadAllSounds();
        playBackgroundMusic();
    }

    private void loadSound(String id, String filePath, double volume) {
        try {
            Media sound = new Media(getClass().getResource(filePath).toString());
            MediaPlayer player = new MediaPlayer(sound);
            player.setVolume(volume);
            soundPlayers.put(id, player);
        } catch (Exception e) {
            System.err.println("Error loading sound " + id + ": " + e.getMessage());
        }
    }

    private void playSound(String id) {
        MediaPlayer player = soundPlayers.get(id);
        if (player != null) {
            player.seek(Duration.ZERO);
            player.play();
        }
    }

    private void loadAllSounds() {
        loadSound("startGameSound", "/io/github/berinamajdancic/sounds/startGameSound.wav", 1.0);
        loadSound("death", "/io/github/berinamajdancic/sounds/deathSound.wav", 1.0);
        loadSound("backgroundMusic", "/io/github/berinamajdancic/sounds/background.mp3", 0.2);
        loadSound("kill", "/io/github/berinamajdancic/sounds/killSound.wav", 1.0);
        loadSound("achievement", "/io/github/berinamajdancic/sounds/achievementSound.wav", 1.0);
        loadSound("loseLifeSound", "/io/github/berinamajdancic/sounds/loseLifeSound.wav", 1.0);


    }

    public void playStartGameSound() {
        playSound("startGameSound");
    }

    public void playBackgroundMusic() {
        soundPlayers.get("backgroundMusic").setCycleCount(MediaPlayer.INDEFINITE);
        playSound("backgroundMusic");
    }

    public void playKillSound() {
        playSound("kill");
    }

    public void playDeathSound() {
        playSound("death");

    }

    public void playAchievementSound() {
        playSound("achievement");
    }

    public void playLoseLifeSound() {
        playSound("loseLifeSound");
    }

    public void dispose() {
        soundPlayers.values().forEach(MediaPlayer::dispose);
        soundPlayers.clear();
    }

}
