import java.util.Arrays;
import java.util.Comparator;

public class TripController implements DepartureController, ArrivalController {
    IO io;
    protected TripSchedule tripSchedule;

    TripController(IO io, TripSchedule tripSchedule) {
        this.io = io;
        this.tripSchedule = tripSchedule;
        DepartureSchedule(tripSchedule);
        io.outputStrings.add(""); // new line
        setAllTripsState(STATE_IDLE); // set all states to idle again
        ArrivalSchedule(tripSchedule);
    }

    @Override
    public void ArrivalSchedule(TripSchedule tripSchedule) {
        io.outputStrings.add("Arrival order:");
        Arrays.sort(tripSchedule.trips, Comparator.comparing(Trip::getArrivalTime));
        // compare sequential items arrival times for delayed
        for (int i = 0; i < (tripSchedule.trips.length - 1); i++) {
            if (tripSchedule.trips[i].arrivalTime.equals(tripSchedule.trips[i + 1].arrivalTime)) {
                tripSchedule.trips[i].setState(STATE_DELAYED);
                tripSchedule.trips[i + 1].setState(STATE_DELAYED);
            }
            io.outputStrings.add(tripSchedule.trips[i].arrivalString());
        }
        io.outputStrings.add(tripSchedule.trips[tripSchedule.trips.length - 1].arrivalString()); // print last item
    }

    @Override
    public void DepartureSchedule(TripSchedule tripSchedule) {
        io.outputStrings.add("Departure order:");
        Arrays.sort(tripSchedule.trips, Comparator.comparing(Trip::getDepartureTime));
        for (int i = 0; i < (tripSchedule.trips.length - 1); i++) {
            if (tripSchedule.trips[i].departureTime.equals(tripSchedule.trips[i + 1].departureTime)) {
                tripSchedule.trips[i].setState(STATE_DELAYED);
                tripSchedule.trips[i + 1].setState(STATE_DELAYED);
            }
            io.outputStrings.add(tripSchedule.trips[i].departureString());
        }
        io.outputStrings.add(tripSchedule.trips[tripSchedule.trips.length - 1].departureString()); // print last item
    }

    private void setAllTripsState(String state) {
        for (Trip trip : tripSchedule.trips) {
            trip.setState(state);
        }
    }

    final static public String STATE_IDLE = "IDLE";
    final static public String STATE_DELAYED = "DELAYED";

}
