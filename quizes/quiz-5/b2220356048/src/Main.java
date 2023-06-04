public class Main {
    public static void main(String[] args) {
        IO io = new IO(args[0], args[1]);
        Queue<String> inputs = io.readInputFile();
        new InputParser(inputs);
        io.writeToFile();
    }
}