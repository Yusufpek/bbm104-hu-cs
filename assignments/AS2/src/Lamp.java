public class Lamp extends SmartDevice {
    private int brightness = 100;
    private int kelvin = 4000;

    /**
     * @param name
     * @param initialStatus
     * @param kelvin
     * @param brightness
     */
    Lamp(String name, String initialStatus, String kelvin, String brightness) {
        super(name, initialStatus, DeviceType.LAMP);
        this.kelvin = Integer.parseInt(kelvin);
        this.brightness = Integer.parseInt(brightness);
    }

    /**
     * @param name
     */
    Lamp(String name) {
        super(name, DeviceType.LAMP);
    }

    /**
     * @param name
     * @param initialStatus
     */
    Lamp(String name, String initialStatus) {
        super(name, initialStatus, DeviceType.LAMP);
    }

    public int getBrightness() {
        return brightness;
    }

    /**
     * @param brightness which has to be in [0,100]
     */
    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getKelvin() {
        return kelvin;
    }

    /**
     * @param kelvin
     */
    public void setKelvin(int kelvin) {
        this.kelvin = kelvin;
    }

    @Override
    public String toString() {
        return String.format(
                "%s its kelvin value is %sK with %s%s brightness, and its time to switch its status is %s",
                super.toString(), kelvin, brightness, "%",
                getSwitchtimeString());
    }
}
