package components;

import javafx.scene.CacheHint;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SoundButton {
    private static final int SOUND_BUTTON_SIZE = 60;
    private static boolean currentMusicState;

    public static Button createMusicButton(boolean initialMusicState, OnMusicToggleListener listener) {
        currentMusicState = initialMusicState;
        Button musicButton = new Button();
        musicButton.setPrefSize(70, 70);

        ImageView imageView = createImageView(currentMusicState);
        musicButton.setGraphic(imageView);

        musicButton.setStyle("-fx-background-color: transparent;");

        musicButton.setOnMouseEntered(e ->
            musicButton.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1);")
        );

        musicButton.setOnMouseExited(e ->
            musicButton.setStyle("-fx-background-color: transparent;")
        );

        musicButton.setOnAction(e -> {
            currentMusicState = !currentMusicState;
            ImageView newImageView = createImageView(currentMusicState);
            musicButton.setGraphic(newImageView);
            listener.onMusicToggle(currentMusicState);
        });

        return musicButton;
    }

    private static ImageView createImageView(boolean isMusicPlaying) {
        try {
            Image image = new Image(
                ClassLoader.getSystemResource(isMusicPlaying ? "sound_on.png" : "sound_off.png").toString()
            );
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(false);
            imageView.setCache(false);
            imageView.setCacheHint(CacheHint.SPEED);
            imageView.setFitWidth(SOUND_BUTTON_SIZE);
            imageView.setFitHeight(SOUND_BUTTON_SIZE);
            return imageView;
        } catch (Exception e) {
            System.err.println("Failed to load music icon: " + e.getMessage());
            return new ImageView();
        }
    }

    public interface OnMusicToggleListener {
        void onMusicToggle(boolean isMusicPlaying);
    }
}