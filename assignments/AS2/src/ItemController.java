import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

//TODO: Güç hesaplamaları yapılacak !!!

public class ItemController {
    List<SmartDevice> devices;

    ItemController() {
        this.devices = new ArrayList<SmartDevice>();
    }

    void setSwitchTime(String name, String date) {
        SmartDevice device = getItemByName(name);
        if (device == null) {
            System.out.println(NO_DEVICE_ERROR);
            return;
        }
        device.setSwitchtime(date);

        sortDevices();
    }

    void changeName(String name, String newName) {
        if (name.equals(newName)) {
            System.out.println(SAME_NAME_ERROR);
        }

        SmartDevice device = getItemByName(name);
        if (device == null) {
            System.out.println(NO_DEVICE_ERROR);
            return;
        }
        if (getItemByName(newName) != null) {
            System.out.println(NAME_ERROR);
            return;
        }
        device.setName(newName);
    }

    void switchItem(Date time) {
        int index = 0;
        while (index < devices.size() && devices.get(index).getSwitchtime() != null) {
            final SmartDevice device = devices.get(index);
            if (device.getSwitchtime() != null && !device.getSwitchtime().after(time)) {
                device.setSwitchtime(""); // set null
            } else {
                index++;
            }
            sortDevices();
        }
    }

    void setStatus(String name, String status) {
        SmartDevice device = getItemByName(name);
        if (device == null) {
            System.out.println(NO_DEVICE_ERROR);
            return;
        }
        if (!checkStatus(status))
            return;
        if (device.getStatus().equals(status)) {
            System.out.println(String.format("ERROR: This device is already switched %s!", status.toLowerCase()));
            return;
        }
        device.setStatus(status);
    }

    void setWhite(String name, String kelvin, String brightness) {
        SmartDevice device = getItemByName(name);
        if (device == null) {
            System.out.println(NO_DEVICE_ERROR);
            return;
        }
        if (deviceTypeCheck(device, SmartDevice.DeviceType.LAMP)
                || deviceTypeCheck(device, SmartDevice.DeviceType.COLOR_LAMP)) {
            ((Lamp) device).setKelvin(kelvin);
            ((Lamp) device).setBrightness(brightness);
        } else
            deviceTypeErrorMessage(SmartDevice.DeviceType.LAMP);
    }

    void setKelvin(String name, String kelvin) {
        SmartDevice device = getItemByName(name);
        if (device == null) {
            System.out.println(NO_DEVICE_ERROR);
            return;
        }
        if (deviceTypeCheck(device, SmartDevice.DeviceType.LAMP)
                || deviceTypeCheck(device, SmartDevice.DeviceType.COLOR_LAMP))
            ((Lamp) device).setKelvin(kelvin);
        else
            deviceTypeErrorMessage(SmartDevice.DeviceType.LAMP);
    }

    void setColor(String name, String color) {
        SmartDevice device = getItemByName(name);
        if (device == null) {
            System.out.println(NO_DEVICE_ERROR);
            return;
        }
        if (deviceTypeCheck(device, SmartDevice.DeviceType.COLOR_LAMP))
            ((ColorLamp) device).setColorCode(color);
        else
            deviceTypeErrorMessage(SmartDevice.DeviceType.COLOR_LAMP);
    }

    void setBrightness(String name, String brightness) {
        SmartDevice device = getItemByName(name);
        if (device == null) {
            System.out.println(NO_DEVICE_ERROR);
            return;
        }
        if (deviceTypeCheck(device, SmartDevice.DeviceType.LAMP))
            ((Lamp) device).setBrightness(brightness);
        else
            deviceTypeErrorMessage(SmartDevice.DeviceType.LAMP);
    }

    void plugIn(String name, String ampere) {
        SmartDevice device = getItemByName(name);
        if (device == null) {
            System.out.println(NO_DEVICE_ERROR);
            return;
        }
        if (!deviceTypeCheck(device, SmartDevice.DeviceType.PLUG)) {
            deviceTypeErrorMessage(SmartDevice.DeviceType.PLUG);
            return;
        }
        ((Plug) device).setAmpere(ampere);
    }

    void plugOut(String name) {
        SmartDevice device = getItemByName(name);
        if (device == null) {
            System.out.println(NO_DEVICE_ERROR);
            return;
        }
        if (!deviceTypeCheck(device, SmartDevice.DeviceType.PLUG)) {
            deviceTypeErrorMessage(SmartDevice.DeviceType.PLUG);
            return;
        }
        if (((Plug) device).getAmpere() == 0.0) {
            System.out.println(PLUG_OUT_ERROR);
            return;
        }
        ((Plug) device).setAmpere("0");
    }

    void removeItem(String name) {
        SmartDevice device = getItemByName(name);
        if (device == null) {
            System.out.println(NO_DEVICE_ERROR);
            return;
        }
        devices.remove(device);
        System.out.println("SUCCESS: Information about removed smart device is as follows:");
        System.out.println(device.toString());
        sortDevices();
    }

    void deviceTypeErrorMessage(SmartDevice.DeviceType type) {
        System.out.println(
                String.format("ERROR: This device is not a %s!", type.toString().toLowerCase()));
    }

    void addItem(String[] arr) {
        String type = arr[1];
        switch (type) {
            case LAMP:
                addLamp(arr);
                break;
            case COLOR_LAMP:
                addColorLamp(arr);
                break;
            case PLUG:
                addPlug(arr);
                break;
            case CAMERA:
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
            System.out.println(NAME_ERROR);
            return;
        }
        Lamp lamp = new Lamp(arr[2]);
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
        ColorLamp clamp = new ColorLamp(name);
        if (checkName(arr[2])) {
            System.out.println(NAME_ERROR);
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
        System.out.println("color lamp added " + clamp.getName());
    }

    // arr = command, device type, name, status, ampere
    void addPlug(String[] arr) {
        Plug plug = new Plug(arr[2]);
        if (checkName(arr[2])) {
            System.out.println(NAME_ERROR);
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
        Camera camera = new Camera(name);
        if (arr.length < 4) {
            System.out.println(CommandController.ERROR_COMMAND);
            return;
        }
        if (checkName(name)) {
            System.out.println(NAME_ERROR);
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
            System.out.println(CommandController.ERROR_COMMAND);
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
}
