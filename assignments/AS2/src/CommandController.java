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
        String lastLine = "";
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
            else if (timeController != null && timeController.now != null) {
                // commands
                try {
                    switch (parsedLine[0]) {
                        case COMMAND_SET_TIME:
                            if (timeController.setTime(parsedLine[1]))
                                itemController.switchItem(timeController.now);
                            break;
                        case COMMAND_SKIP:
                            timeController.skipMinutes(Integer.parseInt(parsedLine[1]));
                            itemController.switchItem(timeController.now);
                            break;
                        case COMMAND_NOP:
                            nopCommand();
                            break;
                        case COMMAND_ADD:
                            itemController.addItem(parsedLine);
                            break;
                        case COMMAND_REMOVE:
                            itemController.removeItem(parsedLine[1]);
                            break;
                        case COMMAND_BRIGHTNESS:
                            itemController.setBrightness(parsedLine[1], parsedLine[2]);
                            break;
                        case COMMAND_SWITCH:
                            itemController.setStatus(parsedLine[1], parsedLine[2]);
                            break;
                        case COMMAND_PLUG_IN:
                            itemController.plugIn(parsedLine[1], parsedLine[2]);
                            break;
                        case COMMAND_PLUG_OUT:
                            itemController.plugOut(parsedLine[1]);
                            break;
                        case COMMAND_KELVIN:
                            itemController.setKelvin(parsedLine[1], parsedLine[2]);
                            break;
                        case COMMAND_WHITE:
                            itemController.setWhite(parsedLine[1], parsedLine[2], parsedLine[3]);
                            break;
                        case COMMAND_COLOR_CODE:
                            itemController.setColorCode(parsedLine[1], parsedLine[2]);
                        case COMMAND_COLOR:
                            itemController.setColor(parsedLine[1], parsedLine[2], parsedLine[3]);
                            break;
                        case COMMAND_SWITCH_TIME:
                            itemController.setSwitchTime(parsedLine[1], parsedLine[2]);
                            break;
                        case COMMAND_NAME:
                            itemController.changeName(parsedLine[1], parsedLine[2]);
                            break;
                        case COMMAND_REPORT:
                            System.out.println("Time is: " + timeController.getTime());
                            itemController.devices.forEach(device -> System.out.println(device.toString()));
                            break;
                        default:
                            System.out.println(ERROR_COMMAND);
                            break;
                    }
                } catch (Exception exception) {
                    System.out.println(ERROR_COMMAND);
                }

            }
            lastLine = line;
        }
        // if last command is not z report, write the z report
        if (!lastLine.equals(COMMAND_REPORT))
            itemController.devices.forEach(device -> System.out.println(device.toString()));
    }

    private void nopCommand() {
        try {
            Date time = itemController.devices.get(0).getSwitchtime();
            if (time == null) {
                System.out.println(ERROR_NOP);
                return;
            }
            timeController.setTime(time);
            itemController.switchItem(timeController.now);
        } catch (IndexOutOfBoundsException exception) {
            System.out.println(ERROR_NOP);
        }
    }

    private final String COMMAND_INITIAL_TIME = "SetInitialTime";
    private final String COMMAND_SET_TIME = "SetTime";
    private final String COMMAND_SKIP = "SkipMinutes";
    private final String COMMAND_ADD = "Add";
    private final String COMMAND_REMOVE = "Remove";
    private final String COMMAND_NOP = "Nop";
    private final String COMMAND_REPORT = "ZReport";
    private final String COMMAND_SWITCH = "Switch";
    private final String COMMAND_PLUG_IN = "PlugIn";
    private final String COMMAND_PLUG_OUT = "PlugOut";
    private final String COMMAND_BRIGHTNESS = "SetBrightness";
    private final String COMMAND_WHITE = "SetWhite";
    private final String COMMAND_KELVIN = "SetKelvin";
    private final String COMMAND_COLOR = "SetColor";
    private final String COMMAND_COLOR_CODE = "SetColorCode";
    private final String COMMAND_SWITCH_TIME = "SetSwitchTime";
    private final String COMMAND_NAME = "ChangeName";
    // Error Messages
    static public final String ERROR_COMMAND = "ERROR: Erroneous command!";
    static public final String ERROR_NOP = "ERROR: There is nothing to switch!";
}
