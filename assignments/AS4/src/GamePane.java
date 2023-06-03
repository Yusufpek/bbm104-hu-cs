import java.io.FileNotFoundException;
import java.util.Arrays;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class GamePane extends Pane {
    int crossId;
    int backgroundId;
    Level level;
    DuckScene scene;
    private CustomMediaView levelCompletedEffect;
    private CustomMediaView gameCompletedEffect;
    private CustomMediaView gameOverEffect;
    private CustomMediaView shotEffect;
    private CustomMediaView fallEffect;
    private CustomImageView crosshair;

    GamePane() {
        super();
        level = new Level();
    }

    GamePane(DuckScene scene) {
        this();
        this.scene = scene;
    }

    GamePane(int cross, int background) {
        this.crossId = cross;
        this.backgroundId = background;
    }

    public int getCrossId() {
        return crossId;
    }

    public void setCrossId(int crossId) {
        this.crossId = crossId;
        this.setCursor(Cursor.NONE);
        try {
            crosshair = new CustomImageView(crossId + "", Images.CROSSHAIR);
            removeWidget(crosshair);
            getChildren().add(crosshair);
            crosshair.setTranslateY(ScreenSize.SCREEN_HEIGHT / 2);
            crosshair.setTranslateX(ScreenSize.SCREEN_WIDTH / 2);
            this.setOnMouseMoved(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    // remove image when the cursor get the out of the screen
                    double crosshairHalfWidth = crosshair.getFitWidth() / 2;
                    double crosshairHalfHeight = crosshair.getFitHeight() / 2;
                    if (event.getX() < crosshairHalfWidth - 10 || event.getY() < crosshairHalfHeight
                            || event.getX() > ScreenSize.SCREEN_WIDTH - crosshairHalfWidth
                            || event.getY() > ScreenSize.getHeight(0.95) - crosshairHalfWidth)
                        removeWidget(crosshair);
                    else {
                        System.out.println(event.getX());
                        System.out.println(event.getSceneX());
                        crosshair.setTranslateX(event.getX() - crosshair.getFitWidth() / 2);
                        crosshair.setTranslateY(event.getY() - crosshair.getFitHeight() / 2);
                        removeWidget(crosshair);
                        getChildren().add(crosshair);
                    }
                }
            });
        } catch (FileNotFoundException e) {
            System.out.println("Cross hair file not found !");
        }
    }

    public int getBackgroundId() {
        return backgroundId;
    }

    public void setBackgroundId(int backgroundId) {
        removeWidget(Texts.nextRoundTextFlow, Texts.noAmmoTextFlow, crosshair); // remove given texts
        stopEffect(); // stop all effects
        this.backgroundId = backgroundId;
        try {
            CustomText levelText = new CustomText("Level: " + level.level + "/" + Level.MAX_LEVEL, 8, 0.05); // top of
            CustomText ammoText = new CustomText("Ammo Left: " + level.ammo, 8, 0.05); // top of the screen
            ammoText.setTranslateX(ScreenSize.getWidth(0.8)); // right of the screen
            // set the ducks
            Duck[] ducks = new Duck[level.duckCount];
            for (int i = 0; i < level.duckCount; i++) {
                ducks[i] = new Duck(level.duckNames[i], level.duckVerticalMovement[i], level);
                if (i % 2 == 1 && level.duckVerticalMovement[i])
                    ducks[i].reverseDuckY();
            }
            this.getChildren().add(new CustomImageView(backgroundId + "", Images.BACKGROUND));
            this.getChildren().addAll(ducks);
            this.getChildren().add(new CustomImageView(backgroundId + "", Images.FOREGROUND));
            this.getChildren().addAll(levelText, ammoText);
            this.getChildren().add(crosshair);
            // Key Control
            this.setOnKeyPressed(key -> {
                System.out.println("Level: " + level.level);
                System.out.println(key.getCode());
                System.out.println("is finished: " + level.isFinished());
                if (key.getCode() == KeyCode.ENTER && this.getChildren().contains(Texts.nextRoundTextFlow)) {
                    System.out.println("round finish enter");
                    this.getChildren().remove(Texts.winText);
                    level.incrementLevelCount();
                    setBackgroundId(this.backgroundId);
                } else if (this.getChildren().contains(Texts.noAmmoTextFlow)
                        || this.getChildren().contains(Texts.gameCompletedTextFlow)) {
                    if (key.getCode() == KeyCode.ENTER) {
                        stopEffect();
                        scene.setCurrentPane(DuckScene.Panes.GAME);
                    } else if (key.getCode() == KeyCode.ESCAPE) {
                        System.out.println("go to welcome pane");
                        stopEffect();
                        scene.setCurrentPane(DuckScene.Panes.WELCOME);
                    }
                }
            });

            this.setOnMouseClicked((event) -> {
                System.out.println("ammo: " + level.ammo);
                // duck kill check
                if (level.ammo > 0) {
                    shotEffect = new CustomMediaView(Effects.SHOT, 1); // gun shot effect
                    this.getChildren().add(shotEffect);
                    for (Duck duck : ducks) {
                        if (duck.getBoundsInParent().contains(event.getX(), event.getY()) && !duck.isKilled) {
                            level.killedDuckCount++;
                            // duck fall
                            duck.stop();
                            duck.setDeathImage();
                            duck.deathAnimation();
                            // fall effect
                            fallEffect = new CustomMediaView(Effects.FALL, 1);
                            this.getChildren().add(fallEffect);
                            // check level
                            if (level.level < Level.MAX_LEVEL && level.isFinished()) {
                                this.getChildren().add(Texts.nextRoundTextFlow);
                                levelCompletedEffect = new CustomMediaView(Effects.LEVEL_COMPLETED, 1);
                                this.getChildren().add(levelCompletedEffect);
                            }
                        }
                    }
                    // max level completed check
                    if (level.level == Level.MAX_LEVEL && level.isFinished()) {
                        this.getChildren().add(Texts.gameCompletedTextFlow);
                        gameCompletedEffect = new CustomMediaView(Effects.GAME_COMPLETED, 1);
                        this.getChildren().add(gameCompletedEffect);
                        System.out.println("game over, all levels finished");
                    }
                }
                // increase ammo
                if (level.ammo > 0 && !DuckScene.isUnlimitedAmmo) {
                    level.ammo -= 1;
                    ammoText.setText("Ammo Left: " + level.ammo);
                }
                // ammo check
                if (level.ammo <= 0 && !level.isFinished()) {
                    if (!this.getChildren().contains(Texts.noAmmoTextFlow)) {
                        if (!Texts.noAmmoTextFlow.getChildren().contains(Texts.gameOverEnterText))
                            Texts.noAmmoTextFlow.getChildren().addAll(Texts.gameOverEnterText,
                                    Texts.gameOverEscapeText);
                        this.getChildren().add(Texts.noAmmoTextFlow);
                    }
                    gameOverEffect = new CustomMediaView(Effects.GAME_OVER, 1);
                    this.getChildren().add(gameOverEffect);
                    Arrays.stream(ducks).forEach(duck -> duck.stop());
                }
            });
        } catch (FileNotFoundException e) {
            System.out.println("Cross hair file not found !");
        }
    }

    private void removeWidget(Node... widgets) {
        for (Node node : widgets) {
            if (this.getChildren().contains(node))
                this.getChildren().remove(node);
        }
    }

    private void stopEffect() {
        CustomMediaView[] effects = new CustomMediaView[] { gameCompletedEffect, gameOverEffect, levelCompletedEffect,
                shotEffect, fallEffect };
        for (CustomMediaView effect : effects) {
            if (effect != null)
                effect.pauseMedia();
        }
    }
}
