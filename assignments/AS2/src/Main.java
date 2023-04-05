import java.util.List;

class Main {
    public static void main(String[] args) {
        IO io = new IO(args[0], args[1]); // set the file names with command line arguments.
        List<String> commands = io.readInputFile(true);
        CommandController commandController = new CommandController(commands);
        commandController.parseCommands();
    }
}