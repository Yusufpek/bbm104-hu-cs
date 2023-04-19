import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The ItemController class represents a controller for
 * managing Smart Devices in a smart home system.
 */
public class ItemController {
    List<SmartDevice> devices; // list of SmartDevice objects.

    /**
     * Constructor that initializes an empty ArrayList of SmartDevice objects.
     */
    ItemController() {
        this.devices = new ArrayList<SmartDevice>();
    }

    /**
     * Adds a new smart device to the system based on the device type
     * which is given with the input array.
     * 
     * @param arr An array of strings containing the command, device type, name,
     *            and additional parameters like status, kelvin, brightness, ampere
     *            (optional)
     */
    void addItem(String[] arr) {
        String type = arr[1];
        switch (type) {
            case SmartHomeConstants.LAMP:
                addLamp(arr);
                break;
            case SmartHomeConstants.COLOR_LAMP:
                addColorLamp(arr);
                break;
            case SmartHomeConstants.PLUG:
                addPlug(arr);
                break;
            case SmartHomeConstants.CAMERA:
                addCamera(arr);
                break;
            default:
                break;
        }
        sortDevices();
    }

    /**
     * Adds a new lamp device to the system based on the input parameters
     * 
     * @param arr An array of strings containing the command, device type, name,
     *            status (optional), kelvin (optional), and brightness (optional)
     */
    void addLamp(String[] arr) {
        if (!(arr.length == 3 || arr.length == 6 || arr.length == 4)) { // check lenght of the command
            IO.outputStrings.add(SmartHomeConstants.ERROR_COMMAND);
            return;
        }
        if (checkName(arr[2])) {
            IO.outputStrings.add(SmartHomeConstants.NAME_ERROR);
            return;
        }
        Lamp lamp = new Lamp(TimeController.now, arr[2]);
        if (arr.length > 3) {
            String status = arr[3];
            if (!checkStatus(status))
                return;
            lamp.setStatus(status);
            if (arr.length > 4) {
                if (!lamp.setKelvin(arr[4]))
                    return;
                if (!lamp.setBrightness(arr[5]))
                    return;
            }
        }
        devices.add(lamp);
    }

    /**
     * Adds a new color lamp device to the system based on the input parameters
     * 
     * @param arr An array of strings containing the command, device type, name,
     *            status (optional), color code or kelvin value (optional), and
     *            brightness (optional)
     */
    void addColorLamp(String[] arr) {
        if (!(arr.length == 3 || arr.length == 6 || arr.length == 4)) { // check lenght of the command
            IO.outputStrings.add(SmartHomeConstants.ERROR_COMMAND);
            return;
        }
        String name = arr[2];
        ColorLamp clamp = new ColorLamp(TimeController.now, name);
        if (checkName(arr[2])) {
            IO.outputStrings.add(SmartHomeConstants.NAME_ERROR);
            return;
        }
        if (arr.length > 3) {
            String status = arr[3];
            if (!checkStatus(status))
                return;
            clamp.setStatus(status);
            if (arr.length > 4) {
                if (arr[4].startsWith("0x")) {
                    if (!clamp.setColorCode(arr[4]))
                        return;
                } else {
                    if (!clamp.setKelvin(arr[4]))
                        return;
                }
                if (!clamp.setBrightness(arr[5]))
                    return;
            }
        }
        devices.add(clamp);
    }

    /**
     * Adds a new plug device to the system based on the input parameters
     * 
     * @param arr An array of strings containing the command, device type, name,
     *            status (optional) and ampere (optional)
     */
    void addPlug(String[] arr) {
        if (arr.length < 3 || arr.length > 5) { // check lenght of the command
            IO.outputStrings.add(SmartHomeConstants.ERROR_COMMAND);
            return;
        }
        Plug plug = new Plug(TimeController.now, arr[2]);
        if (checkName(arr[2])) {
            IO.outputStrings.add(SmartHomeConstants.NAME_ERROR);
            return;
        }
        if (arr.length >= 4) {
            String status = arr[3];
            if (!checkStatus(status))
                return;
            plug.setStatus(status);
            if (arr.length == 5) {
                if (!plug.setAmpere(arr[4]))
                    return;
            }
        }
        devices.add(plug);
    }

