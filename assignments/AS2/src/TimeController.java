import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeController {
    Date now;

    /**
     * @param time
     */
    TimeController(String time) {
        try {
            this.now = dateFormat.parse(time);
            System.out.println(String.format("SUCCESS: Time has been set to %s!", time));
        } catch (ParseException e) {
            System.out.println("Error, wrong date !");
            e.printStackTrace();
        }
    }

    void setTime(String time) {
        try {
            Date newDate = dateFormat.parse(time);
            if (newDate.getTime() - this.now.getTime() < 0) {
                System.out.println("ERROR: Time cannot be reversed!");
            } else {
                this.now = newDate;
            }
        } catch (ParseException e) {
            System.out.println("Error, wrong date !");
            e.printStackTrace();
        }
    }

    void setTime(Date time) {
        this.now = time;
    }

    void skipMinutes(int minute) {
        this.now = new Date(now.getTime() + minute * 60 * 1000);
    }

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss");
}
