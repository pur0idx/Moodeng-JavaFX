package components;

import character.Moodeng;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class HpBoard extends HBox {
    private static HpBoard instance;
    private static final int HEART_SIZE = 30;
    
    private HpBoard() {
        setSpacing(5);
    }
    
    public static void updateHpBoard() {
        HpBoard board = getInstance();
        board.getChildren().clear();
        
        int currentHp = Moodeng.getInstance().getHp();
        for (int i = 0; i < currentHp; i++) {
            ImageView heartImage = new ImageView(
                new Image(ClassLoader.getSystemResource("heart.png").toString())
            );
            heartImage.setFitWidth(HEART_SIZE);
            heartImage.setFitHeight(HEART_SIZE);
            board.getChildren().add(heartImage);
        }
    }
    
    public static HpBoard getInstance() {
        if (instance == null) {
            instance = new HpBoard();
        }
        return instance;
    }
}