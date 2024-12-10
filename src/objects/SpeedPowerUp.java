package objects;

import character.Moodeng;
import types.FruitType;

public class SpeedPowerUp extends BasePowerUp {
    private static final double SPEED_MULTIPLIER = 1.5;
    private double originalSpeed;
    
    public SpeedPowerUp() {
        super("Swift Sprint", "banana.png", 5.0);
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
        return 75;
    }
    
    public FruitType getFruitType() {
		return FruitType.BANANA;
	}
}
