import java.util.*;

public class Game {
    private String[][] board;
    private String[] moves;
    private Player player;
    private IO io;

    Game(IO io, List<String> board, List<String> moves) {
        this.io = io;
        this.moves = parseMoveInput(moves);
        this.board = parseBoard(board);
    }

    private String[] parseMoveInput(List<String> moves) {
        return moves.get(0).split(" "); // parse move input
    }

    private String[][] parseBoard(List<String> board) {
        String[][] gameBoard = new String[board.size()][board.get(0).split(" ").length];
        // Parse board input
        for (int i = 0; i < board.size(); i++) {
            gameBoard[i] = board.get(i).split(" ");
            int indexOfPlayer = Arrays.asList(gameBoard[i]).indexOf("*");
            if (indexOfPlayer != -1) {
                player = new Player(indexOfPlayer, i);
            }
        }
        return gameBoard;
    }

    public void play() {
        io.outputStrings.add("Game board:\n" + boardToString());
        List<String> playedMoves = new ArrayList<String>();
        boolean isHole = false;
        for (String move : moves) {
            makeMove(move);
            updateMove();
            playedMoves.add(move);
            if (checkHole()) {
                isHole = true;
                break;
            }
            updateBoard();
        }
        io.outputStrings.add("Your movement is:\n" + arrayToString(playedMoves.toArray()));
        io.outputStrings.add(""); // new line
        io.outputStrings.add("Your output is:\n" + boardToString());
        if (isHole)
            io.outputStrings.add("Game Over !");
        io.outputStrings.add(player.getScore());
    }

    private void updateBoard() {
        int x = player.lastPoint.getX();
        int y = player.lastPoint.getY();
        String color = board[player.point.getY()][player.point.getX()];
        board[player.point.getY()][player.point.getX()] = "*";
        if (color.equals("R")) {
            board[y][x] = "X";
            player.updateScore(10);
        } else if (color.equals("Y")) {
            board[y][x] = "X";
            player.updateScore(5);
        } else if (color.equals("B")) {
            board[y][x] = "X";
            player.updateScore(-5);
        } else {
            board[y][x] = color;
        }
    }

    private void updateMove() {
        updateMoveForBoard();
        // wall controller
        if (checkWall(player.point.getX(), player.point.getY())) {
            int x = player.lastPoint.getX() - player.point.getX();
            int y = player.lastPoint.getY() - player.point.getY();
            if (x == 0) {
                if (y == (board.length - 1)) {
                    player.point.setY(player.lastPoint.getY() - 1);
                } else if (y == -(board.length - 1)) {
                    player.point.setY(player.lastPoint.getY() + 1);
                } else {
                    player.point.updateY(2 * y); // moves 2 unit
                }
            } else {
                if (x == board[0].length) {
                    player.point.setX(player.lastPoint.getX() - 1);
                } else if (x == -(board[0].length)) {
                    player.point.setX(player.lastPoint.getX() + 1);
                } else {
                    player.point.updateX(2 * x); // moves 2 unit
                }
            }
            player.incrementMove(); // wall causes one extra move
            updateMoveForBoard(); // wall update for move
        }
        player.incrementMove();
    }

    // update the move for board size
    private void updateMoveForBoard() {
        player.point.setX((player.point.getX() + board[0].length) % board[0].length);
        player.point.setY((player.point.getY() + board.length) % board.length);
    }

    private void makeMove(String move) {
        if (move.equals("R")) {
            player.updateX(1);
        } else if (move.equals("L")) {
            player.updateX(-1);
        } else if (move.equals("U")) {
            player.updateY(-1);
        } else {
            player.updateY(1);
        }
    }

    private boolean checkWall(int x, int y) {
        return board[y][x].equals("W");
    }

    private boolean checkHole() {
        boolean result = board[player.point.getY()][player.point.getX()].equals("H");
        if (result) {
            board[player.lastPoint.getY()][player.lastPoint.getX()] = " ";
        }
        return result;

    }

    private String boardToString() {
        String boardString = "";
        for (int i = 0; i < board.length; i++) {
            boardString += arrayToString(board[i]);
            boardString += "\n";
        }
        return boardString;
    }

    private String arrayToString(Object[] array) {
        return Arrays.toString(array)
                .replace(",", "")
                .replace("[", "")
                .replace("]", "");
    }
}