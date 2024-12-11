package components;

import character.Moodeng;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class BuffIndicator extends VBox {	
	private Label speedLabel = new Label("Speed");
	private Label dashCooldownLabel = new Label("Dash is Ready!");
	private Moodeng moodeng = Moodeng.getInstance();
	private static BuffIndicator instance;

	public BuffIndicator(){
		this.getChildren().addAll(speedLabel, dashCooldownLabel);
        speedLabel.setFont(Font.font("Monospace",FontWeight.BOLD, 24));
        speedLabel.setTextFill(Color.WHITE);
        speedLabel.setMinWidth(40);
        speedLabel.setAlignment(Pos.CENTER_LEFT);
        
        dashCooldownLabel.setFont(Font.font("Monospace",FontWeight.BOLD, 24));
        dashCooldownLabel.setTextFill(Color.WHITE);
        dashCooldownLabel.setMinWidth(40);
        dashCooldownLabel.setAlignment(Pos.CENTER_LEFT);
	}
	
	public void setSpeedDisplay(double d) {
		speedLabel.setText(String.format("Speed %.2fx", d/2));
	}
	
	public void setCurrentSpeedDisplay() {
		setSpeedDisplay(moodeng.getSpeed());
	}
	
	public void setDashCooldownDisplay(Double cd) {
		if (cd != 0) {
			dashCooldownLabel.setText(String.format("Dash ready in %.2fs", cd));			
		} else {
			dashCooldownLabel.setText(String.format("Dash is Ready!"));			
		}
	}
	
	public void setCurrentDashCooldownDisplay() {
		setDashCooldownDisplay((moodeng.getDashCooldownRemaining() / 1000.0));
	}
	
	public static BuffIndicator getInstance() {
		if(instance == null) {
			instance = new BuffIndicator();
		}
		return instance;
	}
}
