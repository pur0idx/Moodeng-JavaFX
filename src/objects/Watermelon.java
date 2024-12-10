package objects;

import character.Moodeng;
import components.BuffIndicator;
import types.FruitType;

public class Watermelon extends BaseFruit {
    private static final double SPEED_MULTIPLIER = 2;
	
    public Watermelon() {
        super("Fruitful Recovery", "watermelon.png", 4);
    }
    
    @Override
    public void applyEffect(Moodeng moodeng) {
        moodeng.setSpeed(moodeng.getSpeed() * SPEED_MULTIPLIER);
        updateSpeedIndicator();
    }
    
    @Override
    public void removeEffect(Moodeng moodeng) {
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