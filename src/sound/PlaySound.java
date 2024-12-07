package sound;

import javafx.scene.media.AudioClip;

public class PlaySound {
	private static final PlaySound INSTANCE = new PlaySound();
	
	public static AudioClip backgroundMusic;

	public static PlaySound getInstance() {
		return INSTANCE;
	}
	
	
}