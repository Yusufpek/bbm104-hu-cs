import java.text.SimpleDateFormat;

public class TimeManager {
    static final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");

    static long weekToTime(int week) {
        return week * 7 * 24 * 60 * 60; // day, hour, minute, second
    }
}
