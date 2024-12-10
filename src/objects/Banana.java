package objects;

import character.Moodeng;
import components.BuffIndicator;
import types.FruitType;

public class Banana extends BaseFruit {
    private static final double SPEED_MULTIPLIER = 1.5;
    
    public Banana() {
        super("Swift Sprint", "banana.png", 5.0);
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
        return 75;
    }
    
    public FruitType getFruitType() {
		return FruitType.BANANA;
	}
}
