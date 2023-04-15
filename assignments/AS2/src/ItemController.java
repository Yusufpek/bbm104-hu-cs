import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ItemController {
    List<SmartDevice> devices;

    ItemController() {
        this.devices = new ArrayList<SmartDevice>();
    }

    void setSwitchTime(String name, String date) {
        SmartDevice device = getItemByName(name);
        if (device == null) {
            IO.outputStrings.add(SmartHomeConstants.NO_DEVICE_ERROR);
            return;
        }
        device.setSwitchtime(date);

        sortDevices();
    }

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

    String changeStatus(String currentStatus) {
        if (currentStatus.equals("On"))
            return "Off";
        return "On";
    }

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
    }

    void setDeviceStatus(SmartDevice device, String status) {
        if (device instanceof Plug)
            ((Plug) device).setStatus(TimeController.now, status);
        else
            device.setStatus(status);
    }

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
                    ((ColorLamp) device).setColorCode("0"); // change the mode by assigning zero the color code value
        } else
            deviceTypeErrorMessage(SmartDevice.DeviceType.LAMP);
    }

    void setKelvin(String name, String kelvin) {
        SmartDevice device = getItemByName(name);
        if (device == null) {
            IO.outputStrings.add(SmartHomeConstants.NO_DEVICE_ERROR);
            return;
        }
        if (device instanceof Lamp) {
            if (((Lamp) device).setKelvin(kelvin) && device instanceof ColorLamp)
                ((ColorLamp) device).setColorCode("0"); // change the mode by assigning zero the color code value
        } else
            deviceTypeErrorMessage(SmartDevice.DeviceType.LAMP);
    }

    void setColor(String name, String color, String brightness) {
        SmartDevice device = getItemByName(name);
        if (device == null) {
            IO.outputStrings.add(SmartHomeConstants.NO_DEVICE_ERROR);
            return;
        }
        if (device instanceof ColorLamp) {
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
            IO.outputStrings.add(SmartHomeConstants.NO_DEVICE_ERROR);
            return;
        }
        if (device instanceof ColorLamp)
            ((ColorLamp) device).setColorCode(color);
        else {
            deviceTypeErrorMessage(SmartDevice.DeviceType.COLOR_LAMP);
        }
    }

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

    void deviceTypeErrorMessage(SmartDevice.DeviceType type) {
        IO.outputStrings.add(
                String.format("ERROR: This device is not a smart %s!",
                        type.toString().toLowerCase().replace('_', ' ')));
    }

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

    void sortDevices() {
        Collections.sort(
                devices,
                Comparator.comparing(SmartDevice::getSwitchtime, Comparator.nullsLast(Comparator.naturalOrder())));
    }

    // arr = command, device type, name, status, kelvin, brightness
    void addLamp(String[] arr) {
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

    void addColorLamp(String[] arr) {
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

    // arr = command, device type, name, status, ampere
    void addPlug(String[] arr) {
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

    void addCamera(String[] arr) {
        String name = arr[2];
        Camera camera = new Camera(TimeController.now, name);
        if (arr.length < 4) {
            IO.outputStrings.add(SmartHomeConstants.ERROR_COMMAND);
            return;
        }
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

    boolean checkStatus(String status) {
        if (!status.equals("On") && !status.equals("Off")) {
            IO.outputStrings.add(SmartHomeConstants.ERROR_COMMAND);
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

    SmartDevice getItemByName(String name) {
        for (SmartDevice device : devices) {
            if (device.getName().equals(name)) {
                return device;
            }
        }
        return null;
    }
}
