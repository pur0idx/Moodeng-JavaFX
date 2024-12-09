package gui;

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
import logic.GameLogic;
import main.Main;
import sound.PlaySound;

public class MapSelectorPane extends BorderPane {
    private static MapSelectorPane instance;
    private final String[] MAP_NAMES = {"Beast", "Forest", "Jungle", "Apocalypse"};
    private final String[] MAP_FILES = {"beast.gif", "forest.gif", "jungle.gif", "apocalypse.gif"};
    private final String[] MAP_DESCRIPTIONS = {
        "A dark realm where fearsome beasts roam freely",
        "Dense forest teeming with magical creatures",
        "Wild jungle with hidden dangers and rewards",
        "A desolate wasteland where only the strongest survive"
    };
    
    public MapSelectorPane() {
        instance = this;
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
        VBox contentBox = new VBox(40);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new javafx.geometry.Insets(40));
        contentBox.setStyle(
            "-fx-background-color: rgba(0, 0, 0, 0.8);" +
            "-fx-border-color: #333333;" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 10;" +
            "-fx-background-radius: 10;"
        );

        Button backButton = new Button("BACK TO LOGIN");
        backButton.setFont(Font.font("Monospace", FontWeight.BOLD, 18));
        backButton.setPrefWidth(200);
        setupButtonStyle(backButton);
        
        backButton.setOnAction(e -> {
            try {
                LoginPane.resetInstance();
                MapSelectorPane.resetInstance();
                Main.getInstance().changeScene(LoginPane.getInstance());
            } catch (Exception ex) {
                System.err.println("Error returning to login: " + ex.getMessage());
            }
        });
        
        Text welcomeText = new Text(" WELCOME, " + LoginPane.getPlayerName().toUpperCase() + "!");
        welcomeText.setFont(Font.font("Monospace", FontWeight.BOLD, 32));
        welcomeText.setFill(Color.WHITE);
        welcomeText.setEffect(new DropShadow(10, Color.GOLD));
        welcomeText.setWrappingWidth(280);
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        
        Text subTitle = new Text("Select Your Battlefield");
        subTitle.setFont(Font.font("Monospace", FontWeight.MEDIUM, 24));
        subTitle.setFill(Color.LIGHTGRAY);
        
        Button aboutButton = new Button("HOW TO PLAY");
        aboutButton.setFont(Font.font("Monospace", FontWeight.BOLD, 18));
        aboutButton.setPrefWidth(200);
        setupButtonStyle(aboutButton);
        
        aboutButton.setOnAction(e -> {
            try {
                Main.getInstance().changeScene(AboutPane.getInstance());
            } catch (Exception ex) {
                System.err.println("Error returning to login: " + ex.getMessage());
            }
        });
        
        contentBox.getChildren().addAll(
            backButton,
            welcomeText,
            subTitle,
            aboutButton
        );

        VBox leftPanel = new VBox(contentBox);
        leftPanel.setAlignment(Pos.CENTER);
        BorderPane.setMargin(leftPanel, new javafx.geometry.Insets(0, 0, 0, 100));

        GridPane mapsGrid = createMapsGrid();
        mapsGrid.setAlignment(Pos.CENTER);
        mapsGrid.setHgap(20);
        mapsGrid.setVgap(20);
        
        StackPane centerContainer = new StackPane(mapsGrid);
        centerContainer.setAlignment(Pos.CENTER);
        centerContainer.setPadding(new javafx.geometry.Insets(0, 20, 0, 20));
        
