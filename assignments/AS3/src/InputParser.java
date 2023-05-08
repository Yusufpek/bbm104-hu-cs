import java.util.List;

public class InputParser {
    private List<String> inputs;

    InputParser(List<String> inputs) {
        this.inputs = inputs;
        LibraryManager manager = new LibraryManager();
        parseCommands(manager);
    }

    private void parseCommands(LibraryManager manager) {
        for (String input : inputs) {
            String[] splittedInput = input.split("\t");
            switch (splittedInput[0]) {
                case Constants.ADD_BOOK:
                    manager.addBook(splittedInput[1]); // Type of book H or P
                    break;
                case Constants.ADD_MEMBER:
                    manager.addMember(splittedInput[1]); // Type of member S or A
                    break;
                case Constants.BORROW_BOOK: // book id, member id, date
                    manager.borrowBook(splittedInput[1], splittedInput[2], splittedInput[3]);
                    break;
                case Constants.RETURN_BOOK: // book id, member id, date
                    manager.returnBook(splittedInput[1], splittedInput[2], splittedInput[3]);
                    break;
                case Constants.READ_LIBRARY: // book id, member id, date
                    manager.libraryRead(splittedInput[1], splittedInput[2], splittedInput[3]);
                    break;
                case Constants.EXTEND_BOOK: // book id, member id, date
                    manager.extendBook(splittedInput[1], splittedInput[2], splittedInput[3]);
                    break;
                case Constants.GET_HISTORY:
                    manager.getHistory();
                    break;
                default:
                    break;
            }
        }
    }
}
