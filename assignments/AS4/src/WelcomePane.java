import java.io.FileNotFoundException;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

public class WelcomePane extends StackPane {
    int currentBackgroundIndex;
    int currentCross;
    public CustomImage[] backgrounds;
    public CustomImage[] crosshairs;

    private final CustomImageView backgroundView = new CustomImageView();
    private final CustomImageView crossView = new CustomImageView();

    WelcomePane() {
        getImages();
        CustomImageView welcomeImage;
        try {
            welcomeImage = new CustomImageView("1", Images.WELCOME);
            this.getChildren().add(welcomeImage);
        } catch (FileNotFoundException e) {
            System.out.println("Welcome image could not find");
        }
        TextFlow textflow = new TextFlow(Texts.welcomeEnterTextWidget, Texts.welcomeEscTextWidget);
        textflow.setTranslateY(ScreenSize.getHeight(0.6));
        animation(textflow);
        this.getChildren().add(textflow);
    }

    private void getImages() {
        backgrounds = new CustomImage[6];
        crosshairs = new CustomImage[7];
        for (int i = 0; i < crosshairs.length; i++) {
            try {
                crosshairs[i] = new CustomImage("" + (i + 1), Images.CROSSHAIR);
                backgrounds[i] = new CustomImage("" + (i + 1), Images.BACKGROUND);
            } catch (FileNotFoundException ex) {
            }
        }
    }

    private void animation(Node node) {
        FadeTransition fade = new FadeTransition();
        fade.setNode(node);
        fade.setDuration(Duration.millis(1500));
        fade.setCycleCount(TranslateTransition.INDEFINITE);
        fade.setInterpolator(Interpolator.LINEAR);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.play();
    }

    public void navigateSettings() {
        System.out.println("navigate setting");
        updateScreen();
        backgroundView.setSize(backgroundView.getImage());
        crossView.setSize(crossView.getImage());
        this.getChildren().addAll(backgroundView, crossView);
    }

    public void updateScreen() {
        backgroundView.setImage(backgrounds[Math.abs(currentBackgroundIndex % backgrounds.length)]);
        crossView.setImage(crosshairs[Math.abs(currentCross % crosshairs.length)]);
    }

    public int getCurrentCross() {
        return 0;
    }

    public int getCurrentBackgroundIndex() {
        return currentBackgroundIndex;
    }
}
