package group_0617.csc207.gamecentre;

import java.io.Serializable;
import java.util.Stack;

/**
 * A Generic BoardManager that manages a Generic Board
 */
public abstract class GenericBoardManager implements Serializable {

    /**
     * The board being managed
     */
    private GenericBoard genericBoard;

    /**
     * The time of last game's timer counts.
     */
    private int lastTime = 0;

    /**
     * The number of undos.
     */
    private int timesOfUndo = 0;

    /**
     * The score which outputs after solving game.
     */
    private int score = 0;

    /**
     * The stack of all previous reversed moves.
     */
    private final Stack<GenericBoard> boardStack = new Stack<>();

    /**
     * Creates an empty BoardManager
     */
    protected GenericBoardManager() {
    }

    /**
     * Create an BoardManager with specified Board
     *
     * @param inGenericBoard the Board of the BoardManager
     */
    protected GenericBoardManager(GenericBoard inGenericBoard) {
        this.genericBoard = inGenericBoard;
    }

    /**
     * Return the Board managed by this manager
     *
     * @return the board managed by this manager
     */
    public GenericBoard getBoard() {
        return this.genericBoard;
    }

    /**
     * Set the board manager by this manager
     *
     * @param inGenericBoard the board to be managed by this manager.
     */
    protected void setBoard(GenericBoard inGenericBoard) {
        this.genericBoard = inGenericBoard;
    }

    /**
     * Return whether the puzzle has been solved
     *
     * @return whether the puzzle has been solved
     */
    public abstract boolean puzzleSolved();

    /**
     * Return whether a tap at specified position is valid
     *
     * @param pos the position tapped
     * @return whether the tap is valid
     */
    public abstract boolean isValidTap(int pos);

    /**
     * Move according to the position specified
     *
     * @param pos the position specified
     */
    public abstract void touchMove(int pos);

    /**
     * Undo last move and return true if undo successfully, false if it has been initial states.
     *
     * @return whether the current can undo.
     */
    public boolean undo() {
        if (boardStack.isEmpty()) {
            return false;
        } else {
            genericBoard.applyBoard(boardStack.pop());
            timesOfUndo++;
            return true;
        }
    }

    /**
     * Return the score recorded by this manager
     *
     * @return the score recorded
     */
    public int getScore() {
        return score;
    }

    /**
     * Set score at score.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Return the last timer counts.
     */
    public int getLastTime() {
        return lastTime;
    }

    /**
     * Set lastTime at lastTime.
     */
    public void setLastTime(int lastTime) {
        this.lastTime = lastTime;
    }

    /**
     * Return the times of undos.
     */
    public int getTimesOfUndo() {
        return this.timesOfUndo;
    }

    /**
     * Return the stack that contains state of previous boards.
     *
     * @return the stack that contains state of previous boards.
     */
    protected Stack<GenericBoard> getBoardStack() {
        return boardStack;
    }

    /**
     * The method which return the abbreviation of current game.
     *
     * @return The name of the current Game
     */
    public abstract String getCurrentGame();
}