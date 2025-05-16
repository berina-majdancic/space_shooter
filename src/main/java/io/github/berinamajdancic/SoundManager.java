package io.github.berinamajdancic;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SoundManager {
    private double volume = 1.0;
    private final Map<String, MediaPlayer> soundPlayers = new HashMap<>();

    public SoundManager() {
        loadAllSounds();
        playBackgroundMusic();
    }

    private void loadSound(String id, String filePath) {
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
        loadSound("levelUp", "/io/github/berinamajdancic/sounds/levelUpSound.wav");
        loadSound("death", "/io/github/berinamajdancic/sounds/deathSound.wav");
        loadSound("backgroundMusic", "/io/github/berinamajdancic/sounds/background.mp3");
        loadSound("shoot", "/io/github/berinamajdancic/sounds/shootSound.wav");
        loadSound("achievemnt", "/io/github/berinamajdancic/sounds/achievementSound.wav");

    }

    public void playLevelUpSound() {
        System.out.println("playing sound");
        playSound("levelUp");
    }

    public void playBackgroundMusic() {
        System.out.println("playing sound");
        playSound("backgroundMusic");
    }

    public void playShootSound() {
        playSound("shoot");
    }

    public void dispose() {
        soundPlayers.values().forEach(MediaPlayer::dispose);
        soundPlayers.clear();
    }

}