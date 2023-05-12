import java.util.List;
import java.util.Optional;

public class LibraryManager implements IManager {
    String borrowedMessage(int bookId, int memberId, String date) {
        return String.format("The book [%d] was borrowed by member [%d] at %s", bookId,
                memberId, date);
    }

    String readMessage(int bookId, int memberId, String date) {
        return String.format("The book [%d] was read in library by member [%d] at %s", bookId,
                memberId, date);
    }

    String returnMessage(int bookId, int memberId, String date, int fee) {
        return String.format("The book [%d] was returned by member [%d] at %s Fee: %d", bookId,
                memberId, date, fee);
    }

    Integer getId(List<?> list) {
        int id = list.size() > 0 ? ((LibraryObject) list.get(list.size() - 1)).getId() + 1 : 1;
        return Integer.toString(id).length() <= 6 ? id : null;
    }

    Book getBookByID(int id) {
        Optional<Book> book = books.stream().filter(item -> item.getId() == id).findFirst();
        return book.isPresent() ? book.get() : null;
    }

    Member getMemberByID(int id) {
        Optional<Member> member = members.stream().filter(item -> item.getId() == id).findFirst();
        return member.isPresent() ? member.get() : null;
    }

    int getCount(Class<?> model, List<?> list) {
        return (int) list.stream().filter(model::isInstance).count();
    }

    void printLibraryObjectByModel(Class<?> model, List<?> list) {
        list.stream().filter(model::isInstance).forEach(item -> IO.outputStrings.add(item.toString()));
    }

    @Override
    public void addBook(String type) {
        Integer id = getId(books);
        if (id == null) {
            IO.outputStrings.add(Constants.NOT_ADD_ID_BOOK);
            return;
        }
        Book newBook = type.equals("P")
                ? new Printed(id) // If member is Student
                : new Handwritten(id);
        books.add(newBook);
        IO.outputStrings.add(Constants.NEW_BOOK + " " + newBook.toString());
    }

    @Override
    public void addMember(String type) {
        Integer id = getId(members);
        if (id == null) {
            IO.outputStrings.add(Constants.NOT_ADD_ID_MEMBER);
            return;
        }
        Member newMember = type.equals("S")
                ? new Student(id) // If member is Student
                : new Academic(id);
        members.add(newMember);
        IO.outputStrings.add(Constants.NEW_MEMBER + " " + newMember.toString());
    }

    @Override
    public void borrowBook(String BookId, String memberId, String date) {
        Book book = getBookByID(Integer.parseInt(BookId));
        Member member = getMemberByID(Integer.parseInt(memberId));
        if (book == null) {
            IO.outputStrings.add(Constants.NOT_EXIST_BOOK);
        } else if (member == null) {
            IO.outputStrings.add(Constants.NOT_EXIST_MEMBER);
        } else if (book instanceof Handwritten || book.getBorrowDate() != null) {
            IO.outputStrings.add(Constants.CAN_NOT_BORROW);
        } else if (member.bookLimit <= 0) {
            IO.outputStrings.add(Constants.BORROW_LIMIT);
        } else {
            if (member.isMissed) {
                IO.outputStrings.add(Constants.PENALTY_BORROW);
                member.isMissed = false;
            }
            ((Printed) book).setBorrowDate(TimeManager.stringToDate(date), member.timeLimitInWeek);
            book.setMemberId(member.getId());
            member.bookLimit--;
            borrowedBooks.add(borrowedMessage(book.getId(), member.getId(), date));
            IO.outputStrings.add(borrowedMessage(book.getId(), member.getId(), date));
        }
    }

    @Override
    public void returnBook(String BookId, String memberId, String date) {
        Book book = getBookByID(Integer.parseInt(BookId));
        Member member = getMemberByID(Integer.parseInt(memberId));
        if (book == null) {
            IO.outputStrings.add(Constants.NOT_EXIST_BOOK);
        } else if (member == null) {
            IO.outputStrings.add(Constants.NOT_EXIST_MEMBER);
        } else if (book.getBorrowDate() == null) {
            IO.outputStrings.add(Constants.CAN_NOT_RETURN);
        } else if (book.getMemberId() != member.getId()) {
            IO.outputStrings.add(Constants.DIFFERENT_MEMBER);
        } else {
            int fee = 0;
            if (book.getReturnDate() != null) {
                int difference = TimeManager.getDifference(TimeManager.stringToDate(date), book.getReturnDate());
                if (difference <= 0) {
                    fee = 0;
                } else {
                    fee = difference;
                    member.isMissed = true;
                }
            }
            IO.outputStrings.add(returnMessage(book.getId(), member.getId(), date, fee));
            if (book instanceof Printed && book.getReturnDate() != null)
                borrowedBooks.remove(borrowedMessage(book.getId(), member.getId(),
                        TimeManager.dateToString(book.getBorrowDate())));
            else
                readBooks.remove(
                        readMessage(book.getId(), member.getId(), TimeManager.dateToString(book.getBorrowDate())));
            book.setBorrowDate(null); // return the book
            member.setBookLimit(member.getBookLimit() + 1);
        }
    }

