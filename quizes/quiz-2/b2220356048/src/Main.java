
class Main {
    public static void main(String[] args) {
        IO io = new IO(args[0], args[1]);
        TripSchedule trips = new TripSchedule(InputParser.parse(io.readInputFile()));
        new TripController(io, trips);
        io.writeToFile();
    }

}