/**
 * A class representing the effects used in the Duck Hunt game.
 * Extends the Assets class.
 */
public class Effects extends Assets {

    static final private String EFFECTS_BASE = BASE + "effects/";
    static final String FALL = "DuckFalls";
    static final String GAME_COMPLETED = "GameCompleted";
    static final String GAME_OVER = "GameOver";
    static final String SHOT = "Gunshot";
    static final String INTRO = "Intro";
    static final String LEVEL_COMPLETED = "LevelCompleted";
    static final String TITLE = "Title";

    /**
     * Retrieves the file path for the specified effect.
     *
     * @param name the name of the effect
     * @return the file path of the effect
     */
    static String getEffect(String name) {
        Effects.TYPE = ".mp3";
        return EFFECTS_BASE + name + TYPE;
    }
}
