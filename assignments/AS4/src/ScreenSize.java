public class ScreenSize {
    static final double SCREEN_HEIGHT = DuckHunt.SCALE * 240;
    static final double SCREEN_WIDTH = DuckHunt.SCALE * 256;

    static double getHeight(double multiplier) {
        return SCREEN_HEIGHT * multiplier;
    }

    static double getWidth(double multiplier) {
        return SCREEN_WIDTH * multiplier;
    }
}
