import java.io.FileNotFoundException;

import javafx.scene.layout.StackPane;

/**
 * The WelcomePane class represents the pane displayed at the start of the game.
 * It contains the welcome image, text, and background/crosshair selection
 * options.
 */
public class WelcomePane extends StackPane {
    static int currentBackgroundIndex;
    static int currentCross;
    public CustomImage[] backgrounds;
    public CustomImage[] crosshairs;

    private final CustomImageView backgroundView = new CustomImageView();
    private final CustomImageView crossView = new CustomImageView();
    private final CustomMediaView titleEffect;

    /**
     * Constructs a new WelcomePane object.
     * Initializes the welcome image, text, and title effect.
     */
    WelcomePane() {
        getImages();
        CustomImageView welcomeImage;
        try {
            welcomeImage = new CustomImageView("1", Images.WELCOME);
            this.getChildren().add(welcomeImage);
        } catch (FileNotFoundException e) {
            System.out.println("Welcome image could not find");
        }
        Texts.welcomeTextFlow.setTranslateY(ScreenSize.getHeight(0.6));
        this.getChildren().add(Texts.welcomeTextFlow);
        titleEffect = new CustomMediaView(Effects.TITLE);
        titleEffect.playMedia();
        this.getChildren().add(titleEffect);
    }

    /**
     * Retrieves the background and crosshair images.
     */
    private void getImages() {
        backgrounds = new CustomImage[6];
        crosshairs = new CustomImage[7];
        for (int i = 0; i < crosshairs.length; i++) {
            try {
                crosshairs[i] = new CustomImage("" + (i + 1), Images.CROSSHAIR);
                backgrounds[i] = new CustomImage("" + (i + 1), Images.BACKGROUND);
            } catch (FileNotFoundException ex) {
                // Handle file not found exception
            }
        }
    }

    /**
     * Navigates to the settings screen.
     * Updates the screen with the selected background and crosshair.
     */
    public void navigateSettings() {
        System.out.println("navigate setting");
        updateScreen();
        backgroundView.setSize(backgroundView.getImage());
        crossView.setSize(crossView.getImage());
        this.getChildren().addAll(backgroundView, crossView);
        showSettingsText();
    }

    /**
     * Updates the screen with the selected background and crosshair.
     */
    public void updateScreen() {
        backgroundView.setImage(backgrounds[Math.abs(currentBackgroundIndex % backgrounds.length)]);
        crossView.setImage(crosshairs[Math.abs(currentCross % crosshairs.length)]);
        showSettingsText();
    }

    /**
     * Displays the settings text on the screen.
     */
    private void showSettingsText() {
        if (this.getChildren().contains(Texts.settingsTextFlow))
            this.getChildren().remove(Texts.settingsTextFlow);
        this.getChildren().add(Texts.settingsTextFlow);
    }

    /**
     * Returns the index of the currently selected crosshair.
     *
     * @return the current crosshair index
     */
    public int getCurrentCross() {
        return currentCross;
    }

    /**
     * Returns the index of the currently selected background.
     *
     * @return the current background index
     */
    public int getCurrentBackgroundIndex() {
        return currentBackgroundIndex;
    }

    /**
     * Stops the title effect animation.
     */
    public void finish() {
        titleEffect.pauseMedia();
    }
}
