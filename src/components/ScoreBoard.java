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
import types.FruitType;

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
                    32, 32, true, true);
            ImageView fruitIcon = new ImageView(fruitImage);
            fruitIcon.setPreserveRatio(true);
            fruitIcon.setSmooth(false);
            
            String fruitType = imageName.replace(".png", "");
            fruitIcon.setUserData(fruitType);
            fruitIcon.setId(fruitType);
            
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
    
    public static boolean isFruit(ImageView imageView) {
        String id = imageView.getId();
        return id != null && (
            id.equals("watermelon") ||
            id.equals("coconut") ||
            id.equals("banana") ||
            id.equals("pineapple")
        );
    }
    
    public static String getFruitType(ImageView imageView) {
        return imageView.getId();
    }
    
    public void updateFruitCount(FruitType fruitType) {
        switch(fruitType) {
            case WATERMELON:
                watermelonCount.setText(String.valueOf(Moodeng.getInstance().getWatermelons()));
                break;
            case COCONUT:
                coconutCount.setText(String.valueOf(Moodeng.getInstance().getCoconuts()));
                break;
            case BANANA:
                bananaCount.setText(String.valueOf(Moodeng.getInstance().getBananas()));
                break;
            case PINEAPPLE:
                pineappleCount.setText(String.valueOf(Moodeng.getInstance().getPineapples()));
                break;
        }
    }
    
    public void incrementFruitCount(FruitType fruitType) {
        Moodeng player = Moodeng.getInstance();
        switch(fruitType) {
            case WATERMELON:
                player.addWatermelon();
                watermelonCount.setText(String.valueOf(player.getWatermelons()));
                break;
            case COCONUT:
                player.addCoconut();
                coconutCount.setText(String.valueOf(player.getCoconuts()));
                break;
            case BANANA:
                player.addBanana();
                bananaCount.setText(String.valueOf(player.getBananas()));
                break;
            case PINEAPPLE:
                player.addPineapple();
                pineappleCount.setText(String.valueOf(player.getPineapples()));
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
    
    public void resetScoreboard() {
        Moodeng moodeng = Moodeng.getInstance();
        moodeng.resetFruits();
        watermelonCount.setText("0");
        coconutCount.setText("0");
        bananaCount.setText("0");
        pineappleCount.setText("0");
    }
    
    public static ScoreBoard getInstance() {
        if (instance == null) {
            instance = new ScoreBoard();
        }
        return instance;
    }
}