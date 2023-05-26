import java.io.FileNotFoundException;

import javafx.scene.layout.StackPane;

public class WelcomePane extends StackPane {
    static int currentBackgroundIndex;
    static int currentCross;
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
        Texts.welcomeTextFlow.setTranslateY(ScreenSize.getHeight(0.6));
        FadeAnimation.animate(Texts.welcomeTextFlow);
        this.getChildren().add(Texts.welcomeTextFlow);
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

    public void navigateSettings() {
        System.out.println("navigate setting");
        updateScreen();
        backgroundView.setSize(backgroundView.getImage());
        crossView.setSize(crossView.getImage());
        this.getChildren().addAll(backgroundView, crossView);
        showSettingsText();
    }

    public void updateScreen() {
        backgroundView.setImage(backgrounds[Math.abs(currentBackgroundIndex % backgrounds.length)]);
        crossView.setImage(crosshairs[Math.abs(currentCross % crosshairs.length)]);
        showSettingsText();
    }

    private void showSettingsText() {
        if (this.getChildren().contains(Texts.settingsTextFlow))
            this.getChildren().remove(Texts.settingsTextFlow);
        this.getChildren().add(Texts.settingsTextFlow);
    }

    public int getCurrentCross() {
        return currentCross;
    }

    public int getCurrentBackgroundIndex() {
        return currentBackgroundIndex;
    }
}
