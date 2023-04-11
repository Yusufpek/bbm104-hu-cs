import java.util.Date;

public class ColorLamp extends Lamp {
    private final int MAX_HEX = 214748347; // maximum color code value in integer
    private int colorCode;

    /**
     * @param name
     * @param initialStatus
     * @param colorCode
     * @param brightness
     */
    ColorLamp(Date now, String name, String initialStatus, String colorCode, String brightness) {
        super(now, name, initialStatus);
        this.colorCode = Integer.decode(colorCode);
        this.setBrightness(brightness);
    }

    /**
     * @param name
     * @param initialStatus
     */
    ColorLamp(Date now, String name, String initialStatus) {
        super(now, name, initialStatus);
        this.setDeviceType(DeviceType.COLOR_LAMP);
    }

    /**
     * @param now
     * @param name
     */
    ColorLamp(Date now, String name) {
        super(now, name);
        this.setDeviceType(DeviceType.COLOR_LAMP);
    }

    /*
     * 
     * Getters and setter for variables
     * 
     */

    public int getColorCode() {
        return colorCode;
    }

    public String getColorCodeString() {
        return String.format("0x%06X", colorCode);
    }

    public boolean setColorCode(String colorCode) {
        if (!checkColorCode(colorCode))
            return false;
        this.colorCode = Integer.decode(colorCode);
        return true;
    }

    boolean checkColorCode(String colorCode) {
        try {
            int color = Integer.decode(colorCode);
            if (!(color > 0 && color < MAX_HEX)) {
                System.out.println("ERROR: Color code value must be in range of 0x0-0xFFFFFF");
                return false;
            }
        } catch (Exception e) {
            System.out.println(CommandController.ERROR_COMMAND);
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String colorText = "";
        if (colorCode != 0) {
            colorText = getColorCodeString();
        } else {
            colorText = getKelvin() + "K";
        }
        return String.format(
                "%sand its color value is %s with %s%s brightness, and its time to switch its status is %s",
                super.toString().substring(0, super.toString().indexOf("and")), colorText, getBrightness(), "%",
                getSwitchtimeString());
    }
}
