package group_0617.csc207.gamecentre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class BoardManager implements Serializable {

    /**
     * The board being managed.
     */
    private Board board;

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

    /**
     * The stack of all previous reversed moves.
     */
    private Stack<Integer> moveStack = new Stack<Integer>();


    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    BoardManager(Board board) {
        this.board = board;
    }

    /**
     * Return the current board.
     */
    Board getBoard() {
        return board;
    }

    /**
     * Manage a new shuffled board.
     */
    BoardManager() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = Board.NUM_ROWS * Board.NUM_COLS;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }

        Collections.shuffle(tiles);
        this.board = new Board(tiles);
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    boolean puzzleSolved() {

//        for (int row = 0; row != Board.NUM_ROWS; row++) {
//            for (int col = 0; col != Board.NUM_COLS; col++) {
//                int correct_older_id = 4 * row + col + 1;
//                if (getId(row, col) != correct_older_id) {
//                    return false;
//                }
//            }
//        }
        if (stepCounter == 5) {
            score = 10000 / lastTime / (stepCounter + timesOfUndo);
            stepCounter = 0;
            lastTime = 0;
            timesOfUndo = 0;
            return true;
        }
        return false;
    }


    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    boolean isValidTap(int position) {

        int row = position / Board.NUM_COLS;
        int col = position % Board.NUM_COLS;
        int blankId = board.numTiles();
        // Are any of the 4 the blank tile?
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == Board.NUM_ROWS - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == Board.NUM_COLS - 1 ? null : board.getTile(row, col + 1);

        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    /**
     * Process a touch at position in the board, swapping tiles if any of the neighbouring
     * tiles is the blank tile.
     *
     * @param position the position of a touch
     */
    void touchMove(int position) {

        int row = position / Board.NUM_ROWS;
        int col = position % Board.NUM_COLS;
        int blankId = board.numTiles();

        if (getId(row + 1, col) == blankId) {
            getBoard().swapTiles(row, col, row + 1, col);
            moveStack.add(position + Board.NUM_COLS);
        } else if (getId(row, col + 1) == blankId) {
            getBoard().swapTiles(row, col, row, col + 1);
            moveStack.add(position + 1);
        } else if (getId(row - 1, col) == blankId) {
            getBoard().swapTiles(row, col, row - 1, col);
            moveStack.add(position - Board.NUM_COLS);
        } else if (getId(row, col - 1) == blankId) {
            getBoard().swapTiles(row, col, row, col - 1);
            moveStack.add(position - 1);
        }
        stepCounter++;
        System.out.println("Step: " + stepCounter);
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

        if (row <= Board.NUM_ROWS - 1
                && row >= 0
                && col <= Board.NUM_COLS - 1
                && col >= 0) {
            return getBoard().getTile(row, col).getId();
        }
//        this -1 means the row and col is out of bound;
        return -1;
    }

    /**
     * Undo last move and return true if undo successfully, false if it has been initial states.
     *
     * @return whether the current can be undoed.
     */
    boolean undo() {
        if (moveStack.isEmpty()) {
            return false;
        } else {
            int lastInverseMove = moveStack.pop();
            touchUndo(lastInverseMove);
            timesOfUndo++;
            return true;
        }
    }

    /**
     * Similar to touchMove(). However, when do the undo, the reverse move won't
     * be saved in the moveStack.
     *
     * @param position the reverse move to be made for undo
     */
    private void touchUndo(int position) {

        int row = position / Board.NUM_ROWS;
        int col = position % Board.NUM_COLS;
        int blankId = board.numTiles();

        if (getId(row + 1, col) == blankId) {
            getBoard().swapTiles(row, col, row + 1, col);
        } else if (getId(row, col + 1) == blankId) {
            getBoard().swapTiles(row, col, row, col + 1);
        } else if (getId(row - 1, col) == blankId) {
            getBoard().swapTiles(row, col, row - 1, col);
        } else if (getId(row, col - 1) == blankId) {
            getBoard().swapTiles(row, col, row, col - 1);
        }
    }

    /**
     * Return the last timer counts.
     */
    int getLastTime() {
        return lastTime;
    }

    /**
     * Set lastTime at lastTime.
     */
    void setLastTime(int lastTime) {
        this.lastTime = lastTime;
    }

    /**
     * Return the times of undos.
     */
    int getTimesOfUndo() {
        return this.timesOfUndo;
    }

    /**
     * Return the score.
     */
    public int getScore() {
        return score;
    }


    /**
     * Return the number of steps.
     */
    public int getStepCounter() {
        return stepCounter;
    }

}


