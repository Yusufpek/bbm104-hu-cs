import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The TimeController class represents a controller for managing time within the
 * program.
 * It has methods for setting, getting, and skipping time.
 */
public class TimeController {
    static Date now; // make static for reaching everywhere

    /**
     * Constructs a new TimeController object and sets the initial time.
     * Writes the error mesagges if it is necessary.
     *
     * @param time A String representing the initial time
     *             in the format "yyyy-MM-dd_HH:mm:ss"
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

    /**
     * Sets the time to the specified value.
     *
     * @param time A String representing the new time in the format
     *             "yyyy-MM-dd_HH:mm:ss"
     * @return true if the time was set successfully, false otherwise
     */
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

    /**
     * Sets the time to the specified Date object.
     *
     * @param time A Date object representing the new time
     */
    void setTime(Date time) {
        TimeController.now = time;
    }

    /**
     * Skips the specified number of minutes in time.
     * Also checks the time for reverse and 0 situations.
     * If the minute is not valid writes the error message.
     *
     * @param minute An integer representing the number of minutes to skip
     */
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

    /**
     * Returns the current time in the format "yyyy-MM-dd_HH:mm:ss"
     *
     * @return A String representing the current time
     */
    String getTime() {
        return dateFormat.format(TimeController.now);
    }

    private final String REVERSED_TIME_ERROR = "ERROR: Time cannot be reversed!";
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
}
