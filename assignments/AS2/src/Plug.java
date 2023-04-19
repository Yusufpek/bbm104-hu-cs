import java.util.Date;

/**
 * Represents a smart plug that can be controlled through the Smart Home System.
 * 
 * It inherits from the SmartDevice class and includes additional properties
 * such as ampere, watt, and a constant value for VOLT.
 * 
 * It also includes methods to calculate the consumed watt, check the ampere
 * value, set the ampere to zero, plug in the device, and set the status.
 */
public class Plug extends SmartDevice {
    private double ampere;
    private double watt;
    private final int VOLT = 220; // constant volt value

    /**
     * Constructs a new Plug object with the given name and current date.
     * 
     * @param now  the current date
     * @param name the name of the device
     */
    Plug(Date now, String name) {
        super(now, name, DeviceType.PLUG);
    }

    /**
     * Constructs a new Plug object with the given name, initial status, and current
     * date.
     * 
     * @param now           the current date
     * @param name          the name of the device
     * @param initialStatus the initial status of the device (On/Off)
     */
    Plug(Date now, String name, String initialStatus) {
        super(now, name, initialStatus, DeviceType.PLUG);
    }

    /**
     * Constructs a new Plug object with the given name, initial status, current
     * date, and ampere value.
     * 
     * @param now           the current date
     * @param name          the name of the device
     * @param initialStatus the initial status of the device (On/Off)
     * @param ampere        the ampere value of the device
     */
    Plug(Date now, String name, String initialStatus, double ampere) {
        super(now, name, initialStatus, DeviceType.PLUG);
        this.ampere = ampere;
    }

    /**
     * Calculates the consumed watt value of the device.
     * The formula is watt = volt * ampere * time.
     * 
     * @param time the current time
     */
    public void calculateWatt(Date time) {
        if (ampere != 0 && this.getOldSwitchtime() != null) {
            // Watt = V * i * t
            // milliseconds to hour -> / 3600000
            long duration = this.getSwitchtime() == null ? (time.getTime() - this.getOldSwitchtime().getTime())
                    : this.getSwitchtime().getTime() - this.getOldSwitchtime().getTime(); // in milliseconds
            setWatt(getWatt() + VOLT * ampere * duration / (60 * 60 * 1000));
            this.setOldSwitchtime(time);
        }
    }

    /**
     * Sets the ampere value of the device to zero.
     */
    public void setAmpereZero() {
        this.ampere = 0;
    }

    /**
     * Gets the ampere value of the device.
     * 
     * @return the ampere value of the device
     */
    public double getAmpere() {
        return ampere;
    }

    /**
     * Sets the ampere value of the device.
     * 
     * @param ampere the new ampere value of the device
     * @return true if the ampere value is valid, false otherwise
     */
    public boolean setAmpere(String ampere) {
        if (!checkAmpere(ampere))
            return false;
        this.ampere = Double.parseDouble(ampere);
        return true;
    }

    /**
     * 
     * Sets the wattage of the device.
     * 
     * @param watt The new wattage of the device.
     */
    public void setWatt(double watt) {
        this.watt = watt;
    }

    /**
     * 
     * Returns the watt of the device.
     * 
     * @return The watt of the device.
     */
    public double getWatt() {
        return watt;
    }

    /**
     * Checks if the given ampere value is valid.
     * - can be double value,
     * - is bigger than 0 .
     * 
     * @param ampereStr The string representation of the ampere value to check.
     * @return True if the ampere value is valid, false otherwise.
     */
    boolean checkAmpere(String ampereStr) {
        try {
            double ampere = Double.parseDouble(ampereStr);
            if (ampere <= 0) {
                IO.outputStrings.add("ERROR: Ampere value must be a positive number!");
                return false;
            }
            return true;
        } catch (Exception e) {
            IO.outputStrings.add(SmartHomeConstants.ERROR_COMMAND);
            return false;
        }
    }

    /**
     * Sets the device's ampere value and old switch time if the device is currently
     * on.
     * 
     * @param ampere The new ampere value of the device.
     * @param now    The current date and time.
     */
    void plugIn(String ampere, Date now) {
        if (this.setAmpere(ampere) && this.getStatus().equals("On")) {
            this.setOldSwitchtime(now);
        }
    }

    /**
     * Sets the status of the device and calculates the wattage usage if necessary.
     * Update the old switch time
     * 
     * @param now    The current date and time.
     * @param status The new status of the device.
     */
    public void setStatus(Date now, String status) {
        if (this.getAmpere() != 0)
            if (status.equals("Off"))
                this.calculateWatt(now);
            else
                this.setOldSwitchtime(now);
        super.setStatus(status);
    }

    /**
     * Returns a string representation of the device's current state.
     * inculding watt and switch time.
     * 
     * @return A string representation of the device's current state.
     */
    @Override
    public String toString() {
        return String.format(
                "%s consumed %,.2fW so far (excluding current device), and its time to switch its status is %s.",
                super.toString(), watt, getSwitchtimeString());
    }
}
