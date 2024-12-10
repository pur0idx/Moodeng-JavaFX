package objects;

import java.util.Random;

public class PowerUpFactory {
    private static final Random random = new Random();
    
    private static final PowerUpType[] TYPES = PowerUpType.values();
    
    public enum PowerUpType {
        HEALTH(40, Watermelon.class),
        SPEED(25, Banana.class),
        DEFENSE(20, Coconut.class),
        ATTACK(15, Pineapple.class);
        
        private final int spawnChance;
        private final Class<? extends BaseFruit> powerUpClass;
        
        PowerUpType(int spawnChance, Class<? extends BaseFruit> powerUpClass) {
            this.spawnChance = spawnChance;
            this.powerUpClass = powerUpClass;
        }
    }
    
    public static BaseFruit createRandomPowerUp() {
        int roll = random.nextInt(100);
        int cumulative = 0;
        
        for (PowerUpType type : TYPES) {
            cumulative += type.spawnChance;
            if (roll < cumulative) {
                try {
                    return type.powerUpClass.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        return new Watermelon();
    }
}