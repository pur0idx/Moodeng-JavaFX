package objects;

import character.Moodeng;
import types.FruitType;

public class Watermelon extends BaseFruit {
    private static final double SPEED_MULTIPLIER = 2;
    private double originalSpeed;
	
    public Watermelon() {
        super("Fruitful Recovery", "watermelon.png", 0);
    }
    
    @Override
    public void applyEffect(Moodeng moodeng) {
        originalSpeed = moodeng.getSpeed();
        moodeng.setSpeed(originalSpeed * SPEED_MULTIPLIER);
    }
    
    @Override
    public void removeEffect(Moodeng moodeng) {
        moodeng.setSpeed(originalSpeed);
    }
    
    @Override
    public int getScoreValue() {
        return 50;
    }
    
    public FruitType getFruitType() {
		return FruitType.WATERMELON;
	}
}