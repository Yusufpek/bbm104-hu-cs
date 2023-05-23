import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class MainApplication extends Application {
    static boolean isSetting = false;
    static boolean isUnlimitedAmmo = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        WelcomePane welcomePane = new WelcomePane();
        GamePane gamePane = new GamePane();

        Scene scene = new Scene(welcomePane, ScreenSize.SCREEN_WIDTH, ScreenSize.SCREEN_HEIGHT);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            switch (key.getCode()) {
                case ENTER:
                    if (!isSetting) {
                        MainApplication.isSetting = true;
                        welcomePane.navigateSettings();
                        System.out.println("navigate");
                    } else {
                        if (scene.getRoot() != gamePane) {
                            int crossId = Math.abs(welcomePane.getCurrentCross() % welcomePane.crosshairs.length) + 1;
                            int backgroundId = Math
                                    .abs(welcomePane.getCurrentBackgroundIndex() % welcomePane.backgrounds.length) + 1;
                            gamePane.setCrossId(crossId);
                            gamePane.setBackgroundId(backgroundId);
                            gamePane.setFocusTraversable(true);
                            scene.setRoot(gamePane);
                            gamePane.requestFocus();
                        }
                    }
                    break;
                case ESCAPE:
                    System.out.println("Escape");
                    System.exit(0);
                    break;
                case RIGHT:
                    System.out.println("Right");
                    if (isSetting) {
                        welcomePane.currentBackgroundIndex++;
                        welcomePane.updateScreen();
                    }
                    break;
                case LEFT:
                    if (isSetting) {
                        welcomePane.currentBackgroundIndex--;
                        welcomePane.updateScreen();
                    }
                    break;
                case UP:
                    if (isSetting) {
                        welcomePane.currentCross++;
                        welcomePane.updateScreen();
                    }
                    break;
                case DOWN:
                    if (isSetting) {
                        welcomePane.currentCross--;
                        welcomePane.updateScreen();
                    }
                    break;
                case U:
                    if (isSetting) {
                        MainApplication.isUnlimitedAmmo = !MainApplication.isUnlimitedAmmo;
                        System.out.println("unlimited ammo");
                    }
                    break;
                default:
                    break;
            }
        });

        primaryStage.setTitle(Texts.WINDOW_TEXT);
        primaryStage.getIcons().add(new CustomImage("1", Images.FAVICON));
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}