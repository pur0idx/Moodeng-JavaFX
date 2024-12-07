package gui;

import javafx.animation.FadeTransition;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import main.Main;
import sound.PlaySound;

public class LoginPane extends GridPane {
    private static LoginPane instance;
    private static String playerName;
    private TextField nameInput;
    
    private static final double LOGO_WIDTH = 500;
    private static final int SPACING = 30;
    
    private LoginPane() {
        initializeLoginScreen();
    }
    
    private void initializeLoginScreen() {
        // Start background music
//        PlaySound.backgroundMusic.play();
        
    	setupLayout();
        
        ImageView logoView = createLogo();
        nameInput = createNameField();
        Button playButton = createPlayButton();
        
        setupButtonVisibility(nameInput, playButton);
        
        playButton.setOnAction(e -> startGame(nameInput.getText()));
        
        getChildren().addAll(logoView, nameInput, playButton);
    }
    
    private void setupLayout() {
        setAlignment(Pos.CENTER);
        setVgap(SPACING);
        setBackground(createBackground());
    }
    
    private ImageView createLogo() {
        try {
            Image logo = new Image(ClassLoader.getSystemResource("logo.png").toString());
            ImageView logoView = new ImageView(logo);
            
            logoView.setFitWidth(LOGO_WIDTH);
            logoView.setPreserveRatio(true);
            
//            logoView.setOnMouseEntered(e -> logoView.setOpacity(0.9));
//            logoView.setOnMouseExited(e -> logoView.setOpacity(1.0));
            
            GridPane.setConstraints(logoView, 0, 0);
            return logoView;
        } catch (Exception e) {
            System.err.println("Failed to load logo: " + e.getMessage());
            ImageView fallbackView = new ImageView();
            fallbackView.setFitWidth(LOGO_WIDTH);
            fallbackView.setPreserveRatio(true);
            GridPane.setConstraints(fallbackView, 0, 0);
            return fallbackView;
        }
    }
    
    private TextField createNameField() {
        TextField field = new TextField();
        field.setPromptText("ENTER HERO NAME");
        
        field.setFont(Font.font("Monospace", FontWeight.BOLD, 20));
        
        field.setStyle(
            "-fx-background-color: #000000; " +
            "-fx-text-fill: #ffffff; " +
            "-fx-prompt-text-fill: #888888; " +
            "-fx-background-radius: 0; " +
            "-fx-border-radius: 0; " +
            "-fx-border-width: 3; " +
            "-fx-border-color: #ffffff; " +
            "-fx-border-style: solid; " +
            "-fx-padding: 10; " +
            "-fx-cursor: default;"
        );
        
        field.setPrefWidth(300);
        field.setMaxWidth(400);
        field.setFocusTraversable(false);
        
        field.setOnMouseEntered(e -> field.setStyle(
            "-fx-background-color: #000000; " +
            "-fx-text-fill: #ffffff; " +
            "-fx-prompt-text-fill: #888888; " +
            "-fx-background-radius: 0; " +
            "-fx-border-radius: 0; " +
            "-fx-border-width: 3; " +
            "-fx-border-color: #ffff00; " +
            "-fx-border-style: solid; " +
            "-fx-padding: 10; " +
            "-fx-cursor: default;"
        ));
        
        field.setOnMouseExited(e -> field.setStyle(
            "-fx-background-color: #000000; " +
            "-fx-text-fill: #ffffff; " +
            "-fx-prompt-text-fill: #888888; " +
            "-fx-background-radius: 0; " +
            "-fx-border-radius: 0; " +
            "-fx-border-width: 3; " +
            "-fx-border-color: #ffffff; " +
            "-fx-border-style: solid; " +
            "-fx-padding: 10; " +
            "-fx-cursor: default;"
        ));
        
        GridPane.setConstraints(field, 0, 1);
        GridPane.setHalignment(field, HPos.CENTER);
        return field;
    }
    
    private Button createPlayButton() {
        Button button = new Button("BEGIN ADVENTURE");
        button.setFont(Font.font("Monospace", FontWeight.BOLD, 20));
        button.setTextFill(Color.WHITE);
        button.setStyle(
            "-fx-background-color: #000000; " +
            "-fx-background-radius: 0; " +
            "-fx-border-radius: 0; " +
            "-fx-border-width: 3; " +
            "-fx-border-color: #ffffff; " +
            "-fx-border-style: solid; " +
            "-fx-padding: 10 20; " +
            "-fx-cursor: default;"
        );
        button.setPrefWidth(250);
        button.setVisible(false);
        
        button.setOnMouseEntered(e -> button.setStyle(
            "-fx-background-color: #ffffff; " +
            "-fx-text-fill: #000000; " +
            "-fx-background-radius: 0; " +
            "-fx-border-radius: 0; " +
            "-fx-border-width: 3; " +
            "-fx-border-color: #000000; " +
            "-fx-border-style: solid; " +
            "-fx-padding: 10 20; " +
            "-fx-cursor: default;"
        ));
        
        button.setOnMouseExited(e -> button.setStyle(
            "-fx-background-color: #000000; " +
            "-fx-text-fill: #ffffff; " +
            "-fx-background-radius: 0; " +
            "-fx-border-radius: 0; " +
            "-fx-border-width: 3; " +
            "-fx-border-color: #ffffff; " +
            "-fx-border-style: solid; " +
            "-fx-padding: 10 20; " +
            "-fx-cursor: default;"
        ));
        
        GridPane.setConstraints(button, 0, 2);
        GridPane.setHalignment(button, HPos.CENTER);
        return button;
    }
    
    private void setupButtonVisibility(TextField field, Button button) {
        field.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.trim().isEmpty() && !button.isVisible()) {
                button.setVisible(true);
                FadeTransition fade = new FadeTransition(Duration.millis(400), button);
                fade.setFromValue(0);
                fade.setToValue(1);
                fade.play();
            } else if (newText.trim().isEmpty() && button.isVisible()) {
                FadeTransition fade = new FadeTransition(Duration.millis(200), button);
                fade.setFromValue(1);
                fade.setToValue(0);
                fade.setOnFinished(e -> button.setVisible(false));
                fade.play();
            }
        });
    }
    
    private Background createBackground() {
        Image bgImage = new Image(ClassLoader.getSystemResource("login_image.gif").toString());
        BackgroundSize bgSize = new BackgroundSize(1280, 720, false, false, true, true);
        BackgroundImage background = new BackgroundImage(
            bgImage, 
            BackgroundRepeat.NO_REPEAT, 
            BackgroundRepeat.NO_REPEAT, 
            BackgroundPosition.CENTER, 
            bgSize
        );
        return new Background(background);
    }
    
    private void startGame(String name) {
        try {
            setPlayerName(name);
            Main.getInstance().changeScene(MapPane.getInstance());
        } catch (Exception e) {
            System.err.println("Error starting game: " + e.getMessage());
        }
    }
    
    public static String getPlayerName() {
        return playerName;
    }
    
    public static void setPlayerName(String name) {
        playerName = name;
        if (instance != null && instance.nameInput != null) {
            instance.nameInput.setText(name);
        }
    }
    
    public static LoginPane getInstance() {
        if (instance == null) {
            instance = new LoginPane();
        }
        return instance;
    }
    
    public static void resetInstance() {
        if (instance != null) {
            instance.nameInput.setText("");
            instance.nameInput.setPromptText("ENTER HERO NAME");
        }
        
        instance = null;
        playerName = null;
    }
}