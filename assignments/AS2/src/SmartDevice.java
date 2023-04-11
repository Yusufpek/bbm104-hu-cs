import java.text.ParseException;
import java.util.Date;

/*
 * Abstract class for smart devices 
 * Smart lamp, color, camera, plug devices base class
 */

public abstract class SmartDevice {
    private String name;
    private String status = "Off";
    private DeviceType deviceType;
    private Date switchtime;
    private Date oldSwitchtime;

    public Date getOldSwitchtime() {
        return oldSwitchtime;
    }

    public void setOldSwitchtime(Date oldSwitchtime) {
        this.oldSwitchtime = oldSwitchtime;
    }

    SmartDevice(Date now, String name, DeviceType type) {
        this.name = name;
        this.deviceType = type;
        oldSwitchtime = now;
    }

    /**
     * @param name          name of device
     * @param initialStatus initial status of device
     * @param type          device type (enum)
     */
    SmartDevice(Date now, String name, String initialStatus, DeviceType type) {
        this.name = name;
        this.status = initialStatus;
        this.deviceType = type;
    }

    // return the device type string by lookin enum value
    public String getDeviceTypeString() {
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

    // return the device type
    public DeviceType getDeviceType() {
        return this.deviceType;
    }

    @Override
    public String toString() {
        return String.format("%s %s is %s and", getDeviceTypeString(), name, status.toLowerCase());
    }

    /*
     * 
     * Getters and setter for variables
     * 
     */

    public Date getSwitchtime() {
        return switchtime;
    }

    public String getSwitchtimeString() {
        if (switchtime == null)
            return null;
        return TimeController.dateFormat.format(switchtime);
    }

    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
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
        this.oldSwitchtime = this.switchtime;
        this.switchtime = switchtime;
    }

    /**
     * @param switchtime
     */
    public void setSwitchtime(String switchtime) {
        try {
            if (switchtime.equals(""))
                this.switchtime = null;
            else
                this.switchtime = TimeController.dateFormat.parse(switchtime);
        } catch (ParseException e) {
            System.out.println("Wrong date !");
        }
    }

    // set constant the device type
    static enum DeviceType {
        PLUG,
        CAMERA,
        LAMP,
        COLOR_LAMP,
    }
}
