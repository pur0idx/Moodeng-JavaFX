package character;

public abstract class Entity {  
    private int hp;  
    private double posX;  
    private double posY;
    private int atk;
    private double speed;
	private boolean isDead;
    
    public double getXPos() {
        return posX;
    }

	public double getPosX() {
		return posX;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}
	
	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = Math.max(0, hp); 
	}
	
	public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }
    
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = Math.max(0, speed);
    }
    
    public boolean isDead() { 
    	return isDead; 
    }
    
    public void setDead(boolean isDead) { 
    	this.isDead = isDead; 
    }
}
