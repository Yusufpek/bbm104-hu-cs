public class Level {
    final static int MAX_LEVEL = 6;
    final String[] duckNames = new String[] { Images.DUCK_BLACK, Images.DUCK_BLUE, Images.DUCK_RED };
    int level;
    int ammo;
    int duckCount = 1;
    int killedDuckCount = 0;
    boolean[] duckVerticalMovement = new boolean[3];

    private void setLevelDesign() {
        switch (level) {
            case 1:
                duckCount = 1;
                duckVerticalMovement[0] = false;
                break;
            case 2:
                duckCount = 2;
                duckVerticalMovement[1] = false;
                break;
            case 3:
                duckCount = 2;
                duckVerticalMovement[0] = true;
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

    int getLevel() {
        return level;
    }

    void incrementLevelCount() {
        setLevel(getLevel() + 1);
    }

    void setLevel(int level) {
        this.level = level;
        setLevelDesign();
        ammo = duckCount * 3;
        killedDuckCount = 0;
    }
}
