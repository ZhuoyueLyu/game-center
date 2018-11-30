package group_0617.csc207.gamecentre.dataBase;

import java.io.Serializable;

/**
 * This tuple class is used to store corresponding data of unspecified type
 *
 * @param <X> the X class stored
 * @param <Y> the Y class stored
 */
public class Tuple<X, Y> implements Serializable {

    /**
     * The x stored by the tuple
     */
    private X x;

    /**
     * The y stored by the tuple
     */
    private Y y;

    /**
     * Constructor
     *
     * @param x the x to store
     * @param y the y to stored
     */
    public Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the value of x
     *
     * @return value of x
     */
    public X getX() {
        return x;
    }

    /**
     * Set the value of x
     *
     * @param x value of x
     */
    public void setX(X x) {
        this.x = x;
    }

    /**
     * Get the value of y
     *
     * @return value of y
     */
    public Y getY() {
        return y;
    }

    /**
     * Set the value of y
     *
     * @param y value of y
     */
    public void setY(Y y) {
        this.y = y;
    }
}