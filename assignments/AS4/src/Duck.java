import java.io.FileNotFoundException;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

/**
 * The Duck class represents a duck in the Duck Hunt game, extends the
 * CustomImageView class and encapsulates the some properties of custom image
 * view and animations of a duck, including its movement, animations, and
 * interaction with the game.
 */
public class Duck extends CustomImageView {
    private final double velocity = 8 * DuckHunt.SCALE;
    private String duck;
    private final Random random = new Random();
    private int index = 0;
    private double x, y;
    private double dx = velocity, dy = -velocity;
    private Image[] duckImages = new Image[3];
    private Image[] duckVerticalImages = new Image[3];
    private Timeline animation;
    public boolean isKilled;

    /**
     * Constructs a new Duck instance.
     *
     * @param duck       The type of duck.
     * @param isVertical True if the duck should fly vertically, false otherwise.
     * @param level      The level of the game.
     * @throws FileNotFoundException if the image file for the duck is not found.
     */
    public Duck(String duck, boolean isVertical, Level level) throws FileNotFoundException {
        super(new CustomImage("1", duck));
        this.duck = duck;
        if (!isVertical)
            dy = 0;

        for (int i = 0; i < duckImages.length; i++) {
            duckImages[i] = new CustomImage("" + (i + 4), duck);
        }

        for (int i = 0; i < duckVerticalImages.length; i++) {
            duckVerticalImages[i] = new CustomImage("" + (i + 1), duck);
        }

        x = random.nextInt((int) ScreenSize.getWidth(0.8));
        this.setTranslateX(x);
        this.setImage(duckImages[0]);
        y = random.nextInt((int) ScreenSize.getHeight(0.40)) + this.getFitHeight();
        System.out.println("random y: " + y);
        this.setTranslateY(y);

        flyAnimation();
    }

    /**
     * Starts the fly animation for the duck.
     */
    public void flyAnimation() {
        animation = new Timeline(
                new KeyFrame(Duration.millis(100), e -> moveDuck()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.setAutoReverse(true);
        animation.play();
    }

    /**
     * Starts the death animation for the duck.
     */
    public void deathAnimation() {
        System.out.println("death animation");
        setDeathImage();
        animation = new Timeline(
                new KeyFrame(Duration.millis(100), e -> deathDuck()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
        System.out.println("playing");
    }

    /**
     * Sets the image of the duck to the death image.
     */
    public void setDeathImage() {
        try {
            this.setImage(new CustomImage("7", duck));
            System.out.println("set the new image");
        } catch (FileNotFoundException e) {
            System.out.println("Duck file not found!");
        }
    }

    /**
     * Plays the animation.
     */
    public void play() {
        animation.play();
    }

    /**
     * Stops the animation.
     */
    public void stop() {
        animation.stop();
    }

    /**
     * Move Duck, set the translate x and y values by looking velocity.
     */
    private void moveDuck() {
        index++;
        // Check boundaries
        if (x > (ScreenSize.SCREEN_WIDTH - this.getFitWidth()) || x < 0) {
            reverseDuckX();
        }
        if (y > (ScreenSize.SCREEN_HEIGHT - this.getFitHeight()) || y < 5) {
            reverseDuckY();
        }
        x += dx;
        y += dy;
        this.setTranslateX(x);
        this.setTranslateY(y);
        // Change images
        if (dy == 0)
            this.setImage(duckImages[index % duckImages.length]);
        else
            this.setImage(duckVerticalImages[index % duckVerticalImages.length]);
    }

    /**
     * Reverses the direction of the duck's vertical movement.
     */
    public void reverseDuckY() {
        dy *= -1; // Change duck's vertical direction
        this.setScaleY(this.getScaleY() * -1);
    }

    /**
     * Reverses the direction of the duck's horizontal movement.
     */
    public void reverseDuckX() {
        dx *= -1; // Change duck's horizontal direction
        this.setScaleX(this.getScaleX() * -1);
    }

    private void deathDuck() {
        System.out.println("death duck func");
        System.out.println("y: " + y);
        System.out.println("velocity: " + velocity);
        if (y >= ScreenSize.SCREEN_HEIGHT) {
            System.out.println("animation stopped");
            animation.stop();
            // func.onFinished();
            isKilled = true;
        }
        y += velocity * 2;
        this.setTranslateY(y);
        System.out.println("death y: " + y);
        try {
            this.setImage(new CustomImage("8", duck));
        } catch (FileNotFoundException e) {
            System.out.println("Duck image not found !");
        }
    }
}