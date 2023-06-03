import java.io.IOException; //Exceotion Handling
import java.nio.file.Files; //Reading and writing
import java.nio.file.Paths; //Reaching io files
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.FileWriter; // write to output file

// Input Output file class
public class IO {
    private String inputFileName;
    private String outputFileName;
    static List<String> outputStrings;

    public IO(String inputFileName, String outputFileName) {
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
        IO.outputStrings = new ArrayList<String>();
    }

    List<String> readInputFile() {
        try {
            final List<String> inputs = Files.readAllLines(Paths.get(inputFileName));
            return inputs;
        } catch (IOException exception) {
            System.out.println(exception);
            return null;
        }
    }

    boolean writeToFile() {
        try {
            FileWriter myWriter = new FileWriter(outputFileName);
            Iterator<String> iterator = outputStrings.iterator();
            while (iterator.hasNext()) {
                String line = iterator.next();
                if (iterator.hasNext())
                    line += "\n";
                myWriter.write(line);
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
