package objects;

import character.Moodeng;
import types.FruitType;

public class Watermelon extends BaseFruit {
    public Watermelon() {
        super("Fruitful Recovery", "watermelon.png", 0);
    }
    
    @Override
    public void applyEffect(Moodeng moodeng) {
    }
    
    @Override
    public void removeEffect(Moodeng moodeng) {
    }
    
    @Override
    public int getScoreValue() {
        return 50;
    }
    
    public FruitType getFruitType() {
		return FruitType.WATERMELON;
	}
}