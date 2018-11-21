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
abstract class BoardManager implements Serializable {

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

//    /**
//     * The stack of all previous reversed moves.
//     */
//    private Stack<Board> boardStack = new Stack<>();


    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    BoardManager(Board board) {
        this.board = board;
    }

//    /**
//     * Return the current board.
//     */
//    Board getBoard() {
//        return board;
//    }

    /**
     * Manage a new shuffled board.
     */
    BoardManager() {
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    abstract boolean puzzleSolved();

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    abstract boolean isValidTap(int position);

    /**
     * Process a touch at position in the board, swapping tiles if any of the neighbouring
     * tiles is the blank tile.
     *
     * @param position the position of a touch
     */
    void touchMove(int position) {
        int a = 0;
    }

//    /**
//     * Undo last move and return true if undo successfully, false if it has been initial states.
//     *
//     * @return whether the current state can make undo.
//     */
//    boolean undo() {
//        if (boardStack.isEmpty()) {
//            return false;
//        } else {
//            //board = boardStack.pop();
//            board.changeTo(boardStack.pop());
//            timesOfUndo++;
//            return true;
//        }
//    }
//
//    void addBoard(Board board){
//        boardStack.add(board);
//    }

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


