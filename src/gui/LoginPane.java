package gui;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.Main;
import sound.PlaySound;

public class LoginPane extends AnchorPane {
    private static LoginPane instance;
    private static String playerName;
    private TextField nameInput;
    private boolean isMusicPlaying = true;
    private Button musicButton;
    
    private static final double LOGO_WIDTH = 500;
    private static final int SPACING = 30;
    private static final int SOUND_BUTTON_SIZE = 60;
    
    private LoginPane() {
        initializeLoginScreen();
    }
    
    private void initializeLoginScreen() {
        setBackground(createBackground());
        
        GridPane contentGrid = new GridPane();
        contentGrid.setAlignment(Pos.CENTER);
        contentGrid.setVgap(SPACING);
        
        ImageView logoView = createLogo();
        nameInput = createNameField();
        Button playButton = createPlayButton();
        Button musicButton = createMusicButton();
        
        contentGrid.add(logoView, 0, 0);
        contentGrid.add(nameInput, 0, 1);
        contentGrid.add(playButton, 0, 2);
        
        AnchorPane.setTopAnchor(contentGrid, 40.0);
        AnchorPane.setBottomAnchor(contentGrid, 0.0);
        AnchorPane.setLeftAnchor(contentGrid, 0.0);
        AnchorPane.setRightAnchor(contentGrid, 0.0);
        
        AnchorPane.setTopAnchor(musicButton, 20.0);
        AnchorPane.setRightAnchor(musicButton, 20.0);
        
        getChildren().addAll(contentGrid, musicButton);
        
        if (isMusicPlaying) {
//            PlaySound.backgroundMusic.play();
        }
    }
    
    private ImageView createLogo() {
        try {
            Image logo = new Image(ClassLoader.getSystemResource("logo.png").toString());
            ImageView logoView = new ImageView(logo);
            logoView.setFitWidth(LOGO_WIDTH);
            logoView.setPreserveRatio(true);
            
            logoView.setTranslateX((LOGO_WIDTH - logoView.getFitWidth()) / 2);
            
            return logoView;
        } catch (Exception e) {
            System.err.println("Failed to load logo: " + e.getMessage());
            ImageView fallbackView = new ImageView();
            fallbackView.setFitWidth(LOGO_WIDTH);
            fallbackView.setPreserveRatio(true);
            
            fallbackView.setTranslateX((LOGO_WIDTH - fallbackView.getFitWidth()) / 2);
            
            return fallbackView;
        }
    }
    
    private TextField createNameField() {
        TextField field = new TextField();
        field.setPromptText("ENTER HERO NAME");
        field.setFont(Font.font("Monospace", FontWeight.BOLD, 20));
        field.setStyle(getDefaultFieldStyle());
        field.setPrefWidth(300);
        field.setMaxWidth(400);
        field.setFocusTraversable(false);
        
        GridPane.setHalignment(field, HPos.CENTER);
        GridPane.setColumnSpan(field, 2);
        
        field.setOnMouseEntered(e -> {
            if (!field.getPromptText().equals("NAME REQUIRED!")) {
                field.setStyle(getHoverFieldStyle());
            }
        });
        
        field.setOnMouseExited(e -> {
            if (!field.getPromptText().equals("NAME REQUIRED!")) {
                field.setStyle(getDefaultFieldStyle());
            }
        });
        
        field.setOnMouseClicked(e -> {
            if (field.getPromptText().equals("NAME REQUIRED!")) {
                field.setPromptText("ENTER HERO NAME");
                field.setStyle(getDefaultFieldStyle());
            }
        });
        
        return field;
    }
    
    private Button createPlayButton() {
        Button button = new Button("BEGIN ADVENTURE");
        button.setFont(Font.font("Monospace", FontWeight.BOLD, 20));
        button.setTextFill(Color.WHITE);
        button.setStyle(getDefaultButtonStyle());
        button.setPrefWidth(250);
        
        GridPane.setHalignment(button, HPos.CENTER);
        GridPane.setColumnSpan(button, 2);
        
        button.setOnMouseEntered(e -> button.setStyle(getHoverButtonStyle()));
        button.setOnMouseExited(e -> button.setStyle(getDefaultButtonStyle()));
        button.setOnAction(e -> startGame(nameInput.getText()));
        
        return button;
    }
    
