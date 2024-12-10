package objects;

import character.Moodeng;
import types.FruitType;

public class AttackPowerUp extends BasePowerUp {
    private int originalAttack;
    
    public AttackPowerUp() {
        super("Spiky Strike", "pineapple.png", 10.0);
    }
    
    @Override
    public void applyEffect(Moodeng moodeng) {
        originalAttack = moodeng.getAtk();
        moodeng.setAtk(originalAttack * 2);
    }
    
    @Override
    public void removeEffect(Moodeng moodeng) {
        moodeng.setAtk(originalAttack);
    }
    
    @Override
    public int getScoreValue() {
        return 150;
    }
    
    public FruitType getFruitType() {
		return FruitType.PINEAPPLE;
	}
}
