package objects;

import character.Moodeng;

public class HealthPowerUp extends BasePowerUp {
    public HealthPowerUp() {
        super("Fruitful Recovery", "watermelon.png", 0);
    }
    
    @Override
    public void applyEffect(Moodeng moodeng) {
        moodeng.setHp(Math.min(moodeng.getHp() + 1, 5));
    }
    
    @Override
    public void removeEffect(Moodeng moodeng) {
        // Instant effect, no removal needed
    }
    
    @Override
    public int getScoreValue() {
        return 50;
    }
}