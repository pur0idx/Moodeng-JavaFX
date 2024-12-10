package components;

import character.Moodeng;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ScoreBoard extends HBox {
    private static ScoreBoard instance;
    private Label watermelonCount;
    private Label coconutCount;
    private Label bananaCount;
    private Label pineappleCount;
    
    private ScoreBoard() {
        setAlignment(Pos.CENTER);
        setSpacing(20);
        setPadding(new Insets(10));
        
        watermelonCount = createFruitCounter("watermelon.png");
        coconutCount = createFruitCounter("coconut.png");
        bananaCount = createFruitCounter("banana.png");
        pineappleCount = createFruitCounter("pineapple.png");
        
        getChildren().addAll(
            watermelonCount.getParent(),
            coconutCount.getParent(),
            bananaCount.getParent(),
            pineappleCount.getParent()
        );
    }
    
    private Label createFruitCounter(String imageName) {
        HBox fruitBox = new HBox(5);
        fruitBox.setAlignment(Pos.CENTER);
        
        try {
            Image fruitImage = new Image(ClassLoader.getSystemResource(imageName).toString(),
                    32, 32, true, true);  // Slightly larger icons
            ImageView fruitIcon = new ImageView(fruitImage);
            fruitIcon.setPreserveRatio(true);
            fruitIcon.setSmooth(false);
            
            Label countLabel = new Label("0");
            countLabel.setFont(Font.font("Arial", 24));
            countLabel.setTextFill(Color.WHITE);
            countLabel.setMinWidth(40);
            countLabel.setAlignment(Pos.CENTER_LEFT);
            
            fruitBox.getChildren().addAll(fruitIcon, countLabel);
            return countLabel;
            
        } catch (Exception e) {
            System.err.println("Error loading fruit icon " + imageName + ": " + e.getMessage());
            return new Label("0");
        }
    }

    public void updateFruitCount(String fruitType) {
        switch(fruitType) {
            case "watermelon":
                watermelonCount.setText(String.valueOf(Moodeng.getInstance().getWatermelons()));
                break;
            case "coconut":
                coconutCount.setText(String.valueOf(Moodeng.getInstance().getCoconuts()));
                break;
            case "banana":
                bananaCount.setText(String.valueOf(Moodeng.getInstance().getBananas()));
                break;
            case "pineapple":
                pineappleCount.setText(String.valueOf(Moodeng.getInstance().getPineapples()));
                break;
        }
    }
    
    public void setScoreboard() {
        Moodeng player = Moodeng.getInstance();
        watermelonCount.setText(String.valueOf(player.getWatermelons()));
        coconutCount.setText(String.valueOf(player.getCoconuts()));
        bananaCount.setText(String.valueOf(player.getBananas()));
        pineappleCount.setText(String.valueOf(player.getPineapples()));
    }
    
    public static ScoreBoard getInstance() {
        if (instance == null) {
            instance = new ScoreBoard();
        }
        return instance;
    }
}