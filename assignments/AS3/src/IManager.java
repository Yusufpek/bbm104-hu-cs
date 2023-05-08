import java.util.ArrayList;
import java.util.List;

public interface IManager {
    List<Member> members = new ArrayList<Member>();
    List<Book> books = new ArrayList<Book>();
    Log borrowedBooks = new Log();
    Log readBooks = new Log();

    void addBook(String types);

    void addMember(String type);

    void borrowBook(String BookId, String memberId, String date);

    void returnBook(String BookId, String memberId, String date);

    void extendBook(String BookId, String memberId, String currentDate);

    void libraryRead(String BookId, String memberId, String currentDate);

    void getHistory();
}
