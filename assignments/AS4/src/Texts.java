import javafx.scene.text.Text;

public class Texts {
    // Strings
    final static String WINDOW_TEXT = "HUBBM DUCK HUNT";
    final static String ENTER_TEXT = "PRESS ENTER TO START\n";
    final static String ESC_TEXT = "PRESS ESC TO EXIT";
    final static String SETTINGS_NAVIGATE_TEXT = "USE ARROW KEYS TO NAVIGATE\n";

    // Text Widgets
    static final Text welcomeEnterTextWidget = new CustomText(ENTER_TEXT, 12, 0.6);
    static final Text welcomeEscTextWidget = new CustomText(ESC_TEXT, 12, 0.61);
    static final Text settingsNavigateTextWidget = new CustomText(SETTINGS_NAVIGATE_TEXT, 8, 0.06);
    static final Text settingsEnterTextWidget = new CustomText(ENTER_TEXT, 8, 0.07);
    static final Text settingsEscTextWidget = new CustomText(ESC_TEXT, 8, 0.08);
    static final CustomText gameOverText = new CustomText("Game is over, all levels are finished ", 12, 0.5);
    static final CustomText noAmmoText = new CustomText("No Ammo left game is over ", 8, 0.5);
}
