import java.io.IOException; //For exception Handling
import java.nio.file.Files; //For reading and writing
import java.nio.file.Paths; //For reaching io files
import java.util.ArrayList; //For output Strings
import java.util.List; //For input files content
import java.io.FileWriter; // For write to output file

// Input Output file class
public class IO {
    public ArrayList<String> outputStrings;

    IO() {
        this.outputStrings = new ArrayList<String>();
    }

    static List<String> readInputFile(String inputFileName) {
        try {
            List<String> inputs = Files.readAllLines(Paths.get(inputFileName));
            return inputs;
        } catch (IOException exception) {
            System.out.println(exception);
            return null;
        }
    }

    boolean writeToFile(String outputFileName) {
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
}