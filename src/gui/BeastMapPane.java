package gui;

import java.util.Random;

import character.FlyingDemon;
import sound.PlaySound;

public class BeastMapPane extends MapPane {
    private static BeastMapPane instance;
    
    public BeastMapPane() {
        super("BeastMap", "beast.gif", "beast_ground.png");
        PlaySound.playBeastMusic();
    }
    
    @Override
    protected void customMapSetup() {
    	// custom setup
//    	setupEnemies();
//    	
//    	Random random = new Random();
//        
//        FlyingDemon demon1 = new FlyingDemon();
//        addEnemy(demon1, 
//                random.nextInt(1000) + 100,
//                random.nextInt(300) + 100);
//        
//        FlyingDemon demon2 = new FlyingDemon();
//        addEnemy(demon2, 
//                random.nextInt(1000) + 100,
//                random.nextInt(300) + 100);
        
        // EvilWizard wizard = new EvilWizard();
        // addEnemy(wizard, 500, GROUND_Y);
        
        // Werewolf wolf = new Werewolf();
        // addEnemy(wolf, 700, GROUND_Y);
    }
    
    @Override
    public String toString() {
        return "BeastMap";
    }
    
    public static BeastMapPane getInstance() {
        if (instance == null) {
            instance = new BeastMapPane();
        }
        return instance;
    }
}