package objects;

import character.Moodeng;
import types.FruitType;

public class Pineapple extends BaseFruit {
    
    public Pineapple() {
        super("Spiky Strike", "pineapple.png", 10.0);
    }
    
    @Override
    public void applyEffect(Moodeng moodeng) {
    }
    
    @Override
    public void removeEffect(Moodeng moodeng) {
    }
    
    @Override
    public int getScoreValue() {
        return 150;
    }
    
    public FruitType getFruitType() {
		return FruitType.PINEAPPLE;
	}
}
