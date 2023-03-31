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
    public ArrayList<String> outputStrings;

    IO(String inputFileName, String outputFileName) {
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
        this.outputStrings = new ArrayList<String>();
    }

    List<String> readInputFile() {
        try {
            List<String> inputs = Files.readAllLines(Paths.get(inputFileName));
            return inputs;
        } catch (IOException exception) {
            System.out.println(exception);
            return null;
        }
    }

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
