import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * A utility class that provides a method to animate the visibility of a JavaFX
 * Node, creating a blinking effect.
 */
public class VisibleAnimation {
    /**
     * Animates the visibility of the specified Node, creating a blinking
     * effect.
     *
     * @param node the Node to animate
     */
    public static void animate(Node node) {
        final Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000), event -> changeOpacity(node)));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play(); // Start animation
    }

    /**
     * Changes the opacity of the specified Node by toggling its visibility.
     *
     * @param node the Node to change opacity
     */
    private static void changeOpacity(Node node) {
        node.setVisible(!node.isVisible());
    }
}
