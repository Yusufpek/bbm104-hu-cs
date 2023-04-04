import java.io.IOException; //Exceotion Handling
import java.nio.file.Files; //Reading and writing
import java.nio.file.Paths; //Reaching io files
import java.util.ArrayList; //Output Strings
import java.util.List;
import java.io.FileWriter; // write to output file

// Input Output file class
public class IO {
    private String inputFileName;
    private String outputFileName;
    public ArrayList<String> outputStrings; // output strings array for outputs line

    /*
     * Contructor method of this class,
     * Set the input and output files names
     * Set the output strings array list as a empty list.
     * 
     * 
     * @param outputFileName for setting the output file name.
     * 
     * @param inputFileName for setting the input file name.
     */
    IO(String inputFileName, String outputFileName) {
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
        this.outputStrings = new ArrayList<String>();
    }

    /*
     * Read the input file which is given in constructor
     * 
     * @param discardEmptyLines If true, discards empty lines with respect to trim
     * by deleting.
     * 
     * @return Contents of the file as a list of string, returns null if there is
     * any io exception.
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

    /*
     * Write to the output file which is given in constructor.
     * Write the output strings array items which is contains each output line.
     * Output strings is a list which items are added in <> class
     * 
     * @return Situation of function if the writing is succesfully return true, if
     * there is any io error return false.
     */
    boolean writeToFile() {
        try {
            FileWriter myWriter = new FileWriter(outputFileName);
            for (String line : outputStrings) {
                if (outputStrings.indexOf(line) == outputStrings.size() - 1)
                    myWriter.write(line);
                else
                    myWriter.write(line + "\n");
            }
            myWriter.close();
            return true;
        } catch (IOException exception) {
            System.out.println(exception);
            return false;
        }
    }

    /*
     *
     * Getters and setters for inputFileName and outputFileName variables
     * 
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
