import java.io.FileNotFoundException;
import java.util.Arrays;

import javafx.scene.ImageCursor;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class GamePane extends Pane implements Function {
    int crossId;
    int backgroundId;
    Level level;

    GamePane() {
        super();
        level = new Level();
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
        this.backgroundId = backgroundId;
        try {
            CustomText levelText = new CustomText("Level: " + level.level + "/" + Level.MAX_LEVEL, 8, 0.05); // top of
            CustomText ammoText = new CustomText("Ammo Left: " + level.ammo, 8, 0.05); // top of the screen
            ammoText.setTranslateX(ScreenSize.getWidth(0.8)); // right of the screen
            System.out.println("Width: " + ScreenSize.SCREEN_WIDTH);
            CustomText duckText = new CustomText("Duck Left: " + (level.duckCount - level.killedDuckCount), 8, 0.06);
            duckText.setTranslateX(0);
            Duck[] ducks = new Duck[level.duckCount];
            for (int i = 0; i < level.duckCount; i++) {
                ducks[i] = new Duck(level.duckNames[i], level.duckVerticalMovement[i], level);
                if (i % 2 == 1 && level.duckVerticalMovement[i])
                    ducks[i].reverseDuckY();
            }
            this.getChildren().add(new CustomImageView(backgroundId + "", Images.BACKGROUND));
            this.getChildren().addAll(ducks);
            this.getChildren().add(new CustomImageView(backgroundId + "", Images.FOREGROUND));
            this.getChildren().addAll(levelText, ammoText, duckText);

            this.setOnKeyPressed(key -> {
                System.out.println("Level: " + level.level);
                System.out.println(key.getCode());
                System.out.println(
                        key.getCode() == KeyCode.ENTER && this.getChildren().contains(Texts.roundFinishedText));
                if (key.getCode() == KeyCode.ENTER && this.getChildren().contains(Texts.roundFinishedText)) {
                    System.out.println("round finish enter");
                    this.getChildren().remove(Texts.roundFinishedText);
                    setBackgroundId(this.backgroundId);
                }
            });

            this.setOnMouseClicked((event) -> {
                System.out.println(level.ammo);
                if (level.ammo > 0 && !MainApplication.isUnlimitedAmmo) {
                    level.ammo -= 1;
                    ammoText.setText("Ammo Left: " + level.ammo);
                }

                double mouseX = event.getX();
                double mouseY = event.getY();

                // Iterate through the children (ducks) in reverse order to check the topmost
                // duck first
                for (Duck duck : ducks) {
                    if (duck.getBoundsInParent().contains(mouseX, mouseY) && !duck.isKilled) {
                        level.killedDuckCount++;
                        duck.stop();
                        duck.setDeathImage();
                        duck.deathAnimation(this);
                        // after finishing animation
                        System.out.println("duck dead");
                    }
                }

                if (level.ammo <= 0 && level.duckCount != level.killedDuckCount) {
                    if (!this.getChildren().contains(Texts.noAmmoText))
                        this.getChildren().add(Texts.noAmmoText);
                    System.out.println(ScreenSize.SCREEN_HEIGHT);
                    Arrays.stream(ducks).forEach(duck -> duck.stop());
                }
            });
        } catch (FileNotFoundException e) {
            System.out.println("Cross hair file not found !");
        }
    }

    @Override
    public void onDuckKilled() {
        // this.getChildren().remove(duck);
        if (level.killedDuckCount == level.duckCount) {
            level.incrementLevelCount();
            if (level.level <= Level.MAX_LEVEL)
                this.getChildren().add(Texts.roundFinishedText);
            else {
                this.getChildren().add(Texts.gameOverText);
                System.out.println("game over, all levels finished");
            }
        }
        System.out.println("on duck killed function");
    }
}
