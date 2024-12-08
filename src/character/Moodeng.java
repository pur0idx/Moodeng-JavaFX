package character;

public class Moodeng extends Entity{
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
	
	public void goLeft() {
     
    }
	
	public void goRight(){
		
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
