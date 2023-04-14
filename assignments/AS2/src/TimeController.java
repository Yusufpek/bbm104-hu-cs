import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeController {
    static Date now; // make static for reaching everywhere

    /**
     * @param time
     */
    TimeController(String time) {
        try {
            if (!dateFormat.format(dateFormat.parse(time)).equals(time)) {
                throw new ParseException("wrong date", 0); // throw error
            }
            TimeController.now = dateFormat.parse(time);
            IO.outputStrings.add(String.format("SUCCESS: Time has been set to %s!", time));
        } catch (ParseException e) {
            TimeController.now = null;
            IO.outputStrings.add("ERROR: Format of the initial date is wrong! Program is going to terminate!");
        }
    }

    boolean setTime(String time) {
        try {
            Date newDate = dateFormat.parse(time);
            if (newDate.getTime() - TimeController.now.getTime() < 0) {
                IO.outputStrings.add(REVERSED_TIME_ERROR);
            } else if (!dateFormat.format(dateFormat.parse(time)).equals(time)) {
                throw new ParseException("wrong date", 0); // throw error
            } else {
                TimeController.now = newDate;
                return true;
            }
        } catch (ParseException e) {
            IO.outputStrings.add("ERROR: Time format is not correct!");
        }
        return false;
    }

    void setTime(Date time) {
        TimeController.now = time;
    }

    void skipMinutes(int minute) {
        if (minute < 0) {
            IO.outputStrings.add(REVERSED_TIME_ERROR);
            return;
        }
        if (minute == 0) {
            IO.outputStrings.add("ERROR: There is nothing to skip!");
            return;
        }
        TimeController.now = new Date(now.getTime() + minute * 60 * 1000);
    }

    String getTime() {
        return dateFormat.format(TimeController.now);
    }

    private final String REVERSED_TIME_ERROR = "ERROR: Time cannot be reversed!";
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
}
