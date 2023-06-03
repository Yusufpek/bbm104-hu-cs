/**
 * A class representing the screen size used in the Duck Hunt game.
 */
public class ScreenSize {
    static final double SCREEN_HEIGHT = DuckHunt.SCALE * 240;
    static final double SCREEN_WIDTH = DuckHunt.SCALE * 256;

    /**
     * Calculates the height based on the screen size and a given multiplier.
     *
     * @param multiplier the multiplier for the screen height
     * @return the calculated height
     */
    static double getHeight(double multiplier) {
        return SCREEN_HEIGHT * multiplier;
    }

    /**
     * Calculates the width based on the screen size and a given multiplier.
     *
     * @param multiplier the multiplier for the screen width
     * @return the calculated width
     */
    static double getWidth(double multiplier) {
        return SCREEN_WIDTH * multiplier;
    }
}
