package character;

import interfaces.Biter;
import interfaces.Jumpable;
import interfaces.Movable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class KittyCat extends BaseEnemy implements Movable, Biter, Jumpable {
	private Timeline idleAnimation;
	private Timeline moveAnimation;
	private Timeline jumpAnimation;
	private Timeline fallAnimation;

	private static final int SPRITE_WIDTH = 32;
	private static final int SPRITE_HEIGHT = 32;
	private static final int IDLE_FRAMES = 8;
	private static final int MOVE_FRAMES = 10;
	private static final int JUMP_FRAMES = 4;
	private static final int FALL_FRAMES = 4;

	private static final double SCALE = 2.0;
	private static final double JUMP_FORCE = -10.0;
	private double verticalVelocity = 0;
	private boolean isJumping = false;
	private boolean isFalling = false;

	private Timeline moveTimer;
	private Timeline gravityTimer;

	protected Image jumpSprite;
	protected Image fallSprite;
	
	private Timeline jumpCooldown;
    private boolean canJump = true;

	public KittyCat() {
		super(50, 10, 2.0, SPRITE_WIDTH, SPRITE_HEIGHT, IDLE_FRAMES);
		setupIdleAnimation();
		setupMoveAnimation();
	    setupJumpAnimation();
	    setupFallAnimation();
		setupGravity();
		setupMovement();
	}

	@Override
	protected void initEnemySprite() {
		enemyImageView = new ImageView(idleSprite);
		enemyImageView.setFitWidth(SPRITE_WIDTH * SCALE);
		enemyImageView.setFitHeight(SPRITE_HEIGHT * SCALE);
		enemyImageView.setSmooth(false);
		enemyImageView.setCache(true);
		enemyImageView.setCacheHint(javafx.scene.CacheHint.SPEED);
		enemyImageView.setPreserveRatio(true);
		enemyImageView.setViewport(new Rectangle2D(0, 0, SPRITE_WIDTH, SPRITE_HEIGHT));
	}

	@Override
	protected void loadSprites() {
		try {
			idleSprite = new Image(ClassLoader.getSystemResource("cat_idle.png").toString(), SPRITE_WIDTH * IDLE_FRAMES,
					SPRITE_HEIGHT, false, false);
			moveSprite = new Image(ClassLoader.getSystemResource("cat_move.png").toString(), SPRITE_WIDTH * MOVE_FRAMES,
					SPRITE_HEIGHT, false, false);
			jumpSprite = new Image(ClassLoader.getSystemResource("cat_jump.png").toString(), SPRITE_WIDTH * JUMP_FRAMES,
					SPRITE_HEIGHT, false, false);
			fallSprite = new Image(ClassLoader.getSystemResource("cat_fall.png").toString(), SPRITE_WIDTH * FALL_FRAMES,
					SPRITE_HEIGHT, false, false);
			attackSprite = moveSprite;
		} catch (Exception e) {
			System.err.println("Error loading cat sprites: " + e.getMessage());
		}
	}

	private void setupIdleAnimation() {
		idleAnimation = new Timeline();
		Duration frameTime = Duration.millis(100);

		for (int i = 0; i < IDLE_FRAMES; i++) {
			final int frameIndex = i;
			KeyFrame keyFrame = new KeyFrame(frameTime.multiply(i), event -> {
				enemyImageView.setImage(idleSprite);
				enemyImageView.setViewport(new Rectangle2D(frameIndex * SPRITE_WIDTH, 0, SPRITE_WIDTH, SPRITE_HEIGHT));
			});
			idleAnimation.getKeyFrames().add(keyFrame);
		}

		idleAnimation.setCycleCount(Timeline.INDEFINITE);
		idleAnimation.play();
	}

	private void setupMoveAnimation() {
		moveAnimation = new Timeline();
		Duration frameTime = Duration.millis(50);

		for (int i = 0; i < MOVE_FRAMES; i++) {
			final int frameIndex = i;
			KeyFrame keyFrame = new KeyFrame(frameTime.multiply(i), event -> {
				enemyImageView.setImage(moveSprite);
				enemyImageView.setViewport(new Rectangle2D(frameIndex * SPRITE_WIDTH, 0, SPRITE_WIDTH, SPRITE_HEIGHT));
			});
			moveAnimation.getKeyFrames().add(keyFrame);
		}

		moveAnimation.setCycleCount(Timeline.INDEFINITE);
	}

	private void setupJumpAnimation() {
		jumpAnimation = new Timeline();
		Duration frameTime = Duration.millis(100);

		for (int i = 0; i < JUMP_FRAMES; i++) {
			final int frameIndex = i;
			KeyFrame keyFrame = new KeyFrame(frameTime.multiply(i), event -> {
				enemyImageView.setImage(jumpSprite);
				enemyImageView.setViewport(new Rectangle2D(frameIndex * SPRITE_WIDTH, 0, SPRITE_WIDTH, SPRITE_HEIGHT));
			});
			jumpAnimation.getKeyFrames().add(keyFrame);
		}

		jumpAnimation.setCycleCount(1);
	}

	private void setupFallAnimation() {
		fallAnimation = new Timeline();
		Duration frameTime = Duration.millis(100);

		for (int i = 0; i < FALL_FRAMES; i++) {
			final int frameIndex = i;
			KeyFrame keyFrame = new KeyFrame(frameTime.multiply(i), event -> {
				enemyImageView.setImage(fallSprite);
				enemyImageView.setViewport(new Rectangle2D(frameIndex * SPRITE_WIDTH, 0, SPRITE_WIDTH, SPRITE_HEIGHT));
			});
			fallAnimation.getKeyFrames().add(keyFrame);
		}

		fallAnimation.setCycleCount(Timeline.INDEFINITE);
	}

	@Override
	public void moveLeft() {
		setPosX(getPosX() - getSpeed());
		enemyImageView.setTranslateX(getPosX());
		if (facingRight) {
			facingRight = false;
			enemyImageView.setScaleX(-SCALE);
		}
		startMoveAnimation();
	}

	@Override
	public void moveRight() {
		setPosX(getPosX() + getSpeed());
		enemyImageView.setTranslateX(getPosX());
		if (!facingRight) {
			facingRight = true;
			enemyImageView.setScaleX(SCALE);
		}
		startMoveAnimation();
	}

	private void startMoveAnimation() {
	    if (!isJumping && !isFalling) {
	        stopAllAnimations();
	        if (moveAnimation != null) {
	            moveAnimation.play();
	        } else {
	            setupMoveAnimation();
	            moveAnimation.play();
	        }
	    }
	}

	@Override
	public void jump() {
	    if (!isJumping && !isFalling && canJump) {
	        isJumping = true;
	        canJump = false;
	        verticalVelocity = JUMP_FORCE;
	        stopAllAnimations();
	        setupJumpAnimation();
	        jumpAnimation.play();
	        jumpCooldown.playFromStart();
	    }
	}

	private void setupGravity() {
		gravityTimer = new Timeline(new KeyFrame(Duration.millis(16), e -> applyGravity()));
		gravityTimer.setCycleCount(Timeline.INDEFINITE);
		gravityTimer.play();
	}

	private void applyGravity() {
		if (isJumping || isFalling) {
			verticalVelocity += 0.5; // Gravity constant
			setPosY(getPosY() + verticalVelocity);
			enemyImageView.setTranslateY(getPosY());

			if (verticalVelocity > 0) {
				isJumping = false;
				isFalling = true;
				stopAllAnimations();
				fallAnimation.play();
			}

			// Ground collision check (example height of 500)
			if (getPosY() >= 500) {
				setPosY(500);
				enemyImageView.setTranslateY(500);
				verticalVelocity = 0;
				isJumping = false;
				isFalling = false;
				stopAllAnimations();
				setupIdleAnimation();
			}
		}
	}

	@Override
	public void bite() {
		stopAllAnimations();
		setupMoveAnimation();
		moveAnimation.play();
	}

	private void setupMovement() {
		moveTimer = new Timeline(new KeyFrame(Duration.millis(16), e -> move()));
		moveTimer.setCycleCount(Timeline.INDEFINITE);
		moveTimer.play();
	}

	@Override
	public void attack() {
		bite();
	}

	@Override
	public void move() {
	    // Get player's actual screen position using translateX/Y instead of getPosX/Y
	    double playerX = Moodeng.getInstance().getMoodengImageView().getTranslateX();
	    double playerY = Moodeng.getInstance().getMoodengImageView().getTranslateY();
	    
	    // Get cat's current position using translateX/Y
	    double catX = enemyImageView.getTranslateX();
	    double catY = enemyImageView.getTranslateY();
	    
	    double distanceX = playerX - catX;
	    double distanceY = playerY - catY;
	    double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
	    
	    double attackRange = 50.0;
	    
	    if (distance < attackRange) {
	        attack();
	    } else if (!isJumping && !isFalling) {  // Only move if on ground
	        if (Math.abs(distanceX) > 10) {  // Add a small threshold to prevent jittering
	            if (distanceX > 0) {
	                moveRight();
	            } else {
	                moveLeft();
	            }
	        }
	        
	        // Only jump if player is significantly above and we're not too close horizontally
	        if (distanceY < -100 && Math.abs(distanceX) < 100) {
	            jump();
	        }
	    }
	}

	private void stopAllAnimations() {
		if (idleAnimation != null)
			idleAnimation.stop();
		if (moveAnimation != null)
			moveAnimation.stop();
		if (jumpAnimation != null)
			jumpAnimation.stop();
		if (fallAnimation != null)
			fallAnimation.stop();
	}
}