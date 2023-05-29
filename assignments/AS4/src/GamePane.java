import java.io.FileNotFoundException;
import java.util.Arrays;

import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class GamePane extends Pane {
    int crossId;
    int backgroundId;
    Level level;
    DuckScene scene;

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
        try {
            this.setCursor(new ImageCursor(new CustomImage(crossId + "", Images.CROSSHAIR)));
        } catch (FileNotFoundException e) {
            System.out.println("Cross hair file not found !");
        }
    }

    public int getBackgroundId() {
        return backgroundId;
    }

    public void setBackgroundId(int backgroundId) {
        removeWidget(Texts.nextRoundTextFlow, Texts.noAmmoTextFlow);
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
                    if (key.getCode() == KeyCode.ENTER)
                        scene.setCurrentPane(DuckScene.Panes.GAME);
                    else if (key.getCode() == KeyCode.ESCAPE) {
                        System.out.println("go to welcome pane");
                        scene.setCurrentPane(DuckScene.Panes.WELCOME);
                    }
                }
            });

            this.setOnMouseClicked((event) -> {
                System.out.println("ammo: " + level.ammo);
                // duck kill check
                if (level.ammo > 0) {
                    for (Duck duck : ducks) {
                        if (duck.getBoundsInParent().contains(event.getX(), event.getY()) && !duck.isKilled) {
                            level.killedDuckCount++;
                            duck.stop();
                            duck.setDeathImage();
                            duck.deathAnimation();
                            if (level.level < Level.MAX_LEVEL && level.isFinished()) {
                                this.getChildren().add(Texts.nextRoundTextFlow);
                            }
                            System.out.println("duck dead");
                        }
                    }
                    // max level completed check
                    if (level.level == Level.MAX_LEVEL && level.isFinished()) {
                        this.getChildren().add(Texts.gameCompletedTextFlow);
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
                    System.out.println(ScreenSize.SCREEN_HEIGHT);
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
}
