public class Plug extends SmartDevice {
    private double ampere;
    private double watt;
    private final int VOLT = 220; // constant volt value

    Plug(String name) {
        super(name, DeviceType.PLUG);
    }

    Plug(String name, String initialStatus) {
        super(name, initialStatus, DeviceType.PLUG);
    }

    Plug(String name, String initialStatus, double ampere) {
        super(name, initialStatus, DeviceType.PLUG);
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
    public void calculateWatt(double duration) {
        // Watt = V * i * t
        setWatt(getWatt() + VOLT * ampere * duration);
    }

    public double getAmpere() {
        return ampere;
    }

    public void setAmpere(double ampere) {
        this.ampere = ampere;
    }

    public double getWatt() {
        return watt;
    }

    public void setWatt(double watt) {
        this.watt = watt;
    }

    @Override
    public String toString() {
        return String.format(
                "%s and consumend %,.2fW so far (excluding current device), and its time to switch its status is %s",
                super.toString(), watt, getSwitchtimeString());
    }
}
