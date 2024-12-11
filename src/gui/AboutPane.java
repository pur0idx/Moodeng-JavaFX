package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        VBox contentBox = new VBox(20);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setMaxWidth(1100);
        contentBox.setMaxHeight(650);
        contentBox.setPadding(new Insets(30));
        contentBox.setStyle(
            "-fx-background-color: rgba(0, 0, 0, 0.8);" +
            "-fx-border-color: #333333;" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 10;" +
            "-fx-background-radius: 10;"
        );

        Text title = new Text("HOW TO PLAY");
        title.setFont(Font.font("Monospace", FontWeight.BOLD, 32));
        title.setFill(Color.WHITE);
        title.setEffect(new DropShadow(10, Color.GOLD));
        
        VBox instructionsBox = createInstructionsBox();
        
        Button backButton = new Button("BACK");
        backButton.setFont(Font.font("Monospace", FontWeight.BOLD, 18));
        backButton.setPrefWidth(250);
        setupButtonStyle(backButton);
        
        backButton.setOnAction(e -> {
            try {
                Main.getInstance().changeScene(MapSelectorPane.getInstance());
            } catch (Exception ex) {
                System.err.println("Error returning to map: " + ex.getMessage());
            }
        });

        contentBox.getChildren().addAll(title, instructionsBox, backButton);
        
        BorderPane.setAlignment(contentBox, Pos.CENTER);
        setCenter(contentBox);
    }
    
    private VBox createInstructionsBox() {
        VBox box = new VBox(15);
        box.setAlignment(Pos.CENTER);
        
        Image howToImage = new Image(ClassLoader.getSystemResource("howto.png").toString());
        ImageView imageView = new ImageView(howToImage);
        
        imageView.setFitWidth(900);
        imageView.setPreserveRatio(true);
        
        box.getChildren().add(imageView);
        
        return box;
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