    /**
     * Adds a new camera device to the system based on the input parameters
     * 
     * @param arr An array of strings containing the command, device type, name,
     *            megabytes consumed per record value and status (optional)
     */
    void addCamera(String[] arr) {
        if (arr.length < 4 || arr.length > 5) { // check lenght of the command
            IO.outputStrings.add(SmartHomeConstants.ERROR_COMMAND);
            return;
        }
        String name = arr[2];
        Camera camera = new Camera(TimeController.now, name);
        if (checkName(name)) {
            IO.outputStrings.add(SmartHomeConstants.NAME_ERROR);
            return;
        }
        if (!camera.setMBPerMinute(arr[3]))
            return;
        if (arr.length == 5) {
            String status = arr[4];
            if (!checkStatus(status))
                return;
            camera.setStatus(status);
        }
        devices.add(camera);
    }

    /**
     * Sorts the existing list of smart devices based on their switch time,
     * use the Comparator.nullsLast for the null values
     */
    void sortDevices() {
        Collections.sort(
                devices,
                Comparator.comparing(SmartDevice::getSwitchtime, Comparator.nullsLast(Comparator.naturalOrder())));
    }

    /**
     * Sets the switch time of a SmartDevice by name.
     * Write the error message if it is necessary.
     * Check the time if now is equal to switch time switch the device
     * and set null the switch time
     * 
     * @param name The name of the SmartDevice to set the switch time for.
     * @param date The date to set the switch time to.
     */
    void setSwitchTime(String name, String date) {
        SmartDevice device = getItemByName(name);
        if (device == null) {
            IO.outputStrings.add(SmartHomeConstants.NO_DEVICE_ERROR);
            return;
        }
        device.setSwitchtime(date);
        sortDevices();
        // check for the equation of switch time and now
        if (device.getSwitchtime() != null && device.getSwitchtime().equals(TimeController.now)) {
            setDeviceStatus(device, changeStatus(device.getStatus()));
            device.setSwitchtime(""); // set null the switch time
            sortDevices(); // sort again
        }
    }

    /**
     * Changes the name of a SmartDevice by old name.
     * Write the error message if it is necessary:
     * - If the names are same,
     * - If there is no device with the given name,
     * - If the new name is used for another device.
     * 
     * @param name    The name of the SmartDevice to change the new name for.
     * @param newName The new name to change the name of device.
     */
    void changeName(String name, String newName) {
        if (name.equals(newName)) {
            IO.outputStrings.add(SmartHomeConstants.SAME_NAME_ERROR);
            return;
        }

        SmartDevice device = getItemByName(name);
        if (device == null) {
            IO.outputStrings.add(SmartHomeConstants.NO_DEVICE_ERROR);
            return;
        }
        if (getItemByName(newName) != null) {
            IO.outputStrings.add(SmartHomeConstants.NAME_ERROR);
            return;
        }
        device.setName(newName);
    }

    /**
     * Switches the status of the devices based on their switch time.
     * 
     * It checks the switch time of each device and switches their status if the
     * switch time has passed the now which cames from the TimeController.
     * 
     * If multiple devices have the same switch time, it sorts them together
     * and switches their status accordingly.
     * 
     * If a device is a plug, it updates its status and watt usage
     * otherwise it updates only the status.
     * 
     * After the switch is made, the device's switch time is set to null and the
     * devices are sorted based on their switch time with the sortDevices() method.
     */
    void switchItems() {
        int index = 0;
        String lastSwitchTime = null;
        while (index < devices.size() && devices.get(index).getSwitchtime() != null) {
            final SmartDevice device = devices.get(index);
            if (!device.getSwitchtime().after(TimeController.now)) {
                if (lastSwitchTime == null) {
                    lastSwitchTime = device.getSwitchtimeString();
                    index++; // look for new device it status is changed
                } else if (lastSwitchTime.equals(device.getSwitchtimeString()))
                    index++; // look for new device these two devices satatus is same
                else {
                    sortDevices(); // sort the previous devices which times' are same
                    lastSwitchTime = device.getSwitchtimeString();
                }
                if (device instanceof Plug)
                    ((Plug) device).setStatus(TimeController.now, changeStatus(device.getStatus()));
                else
                    device.setStatus(changeStatus(device.getStatus()));
                device.setSwitchtime(""); // set null
            } else {
                break;
            }
        }
        sortDevices();
    }

