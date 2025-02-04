package gui;

import sound.PlaySound;

public class ForestMapPane extends MapPane {
    private static ForestMapPane instance;
    
    public ForestMapPane() {
        super("ForestMap", "forest.gif", "forest_ground.png");
        PlaySound.playForestMusic();
    }
    
    @Override
    protected void customMapSetup() {
    	// custom setup
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