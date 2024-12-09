package character;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;

public abstract class BaseEnemy extends Entity {
    protected ImageView enemyImageView;
    protected boolean facingRight;
    
    protected Image idleSprite;
    protected Image attackSprite;
    protected Image moveSprite;
    
    // sprite dimensions (to be set by each enemy)
    protected final int SPRITE_WIDTH;
    protected final int SPRITE_HEIGHT;
    protected final int FRAMES_PER_ROW;
    
    public BaseEnemy(int hp, int atk, double speed, int spriteWidth, int spriteHeight, int framesPerRow) {
        setHp(hp);
        setAtk(atk);
        setSpeed(speed);
        this.SPRITE_WIDTH = spriteWidth;
        this.SPRITE_HEIGHT = spriteHeight;
        this.FRAMES_PER_ROW = framesPerRow;
        this.facingRight = true;
        
        loadSprites();
        initEnemySprite();
    }
    
    protected abstract void loadSprites();
    
    protected void initEnemySprite() {
        enemyImageView = new ImageView(idleSprite);
        enemyImageView.setFitWidth(SPRITE_WIDTH);
        enemyImageView.setFitHeight(SPRITE_HEIGHT);
        enemyImageView.setPreserveRatio(true);
        enemyImageView.setSmooth(false);
        enemyImageView.setViewport(new Rectangle2D(0, 0, SPRITE_WIDTH, SPRITE_HEIGHT));
    }
    
    public ImageView getEnemyImageView() {
        return enemyImageView;
    }
    
    public abstract void attack();
    public abstract void move();
}