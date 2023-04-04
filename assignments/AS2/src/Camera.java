public class Camera extends SmartDevice {
    private int mbPerMinute; // consumed megabyts per minute

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
    public double usedMegabyts(double duration) {
        return mbPerMinute * duration;
    }
}
