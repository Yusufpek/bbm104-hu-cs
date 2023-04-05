public class Camera extends SmartDevice {
    private int mbPerMinute; // consumed megabyts per minute
    private double usedMegabytes; // consumed megabyts per minute

    /**
     * @param name
     */
    Camera(String name) {
        super(name, DeviceType.CAMERA);
    }

    /**
     * @param name
     * @param mbPerMinute
     */
    Camera(String name, int mbPerMinute) {
        super(name, DeviceType.CAMERA);
        this.mbPerMinute = mbPerMinute;
    }

    /**
     * @param name
     * @param mbPerMinute
     * @param initialStatus
     */
    Camera(String name, int mbPerMinute, String initialStatus) {
        super(name, initialStatus, DeviceType.CAMERA);
        this.mbPerMinute = mbPerMinute;
    }

    /**
     * * Calculate the used megabytes
     * * Formula is duration (in minute) * mb
     * 
     * @param duration time of the device how much it is open
     * @return used megabytes
     */
    public void calculateUsedMegabyts(double duration) {
        setUsedMegabytes(getUsedMegabytes() + mbPerMinute * duration);
    }

    public double getUsedMegabytes() {
        return usedMegabytes;
    }

    public void setUsedMegabytes(double usedMegabytes) {
        this.usedMegabytes = usedMegabytes;
    }

    @Override
    public String toString() {
        return String.format("%s and used %.2fMB of storage so far", super.toString(), getUsedMegabytes());
    }
}
