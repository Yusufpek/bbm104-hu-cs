public class ScreenSize {
    static final double SCALE = 3;
    static final double SCREEN_HEIGHT = SCALE * 240;
    static final double SCREEN_WIDTH = SCALE * 256;

    static double getHeight(double multiplier) {
        return SCREEN_HEIGHT * multiplier;
    }

    static double getWidth(double multiplier) {
        return SCREEN_WIDTH * multiplier;
    }
}
