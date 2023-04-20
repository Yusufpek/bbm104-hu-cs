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
            time = formatDate(time);
            if (!PARSE_FORMAT.format(PARSE_FORMAT.parse(time)).equals(time)) {
                throw new ParseException("wrong date", 0); // throw error
            }
            TimeController.now = PARSE_FORMAT.parse(time);
            IO.outputStrings.add(String.format("SUCCESS: Time has been set to %s!", getTime()));
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
            Date newDate = PARSE_FORMAT.parse(formatDate(time));
            if (newDate.getTime() - TimeController.now.getTime() < 0) {
                IO.outputStrings.add(REVERSED_TIME_ERROR);
            } else if (!dateFormat.format(dateFormat.parse(time)).equals(time)) {
                throw new ParseException("wrong date", 0); // throw error
            } else if (TimeController.now.equals(newDate)) {
                IO.outputStrings.add(SmartHomeConstants.ERROR_SET_TIME);
                return false;
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
        TimeController.now = new Date(now.getTime() + (long) minute * 60 * 1000);
    }

    /**
     * Returns the current time in the format "yyyy-MM-dd_HH:mm:ss"
     *
     * @return A String representing the current time
     */
    String getTime() {
        return dateFormat.format(TimeController.now);
    }

    /**
     * Converts the given time string from the format "yyyy-MM-dd_HH:mm:ss" to the
     * format "yyyy-MM-dd-HH-mm-ss".
     * If the input string at least 19 characters, returns new format;
     * otherwise add "0" for the non padding digits "3-" to "03-"
     * 
     * @param time the time string to be formatted
     * @return the formatted time string
     */
    String formatDate(String time) {
        // 2023-3-31_14:0:0
        // Converts to 2023-03-31-14-00-00
        if (time.length() < 19) {
            String formattedTime = "";
            String[] date = time.split("[-_:]"); // regex for date parse
            for (String part : date) {
                if (part.length() != 4 && part.length() != 2)
                    formattedTime += "0" + part; // add zero for padding
                else
                    formattedTime += part;
                formattedTime += "-";
            }
            return formattedTime.substring(0, formattedTime.length() - 1); // remove last "-" char
        }
        return time.replaceAll(":", "-").replaceAll("_", "-");
    }

    private final String REVERSED_TIME_ERROR = "ERROR: Time cannot be reversed!";
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
    public static final SimpleDateFormat PARSE_FORMAT = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
}
