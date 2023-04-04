import java.util.Date;

/*
 * Abstract class for smart devices 
 * Smart lamp, color, camera, plug devices base class
 */

public abstract class SmartDevice {
    private String name;
    private String initialStatus;
    private DeviceType deviceType;
    private Date switchtime;

    SmartDevice(String name, DeviceType type) {
        this.name = name;
        this.deviceType = type;
    }

    /**
     * @param name          name of device
     * @param initialStatus initial status of device
     * @param type          device type (enum)
     */
    SmartDevice(String name, String initialStatus, DeviceType type) {
        this.name = name;
        this.initialStatus = initialStatus;
        this.deviceType = type;
    }

    // return the device type string by lookin enum value
    public String getDeviceType() {
        if (deviceType == DeviceType.PLUG) {
            return "Smart Plug";
        } else if (deviceType == DeviceType.CAMERA) {
            return "Smart Camera";
        } else if (deviceType == DeviceType.LAMP) {
            return "Smart Lamp";
        } else if (deviceType == DeviceType.COLOR_LAMP) {
            return "Smart Color Lamp";
        } else {
            return null;
        }
    }

    /*
     * 
     * Getters and setter for variables
     * 
     */

    public Date getSwitchtime() {
        return switchtime;
    }

    public String getInitialStatus() {
        return initialStatus;
    }

    /**
     * @param initialStatus
     */
    public void setInitialStatus(String initialStatus) {
        this.initialStatus = initialStatus;
    }

    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param deviceType
     */
    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * @param switchtime
     */
    public void setSwitchtime(Date switchtime) {
        this.switchtime = switchtime;
    }

    // set constant the device type
    enum DeviceType {
        PLUG,
        CAMERA,
        LAMP,
        COLOR_LAMP,
    }
}
