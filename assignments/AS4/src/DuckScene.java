import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

public class DuckScene extends Scene implements Function {
    private static WelcomePane welcomePane = new WelcomePane();
    private static GamePane gamePane = new GamePane();
    static boolean isSetting = false;
    static boolean isUnlimitedAmmo = false;
    private boolean isFirst = true;
    private boolean isEffectPlaying = false;

    DuckScene() {
        super(welcomePane, ScreenSize.SCREEN_WIDTH, ScreenSize.SCREEN_HEIGHT);
        gamePane = new GamePane(this);
        this.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (!isEffectPlaying)
                switch (key.getCode()) {
                    case ENTER:
                        if (isFirst)
                            if (!isSetting) {
                                isSetting = true;
                                welcomePane.navigateSettings();
                                System.out.println("navigate");
                            } else {
                                if (this.getRoot() != gamePane) {
                                    navigateGame();
                                }
                            }
                        isFirst = true;
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

    void setCurrentPane(Panes pane) {
        isFirst = false;
        isSetting = false;

        if (pane == Panes.GAME) {
            setGamePane();
        } else {
            welcomePane = new WelcomePane();
            this.setRoot(welcomePane);
        }
    }

    void navigateGame() {
        isEffectPlaying = true;
        welcomePane.finish();
        // play intro
        CustomMediaView introEffect = new CustomMediaView(Effects.INTRO, 1);
        introEffect.setOnfinished(this);
        welcomePane.getChildren().add(introEffect);

    }

    private void setGamePane() {
        gamePane = new GamePane(this);
        // get cross and background id
        int crossId = Math.abs(welcomePane.getCurrentCross() % welcomePane.crosshairs.length) + 1;
        int backgroundId = Math
                .abs(welcomePane.getCurrentBackgroundIndex() % welcomePane.backgrounds.length) + 1;
        // set background and cross in the game pane
        gamePane.setCrossId(crossId);
        gamePane.setBackgroundId(backgroundId);
        this.setRoot(gamePane);
        gamePane.setFocusTraversable(true);
        gamePane.requestFocus();
    }

    enum Panes {
        WELCOME,
        GAME
    }

    @Override
    public void onFinished() {
        setGamePane();
        isEffectPlaying = false;
    }
}
