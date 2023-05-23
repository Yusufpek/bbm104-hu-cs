public class Images {

    static final String TYPE = ".png";
    static final String BASE = "../assets/";
    static final String WELCOME = BASE + "welcome/"; // 1.png
    static final String BACKGROUND = BASE + "background/"; // 1-6.png
    static final String FOREGROUND = BASE + "foreground/"; // 1-6.png
    static final String CROSSHAIR = BASE + "crosshair/"; // 1-7.png
    static final String FAVICON = BASE + "favicon/"; // 1.png

    static final String DUCK = BASE + "duck_"; // 1-8.png
    static final String DUCK_BLACK = DUCK + "black/"; // 1-8.png
    static final String DUCK_BLUE = DUCK + "blue/"; // 1-8.png
    static final String DUCK_RED = DUCK + "red/"; // 1-8.png

    static String getName(String name, String path) {
        if (path != "") {
            return path + name + TYPE;
        }
        return name + TYPE;
    }
}
