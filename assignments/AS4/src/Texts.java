import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Texts {
    // Strings
    final static String WINDOW_TEXT = "HUBBM DUCK HUNT";
    final static String WELCOME_TEXT_ENTER = "PRESS ENTER TO START\n";
    final static String WELCOME_TEXT_ESC = "PRESS ESC TO EXIT";
    final static String SETTINGS_TEXT = "Navigate";

    // Text Widgets
    static final Text welcomeTextEnterWidget = new CustomText(WELCOME_TEXT_ENTER, 12, 0.6);
    static final Text welcomeTextEscWidget = new CustomText(WELCOME_TEXT_ESC, 12, 0.61);
}

class CustomText extends Text {

    CustomText(String text, double fontSize, double verticalMultiplier) {
        super(text);
        this.setFont(Font.font("Calibri", FontWeight.BOLD, fontSize * ScreenSize.SCALE));
        this.setFill(Color.ORANGE);
        horizontalCenterText(verticalMultiplier);
    }

    void horizontalCenterText(double verticalMultiplier) {
        this.setTranslateY(ScreenSize.getHeight(verticalMultiplier));
        this.setTranslateX(ScreenSize.getWidth(0.5) - (this.getBoundsInParent().getMaxX() / 2));
    }
}