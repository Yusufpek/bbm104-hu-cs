import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeManager {
    static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    static long weekToTime(int week) {
        return week * 7 * 24 * 60 * 60 * 1000; // day, hour, minute, second, millisecond
    }

    static Date stringToDate(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            System.out.println(e.toString());
            return null;
        }
    }

    static String dateToString(Date date) {
        return dateFormat.format(date);
    }

    static int getDifference(Date date, Date bookDate) {
        return (int) ((int) (date.getTime() - bookDate.getTime()) / (24 * 60 * 60 * 1000)); // milliseconds to day
    }

}
