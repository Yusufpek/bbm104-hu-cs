import java.util.Date;
import java.util.List;

public class CommandController {
    List<String> commands; // List of commands to be parsed and executed.
    TimeController timeController; // TimeController instance for handling time-related operations
    ItemController itemController; // ItemController instance for handling item-related operations

    CommandController(List<String> commands) {
        this.commands = commands;
        itemController = new ItemController();
    }

    /**
     * Method to parse and execute the list of commands.
     * 
     * The method parses each commands and executes the relevant operation using the
     * TimeController and ItemController instances.
     * Also the method adds the error messages and information messages
     * to output array list if it is necessary.
     */
    void parseCommands() {
        String lastLine = "";
        for (String line : commands) {
            IO.outputStrings.add("COMMAND: " + line);
            String[] parsedLine = line.split("\t");
            if (parsedLine[0].equals(SmartHomeConstants.COMMAND_INITIAL_TIME)) {
                if (TimeController.now != null) {
                    IO.outputStrings.add(SmartHomeConstants.ERROR_COMMAND);
                    continue;
                }
                if (parsedLine.length != 2) {
                    IO.outputStrings.add(SmartHomeConstants.ERROR_SET_INITIAL_TIME);
                    return;
                }
                if (timeController == null)
                    timeController = new TimeController(parsedLine[1]);
                if (TimeController.now == null) { // if "now" can not be assigned
                    return;
                }
            } else if (timeController == null || TimeController.now == null) {
                IO.outputStrings.add(SmartHomeConstants.ERROR_SET_INITIAL_TIME);
                return;
            }
            // if there is no time controller do not look for any commands.
            // if there is a set initial time command time controller is not null.
            else if (timeController != null && TimeController.now != null) {
                // commands
                try {
                    switch (parsedLine[0]) {
                        case SmartHomeConstants.COMMAND_SET_TIME:
                            if (timeController.setTime(parsedLine[1]))
                                itemController.switchItems();
                            break;
                        case SmartHomeConstants.COMMAND_SKIP:
                            if (parsedLine.length != 2)
                                throw new IndexOutOfBoundsException();
                            timeController.skipMinutes(Integer.parseInt(parsedLine[1]));
                            itemController.switchItems();
                            break;
                        case SmartHomeConstants.COMMAND_NOP:
                            nopCommand();
                            break;
                        case SmartHomeConstants.COMMAND_ADD:
                            itemController.addItem(parsedLine);
                            break;
                        case SmartHomeConstants.COMMAND_REMOVE:
                            itemController.removeItem(parsedLine[1]);
                            break;
                        case SmartHomeConstants.COMMAND_BRIGHTNESS:
                            itemController.setBrightness(parsedLine[1], parsedLine[2]);
                            break;
                        case SmartHomeConstants.COMMAND_SWITCH:
                            itemController.setStatus(parsedLine[1], parsedLine[2]);
                            break;
                        case SmartHomeConstants.COMMAND_PLUG_IN:
                            itemController.plugIn(parsedLine[1], parsedLine[2]);
                            break;
                        case SmartHomeConstants.COMMAND_PLUG_OUT:
                            itemController.plugOut(parsedLine[1]);
                            break;
                        case SmartHomeConstants.COMMAND_KELVIN:
                            itemController.setKelvin(parsedLine[1], parsedLine[2]);
                            break;
                        case SmartHomeConstants.COMMAND_WHITE:
                            itemController.setWhite(parsedLine[1], parsedLine[2], parsedLine[3]);
                            break;
                        case SmartHomeConstants.COMMAND_COLOR_CODE:
                            itemController.setColorCode(parsedLine[1], parsedLine[2]);
                            break;
                        case SmartHomeConstants.COMMAND_COLOR:
                            itemController.setColor(parsedLine[1], parsedLine[2], parsedLine[3]);
                            break;
                        case SmartHomeConstants.COMMAND_SWITCH_TIME:
                            itemController.setSwitchTime(parsedLine[1], parsedLine[2]);
                            break;
                        case SmartHomeConstants.COMMAND_NAME:
                            itemController.changeName(parsedLine[1], parsedLine[2]);
                            break;
                        case SmartHomeConstants.COMMAND_REPORT:
                            zReport();
                            break;
                        default:
                            IO.outputStrings.add(SmartHomeConstants.ERROR_COMMAND);
                            break;
                    }
                } catch (Exception exception) {
                    IO.outputStrings.add(SmartHomeConstants.ERROR_COMMAND);// + " in command controller for");
                }
            }
            lastLine = line;
        }
        // if last command is not z report, write the z report
        if (!lastLine.equals(SmartHomeConstants.COMMAND_REPORT) && TimeController.now != null) {
            IO.outputStrings.add("ZReport:");
            zReport();
        }
    }

    /**
     * Method to output of
     * Z-Report containing the current state of all devices and the current time.
     */

    private void zReport() {
        IO.outputStrings.add("Time is:\t" + timeController.getTime());
        itemController.devices.forEach(device -> IO.outputStrings.add(device.toString()));
    }

    /**
     * Method to execute a NOP command.
     * 
     * The method get the switch time of the first device from the
     * ItemController instance and sets the TimeController instance to that time.
     * The ItemController instance is also instructed to switch devices.
     */
    private void nopCommand() {
        try {
            Date time = itemController.devices.get(0).getSwitchtime();
            if (time == null) {
                IO.outputStrings.add(SmartHomeConstants.ERROR_NOP);
                return;
            }
            timeController.setTime(time);
            itemController.switchItems();
        } catch (IndexOutOfBoundsException exception) {
            IO.outputStrings.add(SmartHomeConstants.ERROR_NOP);
        }
    }
}
