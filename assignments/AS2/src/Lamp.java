import java.util.Date;

public class Lamp extends SmartDevice {
    private int brightness = 100;
    private int kelvin = 4000;

    /**
     * @param name
     * @param initialStatus
     * @param kelvin
     * @param brightness
     */
    Lamp(Date now, String name, String initialStatus, String kelvin, String brightness) {
        super(now, name, initialStatus, DeviceType.LAMP);
        this.kelvin = Integer.parseInt(kelvin);
        this.brightness = Integer.parseInt(brightness);
    }

    /**
     * @param name
     */
    Lamp(Date now, String name) {
        super(now, name, DeviceType.LAMP);
    }

    /**
     * @param name
     * @param initialStatus
     */
    Lamp(Date now, String name, String initialStatus) {
        super(now, name, initialStatus, DeviceType.LAMP);
    }

    public int getBrightness() {
        return brightness;
    }

    /**
     * @param brightness which has to be in [0,100]
     */
    public boolean setBrightness(String brightness) {
        if (!checkBrightness(brightness))
            return false;
        this.brightness = Integer.parseInt(brightness);
        return true;
    }

    public int getKelvin() {
        return kelvin;
    }

    public boolean setKelvin(String kelvin) {
        if (!checkKelvin(kelvin))
            return false;
        this.kelvin = Integer.parseInt(kelvin);
        return true;
    }

    boolean checkBrightness(String brightnessStr) {
        try {
            int brightness = Integer.parseInt(brightnessStr);
            if (brightness > 100 || brightness < 0) {
                IO.outputStrings.add("ERROR: Brightness must be in range of 0%-100%!");
                return false;
            }
            return true;
        } catch (Exception e) {
            IO.outputStrings.add(CommandController.ERROR_COMMAND);
            return false;
        }
    }

    boolean checkKelvin(String kelvinStr) {
        try {
            int kelvin = Integer.parseInt(kelvinStr);
            if (kelvin > 6500 || kelvin < 2000) {
                IO.outputStrings.add("ERROR: Kelvin value must be in range of 2000K-6500K!");
                return false;
            }
        } catch (Exception e) {
            IO.outputStrings.add(CommandController.ERROR_COMMAND);
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format(
                "%s its kelvin value is %sK with %s%s brightness, and its time to switch its status is %s.",
                super.toString(), kelvin, brightness, "%",
                getSwitchtimeString());
    }
}
