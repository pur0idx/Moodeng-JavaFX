package character;

import ability.Jumpable;
import ability.Movable;

public class Moodeng extends Entity implements Movable, Jumpable{
    private static Moodeng instance;
    private int score;
    private double delayShoot;
    
	public Moodeng() {
    	this.setPosX(0);
    	this.setPosY(0); 
    	this.setAtk(1);
    	this.setSpeed(15.0);
    	this.setHp(5);
    	this.setScore(0);
    	this.setDelayShoot(1);
    	this.setSpeed(15.0);
    	
    }
	
	@Override
	public void moveLeft() {
     
    }
	
	@Override
	public void moveRight(){
		
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
