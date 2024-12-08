package sound;

import javafx.scene.media.AudioClip;

public class PlaySound {
    private static final PlaySound INSTANCE = new PlaySound();
    
    public static AudioClip backgroundMusic;
    public static AudioClip forestMapBG;
    
    public static AudioClip exit;
    public static AudioClip jump;
    public static AudioClip collect;
    public static AudioClip hit;

    private PlaySound() {
        loadSounds();
    }

    private void loadSounds() {
        try {
            backgroundMusic = new AudioClip(ClassLoader.getSystemResource("sounds/background.wav").toString());
            forestMapBG = new AudioClip(ClassLoader.getSystemResource("sounds/forest_bg.wav").toString());
            
            exit = new AudioClip(ClassLoader.getSystemResource("sounds/exit.wav").toString());
            jump = new AudioClip(ClassLoader.getSystemResource("sounds/jump.wav").toString());
            collect = new AudioClip(ClassLoader.getSystemResource("sounds/collect.wav").toString());
            hit = new AudioClip(ClassLoader.getSystemResource("sounds/hit.wav").toString());

            backgroundMusic.setCycleCount(AudioClip.INDEFINITE);
            forestMapBG.setCycleCount(AudioClip.INDEFINITE);

            setBackgroundVolume(0.5);
            setEffectsVolume(0.8);
            
        } catch (Exception e) {
            System.err.println("Error loading sounds: " + e.getMessage());
        }
    }

    public static void setBackgroundVolume(double volume) {
        backgroundMusic.setVolume(volume);
        forestMapBG.setVolume(volume);
    }

    public static void setEffectsVolume(double volume) {
        exit.setVolume(volume);
        jump.setVolume(volume);
        collect.setVolume(volume);
        hit.setVolume(volume);
    }

    public static void playSound(String soundName) {
        switch (soundName.toLowerCase()) {
            case "exit":
                exit.play();
                break;
            case "jump":
                jump.play();
                break;
            case "collect":
                collect.play();
                break;
            case "hit":
                hit.play();
                break;
        }
    }

    public static void playBackgroundMusic() {
        stopAllSounds();
        backgroundMusic.play();
    }

    public static void playForestMusic() {
        stopAllSounds();
        forestMapBG.play();
    }

    public static void stopMusic() {
        backgroundMusic.stop();
        forestMapBG.stop();
    }

    public static void stopAllSounds() {
        backgroundMusic.stop();
        forestMapBG.stop();
        
        exit.stop();
        jump.stop();
        collect.stop();
        hit.stop();
    }

    public static void stopAllMapBG() {
        forestMapBG.stop();
    }

    public static PlaySound getInstance() {
        return INSTANCE;
    }

    public void stop() {
        stopAllSounds();
    }
}