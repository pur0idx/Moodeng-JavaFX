package character;

import interfaces.FireBreather;
import interfaces.Flyable;
import interfaces.Movable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class FlyingDemon extends BaseEnemy implements Movable, FireBreather, Flyable {
    private TranslateTransition floatAnimation;
    private Timeline idleAnimation;
    private Timeline attackAnimation;
    private static final double FLOAT_DISTANCE = 50.0;
    private static final double FLOAT_DURATION = 2.0;
    private double currentHeight;
    
    private static final int SPRITE_WIDTH = 81;
    private static final int SPRITE_HEIGHT = 71;
    private static final int FRAMES = 4;
    
    private static final int ATTACK_SPRITE_WIDTH = SPRITE_WIDTH;
    private static final int ATTACK_FRAMES = 8;
    
    private static final int DEATH_SPRITE_WIDTH = SPRITE_WIDTH;
    private static final int DEATH_FRAMES = 7;

    private Timeline hurtAnimation;
    private Timeline deathAnimation;
    
    protected Image hurtSprite;
    protected Image deathSprite;
    
    private static final double SCALE = 2.0;
    
    public FlyingDemon() {
        super(100, 20, 3.0, SPRITE_WIDTH, SPRITE_HEIGHT, FRAMES);
        this.currentHeight = getPosY();
        setupFloating();
        setupIdleAnimation();
    }
    
    @Override
    protected void initEnemySprite() {
        enemyImageView = new ImageView(idleSprite);
        enemyImageView.setFitWidth(SPRITE_WIDTH * SCALE);
        enemyImageView.setFitHeight(SPRITE_HEIGHT * SCALE);
        enemyImageView.setPreserveRatio(true);
        enemyImageView.setSmooth(false);
        enemyImageView.setViewport(new Rectangle2D(0, 0, SPRITE_WIDTH, SPRITE_HEIGHT));
    }
    
    private void setupIdleAnimation() {
        idleAnimation = new Timeline();
        Duration frameTime = Duration.millis(200);
        
        for (int i = 0; i < FRAMES; i++) {
            final int frameIndex = i;
            KeyFrame keyFrame = new KeyFrame(frameTime.multiply(i), event -> {
                enemyImageView.setImage(idleSprite);
                enemyImageView.setViewport(new Rectangle2D(
                    frameIndex * SPRITE_WIDTH, 0, SPRITE_WIDTH, SPRITE_HEIGHT
                ));
            });
            idleAnimation.getKeyFrames().add(keyFrame);
        }
        
        idleAnimation.setCycleCount(Timeline.INDEFINITE);
        idleAnimation.play();
    }
    
    private void setupAttackAnimation() {
        attackAnimation = new Timeline();
        Duration frameTime = Duration.millis(100);
        
        for (int i = 0; i < ATTACK_FRAMES; i++) {
            final int frameIndex = i;
            KeyFrame keyFrame = new KeyFrame(frameTime.multiply(i), event -> {
                enemyImageView.setImage(attackSprite);
                enemyImageView.setViewport(new Rectangle2D(
                    frameIndex * ATTACK_SPRITE_WIDTH, 0, ATTACK_SPRITE_WIDTH, SPRITE_HEIGHT
                ));
            });
            attackAnimation.getKeyFrames().add(keyFrame);
        }
        
        attackAnimation.setOnFinished(e -> {
            setupIdleAnimation();
        });
        
        attackAnimation.setCycleCount(1);
    }
    
    @Override
    protected void loadSprites() {
        try {
            idleSprite = new Image(ClassLoader.getSystemResource("demon_idle.png").toString());
            attackSprite = new Image(ClassLoader.getSystemResource("demon_attack.png").toString());
            moveSprite = new Image(ClassLoader.getSystemResource("demon_fly.png").toString());
            hurtSprite = new Image(ClassLoader.getSystemResource("demon_hurt.png").toString());
            deathSprite = new Image(ClassLoader.getSystemResource("demon_die.png").toString());
        } catch (Exception e) {
            System.err.println("Error loading demon sprites: " + e.getMessage());
        }
    }

    @Override
    public void moveLeft() {
        setPosX(getPosX() - getSpeed());
        enemyImageView.setTranslateX(getPosX());
        if (facingRight) {
            facingRight = false;
            enemyImageView.setScaleX(-1);
        }
    }

    @Override
    public void moveRight() {
        setPosX(getPosX() + getSpeed());
        enemyImageView.setTranslateX(getPosX());
        if (!facingRight) {
            facingRight = true;
            enemyImageView.setScaleX(1);
        }
    }

    @Override
    public void flyUp() {
        currentHeight -= getSpeed();
        setPosY(currentHeight);
        enemyImageView.setTranslateY(currentHeight);
    }

    @Override
    public void flyDown() {
        currentHeight += getSpeed();
        setPosY(currentHeight);
        enemyImageView.setTranslateY(currentHeight);
    }

    @Override
    public void startFloating() {
        setupFloating();
    }

    @Override
    public void stopFloating() {
        if (floatAnimation != null) {
            floatAnimation.stop();
        }
    }

    @Override
    public void breatheFire() {
        if (idleAnimation != null) {
            idleAnimation.stop();
        }
        setupAttackAnimation();
        attackAnimation.play();
    }

    private void setupFloating() {
        floatAnimation = new TranslateTransition(Duration.seconds(FLOAT_DURATION), enemyImageView);
        floatAnimation.setByY(FLOAT_DISTANCE);
        floatAnimation.setAutoReverse(true);
        floatAnimation.setCycleCount(Timeline.INDEFINITE);
        floatAnimation.play();
    }
    
    private void setupHurtAnimation() {
        hurtAnimation = new Timeline();
        Duration frameTime = Duration.millis(100);
        
        for (int i = 0; i < FRAMES; i++) {
            final int frameIndex = i;
            KeyFrame keyFrame = new KeyFrame(frameTime.multiply(i), event -> {
                enemyImageView.setImage(hurtSprite);
                enemyImageView.setViewport(new Rectangle2D(
                    frameIndex * SPRITE_WIDTH, 0, SPRITE_WIDTH, SPRITE_HEIGHT
                ));
            });
            hurtAnimation.getKeyFrames().add(keyFrame);
        }
        
        hurtAnimation.setOnFinished(e -> {
            if (!isDead()) {
                setupIdleAnimation();
            }
        });
        
        hurtAnimation.setCycleCount(1);
    }
    
    private void setupDeathAnimation() {
        deathAnimation = new Timeline();
        Duration frameTime = Duration.millis(150);
        
        for (int i = 0; i < DEATH_FRAMES; i++) {
            final int frameIndex = i;
            KeyFrame keyFrame = new KeyFrame(frameTime.multiply(i), event -> {
                enemyImageView.setImage(deathSprite);
                enemyImageView.setViewport(new Rectangle2D(
                    frameIndex * DEATH_SPRITE_WIDTH, 0, DEATH_SPRITE_WIDTH, SPRITE_HEIGHT
                ));
            });
            deathAnimation.getKeyFrames().add(keyFrame);
        }
        
        deathAnimation.setOnFinished(e -> {
            stopAllAnimations();
        });
        
        deathAnimation.setCycleCount(1);
    }
    
    public void hurt() {
        stopAllAnimations();
        setupHurtAnimation();
        hurtAnimation.play();
    }
    
    public void die() {
        stopAllAnimations();
        setupDeathAnimation();
        deathAnimation.play();
    }
    
    private void stopAllAnimations() {
        if (floatAnimation != null) floatAnimation.stop();
        if (idleAnimation != null) idleAnimation.stop();
        if (attackAnimation != null) attackAnimation.stop();
        if (hurtAnimation != null) hurtAnimation.stop();
        if (deathAnimation != null) deathAnimation.stop();
    }

    @Override
    public void attack() {
        breatheFire();
    }

    @Override
    public void move() {
        // movement pattern implementation
    }
}
