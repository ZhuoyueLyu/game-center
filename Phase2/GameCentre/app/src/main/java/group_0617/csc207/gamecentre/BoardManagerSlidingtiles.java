package group_0617.csc207.gamecentre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class BoardManagerSlidingtiles extends BoardManager implements Serializable {

    /**
     * The board being managed.
     */
    private BoardSlidingtiles boardSlidingtiles;

    /**
     * The stack of all previous reversed moves.
     */
    private Stack<BoardSlidingtiles> boardStack = new Stack<>();

    /**
     * The number of steps.
     */
    private int stepCounter = 0;

    /**
     * The number of undos.
     */
    private int timesOfUndo = 0;

    /**
     * The time of last game's timer counts..
     */
    private int lastTime = 0;

    /**
     * The score which outputs after solving game.
     */
    private int score = 0;

//    /**
//     * Manage a board that has been pre-populated.
//     *
//     * @param boardSlidingtiles the board
//     */
//    BoardManagerSlidingtiles(BoardSlidingtiles boardSlidingtiles) {
//        this.boardSlidingtiles = boardSlidingtiles;
//    }

    /**
     * Manage a new shuffled board.
     */
    BoardManagerSlidingtiles(int complexity) {
        List<TileSlidingtiles> tiles = new ArrayList<>();
        final int numTiles = complexity*complexity;
        for (int tileNum = 1; tileNum <= numTiles; tileNum++) {
            tiles.add(new TileSlidingtiles(tileNum, complexity));
        }
        Collections.shuffle(tiles);
        this.boardSlidingtiles = new BoardSlidingtiles(tiles);
    }

//    @Override
    boolean puzzleSolved() {

        for (int row = 0; row != boardSlidingtiles.getComplexity(); row++) {
            for (int col = 0; col != boardSlidingtiles.getComplexity(); col++) {
                int correct_older_id = boardSlidingtiles.getComplexity() * row + col + 1;
                if (getId(row, col) != correct_older_id) {
                    return false;
                }
            }
        }
        score = 10000 / lastTime / stepCounter + timesOfUndo;
        stepCounter = 0;
        lastTime = 0;
        timesOfUndo = 0;
        return true;
    }


//    @Override
    boolean isValidTap(int position) {
        int row = position / boardSlidingtiles.getComplexity();
        int col = position % boardSlidingtiles.getComplexity();
        int blankId = boardSlidingtiles.numTiles();
        TileSlidingtiles above = row == 0 ? null : boardSlidingtiles.getTile(row - 1, col);
        TileSlidingtiles below = row == boardSlidingtiles.getComplexity() - 1 ? null : boardSlidingtiles.getTile(row + 1, col);
        TileSlidingtiles left = col == 0 ? null : boardSlidingtiles.getTile(row, col - 1);
        TileSlidingtiles right = col == boardSlidingtiles.getComplexity() - 1 ? null : boardSlidingtiles.getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    //@Override
    void touchMove(int position) {

        int row = position / boardSlidingtiles.getComplexity();
        int col = position % boardSlidingtiles.getComplexity();
        int blankId = boardSlidingtiles.numTiles();
        boardStack.add(getBoard());

        if (getId(row + 1, col) == blankId) {
            getBoard().swapTiles(row, col, row + 1, col);
        } else if (getId(row, col + 1) == blankId) {
            getBoard().swapTiles(row, col, row, col + 1);
        } else if (getId(row - 1, col) == blankId) {
            getBoard().swapTiles(row, col, row - 1, col);
        } else if (getId(row, col - 1) == blankId) {
            getBoard().swapTiles(row, col, row, col - 1);
        }
        //addBoard(boardSlidingtiles);
        //setStepCounter(getStepCounter() + 1);
        stepCounter++;
    }

    /**
     * Get the Id of the tile at the specific row, col location.
     *
     * @param row the row number of that tile
     * @param col the column number of that tile
     * @return the Id of tile[row][col],
     * or "-1" if the row number or column number is invalid.
     */
    private int getId(int row, int col) {

        if (row <= boardSlidingtiles.getComplexity() - 1
                && row >= 0
                && col <= boardSlidingtiles.getComplexity() - 1
                && col >= 0) {
            return getBoard().getTile(row, col).getId();
        }
//        this -1 means the row and col is out of bound;
        return -1;
    }

    /**
     * Undo last move and return true if undo successfully, false if it has been initial states.
     *
     * @return whether the current state can make undo.
     */
    boolean undo() {
        if (boardStack.isEmpty()) {
            return false;
        } else {
//            boardSlidingtiles = boardStack.pop();
//            boardSlidingtiles.notifyObservers();
            boardSlidingtiles.changeTo(boardStack.pop());
            //setTimesOfUndo(getTimesOfUndo() + 1);
            timesOfUndo++;
            return true;
        }
    }

    /**
     * Return the current board.
     */
    BoardSlidingtiles getBoard() {
        return boardSlidingtiles;
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

    public void setTimesOfUndo(int timesOfUndo) {
        this.timesOfUndo = timesOfUndo;
    }

    /**
     * Return the score.
     */
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Return the number of steps.
     */
    public int getStepCounter() {
        return stepCounter;
    }

    public void setStepCounter(int stepCounter) {
        this.stepCounter = stepCounter;
    }

}