    private Button createMusicButton() {
        musicButton = new Button();
        musicButton.setPrefSize(70, 70);
        
        try {
            ImageView imageView = new ImageView(new Image(
                ClassLoader.getSystemResource(isMusicPlaying ? "sound_on.png" : "sound_off.png").toString()
            ));
            imageView.setPreserveRatio(true);
            imageView.setSmooth(false);
            imageView.setCache(false);
            imageView.setCacheHint(CacheHint.SPEED);
            imageView.setFitWidth(SOUND_BUTTON_SIZE);
            imageView.setFitHeight(SOUND_BUTTON_SIZE);
            musicButton.setGraphic(imageView);
        } catch (Exception e) {
            System.err.println("Failed to load music icon: " + e.getMessage());
        }
        
        musicButton.setStyle("-fx-background-color: transparent;");
        
        musicButton.setOnMouseEntered(e -> 
            musicButton.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1);")
        );
        
        musicButton.setOnMouseExited(e -> 
            musicButton.setStyle("-fx-background-color: transparent;")
        );
        
        musicButton.setOnAction(e -> toggleMusic());
        
        return musicButton;
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
            if (name.trim().isEmpty()) {
                nameInput.setStyle(getWarningFieldStyle());
                nameInput.setPromptText("NAME REQUIRED!");
                return;
            }
            
            setPlayerName(name);
            Main.getInstance().changeScene(MapPane.getInstance());
        } catch (Exception e) {
            System.err.println("Error starting game: " + e.getMessage());
        }
    }
    
    private void toggleMusic() {
        isMusicPlaying = !isMusicPlaying;
        
        try {
            ImageView imageView = new ImageView(new Image(
                ClassLoader.getSystemResource(isMusicPlaying ? "sound_on.png" : "sound_off.png").toString()
            ));
            imageView.setFitWidth(SOUND_BUTTON_SIZE);
            imageView.setFitHeight(SOUND_BUTTON_SIZE);
            musicButton.setGraphic(imageView);
        } catch (Exception e) {
            System.err.println("Failed to load music icon: " + e.getMessage());
        }
        
        if (isMusicPlaying) {
//            PlaySound.backgroundMusic.play();
        } else {
//            PlaySound.backgroundMusic.stop();
        }
    }
    
    private String getDefaultFieldStyle() {
        return "-fx-background-color: #000000; " +
               "-fx-text-fill: #ffffff; " +
               "-fx-prompt-text-fill: #888888; " +
               "-fx-background-radius: 0; " +
               "-fx-border-radius: 0; " +
               "-fx-border-width: 3; " +
               "-fx-border-color: #ffffff; " +
               "-fx-border-style: solid; " +
               "-fx-padding: 10; " +
               "-fx-cursor: default;";
    }
    
    private String getHoverFieldStyle() {
        return "-fx-background-color: #000000; " +
               "-fx-text-fill: #ffffff; " +
               "-fx-prompt-text-fill: #888888; " +
               "-fx-background-radius: 0; " +
               "-fx-border-radius: 0; " +
               "-fx-border-width: 3; " +
               "-fx-border-color: #ffff00; " +
               "-fx-border-style: solid; " +
               "-fx-padding: 10; " +
               "-fx-cursor: default;";
    }
    
    private String getWarningFieldStyle() {
        return "-fx-background-color: #000000; " +
               "-fx-text-fill: #ffffff; " +
               "-fx-prompt-text-fill: #ff0000; " +
               "-fx-background-radius: 0; " +
               "-fx-border-radius: 0; " +
               "-fx-border-width: 3; " +
               "-fx-border-color: #ff0000; " +
               "-fx-border-style: solid; " +
               "-fx-padding: 10; " +
               "-fx-cursor: default;";
    }
    
    private String getDefaultButtonStyle() {
        return "-fx-background-color: #000000; " +
               "-fx-text-fill: #ffffff; " +
               "-fx-background-radius: 0; " +
               "-fx-border-radius: 0; " +
               "-fx-border-width: 3; " +
               "-fx-border-color: #ffffff; " +
               "-fx-border-style: solid; " +
               "-fx-padding: 10 20; " +
               "-fx-cursor: default;";
    }
    
    private String getHoverButtonStyle() {
        return "-fx-background-color: #ffffff; " +
               "-fx-text-fill: #000000; " +
               "-fx-background-radius: 0; " +
               "-fx-border-radius: 0; " +
               "-fx-border-width: 3; " +
               "-fx-border-color: #000000; " +
               "-fx-border-style: solid; " +
               "-fx-padding: 10 20; " +
               "-fx-cursor: default;";
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
            instance.nameInput.setStyle(instance.getDefaultFieldStyle());
            
//            if (!instance.isMusicPlaying) {
//                PlaySound.backgroundMusic.stop();
//            }
        }
        instance = null;
        playerName = null;
    }
}