        setLeft(leftPanel);
        setCenter(centerContainer);
    }
    
    private GridPane createMapsGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(30);
        grid.setVgap(30);
        grid.setPadding(new javafx.geometry.Insets(20));
        
        for (int i = 0; i < MAP_NAMES.length; i++) {
            VBox mapCard = createMapCard(i);
            grid.add(mapCard, i % 2, i / 2);
        }
        
        return grid;
    }
    
    private VBox createMapCard(int index) {
        VBox card = new VBox(15);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new javafx.geometry.Insets(15));
        card.setStyle(
            "-fx-background-color: rgba(0, 0, 0, 0.8);" +
            "-fx-border-color: #333333;" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 10;" +
            "-fx-background-radius: 10;"
        );
        card.setPrefSize(280, 300);
        
        try {
        	Image mapImage = new Image(ClassLoader.getSystemResource(MAP_FILES[index]).toString());
        	ImageView imageView = new ImageView(mapImage);

        	imageView.setFitWidth(200);
        	imageView.setFitHeight(120);
        	imageView.setPreserveRatio(true);
        	imageView.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 0);");

        	double width = Math.min(mapImage.getWidth(), mapImage.getHeight() * (200.0/120.0));
        	double height = Math.min(mapImage.getHeight(), mapImage.getWidth() * (120.0/200.0));
        	double x = (mapImage.getWidth() - width) / 2;
        	double y = (mapImage.getHeight() - height) / 2;
        	imageView.setViewport(new javafx.geometry.Rectangle2D(x, y, width, height));

        	StackPane imageContainer = new StackPane(imageView);
        	imageContainer.setMinSize(200, 120);
        	imageContainer.setMaxSize(200, 120);
        	imageContainer.setStyle("-fx-background-color: black;");

        	card.getChildren().add(imageContainer);
        } catch (Exception e) {
            System.err.println("Failed to load map image: " + e.getMessage());
        }
        
        Text mapTitle = new Text(MAP_NAMES[index].toUpperCase());
        mapTitle.setFont(Font.font("Monospace", FontWeight.BOLD, 22));
        mapTitle.setFill(Color.WHITE);
        
        Text description = new Text(MAP_DESCRIPTIONS[index]);
        description.setFont(Font.font("Monospace", FontWeight.NORMAL, 14));
        description.setFill(Color.LIGHTGRAY);
        description.setTextAlignment(TextAlignment.CENTER);
        description.setWrappingWidth(230);
        
        Button playButton = createPlayButton(index);
        playButton.setPrefWidth(180);
        
        card.getChildren().addAll(mapTitle, description, playButton);
        setupCardHoverEffect(card);
        
        return card;
    }
    
    private Button createPlayButton(int index) {
        Button button = new Button("PLAY NOW");
        button.setFont(Font.font("Monospace", FontWeight.BOLD, 18));
        button.setPrefWidth(200);
        setupButtonStyle(button);
        
        button.setOnAction(e -> {
        try {
//            PlaySound.playBackgroundMusic().stop();
            GameLogic.setCurrentMap(MAP_NAMES[index] + "Map");
            if (index == 1) {
                Main.getInstance().changeScene(new ForestMapPane());
            }
            if (index == 2) {
                Main.getInstance().changeScene(new JungleMapPane());
            }
            // case 0 -> Main.getInstance().changeScene(new CaveMapPane());
            // case 2 -> Main.getInstance().changeScene(new FactoryMapPane());
            // case 3 -> Main.getInstance().changeScene(new JungleMapPane());
        } catch (Exception ex) {
            System.err.println("Error changing map: " + ex.getMessage());
            ex.printStackTrace();
        }
    });
        
        return button;
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
    
    private void setupCardHoverEffect(VBox card) {
        card.setOnMouseEntered(e -> {
            card.setStyle(
                "-fx-background-color: rgba(20, 20, 20, 0.9);" +
                "-fx-border-color: #666666;" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 10;" +
                "-fx-background-radius: 10;"
            );
        });
        
        card.setOnMouseExited(e -> {
            card.setStyle(
                "-fx-background-color: rgba(0, 0, 0, 0.8);" +
                "-fx-border-color: #333333;" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 10;" +
                "-fx-background-radius: 10;"
            );
        });
    }
    
    public static MapSelectorPane getInstance() {
        if (instance == null) {
            instance = new MapSelectorPane();
        }
        return instance;
    }
    
    public static void resetInstance() {
        instance = null;
    }
}