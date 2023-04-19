import java.util.Date;
import java.text.ParseException;

/**
 * A class represents a color lamp device, which is a subclass of the
 * Lamp class.
 * It provides additional property color code which is represented as a
 * hexadecimal value ranging from 0x0 to 0xffffff.
 */
public class ColorLamp extends Lamp {
    private final int MAX_HEX = 16777215; // maximum color code value in integer 0xffffff
    private int colorCode;

    /**
     * Constructor with all parameters.
     *
     * @param now
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
     * Constructor with name and initial status.
     *
     * @param now
     * @param name
     * @param initialStatus
     */
    ColorLamp(Date now, String name, String initialStatus) {
        super(now, name, initialStatus);
        this.setDeviceType(DeviceType.COLOR_LAMP);
    }

    /**
     * Constructor with name.
     *
     * @param now
     * @param name
     */
    public ColorLamp(Date now, String name) {
        super(now, name);
        this.setDeviceType(DeviceType.COLOR_LAMP);
    }

    /**
     * Getter for colorCode.
     *
     * @return the colorCode
     */
    public int getColorCode() {
        return colorCode;
    }

    /**
     * Getter for colorCode as a formatted string.
     *
     * @return the colorCode as a formatted string
     */
    public String getColorCodeString() {
        return String.format("0x%06X", colorCode);
    }

    /**
     * Setter for colorCode.
     *
     * @param colorCode the color code to set
     * @return true if the color code is valid and setted, false otherwise
     */
    public boolean setColorCode(String colorCode) {
        if (!checkColorCode(colorCode))
            return false;
        this.colorCode = Integer.decode(colorCode);
        return true;
    }

    /**
     * Check the given color code is valid.
     * which means can decoded (is hexadecimal) and in the range
     *
     * @param colorCode the color code to check
     * @return true if the color code is valid, false otherwise
     */
    public boolean checkColorCode(String colorCode) {
        try {
            int color = Integer.decode(colorCode);
            if (!colorCode.startsWith("0x"))
                throw new ParseException("wrong hex value", 0);
            if (!(color >= 0 && color <= MAX_HEX)) {
                IO.outputStrings.add("ERROR: Color code value must be in range of 0x0-0xFFFFFF!");
                return false;
            }
        } catch (Exception e) {
            IO.outputStrings.add(SmartHomeConstants.ERROR_COMMAND);
            return false;
        }
        return true;
    }

    /**
     * Sets the color code of this color lamp to null (-1).
     * When kelvin value is active value
     */
    public void setColorCodeNull() {
        this.colorCode = -1;
    }

    /**
     * Returns a string representation of this color lamp.
     * including lamp to string method (super), color value with looking which is
     * active kelvin or color code, brightness and the switch time.
     *
     * @return a string representation of this color lamp
     */
    @Override
    public String toString() {
        String colorText = "";
        if (colorCode != -1) {
            colorText = getColorCodeString();
        } else {
            colorText = getKelvin() + "K";
        }
        return String.format(
                "%sand its color value is %s with %s%s brightness, and its time to switch its status is %s.",
                super.toString().substring(0, super.toString().indexOf("and")), colorText, getBrightness(), "%",
                getSwitchtimeString());
    }
}
