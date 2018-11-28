package group_0617.csc207.gamecentre.dataBase;

/**
 * This tuple class is used to transfer the game data to the leader board
 *
 * @param <X> username
 * @param <Y> the score of the user in a specific game
 */
public class Tuple<X,Y> {
    private X x;
    private Y y;

    /**
     * Constructor
     *
     * @param x username
     * @param y score
     */
    public Tuple(X x,Y y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the value of x (username)
     *
     * @return value of x (username)
     */
    public X getX() {
        return x;
    }

    /**
     * Set the value of x (username)
     *
     * @param x value of x (username)
     */
    public void setX(X x) {
        this.x = x;
    }

    /**
     * Get the value of y (score)
     *
     * @return value of y (score)
     */
    public Y getY() {
        return y;
    }

    /**
     * Set the value of y (score)
     *
     * @param y value of y (score)
     */
    public void setY(Y y) {
        this.y = y;
    }
}