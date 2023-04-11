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
            if (!dateFormat.format(dateFormat.parse(time)).equals(time)) {
                throw new ParseException("wrong date", 0); // throw error
            }
            this.now = dateFormat.parse(time);
            System.out.println(String.format("SUCCESS: Time has been set to %s!", time));
        } catch (ParseException e) {
            System.out.println("ERROR: Time format is not correct!");
        }
    }

    boolean setTime(String time) {
        try {
            Date newDate = dateFormat.parse(time);
            if (newDate.getTime() - this.now.getTime() < 0) {
                System.out.println("ERROR: Time cannot be reversed!");
            } else if (!dateFormat.format(dateFormat.parse(time)).equals(time)) {
                throw new ParseException("wrong date", 0); // throw error
            } else {
                this.now = newDate;
                return true;
            }
        } catch (ParseException e) {
            System.out.println("ERROR: Time format is not correct!");
        }
        return false;
    }

    void setTime(Date time) {
        this.now = time;
    }

    void skipMinutes(int minute) {
        this.now = new Date(now.getTime() + minute * 60 * 1000);
    }

    String getTime() {
        return dateFormat.format(now);
    }

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
}
