import java.util.Date;

public abstract class Book extends LibraryObject {
    Date returnDate;

    Book(int id, Date currentDate) {
        super(id);
        this.returnDate = currentDate;
    }

    Book(int id) {
        super(id);
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
