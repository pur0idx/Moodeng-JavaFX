package main;

import gui.*;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    private static Main instance;
    private Stage gameWindow;
    private Scene gameScene;
    private final int WIDTH = 1280;
    private final int HEIGHT = 720;
    
    @Override
    public void start(Stage stage) throws Exception {
        gameWindow = stage;
        instance = this;
        
        Parent startScreen = LoginPane.getInstance();
        gameScene = new Scene(startScreen, WIDTH, HEIGHT);
        
//        setGameCursor();
        
        gameWindow.setScene(gameScene);
        gameWindow.setTitle("moodeng");
        gameWindow.setResizable(false);
        gameWindow.show();
    }

    public void changeScene(Parent newScreen) {
        gameWindow.getScene().setRoot(newScreen);
        newScreen.requestFocus();
    }
    
    private void setGameCursor() {
        try {
            Image cursorImage = new Image(
                ClassLoader.getSystemResource("cursor.png").toString(),
                32,
                32,
                true,
                true
            );
            
            int hotspotX = 0;
            int hotspotY = 0;
            
            gameScene.setCursor(new ImageCursor(cursorImage, hotspotX, hotspotY));
            
        } catch (Exception e) {
            gameScene.setCursor(Cursor.CROSSHAIR);
            System.err.println("Failed to load custom cursor: " + e.getMessage());
        }
    }

    public static Main getInstance() {
        if (instance == null) {
            instance = new Main();
        }
        return instance;
    }

    public static void main(String[] args) {
        launch(args);
    }
}