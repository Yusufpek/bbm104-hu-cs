import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * A utility class that provides various text constants and text flow objects
 */
public class Texts {
        // Strings
        final static String WINDOW_TEXT = "HUBBM DUCK HUNT";
        final static String ENTER_TEXT = "PRESS ENTER TO START\n";
        final static String ESC_TEXT = "Press ESC to exit";
        final static String SETTINGS_NAVIGATE_TEXT = "USE ARROW KEYS TO NAVIGATE\n";
        final static String WIN = "YOU WIN!\n";
        final static String NEXT_ROUND = "Press ENTER to play next level";
        final static String GAME_OVER = "GAME OVER!\n";
        final static String GAME_OVER_ENTER = "Press ENTER to play again\n";
        final static String GAME_COMPLETED = "You have completed the game!\n";

        // Text Nodes
        static final Text welcomeEnterTextWidget = new CustomText(ENTER_TEXT, 12, 0, true);
        static final Text welcomeEscTextWidget = new CustomText(ESC_TEXT.toUpperCase(), 12, 0, true);
        static final Text settingsNavigateTextWidget = new CustomText(SETTINGS_NAVIGATE_TEXT, 8, 0.06);
        static final Text settingsEnterTextWidget = new CustomText(ENTER_TEXT, 8, 0.07);
        static final Text settingsEscTextWidget = new CustomText(ESC_TEXT.toUpperCase(), 8, 0.08);
        static final CustomText winText = new CustomText(WIN, 12, 0.5);
        static final private CustomText nextRoundText = new CustomText(NEXT_ROUND, 12, 0.5, true);
        static final private CustomText gameOverText = new CustomText(GAME_OVER, 12, 0.4);
        static final private CustomText gameCompletedText = new CustomText(GAME_COMPLETED, 12, 0.4);
        static final CustomText gameOverEnterText = new CustomText(GAME_OVER_ENTER, 12, 0.4, true);
        static final CustomText gameOverEscapeText = new CustomText(ESC_TEXT, 12, 0.4, true);

        // Text Flow Nodes
        static final TextFlow welcomeTextFlow = new TextFlow(welcomeEnterTextWidget, welcomeEscTextWidget);
        static final TextFlow settingsTextFlow = new TextFlow(settingsNavigateTextWidget, settingsEnterTextWidget,
                        settingsEscTextWidget);
        static final TextFlow nextRoundTextFlow = new TextFlow(winText, nextRoundText);
        static final TextFlow noAmmoTextFlow = new TextFlow(gameOverText, gameOverEnterText, gameOverEscapeText);
        static final TextFlow gameCompletedTextFlow = new TextFlow(gameCompletedText, gameOverEnterText,
                        gameOverEscapeText);
}
