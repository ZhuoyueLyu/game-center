package group_0617.csc207.gamecentre.gameSlidingTiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import group_0617.csc207.gamecentre.GenericBoardManager;
import group_0617.csc207.gamecentre.dataBase.Tuple;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
public class BoardManager extends GenericBoardManager {

    /**
     * The name of the game
     */
    public static final String GAME_NAME = "st";

    /**
     * The stack of all previous reversed moves.
     */
    private Stack<Tuple<Integer, Integer>> moveStack = new Stack<>();

    /**
     * Manage a new shuffled board specifying desired complexity
     *
     * @param complexity the complexity of board to manage
     */
    public BoardManager(int complexity) {
        List<Tile> tiles = new ArrayList<>();
        int numTiles = complexity * complexity;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum + 1, complexity));
        }

        Collections.shuffle(tiles);
        Board board = new Board(tiles);
        setBoard(board);
    }

    @Override
    public boolean puzzleSolved() {
        for (int row = 0; row != getBoard().getComplexity(); row++) {
            for (int col = 0; col != getBoard().getComplexity(); col++) {
                int correct_older_id = getBoard().getComplexity() * row + col + 1;
                if (getId(row, col) != correct_older_id) {
                    return false;
                }
            }
        }
        setScore(1000000 * (getBoard().getComplexity() - 2) / (getLastTime() + 1 + moveStack.size() + 2 * getTimesOfUndo()));
        return true;
    }


    @Override
    public boolean isValidTap(int position) {
        int row = position / getBoard().getComplexity();
        int col = position % getBoard().getComplexity();
        int blankId = getBoard().numTiles();

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
    public void touchMove(int position) {
        Board board = (Board) getBoard();
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < getBoard().getComplexity(); i++) {
            for (int j = 0; j < getBoard().getComplexity(); j++) {
                tiles.add(((Board) getBoard()).getTile(i, j));
            }
        }
        getBoardStack().add(new Board(tiles));

        int row = position / board.getComplexity();
        int col = position % board.getComplexity();
        int blankId = board.numTiles();

        if (getId(row + 1, col) == blankId) {
            board.swapTiles(row, col, row + 1, col);
        } else if (getId(row, col + 1) == blankId) {
            board.swapTiles(row, col, row, col + 1);
        } else if (getId(row - 1, col) == blankId) {
            board.swapTiles(row, col, row - 1, col);
        } else if (getId(row, col - 1) == blankId) {
            board.swapTiles(row, col, row, col - 1);
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
            Tile curTile = (Tile) board.getGenericTile(row, col);
            return curTile.getId();
        }
        return -1;
    }

    /**
     * The method which return the abbreviation of the SlidingTiles game, i.e., "ST"
     *
     * @return "ST"
     */
    @Override
    public String getCurrentGame() {
        return GAME_NAME;
    }

    /**
     * Make the board managed solvable
     */
    public void makeSolvable() {
        Board board = (Board) getBoard();
        board.makeSolvable();
    }
}
