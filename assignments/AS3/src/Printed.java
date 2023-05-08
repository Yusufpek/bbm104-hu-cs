import java.util.Date;

public class Printed extends Book {

    Printed(int id) {
        super(id);
    }

    Printed(int id, Date currentDate, int timeLimit) {
        super(id, new Date(calculateTime(currentDate, timeLimit)));
    }

    public void setBorrowDate(Date currentDate, int timeLimit) {
        super.setBorrowDate(currentDate);
        super.setReturnDate(new Date(calculateTime(currentDate, timeLimit)));
    }

    private static long calculateTime(Date currentDate, int timeLimit) {
        return currentDate.getTime() + TimeManager.weekToTime(timeLimit);
    }

    public void extendDate(int timeLimit) {
        super.setReturnDate(new Date(calculateTime(this.getReturnDate(), timeLimit)));
    }
}
