package components;

import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import logic.GameLogic;
import main.Main;
import sound.PlaySound;
import character.Entity;
import gui.MapPane;

public class ExitButton extends ImageView {
    
    public ExitButton() {
        super(new Image(ClassLoader.getSystemResource("Exit.png").toString()));
        setFitWidth(110);
        setFitHeight(44);
        
        AnchorPane.setBottomAnchor(this, 20.0);
        AnchorPane.setRightAnchor(this, 20.0);
    }
    
    public void setupExitHandler(AnchorPane parentPane, String mapName, Entity character) {
        setOnMouseClicked(event -> {
//            PlaySound.stopMusic();
//            PlaySound.playSound("exit");
            GameLogic.setHighScoreEachMap(mapName, character.getScore());
            fadeExitTransition(parentPane, character);
        });
        
        setOnMouseReleased(event -> {
//            PlaySound.playBackgroundMusic();
        });

        setOnMouseEntered(event -> setOpacity(0.8));
        setOnMouseExited(event -> setOpacity(1.0));
    }
    
    private void fadeExitTransition(AnchorPane parentPane, Entity character) {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), parentPane);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> {
            try {
                PlaySound.stopAllSounds();
                character.setDead(true);
                GameLogic.setIsGameOver(true);
                GameLogic.stopGame();  // Stop game loop
                Main.getInstance().changeScene(new MapPane());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        fadeOut.play();
    }
}