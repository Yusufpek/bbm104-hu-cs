import java.io.FileNotFoundException;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Duck extends CustomImageView {
    // public final double radius = 15 * ScreenSize.SCALE;
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

    public void flyAnimation() {
        animation = new Timeline(
                new KeyFrame(Duration.millis(100), e -> moveDuck()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.setAutoReverse(true);
        animation.play();
    }

    public void deathAnimation(Function func) {
        System.out.println("death animation");
        setDeathImage();
        animation = new Timeline(
                new KeyFrame(Duration.millis(100), e -> deathDuck(func)));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
        System.out.println("playing");
    }

    public void setDeathImage() {
        try {
            this.setImage(new CustomImage("7", duck));
            System.out.println("set the new image");
        } catch (FileNotFoundException e) {
            System.out.println("Duck file not found !");
        }
    }

    public void play() {
        animation.play();
    }

    public void stop() {
        animation.stop();
    }

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
        // change images
        if (dy == 0)
            this.setImage(duckImages[index % duckImages.length]);
        else
            this.setImage(duckVerticalImages[index % duckVerticalImages.length]);
    }

    public void reverseDuckY() {
        dy *= -1; // Change ball move direction
        this.setScaleY(this.getScaleY() * -1);
    }

    public void reverseDuckX() {
        dx *= -1; // Change ball move direction
        this.setScaleX(this.getScaleX() * -1);
    }

    private void deathDuck(Function func) {
        System.out.println("death duck func");
        System.out.println("y: " + y);
        System.out.println("velocity: " + velocity);
        if (y >= ScreenSize.SCREEN_HEIGHT) {
            System.out.println("animation stopped");
            animation.stop();
            func.onDuckKilled();
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