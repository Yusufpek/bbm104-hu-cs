import java.util.Date;

public class Camera extends SmartDevice {
    private int mbPerMinute; // consumed megabyts per minute
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
    Camera(Date now, String name, int mbPerMinute) {
        super(now, name, DeviceType.CAMERA);
        this.mbPerMinute = mbPerMinute;
    }

    /**
     * @param name
     * @param mbPerMinute
     * @param initialStatus
     */
    Camera(Date now, String name, int mbPerMinute, String initialStatus) {
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
        this.mbPerMinute = Integer.parseInt(megabytes);
        return true;
    }

    @Override
    public void setStatus(String status) {
        if (status.equals("Off") && this.getStatus().equals("On")) {
            final long milliSecondsDifference = (this.getSwitchtime().getTime() - this.getOldSwitchtime().getTime());
            calculateUsedMegabyts(milliSecondsDifference / (1000 * 60));
        }
        System.out.println("camera set status");
        super.setStatus(status);
    }

    boolean checkMB(String megabytes) {
        try {
            int megabyte = Integer.parseInt(megabytes);
            if (megabyte < 0) {
                System.out.println("ERROR: Megabyte value has to be a positive number!");
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public String toString() {
        return String.format(
                "%s and used %.2fMB of storage so far (excluding current device), and its time to switch its status is %s.",
                super.toString(), getUsedMegabytes(), getSwitchtimeString());
    }
}
