package objects;

import character.Moodeng;
import components.BuffIndicator;
import types.FruitType;

public class Banana extends BaseFruit {
    private static final double SPEED_MULTIPLIER = 1.5;
    private double originalSpeed;
    
    public Banana() {
        super("Swift Sprint", "banana.png", 5.0);
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
        return 75;
    }
    
    public FruitType getFruitType() {
		return FruitType.BANANA;
	}
}
