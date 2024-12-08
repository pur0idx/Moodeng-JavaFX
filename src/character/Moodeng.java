package character;

import ability.Jumpable;
import ability.Movable;
//import gui.SpriteAnimation;
import javafx.animation.Animation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Moodeng extends Entity implements Movable, Jumpable{
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
    	
        MoodengImageView = new ImageView(moveRight);  // Default start facing right
        MoodengImageView.setFitWidth(12);
        MoodengImageView.setFitHeight(72);
        
        //MoodengAnimation = new SpriteAnimation(MoodengImageView, Duration.millis(1000),4,4,0,0,128,128);
    }
	
	public void setPunkAnimation(Image Image, int count, int column, int width, int height){
        MoodengImageView.setImage(Image);
//        SpriteAnimation.getInstance().setCount(count);
//        SpriteAnimation.getInstance().setColumns(column);
//        SpriteAnimation.getInstance().setWidth(width);
//        SpriteAnimation.getInstance().setHeight(height);
//        punkAnimation.setCycleCount(Animation.INDEFINITE);
        MoodengImageView.setFitWidth(100);
        MoodengImageView.setFitHeight(100);
//        punkAnimation.play();
    }
	
	@Override
	public void moveLeft() {
		if (MoodengImageView.getLayoutX() >= 5.0) {
            MoodengImageView.setLayoutX(MoodengImageView.getLayoutX() - getSpeed());
        }
        setPunkAnimation(moveLeft,4,4,128,128);
    }
	
	@Override
	public void moveRight(){
		if (MoodengImageView.getLayoutX() <= 1080) {
            MoodengImageView.setLayoutX(MoodengImageView.getLayoutX() + getSpeed());
        }
        setPunkAnimation(moveRight,6,6,48,48);
    }
	
	@Override
	public void jump() {
		// TODO Auto-generated method stub
		
	}
	
	public void shoot(){

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
