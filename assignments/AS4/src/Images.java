/**
 * A class that provides paths to various images used in the application.
 */
public class Images extends Assets {
    static final String WELCOME = BASE + "welcome/"; // Path to welcome images (1.png)
    static final String BACKGROUND = BASE + "background/"; // Path to background images (1-6.png)
    static final String FOREGROUND = BASE + "foreground/"; // Path to foreground images (1-6.png)
    static final String CROSSHAIR = BASE + "crosshair/"; // Path to crosshair images (1-7.png)
    static final String FAVICON = BASE + "favicon/"; // Path to favicon images (1.png)

    static final String DUCK = BASE + "duck_"; // Base path for duck images (1-8.png)
    static final String DUCK_BLACK = DUCK + "black/"; // Path to black duck images (1-8.png)
    static final String DUCK_BLUE = DUCK + "blue/"; // Path to blue duck images (1-8.png)
    static final String DUCK_RED = DUCK + "red/"; // Path to red duck images (1-8.png)

    /**
     * Constructs the full image path by concatenating the specified name with the
     * provided path.
     *
     * @param name the name of the image
     * @param path the path of the image
     * @return the full image path
     */
    static String getName(String name, String path) {
        Images.TYPE = ".png";
        if (path != "") {
            return path + name + TYPE;
        }
        return name + TYPE;
    }
}
