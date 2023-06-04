/**
 * The Main class serves as the entry point of the program.
 * It reads input from a file, parses the commands, executes them and writes
 * the output to a file.
 */
public class Main {
    public static void main(String[] args) {
        // Create an instance of IO with input and output file names
        IO io = new IO(args[0], args[1]);
        // Read input commands from the input file
        Queue<String> inputs = io.readInputFile();
        // Parse and execute the input commands
        new InputParser(inputs);
        // Write the output to the output file
        io.writeToFile();
    }
}