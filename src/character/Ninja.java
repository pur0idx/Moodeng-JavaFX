package character;

import interfaces.Movable;

public class Ninja extends Entity implements Movable {
	private static Ninja instance;

	public Ninja(double x, double y) {
	        setHp(5); 
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
	
	public void swordAttack(long currentTime) {

    }
    
	
	public void runAnimation() {
        
    }
	
	public static Ninja getInstance() {
        if (instance == null) {
            instance = new Ninja(10.0, 10.0);  // Create Ninja at a position
        }
        return instance;
    }
	 
	 
}
