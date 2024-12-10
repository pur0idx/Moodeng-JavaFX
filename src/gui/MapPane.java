package gui;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import character.BaseEnemy;
import character.Moodeng;
import components.ExitButton;
import components.ScoreBoard;
import components.HpBoard;
import logic.GameLogic;
import objects.BasePowerUp;
import objects.PowerUpFactory;
import sound.PlaySound;

public abstract class MapPane extends AnchorPane {
    protected static final double WINDOW_WIDTH = 1280;
    protected static final double WINDOW_HEIGHT = 720;
    protected static final double GROUND_Y = WINDOW_HEIGHT - 190;
    protected static final double CHARACTER_Y = GROUND_Y + 25;
    private static final double SPAWN_INTERVAL = 3.0;
    private static final double MIN_SPAWN_X = 50;
    private static final double MAX_SPAWN_X = WINDOW_WIDTH - 50;
    
    protected Timeline itemSpawner;
    protected List<BasePowerUp> activePowerUps;
    protected HpBoard hpBoard;
    protected ScoreBoard scoreBoard;
    protected Moodeng moodeng;
    protected List<BaseEnemy> enemies;
    
    public MapPane(String mapName, String backgroundFileName, String groundFileName) {
        setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setupBackground(backgroundFileName);
        setupGround(groundFileName);
        initializeCharacter();
        setupHUD();
        setupExitButton(mapName);
        
        activePowerUps = new ArrayList<>();
        setupItemSpawner();
        setupCollisionDetection();
        
//        PlaySound.stopAllMapBG();
        
        setupGame();
        customMapSetup();
    }
    
    private void setupBackground(String backgroundFileName) {
        try {
            Image bgImage = new Image(ClassLoader.getSystemResource(backgroundFileName).toString());
            BackgroundImage backgroundImage = new BackgroundImage(
                bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true)
            );
            setBackground(new Background(backgroundImage));
        } catch (Exception e) {
            System.err.println("Error loading background: " + e.getMessage());
        }
    }
    
    private void setupGround(String groundFileName) {
        try {
            Image groundImage = new Image(ClassLoader.getSystemResource(groundFileName).toString());
            ImageView groundImageView = new ImageView(groundImage);
            groundImageView.setFitWidth(WINDOW_WIDTH);
            groundImageView.setPreserveRatio(true);
            setBottomAnchor(groundImageView, 0.0);
            setLeftAnchor(groundImageView, 0.0);
            getChildren().add(groundImageView);
        } catch (Exception e) {
            System.err.println("Error loading ground: " + e.getMessage());
        }
    }

    protected void initializeCharacter() {
        moodeng = Moodeng.getInstance();
        moodeng.setPosX(50);
        
        ImageView moodengView = moodeng.getMoodengImageView();
        if (moodengView != null) {
            setTopAnchor(moodengView, CHARACTER_Y);
            setLeftAnchor(moodengView, moodeng.getPosX());
            getChildren().add(moodengView);
        }
    }
    
    protected void setupEnemies() {
        enemies = new ArrayList<>();
    }
    
    protected void addEnemy(BaseEnemy enemy, double startX, double startY) {
        enemy.setPosX(startX);
        enemy.setPosY(startY);
        enemy.updatePosition();
        enemies.add(enemy);
        getChildren().add(enemy.getEnemyImageView());
    }

    private void setupHUD() {
        hpBoard = HpBoard.getInstance();
        HpBoard.updateHpBoard();
        setTopAnchor(hpBoard, 20.0);
        setLeftAnchor(hpBoard, 20.0);

        scoreBoard = ScoreBoard.getInstance();
        scoreBoard.setScoreboard();
        setTopAnchor(scoreBoard, 20.0);
        setRightAnchor(scoreBoard, 120.0);

        getChildren().addAll(hpBoard, scoreBoard);
    }

    private void setupExitButton(String mapName) {
        ExitButton exitButton = new ExitButton();
        getChildren().add(exitButton);
        exitButton.setupExitHandler(this, mapName, moodeng);
    }

    protected void setupGame() {
        setFocusTraversable(true);
        GameLogic.setIsGameOver(false);
        GameLogic.getPlayerInput(this);
        GameLogic.updateGame(this);
    }

    protected abstract void customMapSetup();
    
    public static double getWindowWidth() {
        return WINDOW_WIDTH;
    }
    
    public static double getWindowHeight() {
        return WINDOW_HEIGHT;
    }
    
    private void setupItemSpawner() {
        itemSpawner = new Timeline(
            new KeyFrame(Duration.seconds(SPAWN_INTERVAL), event -> spawnRandomItem())
        );
        itemSpawner.setCycleCount(Timeline.INDEFINITE);
        itemSpawner.play();
    }

    private void spawnRandomItem() {
        BasePowerUp powerUp = PowerUpFactory.createRandomPowerUp();
        
        double randomX = MIN_SPAWN_X + Math.random() * (MAX_SPAWN_X - MIN_SPAWN_X);
        powerUp.setTranslateX(randomX);
        powerUp.setTranslateY(-50);
        
        TranslateTransition fall = new TranslateTransition(Duration.seconds(4), powerUp);
        fall.setByY(WINDOW_HEIGHT + 100);
        fall.setOnFinished(e -> getChildren().remove(powerUp));
        
        getChildren().add(powerUp);
        activePowerUps.add(powerUp);
        fall.play();
    }

    private void setupCollisionDetection() {
        AnimationTimer collisionTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                checkCollisions();
            }
        };
        collisionTimer.start();
    }

    private void checkCollisions() {
        Iterator<BasePowerUp> iterator = activePowerUps.iterator();
        while (iterator.hasNext()) {
            BasePowerUp powerUp = iterator.next();
            if (powerUp.getBoundsInParent().intersects(
                moodeng.getMoodengImageView().getBoundsInParent())) {
                
            	
            	scoreBoard.incrementFruitCount(powerUp.getFruitType());
            	
                powerUp.onCollect(moodeng);
                showPowerUpText(powerUp.getEffectName());
                
                getChildren().remove(powerUp);
                iterator.remove();
            }
        }
    }

    private void showPowerUpText(String effectName) {
        Text text = new Text(effectName);
        text.setFill(Color.YELLOW);
        text.setStroke(Color.BLACK);
        text.setStrokeWidth(1);
        text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        
        text.setTranslateX(moodeng.getPosX());
        text.setTranslateY(moodeng.getPosY() - 30);
        
        FadeTransition fade = new FadeTransition(Duration.seconds(1), text);
        fade.setFromValue(1);
        fade.setToValue(0);
        
        TranslateTransition flt = new TranslateTransition(Duration.seconds(1), text);
        flt.setByY(-30);
        
        getChildren().add(text);
        
        ParallelTransition transition = new ParallelTransition(fade, flt);
        transition.setOnFinished(e -> getChildren().remove(text));
        transition.play();
    }

    protected void cleanup() {
        if (itemSpawner != null) {
            itemSpawner.stop();
        }
        activePowerUps.clear();
    }
}