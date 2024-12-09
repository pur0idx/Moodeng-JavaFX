package objects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

public class Fireball extends ImageView {
    private double directionX;
    private double directionY;
    private double speed = 5.0;
    private int damage = 1;
    
    private static final int SPRITE_WIDTH = 100;
    private static final int SPRITE_HEIGHT = 100;
    private static final int FRAMES = 61;
    private Timeline animation;
    
    public Fireball(double startX, double startY, double targetX, double targetY) {
        try {
            Image fireballSheet = new Image(ClassLoader.getSystemResource("fireball.png").toString());
            setImage(fireballSheet);
            setFitWidth(SPRITE_WIDTH);
            setFitHeight(SPRITE_HEIGHT);
            setPreserveRatio(true);
            setSmooth(false);
            
            setTranslateX(startX);
            setTranslateY(startY);
            
            double dx = targetX - startX;
            double dy = targetY - startY;
            double length = Math.sqrt(dx * dx + dy * dy);
            directionX = dx / length;
            directionY = dy / length;
            
            setRotate(Math.toDegrees(Math.atan2(dy, dx)));
            
            setupAnimation();
            
        } catch (Exception e) {
            System.err.println("Error loading fireball sprite: " + e.getMessage());
        }
    }
    
    private void setupAnimation() {
        animation = new Timeline();
        Duration frameTime = Duration.millis(30);
        
        for (int i = 0; i < FRAMES; i++) {
            final int frameIndex = i;
            KeyFrame keyFrame = new KeyFrame(frameTime.multiply(i), event -> {
                setViewport(new Rectangle2D(
                    frameIndex * SPRITE_WIDTH, 0, SPRITE_WIDTH, SPRITE_HEIGHT
                ));
            });
            animation.getKeyFrames().add(keyFrame);
        }
        
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }
    
    public void move() {
        setTranslateX(getTranslateX() + directionX * speed);
        setTranslateY(getTranslateY() + directionY * speed);
    }
    
    public void stopAnimation() {
        if (animation != null) {
            animation.stop();
        }
    }
    
    public int getDamage() {
        return damage;
    }
}