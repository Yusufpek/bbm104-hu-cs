import java.io.IOException; //Exceotion Handling
import java.nio.file.Files; //Reading and writing
import java.nio.file.Paths; //Reaching io files
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.io.FileWriter; // write to output file

// Input Output file class
public class IO {

    static List<String> readInputFile(String inputFileName) {
        try {
            List<String> inputs = Files.readAllLines(Paths.get(inputFileName));
            return inputs;
        } catch (IOException exception) {
            System.out.println(exception);
            return null;
        }
    }

    static boolean writeToFile(String outputFileName, Collection<String> outputStrings) {
        try {
            FileWriter myWriter = new FileWriter("poem" + outputFileName + ".txt");
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

    static boolean writeToFileMap(String outputFileName, HashMap<Integer, String> output) {
        try {
            FileWriter myWriter = new FileWriter("poem" + outputFileName + ".txt");
            int lineCount = 0;
            for (HashMap.Entry<Integer, String> entry : output.entrySet()) {
                lineCount++;
                String line = String.format("%d\t%s", entry.getKey(), entry.getValue());
                if (lineCount < output.size())
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
}
