
class Main {
    public static void main(String[] args) {
        IO io = new IO("input.txt", "output.txt");
        TripSchedule trips = new TripSchedule(InputParser.parse(io.readInputFile()));
        TripController tripController = new TripController(io, trips);
        io.writeToFile();
    }

}