    /**
     * Changes the current status of the device to the opposite status.
     * 
     * @param currentStatus the current status of the device
     * @return the new status of the device
     */
    String changeStatus(String currentStatus) {
        if (currentStatus.equals("On"))
            return "Off";
        return "On";
    }

    /**
     * Sets the status of a SmartDevice object with the specified name to the
     * specified status.
     * 
     * If the SmartDevice object does not exist, writes an error message
     * 
     * If the status is not valid, does nothing.
     * 
     * If the status of device and the given status is equal, writes an error
     * message
     * Otherwise, sets the status of the device to the specified status.
     * 
     * @param name   the name of the SmartDevice object to set the status of
     * @param status the new status to set the device to
     */
    void setStatus(String name, String status) {
        SmartDevice device = getItemByName(name);
        if (device == null) {
            IO.outputStrings.add(SmartHomeConstants.NO_DEVICE_ERROR);
            return;
        }
        if (!checkStatus(status))
            return;
        if (device.getStatus().equals(status)) {
            IO.outputStrings.add(String.format("ERROR: This device is already switched %s!", status.toLowerCase()));
            return;
        }
        setDeviceStatus(device, status);
        device.setSwitchtime(""); // set the switch time null when switch command is used
        sortDevices(); // switch time is changed so sort devices again
    }

    /**
     * Sets the status of a SmartDevice object to the specified status.
     * 
     * If the SmartDevice object is a Plug, sets the status of the Plug object with
     * the specified time.
     * Otherwise, sets the status of the SmartDevice object to the specified status.
     * 
     * @param device the SmartDevice object to set the status of
     * @param status the status to set the device to
     */
    void setDeviceStatus(SmartDevice device, String status) {
        if (device instanceof Plug)
            ((Plug) device).setStatus(TimeController.now, status);
        else
            device.setStatus(status);
    }

    /**
     * Sets the kelvin value and brightness of a Lamp object with the specified
     * name.
     * 
     * Writes the error message if it is necessary:
     * - If the Lamp object does not exist,
     * - If the Lamp object is not a Lamp or ColorLamp object,
     * 
     * If the kelvin value is invalid, does nothing and keeps the previous
     * kelvin value
     * If the brightness is invalid, does nothing and keeps the previous brightness
     * 
     * @param name       the name of the Lamp device to set the new properties
     * @param kelvin     the kelvin value to set the Lamp device to
     * @param brightness the brightness to set the Lamp device to
     */
    void setWhite(String name, String kelvin, String brightness) {
        SmartDevice device = getItemByName(name);
        if (device == null) {
            IO.outputStrings.add(SmartHomeConstants.NO_DEVICE_ERROR);
            return;
        }
        if (device instanceof Lamp) {
            int oldKelvin = ((Lamp) device).getKelvin();
            // if color code is proper but brightness is not change the color code with the
            // old value
            if (((Lamp) device).setKelvin(kelvin))
                if (!((Lamp) device).setBrightness(brightness))
                    ((Lamp) device).setKelvin(oldKelvin + "");
                else if (device instanceof ColorLamp)
                    ((ColorLamp) device).setColorCodeNull(); // change the mode by assigning (-1) the color code value
        } else
            deviceTypeErrorMessage(SmartDevice.DeviceType.LAMP);
    }

