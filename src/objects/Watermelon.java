package objects;

import character.Moodeng;
import components.BuffIndicator;
import types.FruitType;

public class Watermelon extends BaseFruit {
    private static final double SPEED_MULTIPLIER = 2;
    private double originalSpeed;
	
    public Watermelon() {
        super("Fruitful Recovery", "watermelon.png", 4);
    }
    
    @Override
    public void applyEffect(Moodeng moodeng) {
        originalSpeed = moodeng.getSpeed();
        System.out.println("DEBUG add buff set speed = "+ moodeng.getSpeed() * SPEED_MULTIPLIER);
        moodeng.setSpeed(moodeng.getSpeed() * SPEED_MULTIPLIER);
        updateSpeedIndicator();
    }
    
    @Override
    public void removeEffect(Moodeng moodeng) {
    	System.out.println("DEBUG remove buff set speed = "+ moodeng.getSpeed() / SPEED_MULTIPLIER);
        moodeng.setSpeed(moodeng.getSpeed() / SPEED_MULTIPLIER);
        updateSpeedIndicator();
    }
    
    @Override
    public int getScoreValue() {
        return 50;
    }
    
    public FruitType getFruitType() {
		return FruitType.WATERMELON;
	}
}