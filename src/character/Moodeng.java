package character;

import ability.Jumpable;
import ability.Movable;
import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Moodeng extends Entity implements Movable, Jumpable {
    private static Moodeng instance;
    private int score;
    private double delayShoot;
    private ImageView MoodengImageView; 
    private Animation MoodengAnimation;
    private Image moveLeft;
    private Image moveRight;
    private boolean facingRight = true;
    
	public Moodeng() {
    	this.setPosX(0);
    	this.setPosY(0); 
    	this.setAtk(1);
    	this.setSpeed(15.0);
    	this.setHp(5);
    	this.setScore(0);
    	this.setDelayShoot(1);
    	
    	this.moveLeft = new Image(ClassLoader.getSystemResource("moodeng_moveLeft.png").toString());
        this.moveRight = new Image(ClassLoader.getSystemResource("moodeng_moveRight.png").toString());
    	
        MoodengImageView = new ImageView(moveRight);
        MoodengImageView.setFitWidth(12);
        MoodengImageView.setFitHeight(72);
        
        initMoodengImageView();
        initAnimations();
    }
    
    private void initMoodengImageView() {
        try {
            rightIdleSprite = new Image(ClassLoader.getSystemResource("moodeng_go_right.png").toString());
            leftIdleSprite = new Image(ClassLoader.getSystemResource("moodeng_go_left.png").toString());
            moodengImageView = new ImageView(rightIdleSprite);
            moodengImageView.setFitWidth(SPRITE_WIDTH);
            moodengImageView.setFitHeight(SPRITE_HEIGHT);
            moodengImageView.setPreserveRatio(true);
            moodengImageView.setViewport(new Rectangle2D(0, 0, SPRITE_WIDTH, SPRITE_HEIGHT));
        } catch (Exception e) {
            System.err.println("Error loading Moodeng sprites: " + e.getMessage());
        }
    }
    
    private void initAnimations() {
        try {
            Image walkRightSheet = new Image(ClassLoader.getSystemResource("moodeng_go_right.png").toString());
            Image walkLeftSheet = new Image(ClassLoader.getSystemResource("moodeng_go_left.png").toString());
            
            walkRightAnimation = createAnimation(walkRightSheet, FRAMES_PER_ROW, Duration.millis(400));
            walkLeftAnimation = createAnimation(walkLeftSheet, FRAMES_PER_ROW, Duration.millis(400));
        } catch (Exception e) {
            System.err.println("Error initializing animations: " + e.getMessage());
        }
    }
    
    private Animation createAnimation(Image spriteSheet, int frameCount, Duration duration) {
        Timeline timeline = new Timeline();
        Duration frameTime = duration.divide(frameCount);
        
        for (int i = 0; i < frameCount; i++) {
            final int frameIndex = i;
            KeyFrame keyFrame = new KeyFrame(frameTime.multiply(i), event -> {
                moodengImageView.setImage(spriteSheet);
                int x = (frameIndex % FRAMES_PER_ROW) * SPRITE_WIDTH;
                moodengImageView.setViewport(new Rectangle2D(x, 0, SPRITE_WIDTH, SPRITE_HEIGHT));
            });
            timeline.getKeyFrames().add(keyFrame);
        }
        
        timeline.setCycleCount(Animation.INDEFINITE);
        return timeline;
    }
    
    @Override
    public void moveLeft() {
        if (getPosX() + 70 > 0) {
            setPosX(getPosX() - getSpeed());
            moodengImageView.setTranslateX(getPosX());
            
            if (facingRight) {
                facingRight = false;
                stopAllAnimations();
                walkLeftAnimation.play();
            }
        }
    }
      
    @Override
    public void moveRight() {
        if (getPosX() + 30 < 1280 - SPRITE_WIDTH) {
            setPosX(getPosX() + getSpeed());
            moodengImageView.setTranslateX(getPosX());
            
            if (!facingRight) {
                facingRight = true;
                stopAllAnimations();
                walkRightAnimation.play();
            }
        }
    }
    
    private void stopAllAnimations() {
        if (walkRightAnimation != null) walkRightAnimation.stop();
        if (walkLeftAnimation != null) walkLeftAnimation.stop();
        
        if (facingRight) {
            moodengImageView.setImage(rightIdleSprite);
            moodengImageView.setViewport(new Rectangle2D(0, 0, SPRITE_WIDTH, SPRITE_HEIGHT));
        } else {
            moodengImageView.setImage(leftIdleSprite);
            moodengImageView.setViewport(new Rectangle2D(3 * SPRITE_WIDTH, 0, SPRITE_WIDTH, SPRITE_HEIGHT));
        }
    }
    
    public void idle() {
        stopAllAnimations();
    }
    
    @Override
    public void jump() {
        // implement later
    }
    
    public void shoot() {
        // implement later
    }
    
    public ImageView getMoodengImageView() {
        return moodengImageView;
    }
    
    public static Moodeng getInstance() {
        if (instance == null) {
            instance = new Moodeng();
        }
        return instance;
    }
    
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = Math.max(0, score);
    }
    
    public double getDelayShoot() {
        return delayShoot;
    }

    public void setDelayShoot(double delayShoot) {
        this.delayShoot = Math.max(0, delayShoot);
    }
}
