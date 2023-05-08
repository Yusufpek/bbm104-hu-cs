import java.io.IOException; //Exception Handling
import java.nio.file.Files; //Reading and writing
import java.nio.file.Paths; //Reaching io files
import java.util.ArrayList; //Output Strings
import java.util.List;
import java.io.FileWriter; // write to output file

public class IO {
    private String inputFileName;
    private String outputFileName;
    public static ArrayList<String> outputStrings; // output strings array for outputs line

    IO(String inputFileName, String outputFileName) {
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
        IO.outputStrings = new ArrayList<String>();
    }

    static void newLine() {
        IO.outputStrings.add("");
    }

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

    boolean writeToFile() {
        try {
            FileWriter myWriter = new FileWriter(outputFileName);
            int counter = 0;
            for (String line : outputStrings) {
                counter++;
                if (counter == outputStrings.size())
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
