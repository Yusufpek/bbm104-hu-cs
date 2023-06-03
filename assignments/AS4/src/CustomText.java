import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

// Custom Text class extends the Text Node (from FX).
public class CustomText extends Text {

    /**
     * Creates a custom text element with the specified text, font size, vertical
     * multiplier, and fade animation.
     *
     * @param text               the text content
     * @param fontSize           the font size
     * @param verticalMultiplier the vertical multiplier for positioning
     * @param isFadeAnimation    determines if the fade animation should be applied
     */
    CustomText(String text, double fontSize, double verticalMultiplier, boolean isFadeAnimation) {
        this(text, fontSize, verticalMultiplier);
        if (isFadeAnimation)
            VisibleAnimation.animate(this);
    }

    /**
     * Creates a custom text element with the specified text, font size, and
     * vertical multiplier.
     *
     * @param text               the text content
     * @param fontSize           the font size
     * @param verticalMultiplier the vertical multiplier for positioning
     */
    CustomText(String text, double fontSize, double verticalMultiplier) {
        super(text);
        this.setFont(Font.font("Arial", FontWeight.BOLD, fontSize * DuckHunt.SCALE));
        this.setFill(Color.ORANGE);
        horizontalCenterText(verticalMultiplier);
    }

    /**
     * Horizontally centers the text element based on the specified vertical
     * multiplier.
     *
     * @param verticalMultiplier the vertical multiplier for positioning
     */
    void horizontalCenterText(double verticalMultiplier) {
        this.setTranslateY(ScreenSize.getHeight(verticalMultiplier));
        this.setTranslateX(ScreenSize.getWidth(0.5) - (this.getBoundsInParent().getMaxX() / 2));
    }
}
