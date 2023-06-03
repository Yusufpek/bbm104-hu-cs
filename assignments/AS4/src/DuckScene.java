import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

/**
 * A custom scene for the Duck Hunt game.
 * Implements the Function interface.
 */
public class DuckScene extends Scene implements Function {
    private static WelcomePane welcomePane = new WelcomePane();
    private static GamePane gamePane = new GamePane();
    static boolean isSetting = false;
    static boolean isUnlimitedAmmo = false;
    private boolean isFirst = true;
    private boolean isEffectPlaying = false;

    /**
     * Constructs a DuckScene object with the WelcomePane as the initial root and
     * specified width, height size.
     * Check for the pressed key.
     */
    DuckScene() {
        super(welcomePane, ScreenSize.SCREEN_WIDTH, ScreenSize.SCREEN_HEIGHT);
        gamePane = new GamePane(this);
        this.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (!isEffectPlaying && this.getRoot() == welcomePane)
                switch (key.getCode()) {
                    case ENTER:
                        if (isFirst)
                            if (!isSetting) {
                                isSetting = true;
                                welcomePane.navigateSettings();
                            } else {
                                navigateGame();
                            }
                        isFirst = true;
                        break;
                    case ESCAPE:
                        if (isFirst)
                            System.exit(0);
                        isFirst = true;
                        break;
                    case RIGHT:
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

    /**
     * Sets the current pane based on the provided enum value.
     * Updates the root of the scene accordingly.
     *
     * @param pane the pane to set as the current pane
     */
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

    /**
     * Navigates to the game pane.
     * Plays the intro effect and sets the game pane as the root when finished.
     */
    void navigateGame() {
        isEffectPlaying = true;
        welcomePane.finish();
        // play intro
        CustomMediaView introEffect = new CustomMediaView(Effects.INTRO, 1);
        introEffect.setOnfinished(this);
        welcomePane.getChildren().add(introEffect);

    }

    /**
     * Sets the game pane as the root of the scene.
     * Retrieves the crosshair and background IDs from the welcome pane
     * and sets them in the game pane accordingly.
     */
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

    /**
     * An enumeration representing the different panes in the DuckScene.
     */
    enum Panes {
        WELCOME,
        GAME
    }

    /**
     * Called when the function is finished.
     * Sets the game pane as the root of the scene and updates the effect playing
     * status.
     */
    @Override
    public void onFinished() {
        setGamePane();
        isEffectPlaying = false;
    }
}
