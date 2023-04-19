/**
 * This class contains static constants for error messages
 * and command strings used in the Smart Home System program.
 */
public class SmartHomeConstants {
    // Devices
    public static final String SMART = "Smart";
    public static final String LAMP = SMART + "Lamp";
    public static final String COLOR_LAMP = SMART + "ColorLamp";
    public static final String PLUG = SMART + "Plug";
    public static final String CAMERA = SMART + "Camera";

    // Error messages
    static public final String ERROR_COMMAND = "ERROR: Erroneous command!";
    static public final String ERROR_NOP = "ERROR: There is nothing to switch!";
    static public final String ERROR_SET_TIME = "ERROR: There is nothing to change!";
    static public final String ERROR_SET_INITIAL_TIME = "ERROR: First command must be set initial time! Program is going to terminate!";
    public static final String NAME_ERROR = "ERROR: There is already a smart device with same name!";
    public static final String NO_DEVICE_ERROR = "ERROR: There is not such a device!";
    public static final String SAME_NAME_ERROR = "ERROR: Both of the names are the same, nothing changed!";
    public static final String PLUG_OUT_ERROR = "ERROR: This plug has no item to plug out from that plug!";
    public static final String PLUG_IN_ERROR = "ERROR: There is already an item plugged in to that plug!";
    public static final String SWITCH_TIME_PAST_ERROR = "ERROR: Switch time cannot be in the past!";

    // Command strings
    public static final String COMMAND_INITIAL_TIME = "SetInitialTime";
    public static final String COMMAND_SET_TIME = "SetTime";
    public static final String COMMAND_SKIP = "SkipMinutes";
    public static final String COMMAND_ADD = "Add";
    public static final String COMMAND_REMOVE = "Remove";
    public static final String COMMAND_NOP = "Nop";
    public static final String COMMAND_REPORT = "ZReport";
    public static final String COMMAND_SWITCH = "Switch";
    public static final String COMMAND_PLUG_IN = "PlugIn";
    public static final String COMMAND_PLUG_OUT = "PlugOut";
    public static final String COMMAND_BRIGHTNESS = "SetBrightness";
    public static final String COMMAND_WHITE = "SetWhite";
    public static final String COMMAND_KELVIN = "SetKelvin";
    public static final String COMMAND_COLOR = "SetColor";
    public static final String COMMAND_COLOR_CODE = "SetColorCode";
    public static final String COMMAND_SWITCH_TIME = "SetSwitchTime";
    public static final String COMMAND_NAME = "ChangeName";

}
