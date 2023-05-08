import java.util.Date;

public class PrintedBook extends Book {

    PrintedBook(int id) {
        super(id);
    }

    PrintedBook(int id, Date currentDate, int timeLimit) {
        super(id, new Date(currentDate.getTime() + TimeManager.weekToTime(timeLimit)));
    }

    public void setReturnDate(Date currentDate, int timeLimit) {
        super.setReturnDate(new Date(currentDate.getTime() + TimeManager.weekToTime(timeLimit)));
    }
}
