package objects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import interfaces.*;
import character.Moodeng;
import sound.PlaySound;

public abstract class BasePowerUp extends ImageView implements Collectible, Temporal, PowerUpEffect {
    protected String name;
    protected double duration;
    protected Timeline effectTimer;
    protected boolean isActive;
    protected String imageFile;
    
    public BasePowerUp(String name, String imageFile, double duration) {
        this.name = name;
        this.duration = duration;
        this.imageFile = imageFile;
        this.isActive = false;
        setupImage();
        setupTimer();
    }
    
    protected void setupImage() {
        try {
            setImage(new Image(ClassLoader.getSystemResource(imageFile).toString()));
            setFitWidth(32);
            setFitHeight(32);
        } catch (Exception e) {
            System.err.println("Error loading image of " + this.imageFile + ": " + e.getMessage());
        }
    }
    
    protected void setupTimer() {
        if (duration > 0) {
            effectTimer = new Timeline(
                new KeyFrame(Duration.seconds(duration), e -> {
                    isActive = false;
                    removeEffect(Moodeng.getInstance());
                })
            );
        }
    }
    
    @Override
    public void onCollect(Moodeng moodeng) {
//        playCollectSound();
        applyEffect(moodeng);
        if (duration > 0) {
            startTimer();
        }
    }
    
    @Override
    public void playCollectSound() {
//        PlaySound.playSound("collect");
    }
    
    @Override
    public void startTimer() {
        if (effectTimer != null) {
            isActive = true;
            effectTimer.playFromStart();
        }
    }
    
    @Override
    public void stopTimer() {
        if (effectTimer != null) {
            effectTimer.stop();
            isActive = false;
        }
    }
    
    @Override
    public double getDuration() {
        return duration;
    }
    
    @Override
    public boolean isExpired() {
        return !isActive;
    }
    
    @Override
    public String getEffectName() {
        return name;
    }
}