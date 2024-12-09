package gui;

import character.FlyingDemon;

public class BeastMapPane extends MapPane {
    private static BeastMapPane instance;
    
    public BeastMapPane() {
        super("BeastMap", "beast.gif", "beast_ground.png");
    }
    
    @Override
    protected void customMapSetup() {
    	// custom setup
    	setupEnemies();
    	
    	FlyingDemon demon = new FlyingDemon();
        addEnemy(demon, 300, 200);
        
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