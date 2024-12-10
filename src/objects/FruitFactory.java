package objects;

import java.util.Random;

public class FruitFactory {
    private static final Random random = new Random();
    
    public static BaseFruit createRandomPowerUp() {
        int roll = random.nextInt(100);

        if(roll < 10) {
        	return new Watermelon();
        }else if(roll < 35) {
        	return new Banana();
        }else if(roll < 67.5) {
        	return new Coconut();
        }else {
        	return new Pineapple();
        }
    }
}