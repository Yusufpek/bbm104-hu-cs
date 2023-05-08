public class Constants {
    // actions
    public static String ADD_BOOK = "addBook";
    public static String ADD_MEMBER = "addMember";
    public static String BORROW_BOOK = "borrowBook";
    public static String RETURN_BOOK = "returnBook";
    public static String EXTEND_BOOK = "extendBook";
    public static String READ_LIBRARY = "readInLibrary";
    public static String GET_HISTORY = "getTheHistory";

    // can not messages
    public static String CAN_NOT_BORROW = "You cannot borrow this book!";
    public static String CAN_NOT_EXTEND = "You cannot extend the deadline!";
    public static String CAN_NOT_READ = "You can not read this book!";
    public static String CAN_NOT_READ_STUDENT = "Students can not read handwritten books!";
    public static String PENALTY_BORROW = "You must pay a penalty!";

    // log messages
    public static String HISTORY = "History of library:";
    public static String NEW_BOOK = "Created new book:";
    public static String NEW_MEMBER = "Created new member:";
    public static String NUMBER = "Number of ";
    public static String NUMBER_STUDENT = NUMBER + MemberType.Student.toString() + "s: ";
    public static String NUMBER_ACADEMIC = NUMBER + MemberType.Academic.toString() + "s: ";
    public static String NUMBER_PRINTED = NUMBER + "printed books: ";
    public static String NUMBER_HAND_WRITTEN = NUMBER + "handwritten books: ";
}
