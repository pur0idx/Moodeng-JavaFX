package sound;

import javafx.scene.media.AudioClip;

public class PlaySound {
    private static final PlaySound INSTANCE = new PlaySound();
    
    public static AudioClip backgroundMusic;
    public static AudioClip forestMapBG;
    public static AudioClip beastMapBG;
    public static AudioClip jungleMapBG;
    public static AudioClip apocalypseMapBG;
    
    public static AudioClip exit;
    public static AudioClip jump;
    public static AudioClip collect;
    public static AudioClip hit;
    
    private static final double DEFAULT_BGM_VOLUME = 0.05;
    private static final double DEFAULT_EFFECTS_VOLUME = 0.2;

    private PlaySound() {
        loadSounds();
    }

    private void loadSounds() {
        try {
        	backgroundMusic = loadAndSetupAudio("background.wav", DEFAULT_BGM_VOLUME, true);
        	forestMapBG = loadAndSetupAudio("forest.wav", DEFAULT_BGM_VOLUME, true);
            beastMapBG = loadAndSetupAudio("beast.wav", DEFAULT_BGM_VOLUME, true);
            jungleMapBG = loadAndSetupAudio("jungle.wav", DEFAULT_BGM_VOLUME, true);
            apocalypseMapBG = loadAndSetupAudio("apocalypse.wav", DEFAULT_BGM_VOLUME, true);
            
        	exit = loadAndSetupAudio("exit.wav", DEFAULT_EFFECTS_VOLUME / 6, false);
            jump = loadAndSetupAudio("jump.wav", DEFAULT_EFFECTS_VOLUME / 6, false);
            collect = loadAndSetupAudio("collect.wav", DEFAULT_EFFECTS_VOLUME, false);
            hit = loadAndSetupAudio("hit.wav", DEFAULT_EFFECTS_VOLUME, false);
            
        } catch (Exception e) {
            System.err.println("Error loading sounds: " + e.getMessage());
        }
    }
    
    private AudioClip loadAndSetupAudio(String path, double volume, boolean loop) {
        AudioClip clip = new AudioClip(ClassLoader.getSystemResource(path).toString());
        clip.setVolume(volume);
        if (loop) {
            clip.setCycleCount(AudioClip.INDEFINITE);
        }
        return clip;
    }

    public static void setBackgroundVolume(double volume) {
        backgroundMusic.setVolume(volume);
        forestMapBG.setVolume(volume);
        beastMapBG.setVolume(volume);
        jungleMapBG.setVolume(volume);
        apocalypseMapBG.setVolume(volume);
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

    public static void playBeastMusic() {
        stopAllSounds();
        beastMapBG.play();
    }

    public static void playJungleMusic() {
        stopAllSounds();
        jungleMapBG.play();
    }

    public static void playApocalypseMusic() {
        stopAllSounds();
        apocalypseMapBG.play();
    }

    public static void stopMusic() {
        backgroundMusic.stop();
        forestMapBG.stop();
        beastMapBG.stop();
        jungleMapBG.stop();
        apocalypseMapBG.stop();
    }

    public static void stopAllSounds() {
        backgroundMusic.stop();
        forestMapBG.stop();
        beastMapBG.stop();
        jungleMapBG.stop();
        apocalypseMapBG.stop();
        
        exit.stop();
        jump.stop();
        collect.stop();
        hit.stop();
    }

    public static void stopAllMapBG() {
        forestMapBG.stop();
        beastMapBG.stop();
        jungleMapBG.stop();
        apocalypseMapBG.stop();
    }

    public static PlaySound getInstance() {
        return INSTANCE;
    }

    public void stop() {
        stopAllSounds();
    }
}