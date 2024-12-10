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
import sound.PlaySound;

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
    
    private int watermelonCount = 0;
    private int coconutCount = 0;
    private int bananaCount = 0;
    private int pineappleCount = 0;
    
    public Moodeng() {
        this.setPosX(0);
        this.setPosY(0); 
        this.setSpeed(2);
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
        
        PlaySound.playSound("jump");

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
	    watermelonCount++;
	    ScoreBoard.getInstance().setScoreboard();
	}

	public void addCoconut() {
	    coconutCount++;
	    ScoreBoard.getInstance().setScoreboard();
	}

	public void addBanana() {
	    bananaCount++;
	    ScoreBoard.getInstance().setScoreboard();
	}

	public void addPineapple() {
	    pineappleCount++;
	    ScoreBoard.getInstance().setScoreboard();
	}
	
	public void setWatermelons(int watermelons) {
		this.watermelonCount = watermelons;
	}
	
	public void setBananas(int bananas) {
		this.bananaCount = bananas;
	}
	
	public void setPineapples(int pineapples) {
		this.pineappleCount = pineapples;
	}
	
	public void setCoconuts(int coconuts) {
		this.coconutCount = coconuts;
	}
	
	public void resetFruits() {
		setWatermelons(0);
		setPineapples(0);
		setBananas(0);
		setCoconuts(0);
	}

	public int getWatermelons() { return watermelonCount; }
	public int getCoconuts() { return coconutCount; }
	public int getBananas() { return bananaCount; }
	public int getPineapples() { return pineappleCount; }

}