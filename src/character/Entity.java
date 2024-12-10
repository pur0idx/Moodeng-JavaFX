package character;

public abstract class Entity {
    private double posX;  
    private double posY;
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
