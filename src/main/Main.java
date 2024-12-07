package main;

import gui.*;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        
        gameWindow.setScene(gameScene);
        gameWindow.setTitle("moodeng");
        gameWindow.setResizable(false);
        gameWindow.show();
    }

    public void changeScene(Parent newScreen) {
        gameWindow.getScene().setRoot(newScreen);
        newScreen.requestFocus();
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