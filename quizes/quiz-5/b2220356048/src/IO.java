import java.io.IOException; //Exceotion Handling
import java.nio.file.Files; //Reading and writing
import java.nio.file.Paths; //Reaching io files
import java.io.FileWriter; // write to output file

// Input Output file class
public class IO {
    private String inputFileName;
    private String outputFileName;
    static Queue<String> outputStrings;

    public IO(String inputFileName, String outputFileName) {
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
        IO.outputStrings = new Queue<String>();
    }

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

    public String getInputFileName() {
        return inputFileName;
    }

    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }
}