    /**
     * Sets the kelvin value for a Lamp or ColorLamp smart device.
     * Writes the error message if it is necessary:
     * - If the Lamp object does not exist,
     * - If the Lamp object is not a Lamp or ColorLamp object,
     * 
     * If the object is color lamp assign the color value to 0 for the set the
     * active value as kelvin
     * 
     * @param name   the name of the smart device
     * @param kelvin the kelvin value to set
     */
    void setKelvin(String name, String kelvin) {
        SmartDevice device = getItemByName(name);
        if (device == null) {
            IO.outputStrings.add(SmartHomeConstants.NO_DEVICE_ERROR);
            return;
        }
        if (device instanceof Lamp) {
            if (((Lamp) device).setKelvin(kelvin) && device instanceof ColorLamp)
                ((ColorLamp) device).setColorCodeNull(); // change the mode by assigning -1 the color code value
        } else
            deviceTypeErrorMessage(SmartDevice.DeviceType.LAMP);
    }

    /**
     * Sets the color code and brightness for a ColorLamp smart device.
     * 
     * Writes the specific error messages if it is necessary:
     * - If the device with the given name does not exist,
     * - If the device is not a color lamp type,
     * 
     * If the color code is invalid, does nothing and keeps the previous
     * color code value
     * 
     * @param name       the name of the smart device
     * @param color      the color code to set (in hexadecimal)
     * @param brightness the brightness value to set
     */
    void setColor(String name, String color, String brightness) {
        SmartDevice device = getItemByName(name);
        if (device == null) {
            IO.outputStrings.add(SmartHomeConstants.NO_DEVICE_ERROR);
            return;
        }
        if (device instanceof ColorLamp) {
            String oldColorCode = ((ColorLamp) device).getColorCodeString();
            // if color code is valid but brightness is not, change the color code
            // with the old value
            if (((ColorLamp) device).setColorCode(color))
                if (!((ColorLamp) device).setBrightness(brightness)) {
                    ((ColorLamp) device).setColorCode(oldColorCode);
                }
        } else
            deviceTypeErrorMessage(SmartDevice.DeviceType.COLOR_LAMP);
    }

    /**
     * Sets the color code for a ColorLamp smart device.
     * 
     * Writes the specific error messages if it is necessary:
     * - If the device with the given name does not exist,
     * - If the device is not a color lamp type,
     * 
     * @param name  the name of the smart device
     * @param color the color code to set (in hexadecimal format)
     */
    void setColorCode(String name, String color) {
        SmartDevice device = getItemByName(name);
        if (device == null) {
            IO.outputStrings.add(SmartHomeConstants.NO_DEVICE_ERROR);
            return;
        }
        if (device instanceof ColorLamp)
            ((ColorLamp) device).setColorCode(color);
        else {
            deviceTypeErrorMessage(SmartDevice.DeviceType.COLOR_LAMP);
        }
    }

    /**
     * Sets the brightness value for a Lamp smart device.
     *
     * Writes the specific error messages if it is necessary:
     * - If the device with the given name does not exist,
     * - If the device is not a lamp type,
     * 
     * @param name       the name of the smart device
     * @param brightness the brightness value to set
     */
    void setBrightness(String name, String brightness) {
        SmartDevice device = getItemByName(name);
        if (device == null) {
            IO.outputStrings.add(SmartHomeConstants.NO_DEVICE_ERROR);
            return;
        }
        if (device instanceof Lamp)
            ((Lamp) device).setBrightness(brightness);
        else
            deviceTypeErrorMessage(SmartDevice.DeviceType.LAMP);
    }

    /**
     * This method plugs in a smart plug device with the given name and ampere
     * value.
     * 
     * Writes the specific error messages if it is necessary:
     * - If the device with the given name does not exist,
     * - If the device is not a plug type,
     * - If the device is already plugged in
     * 
     * If everything is okay,
     * it plugs in the device with the given ampere value and current time.
     * 
     * @param name   The name of the smart plug device to be plugged in
     * @param ampere The ampere value to plug in the device with
     */

