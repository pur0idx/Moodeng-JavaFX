package character;

import interfaces.Movable;

public class Orc extends Entity implements Movable{
	
	public Orc(double x, double y) {
        setHp(10);  
        setPosX(x);
        setPosY(y);
	}
	
	@Override
	public void moveLeft() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveRight() {
		// TODO Auto-generated method stub
		
	}
	
	public void axAttack(long currentTime) {
        
    }

    // Animation Timer to control the Orc's actions
    public void runAnimation() {
        
    }


}
