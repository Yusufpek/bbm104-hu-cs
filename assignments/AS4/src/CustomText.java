import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class CustomText extends Text {

    CustomText(String text, double fontSize, double verticalMultiplier) {
        super(text);
        this.setFont(Font.font("Ariel", FontWeight.BOLD, fontSize * ScreenSize.SCALE));
        this.setFill(Color.ORANGE);
        horizontalCenterText(verticalMultiplier);
    }

    void horizontalCenterText(double verticalMultiplier) {
        this.setTranslateY(ScreenSize.getHeight(verticalMultiplier));
        this.setTranslateX(ScreenSize.getWidth(0.5) - (this.getBoundsInParent().getMaxX() / 2));
    }
}