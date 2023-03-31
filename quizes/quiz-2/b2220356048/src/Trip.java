public class Trip {
    String name;
    String time;
    int duration;

    Trip(String[] inputText) {
        this.name = inputText[0];
        this.time = inputText[1];
        this.duration = Integer.parseInt(inputText[2]);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
