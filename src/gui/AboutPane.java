package gui;

import javafx.scene.layout.AnchorPane;

public class AboutPane extends AnchorPane{
	private static AboutPane instance;

	public static AboutPane getInstance() {
		return instance;
	}
	
}