    void plugIn(String name, String ampere) {
        SmartDevice device = getItemByName(name);
        if (device == null) {
            IO.outputStrings.add(SmartHomeConstants.NO_DEVICE_ERROR);
            return;
        }
        if (!(device instanceof Plug)) {
            deviceTypeErrorMessage(SmartDevice.DeviceType.PLUG);
            return;
        }
        if (((Plug) device).getAmpere() != 0) {
            IO.outputStrings.add(SmartHomeConstants.PLUG_IN_ERROR);
            return;
        }
        ((Plug) device).plugIn(ampere, TimeController.now);

    }

    /**
     * Unplugs a smart plug device (sets the device's ampere value to zero)
     * with the given name
     * 
     * Writes the specific error messages if it is necessary:
     * - If the device with the given name does not exist
     * - If the device is not a plug type
     * - If the device is not plugged in
     * 
     * If the device is currently on, it calculates the watt usage and adds it to
     * the device's history.
     * 
     * @param name The name of the smart plug device to be unplugged
     */
    void plugOut(String name) {
        SmartDevice device = getItemByName(name);
        if (device == null) {
            IO.outputStrings.add(SmartHomeConstants.NO_DEVICE_ERROR);
            return;
        }
        if (!(device instanceof Plug)) {
            deviceTypeErrorMessage(SmartDevice.DeviceType.PLUG);
            return;
        }
        if (((Plug) device).getAmpere() == 0.0) {
            IO.outputStrings.add(SmartHomeConstants.PLUG_OUT_ERROR);
            return;
        }
        if (device.getStatus().equals("On"))
            ((Plug) device).calculateWatt(TimeController.now);
        ((Plug) device).setAmpereZero();
    }

    /**
     * Removes a smart device with the given name from the device list.
     *
     * If the device with the given name does not exist, it adds a NO_DEVICE_ERROR
     * message to the output strings.
     * 
     * If the device is currently on, it turns it off before removing it.
     * 
     * Also it adds the device's information to the output strings and sorts the
     * device list.
     * 
     * @param name The name of the smart device to be removed
     */
    void removeItem(String name) {
        SmartDevice device = getItemByName(name);
        if (device == null) {
            IO.outputStrings.add(SmartHomeConstants.NO_DEVICE_ERROR);
            return;
        }
        if (device.getStatus().equals("On"))
            setDeviceStatus(device, "Off");
        devices.remove(device);
        IO.outputStrings.add("SUCCESS: Information about removed smart device is as follows:");
        IO.outputStrings.add(device.toString());
        sortDevices();
    }

    /**
     * Adds a device type error message to the output strings with the
     * given device type.
     * 
     * @param type The device type causing the error
     */
    void deviceTypeErrorMessage(SmartDevice.DeviceType type) {
        IO.outputStrings.add(
                String.format("ERROR: This device is not a smart %s!",
                        type.toString().toLowerCase().replace('_', ' ')));
    }

    /**
     * Checks if the given status is a valid one (On or Off).
     * 
     * @param status the status to check
     * @return true if the status is valid, false otherwise
     */
    boolean checkStatus(String status) {
        if (!status.equals("On") && !status.equals("Off")) {
            IO.outputStrings.add(SmartHomeConstants.ERROR_COMMAND);
            return false;
        }
        return true;
    }

    /**
     * Checks if the given name already exists in the list of smart devices
     * 
     * @param name the name to check
     * @return true if the name already exists, false otherwise
     */
    boolean checkName(String name) {
        SmartDevice device = getItemByName(name);
        if (device == null)
            return false;
        return true;
    }

    /**
     * Returns the Smart Device with the given name from the
     * list of smart devices.
     * 
     * @param name the name of the SmartDevice object to return
     * @return the SmartDevice object with the given name, or null if not found
     */
    SmartDevice getItemByName(String name) {
        for (SmartDevice device : devices) {
            if (device.getName().equals(name)) {
                return device;
            }
        }
        return null;
    }
}
