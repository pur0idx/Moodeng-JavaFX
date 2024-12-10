package logic;

import character.Moodeng;
import components.ScoreBoard;
import gui.ForestMapPane;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

import java.util.HashMap;
import java.util.HashSet;

public class GameLogic {
	private static boolean isGameOver = false;
    private static final HashSet<KeyCode> activeKeys = new HashSet<>();
    private static final HashMap<String, Integer> highScores = new HashMap<>();
    private static AnimationTimer gameLoop;
    private static String currentMap;

    public static String getCurrentMap() {
        return currentMap;
    }

    public static void setCurrentMap(String mapName) {
        currentMap = mapName;
    }

    public static ImageView getGroundImage(String imageName) {
        try {
            Image image = new Image(ClassLoader.getSystemResource("grounds/" + imageName).toString());
            ImageView groundView = new ImageView(image);
            groundView.setFitWidth(ForestMapPane.getWindowWidth());
            groundView.setPreserveRatio(true);
            return groundView;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void getPlayerInput(AnchorPane gamePane) {
        gamePane.setOnKeyPressed(event -> {
            activeKeys.add(event.getCode());
            if (event.getCode() == KeyCode.SPACE) {
                Moodeng.getInstance().jump();
            }
        });

        gamePane.setOnKeyReleased(event -> {
            activeKeys.remove(event.getCode());
            if (!(activeKeys.contains(KeyCode.LEFT) || activeKeys.contains(KeyCode.A)) && !(activeKeys.contains(KeyCode.RIGHT) || (activeKeys.contains(KeyCode.D)))) {
                Moodeng.getInstance().idle();
            }
        });

        gamePane.setFocusTraversable(true);
    }

    public static void updateGame(AnchorPane gamePane) {
        if (gameLoop != null) {
            gameLoop.stop();
        }

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (isGameOver) {
                    this.stop();
                    return;
                }

                // Handle player movement
                if (activeKeys.contains(KeyCode.LEFT) || activeKeys.contains(KeyCode.A)) {
                    Moodeng.getInstance().moveLeft();
                }
                if (activeKeys.contains(KeyCode.RIGHT) || activeKeys.contains(KeyCode.D)) {
                    Moodeng.getInstance().moveRight();
                }
                
                // Check fruit collisions
                for (Node node : gamePane.getChildren()) {
                    if (node instanceof ImageView && ScoreBoard.isFruit((ImageView)node)) {
                        ImageView fruitView = (ImageView)node;
                        if (fruitView.isVisible()) {
                            String fruitType = ScoreBoard.getFruitType(fruitView);
                            checkFruitCollision(gamePane, fruitView, fruitType);
                        }
                    }
                }
                
                if (!gamePane.isFocused()) {
                    gamePane.requestFocus();
                }
            }
        };

        gameLoop.start();
    }

    public static void setHighScoreEachMap(String mapName, int score) {
        highScores.put(mapName, Math.max(highScores.getOrDefault(mapName, 0), score));
    }

    public static int getHighScore(String mapName) {
        return highScores.getOrDefault(mapName, 0);
    }

    public static boolean isGameOver() {
        return isGameOver;
    }

    public static void setIsGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public static void stopGame() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }
    
    public static void checkFruitCollision(AnchorPane gamePane, ImageView fruit, String fruitType) {
        if (fruit == null || !fruit.isVisible()) return;

        Moodeng player = Moodeng.getInstance();
        if (player.getMoodengImageView().getBoundsInParent().intersects(fruit.getBoundsInParent())) {
            System.out.println("Collected " + fruitType); // Debug print
            
            // Use ScoreBoard's increment method directly
            ScoreBoard.getInstance().incrementFruitCount(fruitType);
            fruit.setVisible(false);
        }
    }
}