import java.util.List;

/**
 * This is the main class of the program that
 * Reads the command line arguments,
 * Initializes IO, reads the input file,
 * Parses the commands and execute them,
 * Writes the output to the file.
 */
class Main {
    public static void main(String[] args) {
        IO io = new IO(args[0], args[1]); // set the file names with command line arguments.
        List<String> commands = io.readInputFile(true);
        CommandController commandController = new CommandController(commands);
        commandController.parseCommands();
        io.writeToFile();
    }
}