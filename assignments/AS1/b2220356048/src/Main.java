import java.util.List;

class Main {
    public static void main(String[] args) {
        List<String> board = IO.readInputFile(args[0]);
        List<String> moves = IO.readInputFile(args[1]);
        IO io = new IO(); // set the outputs array
        Game game = new Game(io, board, moves);
        game.play();
        io.writeToFile("output.txt");
    }
}