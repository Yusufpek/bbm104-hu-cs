import java.util.Date;

/**
 * A class that represents a smart lamp device.
 * It extends the SmartDevice class and adds specific functionality related to
 * controlling the brightness and kelvin values of the lamp.
 */
public class Lamp extends SmartDevice {
    private int brightness = 100; // range [0,100]
    private int kelvin = 4000; // range [2000, 6500]

    /**
     * Constructs a new Lamp with the given name, initial status, kelvin value, and
     * brightness value.
     *
     * @param now           the current date and time
     * @param name          the name of the Lamp
     * @param initialStatus the initial status of the Lamp
     * @param kelvin        the kelvin value of the Lamp
     * @param brightness    the brightness value of the Lamp
     */
    Lamp(Date now, String name, String initialStatus, String kelvin, String brightness) {
        super(now, name, initialStatus, DeviceType.LAMP);
        this.kelvin = Integer.parseInt(kelvin);
        this.brightness = Integer.parseInt(brightness);
    }

    /**
     * Constructs a new Lamp with the given name and current date-time.
     *
     * @param now  the current date and time
     * @param name the name of the Lamp
     */
    Lamp(Date now, String name) {
        super(now, name, DeviceType.LAMP);
    }

    /**
     * Constructs a new Lamp with the given name, initial status, and current
     * date-time.
     *
     * @param now           the current date and time
     * @param name          the name of the Lamp
     * @param initialStatus the initial status of the Lamp
     */
    Lamp(Date now, String name, String initialStatus) {
        super(now, name, initialStatus, DeviceType.LAMP);
    }

    /**
     * Returns the brightness value of the Lamp.
     *
     * @return the brightness value of the Lamp
     */
    public int getBrightness() {
        return brightness;
    }

    /**
     * Sets the brightness value of the Lamp.
     *
     * @param brightness the new brightness value, which must be in the range
     * @return true if the brightness value was set successfully, false otherwise
     */
    public boolean setBrightness(String brightness) {
        if (!checkBrightness(brightness))
            return false;
        this.brightness = Integer.parseInt(brightness);
        return true;
    }

    /**
     * Returns the kelvin value of the Lamp.
     *
     * @return the kelvin value of the Lamp
     */
    public int getKelvin() {
        return kelvin;
    }

    /**
     * Sets the kelvin value of the Lamp.
     *
     * @param kelvin the new kelvin value, which must be in the range [2000, 6500]
     * @return true if the kelvin value was set successfully, false otherwise
     */
    public boolean setKelvin(String kelvin) {
        if (!checkKelvin(kelvin))
            return false;
        this.kelvin = Integer.parseInt(kelvin);
        return true;
    }

    /**
     * Checks the given brightness value is valid.
     * - is numeric
     * - in range of [0,100]
     * If it is not valid writes the error messages.
     *
     * @param brightnessStr the brightness value to check
     * @return true if the brightness value is valid, false otherwise
     */

    boolean checkBrightness(String brightnessStr) {
        try {
            int brightness = Integer.parseInt(brightnessStr);
            if (brightness > 100 || brightness < 0) {
                IO.outputStrings.add("ERROR: Brightness must be in range of 0%-100%!");
                return false;
            }
            return true;
        } catch (Exception e) {
            IO.outputStrings.add(SmartHomeConstants.ERROR_COMMAND);
            return false;
        }
    }

    /**
     * Checks the given kelvin value is within the valid.
     * - is numeric
     * - range of 2000K-6500K
     * 
     * @param kelvinStr The kelvin value to be checked as a String.
     * @return true if the kelvin value is within the valid range, false otherwise.
     */
    boolean checkKelvin(String kelvinStr) {
        try {
            int kelvin = Integer.parseInt(kelvinStr);
            if (kelvin > 6500 || kelvin < 2000) {
                IO.outputStrings.add("ERROR: Kelvin value must be in range of 2000K-6500K!");
                return false;
            }
        } catch (Exception e) {
            IO.outputStrings.add(SmartHomeConstants.ERROR_COMMAND);
            return false;
        }
        return true;
    }

    /**
     * Returns a String representation of the Lamp object, including its
     * name, kelvin value, brightness percentage, and switch time.
     * 
     * @return A String representation of the Lamp object.
     */
    @Override
    public String toString() {
        return String.format(
                "%s its kelvin value is %sK with %s%s brightness, and its time to switch its status is %s.",
                super.toString(), kelvin, brightness, "%",
                getSwitchtimeString());
    }
}
