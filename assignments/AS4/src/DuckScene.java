import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

public class DuckScene extends Scene {
    private static WelcomePane welcomePane = new WelcomePane();
    private static GamePane gamePane = new GamePane();
    static boolean isSetting = false;
    static boolean isUnlimitedAmmo = false;
    private boolean isFirst = true;

    DuckScene() {
        super(welcomePane, ScreenSize.SCREEN_WIDTH, ScreenSize.SCREEN_HEIGHT);
        gamePane = new GamePane(this);
        this.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            switch (key.getCode()) {
                case ENTER:
                    if (!isSetting) {
                        isSetting = true;
                        welcomePane.navigateSettings();
                        System.out.println("navigate");
                    } else {
                        if (this.getRoot() != gamePane) {
                            navigateGame();
                        }
                    }
                    break;
                case ESCAPE:
                    System.out.println("Escape");
                    if (this.getRoot() == welcomePane && isFirst)
                        System.exit(0);
                    isFirst = true;
                    break;
                case RIGHT:
                    System.out.println("Right");
                    if (isSetting) {
                        WelcomePane.currentBackgroundIndex++;
                        welcomePane.updateScreen();
                    }
                    break;
                case LEFT:
                    if (isSetting) {
                        WelcomePane.currentBackgroundIndex--;
                        welcomePane.updateScreen();
                    }
                    break;
                case UP:
                    if (isSetting) {
                        WelcomePane.currentCross++;
                        welcomePane.updateScreen();
                    }
                    break;
                case DOWN:
                    if (isSetting) {
                        WelcomePane.currentCross--;
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

    void navigateGame() {
        gamePane = new GamePane(this);
        int crossId = Math.abs(welcomePane.getCurrentCross() % welcomePane.crosshairs.length) + 1;
        int backgroundId = Math
                .abs(welcomePane.getCurrentBackgroundIndex() % welcomePane.backgrounds.length) + 1;
        System.out.println(crossId + " " + backgroundId);
        gamePane.setCrossId(crossId);
        gamePane.setBackgroundId(backgroundId);
        gamePane.setFocusTraversable(true);
        this.setRoot(gamePane);
        gamePane.requestFocus();
    }

    void setCurrentPane(Panes pane) {
        isFirst = false;
        gamePane = new GamePane(this);
        welcomePane = new WelcomePane();
        isSetting = false;

        if (pane == Panes.GAME)
            navigateGame();
        else
            this.setRoot(welcomePane);
    }

    enum Panes {
        WELCOME,
        GAME
    }
}
