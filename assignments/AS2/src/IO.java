import java.io.IOException; //Exception Handling
import java.nio.file.Files; //Reading and writing
import java.nio.file.Paths; //Reaching io files
import java.util.ArrayList; //Output Strings
import java.util.List;
import java.io.FileWriter; // write to output file

/**
 * This class represents Input/Output operations for the project.
 * It includes methods for reading input file, writing to output file,
 * and handling input and output file names.
 */
public class IO {
    private String inputFileName;
    private String outputFileName;
    public static ArrayList<String> outputStrings; // output strings array for outputs line

    /**
     * Constructor of IO class.
     * Sets the input and output file names and
     * initializes the output strings array list.
     *
     * @param inputFileName  the name of the input file
     * @param outputFileName the name of the output file
     */
    IO(String inputFileName, String outputFileName) {
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
        IO.outputStrings = new ArrayList<String>();
    }

    /**
     * Reads the input file and returns its contents as a list of strings.
     *
     * @param discardEmptyLines if true, discards empty lines with respect to trim
     *                          by deleting.
     * @return contents of the file as a list of strings, returns null if there is
     *         any io exception.
     */
    List<String> readInputFile(boolean discardEmptyLines) {
        try {
            List<String> inputs = Files.readAllLines(Paths.get(inputFileName));
            if (discardEmptyLines) {
                int i = 0;
                while (i < inputs.size()) {
                    if (inputs.get(i).trim().equals("")) // get the line and trim it if it is empty
                        inputs.remove(i); // delete the line
                    else
                        i++;

                }
            }
            return inputs;
        } catch (IOException exception) {
            System.out.println(exception);
            return null;
        }
    }

    /**
     * Writes to the output file.
     * Writes the output strings array items which contain each output line.
     *
     * @return true if writing is successful, false if there is any io error.
     */
    boolean writeToFile() {
        try {
            FileWriter myWriter = new FileWriter(outputFileName);
            for (String line : outputStrings) {
                myWriter.write(line + "\n");
            }
            myWriter.close();
            return true;
        } catch (IOException exception) {
            System.out.println(exception);
            return false;
        }
    }

    /**
     * Getters and setters for inputFileName and outputFileName variables.
     */

    String getInputFileName() {
        return this.inputFileName;
    }

    String getOutputFileName() {
        return this.outputFileName;
    }

    void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }
}
