import java.util.Arrays;
import java.util.Collections;

/**
 * A class representing the level design in the Duck Hunt game.
 */
public class Level {
    final static int MAX_LEVEL = 6;
    String[] duckNames = new String[] { Images.DUCK_BLACK, Images.DUCK_BLUE, Images.DUCK_RED };
    int level;
    int ammo;
    int duckCount = 1;
    int killedDuckCount = 0;
    boolean[] duckVerticalMovement = new boolean[3];

    /**
     * Constructs a Level object with the initial level set to 1.
     * Sets the level design based on the level number.
     */
    Level() {
        setLevel(1);
    }

    /**
     * Set the duck count.
     * Shuffle the ducks colors.
     * Set the movement axis for ducks.
     */
    private void setLevelDesign() {
        Collections.shuffle(Arrays.asList(duckNames));
        switch (level) {
            case 1:
                duckCount = 1;
                duckVerticalMovement[0] = false;
                break;
            case 2:
                duckCount = 1;
                duckVerticalMovement[0] = true;
                break;
            case 3:
                duckCount = 2;
                duckVerticalMovement[1] = false;
                break;
            case 4:
                duckCount = 2;
                duckVerticalMovement[1] = true;
                break;
            case 5:
                duckCount = 3;
                duckVerticalMovement[2] = false;
                break;
            case 6:
                duckCount = 3;
                duckVerticalMovement[2] = true;
                break;
            default:
                break;
        }
    }

    /**
     * Returns the current level number.
     *
     * @return the level number
     */
    int getLevel() {
        return level;
    }

    /**
     * Increments the level count by 1.
     */
    void incrementLevelCount() {
        setLevel(getLevel() + 1);
    }

    /**
     * Sets the level to the specified level number.
     * Updates the level design, ammo count, and killed duck count.
     *
     * @param level the level number to set
     */
    void setLevel(int level) {
        this.level = level;
        setLevelDesign();
        ammo = duckCount * 3;
        killedDuckCount = 0;
    }

    /**
     * Checks if the current level is finished.
     *
     * @return true if all ducks have been killed, false otherwise
     */
    public boolean isFinished() {
        return killedDuckCount == duckCount;
    }
}
