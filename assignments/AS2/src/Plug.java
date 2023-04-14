import java.util.Date;

public class Plug extends SmartDevice {
    private double ampere;
    private double watt;
    private final int VOLT = 220; // constant volt value

    Plug(Date now, String name) {
        super(now, name, DeviceType.PLUG);
    }

    Plug(Date now, String name, String initialStatus) {
        super(now, name, initialStatus, DeviceType.PLUG);
    }

    Plug(Date now, String name, String initialStatus, double ampere) {
        super(now, name, initialStatus, DeviceType.PLUG);
        this.ampere = ampere;
    }

    /*
     * Calculate the consumed watt value
     * Formula is watt = volt * ampere * time
     * 
     * @param duration time of the device when it is opening
     * 
     * @return consumed watt
     */
    public void calculateWatt(Date time) {
        if (ampere != 0 && this.getOldSwitchtime() != null) {
            // Watt = V * i * t
            // milliseconds to hour -> / 3600000
            long duration = (time.getTime() - this.getOldSwitchtime().getTime()); // in milliseconds
            setWatt(getWatt() + VOLT * ampere * duration / (60 * 60 * 1000));
            this.setOldSwitchtime(time);
        }
    }

    public void setAmpereZero() {
        this.ampere = 0;
    }

    public double getAmpere() {
        return ampere;
    }

    public boolean setAmpere(String ampere) {
        if (!checkAmpere(ampere))
            return false;
        this.ampere = Double.parseDouble(ampere);
        return true;
    }

    public double getWatt() {
        return watt;
    }

    public void setWatt(double watt) {
        this.watt = watt;
    }

    boolean checkAmpere(String ampereStr) {
        try {
            double ampere = Double.parseDouble(ampereStr);
            if (ampere <= 0) {
                IO.outputStrings.add("ERROR: Ampere value must be a positive number!");
                return false;
            }
            return true;
        } catch (Exception e) {
            IO.outputStrings.add(CommandController.ERROR_COMMAND);
            return false;
        }
    }

    void plugIn(String ampere, Date now) {
        if (this.setAmpere(ampere) && this.getStatus().equals("On")) {
            this.setOldSwitchtime(now);
        }
    }

    public void setStatus(Date now, String status) {
        if (this.getAmpere() != 0)
            if (status.equals("Off"))
                this.calculateWatt(now);
            else
                this.setOldSwitchtime(now);
        super.setStatus(status);
    }

    @Override
    public String toString() {
        return String.format(
                "%s consumed %.2fW so far (excluding current device), and its time to switch its status is %s.",
                super.toString(), watt, getSwitchtimeString());
    }
}
