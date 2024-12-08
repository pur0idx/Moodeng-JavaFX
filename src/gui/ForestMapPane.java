package gui;

import javafx.geometry.Side;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import character.Moodeng;
import components.ExitButton;
import components.ScoreBoard;
import components.HpBoard;
import logic.GameLogic;
import sound.PlaySound;

public class ForestMapPane extends AnchorPane {
    private static ForestMapPane instance;
    private HpBoard hpBoard;
    private ScoreBoard scoreBoard;
    private Moodeng moodeng;
    
    private static final double WINDOW_WIDTH = 1280;
    private static final double WINDOW_HEIGHT = 720;
    private static final double GROUND_Y = WINDOW_HEIGHT - 190;
    private static final double CHARACTER_Y = GROUND_Y + 25;

    public ForestMapPane() {
        setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        
        try {
            Image bgImage = new Image(ClassLoader.getSystemResource("forest.gif").toString());
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

        try {
            Image groundImage = new Image(ClassLoader.getSystemResource("forest_ground.png").toString());
            ImageView groundImageView = new ImageView(groundImage);
            groundImageView.setFitWidth(WINDOW_WIDTH);
            groundImageView.setPreserveRatio(true);
            setBottomAnchor(groundImageView, 0.0);
            setLeftAnchor(groundImageView, 0.0);
            getChildren().add(groundImageView);
        } catch (Exception e) {
            System.err.println("Error loading ground: " + e.getMessage());
        }

        initializeCharacter();
        setupHUD();
        setupExitButton();
        setupGame();
    }

    private void initializeCharacter() {
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

    private void setupExitButton() {
        ExitButton exitButton = new ExitButton();
        getChildren().add(exitButton);
        exitButton.setupExitHandler(this, "ForestMap", moodeng);
    }

    private void setupGame() {
        setFocusTraversable(true);
        
        GameLogic.setIsGameOver(false);
        
//        PlaySound.playForestMusic();
        
        GameLogic.getPlayerInput(this);
        
        GameLogic.updateGame(this);
    }

    @Override
    public String toString() {
        return "ForestMap";
    }

    public static ForestMapPane getInstance() {
        if (instance == null) {
            instance = new ForestMapPane();
        }
        return instance;
    }
    
    public static double getWindowWidth() {
        return WINDOW_WIDTH;
    }
    
    public static double getWindowHeight() {
        return WINDOW_HEIGHT;
    }
}