package objects;

import character.Moodeng;

public class DefensePowerUp extends BasePowerUp{

	public DefensePowerUp() {
        super("Tropical Guard", "coconut.png", 3.0);
    }
    
    @Override
    public void applyEffect(Moodeng moodeng) {
        moodeng.setInvincible(true);
    }
    
    @Override
    public void removeEffect(Moodeng moodeng) {
        moodeng.setInvincible(false);
    }
    
    @Override
    public int getScoreValue() {
        return 100;
    }

}
