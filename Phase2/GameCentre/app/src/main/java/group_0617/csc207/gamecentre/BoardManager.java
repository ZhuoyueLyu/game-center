package group_0617.csc207.gamecentre;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class BoardManager extends GenericBoardManager {

    /**
     * The number of undos.
     */
    private int timesOfUndo = 0;

    /**
     * The time of last game's timer counts.
     */
    private int lastTime = 0;

    /**
     * The score which outputs after solving game.
     */
    private int score = 0;

    /**
     * The stack of all previous reversed moves.
     */
    private Stack<Tuple<Integer, Integer>> moveStack = new Stack<>();

    /**
     * Manage a new shuffled board specifying desired complexity
     * @param complexity the complexity of board to manage
     */
    BoardManager(int complexity) {
        List<Tile> tiles = new ArrayList<>();
        int numTiles = complexity * complexity;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum, complexity));
        }

        Collections.shuffle(tiles);
        Board board = new Board(tiles);
        board.makeSolvable();
        setBoard(board);
    }

    @Override
    boolean puzzleSolved() {

        for (int row = 0; row != getBoard().getComplexity(); row++) {
            for (int col = 0; col != getBoard().getComplexity(); col++) {
                int correct_older_id = 4 * row + col + 1;
                if (getId(row, col) != correct_older_id) {
                    return false;
                }
            }
        }
        score = 10000 / lastTime / (stepCounter + timesOfUndo);
        stepCounter = 0;
        lastTime = 0;
        timesOfUndo = 0;
        return true;
    }


    @Override
    boolean isValidTap(int position) {
        int row = position / getBoard().getComplexity();
        int col = position % getBoard().getComplexity();
        int blankId = getBoard().numTiles();
        // Are any of the 4 the blank tile?
        Tile above = row == 0 ? null : (Tile) getBoard().getGenericTile(row - 1, col);
        Tile below = row == getBoard().getComplexity() - 1 ? null : (Tile) getBoard().getGenericTile(row + 1, col);
        Tile left = col == 0 ? null : (Tile) getBoard().getGenericTile(row, col - 1);
        Tile right = col == getBoard().getComplexity() - 1 ? null : (Tile) getBoard().getGenericTile(row, col + 1);

        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    @Override
    void touchMove(int position) {

        Board board = (Board) getBoard();

        int row = position / board.getComplexity();
        int col = position % board.getComplexity();
        int blankId = board.numTiles();
        int complexity = board.getComplexity();

        if (getId(row + 1, col) == blankId) {
            board.swapTiles(row, col, row + 1, col);
            moveStack.add(new Tuple<>(position, position + complexity));
        } else if (getId(row, col + 1) == blankId) {
            board.swapTiles(row, col, row, col + 1);
            moveStack.add(new Tuple<>(position, position + 1));
        } else if (getId(row - 1, col) == blankId) {
            board.swapTiles(row, col, row - 1, col);
            moveStack.add(new Tuple<>(position, position - complexity));
        } else if (getId(row, col - 1) == blankId) {
            board.swapTiles(row, col, row, col - 1);
            moveStack.add(new Tuple<>(position, position - 1));
        }
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

        Board board = (Board) getBoard();
        if (row <= board.getComplexity() - 1
                && row >= 0
                && col <= board.getComplexity() - 1
                && col >= 0) {
            Tile curTile = (Tile)board.getGenericTile(row, col);
            return curTile.getId();
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

    @Override
    public int getScore() {
        return score;
    }

}
