import java.util.List;

class Main {
    public static void main(String[] args) {
        IO io = new IO("", "");
        List<Trip> trips = InputParser.parse(io.readInputFile());
    }

}