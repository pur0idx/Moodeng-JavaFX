package components;

import character.Moodeng;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ScoreBoard extends VBox {
    private static ScoreBoard instance;
    private Label scoreLabel;
    
    private ScoreBoard() {
        setAlignment(Pos.CENTER);
        scoreLabel = new Label("Score: 0");
        scoreLabel.setFont(new Font("Arial", 24));
        scoreLabel.setTextFill(Color.WHITE);
        getChildren().add(scoreLabel);
    }
    
    public void setScoreboard() {
        scoreLabel.setText("Score: " + Moodeng.getInstance().getScore());
    }
    
    public static ScoreBoard getInstance() {
        if (instance == null) {
            instance = new ScoreBoard();
        }
        return instance;
    }
}