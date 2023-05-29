import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.util.Duration;

public class VisibleAnimation {
    /**
     * @param node
     */
    public static void animate(Node node) {
        final Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000), event -> changeOpacity(node)));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play(); // Start animation
    }

    private static void changeOpacity(Node node) {
        node.setVisible(!node.isVisible());
    }
}
