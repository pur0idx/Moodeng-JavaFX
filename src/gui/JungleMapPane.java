package gui;

import sound.PlaySound;

public class JungleMapPane extends MapPane{
	private static JungleMapPane instance;
	
	public JungleMapPane() {
		super("JungleMap", "jungle.gif", "");
		PlaySound.playJungleMusic();
    }
    
    @Override
    protected void customMapSetup() {
    	// custom setup
    }
    
    @Override
    public String toString() {
        return "JungleMap";
    }
    
    public static JungleMapPane getInstance() {
        if (instance == null) {
            instance = new JungleMapPane();
        }
        return instance;
    }
}
