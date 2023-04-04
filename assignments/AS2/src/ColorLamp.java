public class ColorLamp extends Lamp {
    private int colorCode;

    /**
     * @param name
     * @param initialStatus
     * @param colorCode
     * @param brightness
     */
    ColorLamp(String name, String initialStatus, String colorCode, String brightness) {
        super(name, initialStatus);
        this.colorCode = Integer.parseInt(colorCode);
        this.setBrightness(Integer.parseInt(brightness));
    }

    /**
     * @param name
     * @param initialStatus
     */
    ColorLamp(String name, String initialStatus) {
        super(name, initialStatus);
        this.setDeviceType(DeviceType.COLOR_LAMP);
    }

    /**
     * @param name
     */
    ColorLamp(String name) {
        super(name);
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

    /**
     * @param colorCode
     */
    public void setColorCode(int colorCode) {
        this.colorCode = colorCode;
    }

}
