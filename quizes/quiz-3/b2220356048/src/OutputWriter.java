import java.io.IOException; //Exceotion Handling
import java.io.FileWriter; // write to output file

// Input Output file class
public class OutputWriter {
    final private static String outputFileName = "output.txt";

    static boolean writeToFile(String message) {
        try {
            FileWriter myWriter = new FileWriter(outputFileName, true);
            myWriter.write(message + "\n");
            myWriter.close();
            return true;
        } catch (IOException exception) {
            System.out.println(exception);
            return false;
        }
    }
}
