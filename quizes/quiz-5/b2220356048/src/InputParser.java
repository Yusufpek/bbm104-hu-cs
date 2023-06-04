/**
 * The InputParser class is responsible for parsing input commands and values,
 * executing the operations and storing the results in the output queue.
 * It utilizes the CommandHandler class to handle different commands.
 */
public class InputParser {

    /**
     * Parses the input commands and values, performs the corresponding operations,
     * and stores the results in the output queue.
     *
     * @param inputs the queue of input commands and values
     */
    InputParser(Queue<String> inputs) {
        CommandHandler commandHandler = new CommandHandler();
        while (inputs.getValue() != null) {
            String input = inputs.pop();
            String command = input.split("\t")[0];
            String value = input.split("\t")[1];
            switch (command) {
                case TO_BINARY:
                    IO.outputStrings.add(commandHandler.convertBinary(value));
                    break;
                case COUNT_BINARY:
                    IO.outputStrings.add(commandHandler.countBinary(value));
                    break;
                case PALINDROME:
                    IO.outputStrings.add(commandHandler.checkPalindrome(value));
                    break;
                case EXPRESSION:
                    IO.outputStrings.add(commandHandler.checkExpression(value));
                    break;
                default:
                    System.out.println("default");
                    break;
            }
        }
    }

    // Final command sentences.
    private final String TO_BINARY = "Convert from Base 10 to Base 2:";
    private final String COUNT_BINARY = "Count from 1 up to n in binary:";
    private final String PALINDROME = "Check if following is palindrome or not:";
    private final String EXPRESSION = "Check if following expression is valid or not:";
}
