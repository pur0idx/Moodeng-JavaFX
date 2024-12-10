package gui;

import sound.PlaySound;

public class ApocalypseMapPane extends MapPane{
	private static ApocalypseMapPane instance;
	
	public ApocalypseMapPane() {
        super("ApocalypseMap", "apocalypse.gif", "");
        PlaySound.playApocalypseMusic();
    }
    
    @Override
    protected void customMapSetup() {
    	// custom setup
    }
    
    @Override
    public String toString() {
        return "ApocalypseMap";
    }
    
    public static ApocalypseMapPane getInstance() {
        if (instance == null) {
            instance = new ApocalypseMapPane();
        }
        return instance;
    }
}
