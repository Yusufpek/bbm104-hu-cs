import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

public class DuckScene extends Scene {
    final private static WelcomePane welcomePane = new WelcomePane();
    final private static GamePane gamePane = new GamePane();
    static boolean isSetting = false;
    static boolean isUnlimitedAmmo = false;

    DuckScene() {
        super(welcomePane, ScreenSize.SCREEN_WIDTH, ScreenSize.SCREEN_HEIGHT);
        this.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            switch (key.getCode()) {
                case ENTER:
                    if (!isSetting) {
                        isSetting = true;
                        welcomePane.navigateSettings();
                        System.out.println("navigate");
                    } else {
                        if (this.getRoot() != gamePane) {
                            int crossId = Math.abs(welcomePane.getCurrentCross() % welcomePane.crosshairs.length) + 1;
                            int backgroundId = Math
                                    .abs(welcomePane.getCurrentBackgroundIndex() % welcomePane.backgrounds.length) + 1;
                            gamePane.setCrossId(crossId);
                            gamePane.setBackgroundId(backgroundId);
                            gamePane.setFocusTraversable(true);
                            this.setRoot(gamePane);
                            gamePane.requestFocus();
                        }
                    }
                    break;
                case ESCAPE:
                    System.out.println("Escape");
                    if (this.getRoot() == welcomePane)
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
                        isUnlimitedAmmo = !isUnlimitedAmmo;
                        System.out.println("unlimited ammo");
                    }
                    break;
                default:
                    break;
            }
        });
    }

}
