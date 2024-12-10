package gui;

import java.util.Random;

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