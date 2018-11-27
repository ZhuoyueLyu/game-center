package group_0617.csc207.gamecentre;

import java.io.Serializable;

/**
 * A Generic BoardManager that manages a Generic Board
 */
abstract class GenericBoardManager implements Serializable {

    /**
     * The board being managed
     */
    private GenericBoard genericBoard;

    /**
     * Creates an empty BoardManager
     */
    GenericBoardManager() {}

    /**
     * Create an BoardManager with specified Board
     * @param inGenericBoard the Board of the BoardManager
     */
    GenericBoardManager(GenericBoard inGenericBoard) {
        this.genericBoard = inGenericBoard;
    }

    /**
     * Return the Board managed by this manager
     * @return the board managed by this manager
     */
    GenericBoard getBoard() {
        return this.genericBoard;
    }

    /**
     * Set the board manager by this manager
     * @param inGenericBoard the board to be managed by this manager.
     */
    void setBoard(GenericBoard inGenericBoard) {
        this.genericBoard = inGenericBoard;
    }

    /**
     * Return whether the puzzle has been solved
     * @return whether the puzzle has been solved
     */
    abstract boolean puzzleSolved();

    /**
     * Return whether a tap at specified position is valid
     * @param pos the position tapped
     * @return whether the tap is valid
     */
    abstract boolean isValidTap(int pos);

    /**
     * Move according to the position specified
     * @param pos the position specified
     */
    abstract void touchMove(int pos);

    /**
     * Return the score recorded by this manager
     * @return the score recorded
     */
    abstract int getScore();

    /**
     * The method which return the abbreviation of current game.
     *
     * @return The name of the current Game
     */
    abstract String getCurrentGame();
}