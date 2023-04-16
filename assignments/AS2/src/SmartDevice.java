import java.text.ParseException;
import java.util.Date;

/**
 * An abstract class representing a Smart Device, which serves as the base class
 * for Smart Lamp,Smart Color Lamp, Smart Camera, Smart and Plug devices.
 */

public abstract class SmartDevice {
    private String name; // the name of the device
    private String status = "Off"; // the current status of the device
    private DeviceType deviceType; // the type of the device
    private Date switchtime; // the time the device was last switched
    private Date oldSwitchtime; // the time the device was last switched before the current time

    /**
     * Constructor for a SmartDevice.
     * 
     * @param now  the current date and time
     * @param name the name of the device
     * @param type the type of the device (enum)
     */
    SmartDevice(Date now, String name, DeviceType type) {
        this.name = name;
        this.deviceType = type;
        oldSwitchtime = now;
    }

    /**
     * Constructor for a SmartDevice with an initial status.
     * 
     * @param now           the current date and time
     * @param name          the name of the device
     * @param initialStatus the initial status of the device
     * @param type          the type of the device (enum)
     */
    SmartDevice(Date now, String name, String initialStatus, DeviceType type) {
        this.name = name;
        this.status = initialStatus;
        this.deviceType = type;
        this.oldSwitchtime = now;
    }

    /**
     * Returns a string representation of the SmartDevice
     * Contains: device type, name, status.
     * 
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return String.format("%s %s is %s and", getDeviceTypeString(), name, status.toLowerCase());
    }

    /*
     * 
     * Getters and setters
     * 
     */

    /**
     * Returns the device type as a string.
     * 
     * @return a string representing the device type
     */
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

    /**
     * Returns the device type.
     * 
     * @return the device type
     */
    public DeviceType getDeviceType() {
        return this.deviceType;
    }

    /**
     * Get the old switch time of the device.
     * 
     * @return the old switch time of the device.
     */
    public Date getOldSwitchtime() {
        return oldSwitchtime;
    }

    /**
     * Set the old switch time of the device.
     * 
     * @param oldSwitchtime the old switch time of the device.
     */
    public void setOldSwitchtime(Date oldSwitchtime) {
        this.oldSwitchtime = oldSwitchtime;
    }

    /**
     * Get the current switch time of the device.
     * 
     * @return the current switch time of the device (in date type).
     */
    public Date getSwitchtime() {
        return switchtime;
    }

    /**
     * Get the current switch time of the device as a formatted string.
     * 
     * @return the current switch time of the device as a formatted string.
     */
    public String getSwitchtimeString() {
        if (switchtime == null)
            return null;
        return TimeController.dateFormat.format(switchtime);
    }

    /**
     * Get the status of the device.
     * 
     * @return the status of the device.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set the status of the device.
     * 
     * @param status the status of the device.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Get the name of the device.
     * 
     * @return the name of the device.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the device.
     * 
     * @param name the name of the device.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the type of the device.
     * 
     * @param deviceType the type of the device.
     */
    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * Set the switch time of the device.
     * 
     * @param switchtime the switch time of the device.
     */
    public void setSwitchtime(Date switchtime) {
        this.oldSwitchtime = this.switchtime;
        this.switchtime = switchtime;
    }

    /**
     * Set the switch time of the device.
     * 
     * @param switchtime the switch time of the device as a formatted string.
     */
    public void setSwitchtime(String switchtime) {
        try {
            if (switchtime.equals(""))
                this.switchtime = null;
            else if (!TimeController.dateFormat.parse(switchtime).before(TimeController.now))
                this.switchtime = TimeController.dateFormat.parse(switchtime);
            else
                IO.outputStrings.add("ERROR: Switch time can not be in past");
        } catch (ParseException e) {
            IO.outputStrings.add("ERROR: Time format is not correct!");
        }
    }

    /**
     * ENUM for the constant device types.
     */
    static enum DeviceType {
        PLUG,
        CAMERA,
        LAMP,
        COLOR_LAMP,
    }
}
