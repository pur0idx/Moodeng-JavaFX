package gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import character.Moodeng;
import components.ExitButton;
import components.ScoreBoard;
import components.HpBoard;
import logic.GameLogic;
import sound.PlaySound;

public abstract class MapPane extends AnchorPane {
    protected static final double WINDOW_WIDTH = 1280;
    protected static final double WINDOW_HEIGHT = 720;
    protected static final double GROUND_Y = WINDOW_HEIGHT - 190;
    protected static final double CHARACTER_Y = GROUND_Y + 25;

    protected HpBoard hpBoard;
    protected ScoreBoard scoreBoard;
    protected Moodeng moodeng;
    
    public MapPane(String mapName, String backgroundFileName, String groundFileName) {
        setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setupBackground(backgroundFileName);
        setupGround(groundFileName);
        initializeCharacter();
        setupHUD();
        setupExitButton(mapName);
        
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
                new BackgroundSize(BackgroundSize.AUTO, 
                                 BackgroundSize.AUTO,
                                 false, false, false, false)
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
}