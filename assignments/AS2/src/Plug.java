public class Plug extends SmartDevice {
    private int ampere;

    Plug(String name) {
        super(name, DeviceType.PLUG);
    }

    Plug(String name, String initialStatus) {
        super(name, initialStatus, DeviceType.PLUG);
    }

    Plug(String name, String initialStatus, int ampere) {
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
    public double calculateWatt(double duration) {
        // Watt = V * i * t
        return VOLT * ampere * duration;
    }

    private int VOLT = 220; // constant volt value

    public int getAmpere() {
        return ampere;
    }

    public void setAmpere(int ampere) {
        this.ampere = ampere;
    }
}
