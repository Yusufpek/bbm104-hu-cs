import java.util.List;

class Main {
    public static void main(String[] args) {
        // set input file name to console argument
        IO io = new IO(args[0], "output.txt");
        // read input file's lines
        List<String> inputs = io.readInputFile();
        // create calcuulations by input file's lines and io class
        Calculation calculations = new Calculation(inputs, io);
        // make calculations
        calculations.calculate();
        // write to output file 'output.txt'
        io.writeToFile();
    }
}