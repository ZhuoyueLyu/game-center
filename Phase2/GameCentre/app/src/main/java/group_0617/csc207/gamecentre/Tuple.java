package group_0617.csc207.gamecentre;

/**
 * This tuple class is used to transfer the game data to the leader board
 * @param <X> username
 * @param <Y> the score of the user in a specific game
 */
public class Tuple<X, Y> {
    private  X x;
    private  Y y;
    public Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public X getX() {
        return x;
    }

    public void setX(X x) {
        this.x = x;
    }

    public Y getY() {
        return y;
    }

    public void setY(Y y) {
        this.y = y;
    }
}