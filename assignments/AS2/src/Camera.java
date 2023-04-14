import java.util.Date;

public class Camera extends SmartDevice {
    private double mbPerMinute; // consumed megabyts per minute
    private double usedMegabytes; // consumed megabyts per minute

    /**
     * @param name
     */
    Camera(Date now, String name) {
        super(now, name, DeviceType.CAMERA);
    }

    /**
     * @param name
     * @param mbPerMinute
     */
    Camera(Date now, String name, double mbPerMinute) {
        super(now, name, DeviceType.CAMERA);
        this.mbPerMinute = mbPerMinute;
    }

    /**
     * @param name
     * @param mbPerMinute
     * @param initialStatus
     */
    Camera(Date now, String name, double mbPerMinute, String initialStatus) {
        super(now, name, initialStatus, DeviceType.CAMERA);
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

    public boolean setMBPerMinute(String megabytes) {
        if (!checkMB(megabytes))
            return false;
        this.mbPerMinute = Double.parseDouble(megabytes);
        return true;
    }

    @Override
    public void setStatus(String status) {
        if (status.equals("Off")) {
            final long milliSecondsDifference = ((this.getSwitchtime() == null)
                    || this.getSwitchtime().after(TimeController.now))
                            ? TimeController.now.getTime() - this.getOldSwitchtime().getTime()
                            : this.getSwitchtime().getTime() - this.getOldSwitchtime().getTime();
            calculateUsedMegabyts(milliSecondsDifference / (1000 * 60));
            this.setOldSwitchtime(getSwitchtime()); // update the old switch time
        }
        super.setStatus(status);
    }

    boolean checkMB(String megabytes) {
        try {
            double megabyte = Double.parseDouble(megabytes);
            if (megabyte <= 0) {
                IO.outputStrings.add("ERROR: Megabyte value must be a positive number!");
                return false;
            }
            return true;
        } catch (Exception e) {
            IO.outputStrings.add(CommandController.ERROR_COMMAND);
            return false;
        }

    }

    @Override
    public String toString() {
        return String.format(
                "%s used %.2f MB of storage so far (excluding current status), and its time to switch its status is %s.",
                super.toString(), getUsedMegabytes(), getSwitchtimeString());
    }
}
