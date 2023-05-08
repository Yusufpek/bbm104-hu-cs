import java.util.Date;

public class Printed extends Book {

    Printed(int id) {
        super(id);
    }

    Printed(int id, Date currentDate, int timeLimit) {
        super(id, new Date(calculateTime(currentDate, timeLimit)));
    }

    public void setReturnDate(Date currentDate, int timeLimit) {
        super.setReturnDate(new Date(calculateTime(currentDate, timeLimit)));
    }

    private static long calculateTime(Date currentDate, int timeLimit) {
        return currentDate.getTime() + TimeManager.weekToTime(timeLimit);
    }
}
