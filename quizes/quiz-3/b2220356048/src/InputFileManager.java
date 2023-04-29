import java.io.IOException; //Exceotion Handling
import java.nio.file.Files; //Reading and writing
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths; //Reaching io files

// Input Output file class
public class InputFileManager {
    private String inputFileName;

    InputFileManager(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    String readInputFile() {
        try {
            String inputs = Files.readAllLines(Paths.get(inputFileName)).toString(); // return [input file content]
            String formattedInputs = inputs.substring(1, inputs.length() - 1); // delete [] chars
            // check empty
            if (formattedInputs.length() == 0) {
                throw new EmptyError();
            }
            // unic code check with regex
            // (delete between a-z, A-Z and space chars and check lenght)
            if (formattedInputs.replaceAll("([a-z A-Z])", "").length() != 0) {
                throw new InvalidValueError();
            }
            return formattedInputs;
            // return inputs;
        } catch (NoSuchFileException exception) {
            return Messages.INPUT_NOT_FOUND_ERROR;
        } catch (EmptyError exception) {
            return exception.getMessage();
        } catch (InvalidValueError exception) {
            return exception.getMessage();
        } catch (IOException e) {
            return e.toString();
        }
    }

    String getInputFileName() {
        return this.inputFileName;
    }

    void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }
}
