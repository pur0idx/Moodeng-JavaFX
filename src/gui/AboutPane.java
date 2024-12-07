package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import main.Main;

public class AboutPane extends BorderPane {
    private static AboutPane instance;
    
    private AboutPane() {
        setupBackground();
        createContent();
    }
    
    private void setupBackground() {
        try {
            Image bgImage = new Image(ClassLoader.getSystemResource("login_image.gif").toString());
            BackgroundImage background = new BackgroundImage(
                bgImage, 
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1280, 720, false, false, true, true)
            );
            setBackground(new Background(background));
        } catch (Exception e) {
            setStyle("-fx-background-color: linear-gradient(to bottom, #1a1a1a, #000000);");
            System.err.println("Failed to load background: " + e.getMessage());
        }
    }
    
    private void createContent() {
        // Main content container
        VBox contentBox = new VBox(20);  // Reduced spacing from 30 to 20
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setMaxWidth(700);     // Reduced from 800 to 700
        contentBox.setPadding(new Insets(30));  // Reduced padding from 40 to 30
        contentBox.setStyle(
            "-fx-background-color: rgba(0, 0, 0, 0.8);" +
            "-fx-border-color: #333333;" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 10;" +
            "-fx-background-radius: 10;"
        );

        // Title
        Text title = new Text("HOW TO PLAY");
        title.setFont(Font.font("Monospace", FontWeight.BOLD, 32));  // Reduced from 36 to 32
        title.setFill(Color.WHITE);
        title.setEffect(new DropShadow(10, Color.GOLD));
        
        // Game instructions sections
        VBox instructionsBox = createInstructionsBox();
        
        // Back button
        Button backButton = new Button("BACK TO MAP SELECTION");  // Changed text
        backButton.setFont(Font.font("Monospace", FontWeight.BOLD, 18));
        backButton.setPrefWidth(250);  // Increased width for new text
        setupButtonStyle(backButton);
        
        backButton.setOnAction(e -> {
            try {
                Main.getInstance().changeScene(MapPane.getInstance());
            } catch (Exception ex) {
                System.err.println("Error returning to map: " + ex.getMessage());
            }
        });

        contentBox.getChildren().addAll(title, instructionsBox, backButton);
        
        // Center the content box
        BorderPane.setAlignment(contentBox, Pos.CENTER);
        setCenter(contentBox);
    }
    
    private VBox createInstructionsBox() {
        VBox box = new VBox(15);  // Reduced spacing from 20 to 15
        box.setAlignment(Pos.CENTER_LEFT);
        
        // Add instruction sections with more concise content
        addSection(box, "OBJECTIVE", 
            "Navigate through dangers, collect coins, and survive obstacles to achieve the highest score!");
                
        addSection(box, "CONTROLS",
            "W/UP: Jump\n" +
            "A/LEFT: Move Left\n" +
            "D/RIGHT: Move Right\n" +
            "SPACE: Shoot");
                
        addSection(box, "SCORING",
            "Coins: +10 points\n" +
            "Enemies: +50 points\n" +
            "Level Complete: +100 points");
                
        addSection(box, "POWER-UPS",
            "Health Potion: Restore HP\n" +
            "Shield: Temporary invincibility\n" +
            "Double Jump: Extra jump ability");
                
        addSection(box, "TIPS",
            "• Watch for obstacles\n" +
            "• Time your jumps\n" +
            "• Use power-ups wisely\n" +
            "• Keep moving");
        
        return box;
    }
    
    private void addSection(VBox parent, String title, String content) {
        Text titleText = new Text(title);
        titleText.setFont(Font.font("Monospace", FontWeight.BOLD, 22));  // Reduced from 24 to 22
        titleText.setFill(Color.WHITE);
        
        Text contentText = new Text(content);
        contentText.setFont(Font.font("Monospace", FontWeight.NORMAL, 14));  // Reduced from 16 to 14
        contentText.setFill(Color.LIGHTGRAY);
        contentText.setTextAlignment(TextAlignment.LEFT);
        
        parent.getChildren().addAll(titleText, contentText);
    }
    
    private void setupButtonStyle(Button button) {
        button.setStyle(
            "-fx-background-color: #2a2a2a;" +
            "-fx-text-fill: #ffffff;" +
            "-fx-background-radius: 5;" +
            "-fx-border-color: #404040;" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 5;" +
            "-fx-padding: 10 20;"
        );
        
        button.setOnMouseEntered(e -> {
            button.setStyle(
                "-fx-background-color: #404040;" +
                "-fx-text-fill: #ffffff;" +
                "-fx-background-radius: 5;" +
                "-fx-border-color: #606060;" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 5;" +
                "-fx-padding: 10 20;"
            );
        });
        
        button.setOnMouseExited(e -> {
            button.setStyle(
                "-fx-background-color: #2a2a2a;" +
                "-fx-text-fill: #ffffff;" +
                "-fx-background-radius: 5;" +
                "-fx-border-color: #404040;" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 5;" +
                "-fx-padding: 10 20;"
            );
        });
    }
    
    public static AboutPane getInstance() {
        if (instance == null) {
            instance = new AboutPane();
        }
        return instance;
    }
}