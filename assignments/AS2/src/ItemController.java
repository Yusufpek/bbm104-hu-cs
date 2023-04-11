import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ItemController {
    List<SmartDevice> devices;

    ItemController() {
        this.devices = new ArrayList<SmartDevice>();
    }

    void setSwitchTime(String name, String date) {
        SmartDevice device = getItemByName(name);
        if (device == null) {
            IO.outputStrings.add(NO_DEVICE_ERROR);
            return;
        }
        device.setSwitchtime(date);

        sortDevices();
    }

    void changeName(String name, String newName) {
        if (name.equals(newName)) {
            IO.outputStrings.add(SAME_NAME_ERROR);
            return;
        }

        SmartDevice device = getItemByName(name);
        if (device == null) {
            IO.outputStrings.add(NO_DEVICE_ERROR);
            return;
        }
        if (getItemByName(newName) != null) {
            IO.outputStrings.add(NAME_ERROR);
            return;
        }
        device.setName(newName);
    }

    void switchItems(Date time) {
        int index = 0;
        while (index < devices.size() && devices.get(index).getSwitchtime() != null) {
            final SmartDevice device = devices.get(index);
            if (device.getSwitchtime() != null && !device.getSwitchtime().after(time)) {
                if (deviceTypeCheck(device, SmartDevice.DeviceType.PLUG))
                    ((Plug) device).setStatus(time, changeStatus(device.getStatus()));
                else
                    device.setStatus(changeStatus(device.getStatus()));
                device.setSwitchtime(""); // set null
            } else {
                index++;
            }
            sortDevices();
        }
    }

    String changeStatus(String currentStatus) {
        if (currentStatus.equals("On"))
            return "Off";
        return "On";
    }

    void setStatus(String name, String status, Date time) {
        SmartDevice device = getItemByName(name);
        if (device == null) {
            IO.outputStrings.add(NO_DEVICE_ERROR);
            return;
        }
        if (!checkStatus(status))
            return;
        if (device.getStatus().equals(status)) {
            IO.outputStrings.add(String.format("ERROR: This device is already switched %s!", status.toLowerCase()));
            return;
        }
        if (device instanceof Plug) {// (deviceTypeCheck(device, SmartDevice.DeviceType.PLUG)) {
            ((Plug) device).setStatus(time, changeStatus(device.getStatus()));
        } else
            device.setStatus(status);
    }

    void setWhite(String name, String kelvin, String brightness) {
        SmartDevice device = getItemByName(name);
        if (device == null) {
            IO.outputStrings.add(NO_DEVICE_ERROR);
            return;
        }
        if (deviceTypeCheck(device, SmartDevice.DeviceType.LAMP)
                || deviceTypeCheck(device, SmartDevice.DeviceType.COLOR_LAMP)) {
            int oldKelvin = ((Lamp) device).getKelvin();
            // if color code is proper but brightness is not change the color code with the
            // old value
            if (((Lamp) device).setKelvin(kelvin))
                if (!((Lamp) device).setBrightness(brightness))
                    ((Lamp) device).setKelvin(oldKelvin + "");
        } else
            deviceTypeErrorMessage(SmartDevice.DeviceType.LAMP);
    }

    void setKelvin(String name, String kelvin) {
        SmartDevice device = getItemByName(name);
        if (device == null) {
            IO.outputStrings.add(NO_DEVICE_ERROR);
            return;
        }
        if (deviceTypeCheck(device, SmartDevice.DeviceType.LAMP)
                || deviceTypeCheck(device, SmartDevice.DeviceType.COLOR_LAMP))
            ((Lamp) device).setKelvin(kelvin);
        else
            deviceTypeErrorMessage(SmartDevice.DeviceType.LAMP);
    }

    void setColor(String name, String color, String brightness) {
        SmartDevice device = getItemByName(name);
        if (device == null) {
            IO.outputStrings.add(NO_DEVICE_ERROR);
            return;
        }
        if (deviceTypeCheck(device, SmartDevice.DeviceType.COLOR_LAMP)) {
            String oldColorCode = ((ColorLamp) device).getColorCodeString();
            // if color code is proper but brightness is not change the color code with the
            // old value
            if (((ColorLamp) device).setColorCode(color))
                if (!((ColorLamp) device).setBrightness(brightness)) {
                    ((ColorLamp) device).setColorCode(oldColorCode);
                }
        } else
            deviceTypeErrorMessage(SmartDevice.DeviceType.COLOR_LAMP);
    }

    void setColorCode(String name, String color) {
        SmartDevice device = getItemByName(name);
        if (device == null) {
            IO.outputStrings.add(NO_DEVICE_ERROR);
            return;
        }
        if (deviceTypeCheck(device, SmartDevice.DeviceType.COLOR_LAMP))
            ((ColorLamp) device).setColorCode(color);
        else {
            deviceTypeErrorMessage(SmartDevice.DeviceType.COLOR_LAMP);
        }
    }

    void setBrightness(String name, String brightness) {
        SmartDevice device = getItemByName(name);
        if (device == null) {
            IO.outputStrings.add(NO_DEVICE_ERROR);
            return;
        }
        if (deviceTypeCheck(device, SmartDevice.DeviceType.LAMP))
            ((Lamp) device).setBrightness(brightness);
        else
            deviceTypeErrorMessage(SmartDevice.DeviceType.LAMP);
    }

    void plugIn(Date now, String name, String ampere) {
        SmartDevice device = getItemByName(name);
        if (device == null) {
            IO.outputStrings.add(NO_DEVICE_ERROR);
            return;
        }
        if (!deviceTypeCheck(device, SmartDevice.DeviceType.PLUG)) {
            deviceTypeErrorMessage(SmartDevice.DeviceType.PLUG);
            return;
        }
        if (((Plug) device).getAmpere() != 0) {
            IO.outputStrings.add(PLUG_IN_ERROR);
            return;
        }
        ((Plug) device).plugIn(ampere, now);

    }

    void plugOut(Date now, String name) {
        SmartDevice device = getItemByName(name);
        if (device == null) {
            IO.outputStrings.add(NO_DEVICE_ERROR);
            return;
        }
        if (!deviceTypeCheck(device, SmartDevice.DeviceType.PLUG)) {
            deviceTypeErrorMessage(SmartDevice.DeviceType.PLUG);
            return;
        }
        if (((Plug) device).getAmpere() == 0.0) {
            IO.outputStrings.add(PLUG_OUT_ERROR);
            return;
        }
        ((Plug) device).calculateWatt(now);
        ((Plug) device).setAmpereZero();
    }

    void removeItem(String name) {
        SmartDevice device = getItemByName(name);
        if (device == null) {
            IO.outputStrings.add(NO_DEVICE_ERROR);
            return;
        }
        devices.remove(device);
        IO.outputStrings.add("SUCCESS: Information about removed smart device is as follows:");
        IO.outputStrings.add(device.toString());
        sortDevices();
    }

    void deviceTypeErrorMessage(SmartDevice.DeviceType type) {
        IO.outputStrings.add(
                String.format("ERROR: This device is not a smart %s!",
                        type.toString().toLowerCase().replace('_', ' ')));
    }

    void addItem(Date now, String[] arr) {
        String type = arr[1];
        switch (type) {
            case LAMP:
                addLamp(now, arr);
                break;
            case COLOR_LAMP:
                addColorLamp(now, arr);
                break;
            case PLUG:
                addPlug(now, arr);
                break;
            case CAMERA:
                addCamera(now, arr);
                break;
            default:
                break;
        }
        sortDevices();
    }

    void sortDevices() {
        Collections.sort(
                devices,
                Comparator.comparing(SmartDevice::getSwitchtime, Comparator.nullsLast(Comparator.naturalOrder())));
    }

    // arr = command, device type, name, status, kelvin, brightness
    void addLamp(Date now, String[] arr) {
        if (checkName(arr[2])) {
            IO.outputStrings.add(NAME_ERROR);
            return;
        }
        Lamp lamp = new Lamp(now, arr[2]);
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

    void addColorLamp(Date now, String[] arr) {
        String name = arr[2];
        ColorLamp clamp = new ColorLamp(now, name);
        if (checkName(arr[2])) {
            IO.outputStrings.add(NAME_ERROR);
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

    // arr = command, device type, name, status, ampere
    void addPlug(Date now, String[] arr) {
        Plug plug = new Plug(now, arr[2]);
        if (checkName(arr[2])) {
            IO.outputStrings.add(NAME_ERROR);
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

    void addCamera(Date now, String[] arr) {
        String name = arr[2];
        Camera camera = new Camera(now, name);
        if (arr.length < 4) {
            IO.outputStrings.add(CommandController.ERROR_COMMAND);
            return;
        }
        if (checkName(name)) {
            IO.outputStrings.add(NAME_ERROR);
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

    boolean checkStatus(String status) {
        if (!status.equals("On") && !status.equals("Off")) {
            IO.outputStrings.add(CommandController.ERROR_COMMAND);
            return false;
        }
        return true;
    }

    boolean checkName(String name) {
        SmartDevice device = getItemByName(name);
        if (device == null)
            return false;
        return true;
    }

    boolean deviceTypeCheck(SmartDevice device, SmartDevice.DeviceType type) {
        if (device.getDeviceType() == type)
            return true;
        return false;
    }

    SmartDevice getItemByName(String name) {
        for (SmartDevice device : devices) {
            if (device.getName().equals(name)) {
                return device;
            }
        }
        return null;
    }

    private final String SMART = "Smart";
    private final String LAMP = SMART + "Lamp";
    private final String COLOR_LAMP = SMART + "ColorLamp";
    private final String PLUG = SMART + "Plug";
    private final String CAMERA = SMART + "Camera";

    //
    private final String NAME_ERROR = "ERROR: There is already a smart device with same name!";
    private final String NO_DEVICE_ERROR = "ERROR: There is not such a device!";
    private final String SAME_NAME_ERROR = "ERROR: Both of the names are the same, nothing changed!";
    private final String PLUG_OUT_ERROR = "ERROR: This plug has no item to plug out from that plug!";
    private final String PLUG_IN_ERROR = "ERROR: There is already an item plugged in to that plug!";
}
