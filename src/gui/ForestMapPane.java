package gui;

import java.util.Random;

import character.KittyCat;

public class ForestMapPane extends MapPane {
    private static ForestMapPane instance;
    
    public ForestMapPane() {
        super("ForestMap", "forest.gif", "forest_ground.png");
    }
    
    @Override
    protected void customMapSetup() {
    	// custom setup
    	setupEnemies();
        
        Random random = new Random();
        
        double catX = random.nextInt(1000) + 100;
        double catY = GROUND_Y + 60;
        KittyCat kittyCat = new KittyCat();
        addEnemy(kittyCat, catX, catY);
    }
    
    @Override
    public String toString() {
        return "ForestMap";
    }
    
    public static ForestMapPane getInstance() {
        if (instance == null) {
            instance = new ForestMapPane();
        }
        return instance;
    }
}