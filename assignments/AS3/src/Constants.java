public class Constants {
    // actions
    public static final String ADD_BOOK = "addBook";
    public static final String ADD_MEMBER = "addMember";
    public static final String BORROW_BOOK = "borrowBook";
    public static final String RETURN_BOOK = "returnBook";
    public static final String EXTEND_BOOK = "extendBook";
    public static final String READ_LIBRARY = "readInLibrary";
    public static final String GET_HISTORY = "getTheHistory";

    // can not messages
    public static final String NOT_EXIST_BOOK = "The book is not exist!";
    public static final String NOT_EXIST_MEMBER = "The member is not exist!";
    public static final String CAN_NOT_BORROW = "You cannot borrow this book!";
    public static final String BORROW_LIMIT = "You have exceeded the borrowing limit!";
    public static final String CAN_NOT_RETURN = "You cannot return this book, this book is not borrowed!";
    public static final String CAN_NOT_EXTEND = "You cannot extend the deadline!";
    public static final String CAN_NOT_READ = "You can not read this book!";
    public static final String CAN_NOT_READ_STUDENT = "Students can not read handwritten books!";
    public static final String PENALTY_BORROW = "You must pay a penalty!";

    // log messages
    public static final String HISTORY = "History of library:";
    public static final String NEW_BOOK = "Created new book:";
    public static final String NEW_MEMBER = "Created new member:";
    public static final String NUMBER = "Number of ";
    public static final String NUMBER_STUDENT = NUMBER + MemberType.Student.toString().toLowerCase() + "s: ";
    public static final String NUMBER_ACADEMIC = NUMBER + MemberType.Academic.toString().toLowerCase() + "s: ";
    public static final String NUMBER_PRINTED = NUMBER + "printed books: ";
    public static final String NUMBER_HAND_WRITTEN = NUMBER + "handwritten books: ";
    public static final String NUMBER_BORROWED = NUMBER + "borrowed books: ";
    public static final String NUMBER_READ = NUMBER + "books read in library: ";
}
