package logic;

import character.Moodeng;
import gui.ForestMapPane;
import javafx.animation.AnimationTimer;
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
        });

        gamePane.setOnKeyReleased(event -> {
            activeKeys.remove(event.getCode());
            if (!activeKeys.contains(KeyCode.LEFT) && !activeKeys.contains(KeyCode.RIGHT)) {
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

                if (activeKeys.contains(KeyCode.LEFT)) {
                    Moodeng.getInstance().moveLeft();
                }
                if (activeKeys.contains(KeyCode.RIGHT)) {
                    Moodeng.getInstance().moveRight();
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
}