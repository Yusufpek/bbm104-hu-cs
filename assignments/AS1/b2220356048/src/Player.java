public class Player {
    public Point point;
    public Point lastPoint;
    private int moveCount;
    private int score;

    Player(int x, int y) {
        point = new Point(x, y);
        lastPoint = new Point(x, y);
        moveCount = 0;
        score = 0;
    }

    public void updateX(int x) {
        lastPoint.updatePoint(point);
        point.updateX(x);
    }

    public void updateY(int y) {
        lastPoint.updatePoint(point);
        point.updateY(y);
    }

    public void incrementMove() {
        this.moveCount++;
    }

    public void updateScore(int puan) {
        this.score += puan;
    }

    public int getMoveCount() {
        return this.moveCount;
    }

    public String getScore() {
        return "Score: " + this.score;
    }
}
