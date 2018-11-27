package group_0617.csc207.gamecentre;

/**
 * This tuple class is used to transfer the game data to the leader board
 *
 * @param <X> username
 * @param <Y> the score of the user in a specific game
 */
class Tuple<X,Y> {
    private X x;
    private Y y;

    /**
     * Constructor
     *
     * @param x username
     * @param y score
     */
    Tuple(X x,Y y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the value of x (username)
     *
     * @return value of x (username)
     */
    X getX() {
        return x;
    }

    /**
     * Set the value of x (username)
     *
     * @param x value of x (username)
     */
    void setX(X x) {
        this.x = x;
    }

    /**
     * Get the value of y (score)
     *
     * @return value of y (score)
     */
    Y getY() {
        return y;
    }

    /**
     * Set the value of y (score)
     *
     * @param y value of y (score)
     */
    void setY(Y y) {
        this.y = y;
    }
}