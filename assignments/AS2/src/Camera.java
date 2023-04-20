import java.util.Date;

/**
 * A class represents the smart camera device that can store video in it.
 * This class extends the SmartDevice class and inherits its properties and
 * methods.
 */
public class Camera extends SmartDevice {
    private double mbPerMinute; // consumed megabytes per minute
    private double usedMegabytes; // total consumed megabytes

    /**
     * Constructs a Camera object with a given name and the current date and time.
     * 
     * @param now  the current date and time
     * @param name the name of the Camera
     */
    Camera(Date now, String name) {
        super(now, name, DeviceType.CAMERA);
    }

    /**
     * Constructs a Camera object with a given name and the current date and time,
     * and sets the rate of memory consumption in megabytes per minute.
     * 
     * @param now         the current date and time
     * @param name        the name of the Camera
     * @param mbPerMinute the rate of memory consumption in megabytes per minute
     */
    Camera(Date now, String name, double mbPerMinute) {
        super(now, name, DeviceType.CAMERA);
        this.mbPerMinute = mbPerMinute;
    }

    /**
     * Constructs a Camera object with a given name and the current date and time,
     * sets the rate of memory consumption in megabytes per minute, and sets the
     * initial status.
     * 
     * @param now           the current date and time
     * @param name          the name of the Camera
     * @param mbPerMinute   the rate of memory consumption in megabytes per minute
     * @param initialStatus the initial status of the Camera
     */
    Camera(Date now, String name, double mbPerMinute, String initialStatus) {
        super(now, name, initialStatus, DeviceType.CAMERA);
        this.mbPerMinute = mbPerMinute;
    }

    /**
     * Calculates the total amount of memory consumed by the Camera
     * Formula is duration (in minute) * mb
     * 
     * @param duration the duration of usage in minutes
     */
    public void calculateUsedMegabytes(double duration) {
        setUsedMegabytes(getUsedMegabytes() + mbPerMinute * duration);
    }

    /**
     * Returns the total amount of memory consumed by the Camera.
     * 
     * @return the total amount of memory consumed by the Camera
     */
    public double getUsedMegabytes() {
        return usedMegabytes;
    }

    /**
     * Sets the total amount of memory consumed by the Camera.
     * 
     * @param usedMegabytes the total amount of memory consumed by the Camera
     */
    public void setUsedMegabytes(double usedMegabytes) {
        this.usedMegabytes = usedMegabytes;
    }

    /**
     * Sets the rate of memory consumption per minute. (mb)
     * 
     * @param megabytes the rate of memory consumption per minute in megabytes
     * @return true if the rate of memory consumption is set successfully,
     *         false otherwise
     */
    public boolean setMBPerMinute(String megabytes) {
        if (!checkMB(megabytes))
            return false;
        this.mbPerMinute = Double.parseDouble(megabytes);
        return true;
    }

    /**
     * Sets the status of the device and calculates the amount of storage used based
     * on the time, if the new status is "Off".
     * 
     * The time (duration) calculates with the switchtime difference if switchtime
     * is not null, otherwise difference between now and the old switch time.
     * 
     * @param status The new status to set for the device.
     */
    @Override
    public void setStatus(String status) {
        if (status.equals("Off")) {
            final long milliSecondsDifference = ((this.getSwitchtime() == null)
                    || this.getSwitchtime().after(TimeController.now))
                            ? TimeController.now.getTime() - this.getOldSwitchtime().getTime()
                            : this.getSwitchtime().getTime() - this.getOldSwitchtime().getTime();
            calculateUsedMegabytes((double) milliSecondsDifference / (1000 * 60)); // convert to milliseconds to minutes.
            this.setOldSwitchtime(getSwitchtime()); // update the old switch time
        }
        super.setStatus(status);
    }

    /**
     * Checks the given string represents a valid positive number of megabytes.
     * 
     * @param megabytes The string to check.
     * @return true if the string represents a valid positive number of megabytes,
     *         false otherwise.
     */
    boolean checkMB(String megabytes) {
        try {
            double megabyte = Double.parseDouble(megabytes);
            if (megabyte <= 0) {
                IO.outputStrings.add("ERROR: Megabyte value must be a positive number!");
                return false;
            }
            return true;
        } catch (Exception e) {
            IO.outputStrings.add(SmartHomeConstants.ERROR_COMMAND);
            return false;
        }
    }

    /**
     * Returns a string representation of the device,
     * including the amount of storage and the switch time.
     * 
     * @return A string representation of the device.
     */
    @Override
    public String toString() {
        return String.format(
                "%s used %.2f MB of storage so far (excluding current status), and its time to switch its status is %s.",
                super.toString(), getUsedMegabytes(), getSwitchtimeString());
    }
}
