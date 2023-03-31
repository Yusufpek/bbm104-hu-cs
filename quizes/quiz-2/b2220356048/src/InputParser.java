import java.util.List;

public class InputParser {
    public static Trip[] parse(List<String> input) {
        Trip[] tripArr = new Trip[input.size()];
        for (int i = 0; i < input.size(); i++) {
            tripArr[i] = new Trip(input.get(i).split("\t"));
        }
        return tripArr;
    }
}