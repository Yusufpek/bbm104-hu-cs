import java.util.Date;
import java.util.List;

public class CommandController {
    List<String> commands;
    TimeController timeController;
    ItemController itemController;

    CommandController(List<String> commands) {
        this.commands = commands;
        itemController = new ItemController();
    }

    void parseCommands() {
        for (String line : commands) {
            System.out.println("COMMAND: " + line);
            String[] parsedLine = line.split("\t");
            if (parsedLine[0].equals(COMMAND_INITIAL_TIME)) {
                if (timeController == null)
                    timeController = new TimeController(parsedLine[1]);
                else
                    System.out.println(ERROR_COMMAND);
            }
            // if there is no time controller do not look for any commands.
            // if there is a set initial time command time controller is not null.
            if (timeController != null) {
                // commands
                switch (parsedLine[0]) {
                    case COMMAND_SET_TIME:
                        timeController.setTime(parsedLine[1]);
                        break;
                    case COMMAND_SKIP:
                        timeController.skipMinutes(Integer.parseInt(parsedLine[1]));
                        itemController.switchItem(timeController.now);
                        break;
                    case COMMAND_NOP:
                        Date time = itemController.devices.get(0).getSwitchtime();
                        if (time == null) {
                            System.out.println(ERROR_NOP);
                        }
                        timeController.setTime(time);
                        itemController.switchItem(timeController.now);
                        break;
                    case COMMAND_ADD:
                        itemController.addItem(parsedLine);
                        break;
                    case COMMAND_REMOVE:
                        itemController.removeItem(parsedLine[1]);
                        break;
                    case COMMAND_REPORT:
                        System.out.println("z report");
                        break;
                    default:
                        System.out.println(ERROR_COMMAND);
                        break;
                }
            }
        }

        System.out.println("Commands read");
        System.out.println("Next is items");
        for (SmartDevice string : itemController.devices) {
            System.out.println(string.getName());
        }
    }

    private final String COMMAND_INITIAL_TIME = "SetInitialTime";
    private final String COMMAND_SET_TIME = "SetTime";
    private final String COMMAND_SKIP = "SkipMinutes";
    private final String COMMAND_ADD = "Add";
    private final String COMMAND_REMOVE = "Remove";
    private final String COMMAND_NOP = "Nop";
    private final String COMMAND_REPORT = "ZReport";
    // Error Messages
    static public final String ERROR_COMMAND = "ERROR: Erroneous command!";
    static public final String ERROR_NOP = "ERROR: There is nothing to switch!";
}
