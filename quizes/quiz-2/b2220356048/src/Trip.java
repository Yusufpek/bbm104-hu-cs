import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Trip {
    public String tripName;
    public Date departureTime;
    public Date arrivalTime;
    public int duration;
    public String state;

    Trip(String[] inputText) {
        this.tripName = inputText[0];
        this.duration = Integer.parseInt(inputText[2]);
        this.state = TripController.STATE_IDLE;
        try {
            this.departureTime = dateFormat.parse(inputText[1]);
            this.arrivalTime = calculateArriveTime();
        } catch (ParseException e) {
            System.out.println("Date Time Parse Error: " + e.toString());
        }
    }

    private Date calculateArriveTime() {
        // get the deparure time in seconds unit
        // add druation in seconds unit (minute * 60 = seconds)
        // convert to milliseconds
        return new Date((getDepartureTime() + duration * (60)) * 1000);
    }

    public int getDepartureTime() {
        // return seconds from milliseconds
        return (int) departureTime.getTime() / 1000;
    }

    public int getArrivalTime() {
        // return seconds from milliseconds
        return (int) arrivalTime.getTime() / 1000;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    // Printing arrival and departure time sentences

    public String arrivalString() {
        return String.format("%s arrive at %s\tTrip State:%s", tripName, dateFormat.format(arrivalTime), state);
    }

    public String departureString() {
        return String.format("%s depart at %s\tTrip State:%s", tripName, dateFormat.format(departureTime), state);
    }

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
}