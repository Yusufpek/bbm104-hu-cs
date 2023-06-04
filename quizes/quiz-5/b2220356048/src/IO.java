import java.io.IOException; //Exceotion Handling
import java.nio.file.Files; //Reading and writing
import java.nio.file.Paths; //Reaching io files
import java.io.FileWriter; // write to output file

/**
 * The IO class provides input/output operations for reading from and writing to
 * files.
 */
public class IO {
    private String inputFileName;
    private String outputFileName;
    static Queue<String> outputStrings;

    /**
     * Constructs a new IO object with the specified input and output file names.
     *
     * @param inputFileName  the name of the input file
     * @param outputFileName the name of the output file
     */
    public IO(String inputFileName, String outputFileName) {
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
        IO.outputStrings = new Queue<String>();
    }

    /**
     * Reads the contents of the input file and returns them as a queue of strings.
     *
     * @return a queue of strings containing the lines from the input file
     */
    Queue<String> readInputFile() {
        try {
            Queue<String> inputs = new Queue<String>();
            for (String line : Files.readAllLines(Paths.get(inputFileName))) {
                inputs.add(line);
            }
            return inputs;
        } catch (IOException exception) {
            System.out.println(exception);
            return null;
        }
    }

    /**
     * Writes the output strings to the output file.
     *
     * @return true if the write operation was successful, false otherwise
     */
    boolean writeToFile() {
        try {
            FileWriter myWriter = new FileWriter(outputFileName);
            while (outputStrings.getValue() != null) {
                myWriter.write(outputStrings.remove() + "\n");
            }
            myWriter.close();
            return true;
        } catch (IOException exception) {
            System.out.println(exception);
            return false;
        }
    }

    /**
     * Returns the name of the input file.
     *
     * @return the input file name
     */
    public String getInputFileName() {
        return inputFileName;
    }

    /**
     * Sets the name of the input file.
     *
     * @param inputFileName the input file name to set
     */
    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    /**
     * Returns the name of the output file.
     *
     * @return the output file name
     */
    public String getOutputFileName() {
        return outputFileName;
    }

    /**
     * Sets the name of the output file.
     *
     * @param outputFileName the output file name to set
     */
    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }
}
