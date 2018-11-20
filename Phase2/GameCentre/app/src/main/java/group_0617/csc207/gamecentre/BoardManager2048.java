package group_0617.csc207.gamecentre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class BoardManager2048 implements Serializable {

    /**
     * The board being managed.
     */
    private Board2048 board2048;

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

    //private boolean hasEmptyTile = true;


    /**
     * Manage a board that has been pre-populated.
     *
     * @param board2048 the board
     */
    BoardManager2048(Board2048 board2048) {
        this.board2048 = board2048;
    }

    /**
     * Return the current board.
     */
    Board2048 getBoard2048() {
        return board2048;
    }

    /**
     * Manage a new shuffled board.
     */
    BoardManager2048() {
        List<Tile2048> tiles = new ArrayList<>();
        final int numTiles = Board2048.NUM_ROWS * Board2048.NUM_COLS;
        for (int tileNum = 0; tileNum < numTiles; tileNum++) {
            tiles.add(new Tile2048(0));
        }
        //Collections.shuffle(tiles);
        this.board2048 = new Board2048(tiles);
        board2048.addRandomTile();
        board2048.addRandomTile();
    }


    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    boolean puzzleSolved() {
        for (int i = 0; i < Board2048.NUM_ROWS; i++) {
            for (int j = 0; j < Board2048.NUM_ROWS; j++) {
                if (getId(i, j) == 2048) {
                    score = 10000 / (stepCounter + timesOfUndo) / lastTime;
                    return true;
                }
            }
        }
        return false;
    }

    boolean isGameOver(){
        score = 10000 / (stepCounter + timesOfUndo) / lastTime;
        int[] moves = {Game2048Activity.UP, Game2048Activity.DOWN, Game2048Activity.LEFT, Game2048Activity.RIGHT};
        for (int move: moves){
            if (isValidTap(move)){
                return false;
            }
        }
        return true;
    }


    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param move the direction to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    boolean isValidTap(int move) {
        Tile2048[][] tiles = board2048.getTiles();
        Tile2048[][] columnTiles = new Tile2048[Board2048.NUM_ROWS][Board2048.NUM_COLS];
        for (int row = 0; row != Board.NUM_ROWS; row++) {
            for (int col = 0; col != Board.NUM_COLS; col++) {
                columnTiles[row][col] = tiles[col][row];
            }
        }
        switch (move) {
            case Game2048Activity.LEFT:
                for (Tile2048[] line : tiles) {
                    Tile2048[] lineCopy = line.clone();
                    board2048.leftCombine(lineCopy);
                    for (int i = 0; i < line.length; i++){
                        if (line[i].getId() != lineCopy[i].getId()){
                            return true;
                        }
                    }
                }
                break;
            case Game2048Activity.RIGHT:
                for (Tile2048[] line : tiles) {
                    Tile2048[] lineCopy = line.clone();
                    board2048.rightCombine(line);
                    if (!Arrays.equals(line, lineCopy)) {
                        return true;
                    }
                }
                break;
            case Game2048Activity.UP:
                for (Tile2048[] line : columnTiles) {
                    Tile2048[] lineCopy = line.clone();
                    board2048.leftCombine(line);
                    if (!Arrays.equals(line, lineCopy)) {
                        return true;
                    }
                }
                break;
            case Game2048Activity.DOWN:
                for (Tile2048[] line : columnTiles) {
                    Tile2048[] lineCopy = line.clone();
                    board2048.rightCombine(line);
                    if (!Arrays.equals(line, lineCopy)) {
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    void touchMove(int move) {
//        for (int position = 0; position < board2048.numTiles(); position++) {
//            int x = position / Board2048.NUM_ROWS;
//            int y = position % Board2048.NUM_ROWS;
//            board2048.setTileId(position, board2048.swipeMove(move)[x][y].getId());
//        }
        board2048.swipeMove(move);
        board2048.addRandomTile();
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

        if (row <= Board2048.NUM_ROWS - 1
                && row >= 0
                && col <= Board2048.NUM_COLS - 1
                && col >= 0) {
            return getBoard2048().getTile(row, col).getId();
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
        timesOfUndo++;
        return true;

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


