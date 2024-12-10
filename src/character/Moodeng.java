package character;

import components.ScoreBoard;
import interfaces.Jumpable;
import interfaces.Movable;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.animation.KeyFrame;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Moodeng extends Entity implements Movable, Jumpable {
    private static Moodeng instance;
    private int score;
    private double delayShoot;
    private ImageView moodengImageView;
    private Animation walkRightAnimation;
    private Animation walkLeftAnimation;
    private boolean facingRight;
    private Image rightIdleSprite;
    private Image leftIdleSprite;
    private boolean isJumping = false;
    private double velocityY = 0;
    private final double GRAVITY = 0.5;
    private final double INITIAL_JUMP_VELOCITY = -10;
    
    private static final int SPRITE_WIDTH = 128;
    private static final int SPRITE_HEIGHT = 128;
    private static final int SPRITESHEET_WIDTH = 512;
    private static final int FRAMES_PER_ROW = 4;
    
    private int watermelons = 0;
    private int coconuts = 0;
    private int bananas = 0;
    private int pineapples = 0;
    
    public Moodeng() {
        this.setPosX(0);
        this.setPosY(0); 
        this.setAtk(1);
        this.setSpeed(2);
        this.setHp(5);
        this.setScore(0);
        this.setDelayShoot(1);
        this.facingRight = true;
        
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
        if (isJumping) return;

        isJumping = true;
        stopAllAnimations();

        double initialY = getPosY();
        velocityY = INITIAL_JUMP_VELOCITY;

        AnimationTimer jumpTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                velocityY += GRAVITY;
                setPosY(getPosY() + velocityY);
                moodengImageView.setTranslateY(getPosY());

                if (getPosY() >= initialY) {
                    setPosY(initialY);
                    moodengImageView.setTranslateY(initialY);
                    isJumping = false;
                    idle();
                    stop();
                }
            }
        };
        jumpTimer.start();
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

	public void setInvincible(boolean b) {
		// TODO Auto-generated method stub
		
	}
	
	public void addWatermelon() {
	    watermelons++;
	    ScoreBoard.getInstance().updateFruitCount("watermelon");
	}

	public void addCoconut() {
	    coconuts++;
	    ScoreBoard.getInstance().setScoreboard();
	}

	public void addBanana() {
	    bananas++;
	    ScoreBoard.getInstance().setScoreboard();
	}

	public void addPineapple() {
	    pineapples++;
	    ScoreBoard.getInstance().setScoreboard();
	}

	public int getWatermelons() { return watermelons; }
	public int getCoconuts() { return coconuts; }
	public int getBananas() { return bananas; }
	public int getPineapples() { return pineapples; }

}