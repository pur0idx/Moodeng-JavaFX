package components;

import character.Moodeng;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class BuffIndicator extends HBox {	
	private Label label = new Label("Buff");
	private Moodeng moodeng = Moodeng.getInstance();
	private static BuffIndicator instance;

	public BuffIndicator(){
		this.getChildren().addAll(label);
        label.setFont(Font.font("Monospace",FontWeight.BOLD, 24));
        label.setTextFill(Color.WHITE);
        label.setMinWidth(40);
        label.setAlignment(Pos.CENTER_LEFT);
	}
	
	public void setSpeedDisplay(double d) {
		label.setText(String.format("Speed %.2fx", d/2));
	}
	
	public void setSpeedDisplayUsingCurrent() {
		setSpeedDisplay(moodeng.getSpeed());
	}
	
	public static BuffIndicator getInstance() {
		if(instance == null) {
			instance = new BuffIndicator();
		}
		return instance;
	}
}