    @Override
    public void extendBook(String BookId, String memberId, String currentDate) {
        Book book = getBookByID(Integer.parseInt(BookId));
        Member member = getMemberByID(Integer.parseInt(memberId));
        if (book == null) {
            IO.outputStrings.add(Constants.NOT_EXIST_BOOK);
        } else if (member == null) {
            IO.outputStrings.add(Constants.NOT_EXIST_MEMBER);
        } else if (book.getBorrowDate() == null // is borrowed, deadline passed, did member extend check
                || book.getReturnDate().getTime() < TimeManager.stringToDate(currentDate).getTime()
                || TimeManager.getDifference(book.getReturnDate(), book.getBorrowDate()) > member.getTimeLimitInDay()) {
            IO.outputStrings.add(Constants.CAN_NOT_EXTEND);
        } else {
            ((Printed) book).extendDate(member.timeLimitInWeek);
            IO.outputStrings.add(String.format("The deadline of book [%d] was extended by member [%d] at %s",
                    book.getId(), member.getId(), currentDate));
            IO.outputStrings.add(String.format("New deadline of book [%d] is %s", book.getId(),
                    TimeManager.dateToString(book.getReturnDate())));
        }
    }

    @Override
    public void libraryRead(String BookId, String memberId, String currentDate) {
        Book book = getBookByID(Integer.parseInt(BookId));
        Member member = getMemberByID(Integer.parseInt(memberId));
        if (book == null) {
            IO.outputStrings.add(Constants.NOT_EXIST_BOOK);
        } else if (member == null) {
            IO.outputStrings.add(Constants.NOT_EXIST_MEMBER);
        } else if (book instanceof Handwritten && member instanceof Student) {
            IO.outputStrings.add(Constants.CAN_NOT_READ_STUDENT);
        } else if (book.getBorrowDate() != null) {
            IO.outputStrings.add(Constants.CAN_NOT_READ);
        } else {
            book.setBorrowDate(TimeManager.stringToDate(currentDate));
            book.setMemberId(member.getId());
            readBooks.add(readMessage(book.getId(), member.getId(), currentDate));
            IO.outputStrings.add(readMessage(book.getId(), member.getId(), currentDate));
        }
    }

    @Override
    public void getHistory() {
        IO.outputStrings.add(Constants.HISTORY);
        IO.newLine();
        // Number of students
        IO.outputStrings.add(Constants.NUMBER_STUDENT + getCount(Student.class, members));
        printLibraryObjectByModel(Student.class, members);
        IO.newLine();
        // Number of academic
        IO.outputStrings.add(Constants.NUMBER_ACADEMIC + getCount(Academic.class, members));
        printLibraryObjectByModel(Academic.class, members);
        IO.newLine();
        // Number of printed
        IO.outputStrings.add(Constants.NUMBER_PRINTED + getCount(Printed.class, books));
        printLibraryObjectByModel(Printed.class, books);
        IO.newLine();
        // Number of handwritten
        IO.outputStrings.add(Constants.NUMBER_HAND_WRITTEN + getCount(Handwritten.class, books));
        printLibraryObjectByModel(Handwritten.class, books);
        IO.newLine();
        // Number of borrowed
        IO.outputStrings.add(Constants.NUMBER_BORROWED + borrowedBooks.size());
        IO.outputStrings.add(borrowedBooks.toString());
        if (borrowedBooks.size() != 0)
            IO.newLine();
        // Number of read in library
        IO.outputStrings.add(Constants.NUMBER_READ + readBooks.size());
        if (readBooks.size() != 0)
            IO.outputStrings.add(readBooks.toString());
    }
}
