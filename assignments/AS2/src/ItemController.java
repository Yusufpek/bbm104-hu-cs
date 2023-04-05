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

    void switchItem(Date time) {
        for (SmartDevice device : devices) {
            if (device.getSwitchtime().before(time)) {
                System.out
                        .println("device status is on: " + device.getName() + " devicetype: " + device.getDeviceType());
                sortDevices();
            }
        }
    }

    void removeItem(String itemName) {
        sortDevices();
    }

    void addItem(String[] arr) {
        String type = arr[1];
        String name = arr[2];
        switch (type) {
            case LAMP:
                addLamp(arr);
                break;
            case COLOR_LAMP:
                if (!checkName(name))
                    break;
                ColorLamp clamp = new ColorLamp(name);
                if (arr.length > 3) {
                    String status = arr[3];
                    if (!checkStatus(status))
                        break;
                    clamp.setInitialStatus(status);
                    if (arr.length > 4) {
                        String kelvin = arr[4];
                        if (!checkKelvin(kelvin))
                            break;
                        clamp.setKelvin(Integer.parseInt(kelvin));
                        String brightness = arr[5];
                        if (!checkBrightness(brightness))
                            break;
                        clamp = new ColorLamp(name, status, kelvin, brightness);
                    } else {
                        clamp = new ColorLamp(name, status);
                    }
                } else {
                    clamp = new ColorLamp(name);
                }
                devices.add(clamp);
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
        Collections.sort(devices, Comparator.nullsLast(Comparator.comparing(SmartDevice::getSwitchtime)));
    }

    // arr = command, device type, name, status, kelvin, brightness
    void addLamp(String[] arr) {
        if (!checkName(arr[2]))
            return;
        Lamp lamp = new Lamp(arr[2]);
        if (arr.length > 3) {
            String status = arr[3];
            if (!checkStatus(status))
                return;
            lamp.setInitialStatus(status);
            if (arr.length > 4) {
                String kelvin = arr[4];
                if (!checkKelvin(kelvin))
                    return;
                lamp.setKelvin(Integer.parseInt(kelvin));
                String brightness = arr[5];
                if (!checkBrightness(brightness))
                    return;
                lamp.setBrightness(Integer.parseInt(brightness));
            }
        }
        devices.add(lamp);
    }

    // arr = command, device type, name, status, ampere
    void addPlug(String[] arr) {
        Plug plug = new Plug(arr[2]);
        if (!checkName(arr[2]))
            return;
        if (arr.length >= 4) {
            String status = arr[3];
            if (!checkStatus(status))
                return;
            plug.setInitialStatus(status);
            if (arr.length == 5) {
                int ampere = Integer.parseInt(arr[4]);
                if (!checkAmpere(ampere))
                    return;
                plug.setAmpere(ampere);
            }
        }
        devices.add(plug);
    }

    void addCamera(String[] arr) {
        String name = arr[2];
        Camera camera;
        if (arr.length < 4) {
            System.out.println(CommandController.ERROR_COMMAND);
            return;
        }
        if (!checkName(name))
            return;
        int megabyte = Integer.parseInt(arr[3]);
        if (!checkMB(megabyte))
            return;
        if (arr.length == 5) {
            String status = arr[4];
            if (!checkStatus(status))
                return;
            camera = new Camera(name, megabyte, status);
        } else {
            camera = new Camera(name, megabyte);
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

    boolean checkKelvin(String kelvinStr) {
        int kelvin = Integer.parseInt(kelvinStr);
        if (kelvin > 6500 || kelvin < 2000) {
            System.out.println("ERROR: Kelvin value must be in range of 2000K-6500K!");
            return false;
        }
        return true;
    }

    boolean checkBrightness(String brightnessStr) {
        try {
            int brightness = Integer.parseInt(brightnessStr);
            if (brightness > 100 || brightness < 0) {
                System.out.println("ERROR: Brightness must be in range of 0%-100%!");
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    boolean checkMB(int megabyte) {
        if (megabyte < 0) {
            System.out.println("ERROR: Megabyte value has to be a positive number!");
            return false;
        }
        return true;
    }

    boolean checkAmpere(int ampere) {
        if (ampere < 0) {
            System.out.println("ERROR: Ampere value must be a positive number!");
            return false;
        }
        return true;
    }

    boolean checkName(String name) {
        for (SmartDevice device : devices) {
            if (device.getName().equals(name)) {
                System.out.println("ERROR: There is already a smart device with same name!");
                return false;
            }
        }
        return true;
    }

    private final String SMART = "Smart";
    private final String LAMP = SMART + "Lamp";
    private final String COLOR_LAMP = SMART + "Color" + LAMP;
    private final String PLUG = SMART + "Plug";
    private final String CAMERA = SMART + "Camera";
}
