import java.util.Date;

public abstract class Book extends LibraryObject {

    private Date borrowDate;
    private Date returnDate;

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

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }
}
