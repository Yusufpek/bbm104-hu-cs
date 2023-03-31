import java.util.ArrayList;
import java.util.List;

public class InputParser<T> {

    static List<Trip> parse(List<String> input) {
        List<Trip> list = new ArrayList<Trip>();
        for (String line : input) {
            list.add(new Trip(line.split(" ")));
        }
        return list;
    }
}
