package group_0617.csc207.gamecentre;

import java.io.Serializable;
import java.util.Observable;

/**
 * A Generic Board that has complexity and Tiles
 */
public abstract class GenericBoard extends Observable implements Serializable {

    /**
     * The complexity of the board
     */
    private int complexity = 4;

    /**
     * The tiles this board manages
     */
    private GenericTile[][] genericTiles;

    /**
     * Create an empty Board
     */
    protected GenericBoard() {
    }

    /**
     * Return the number of tiles
     *
     * @return the number of tiles
     */
    public int numTiles() {
        return this.complexity * this.complexity;
    }

    /**
     * Return the complexity of the board
     *
     * @return the complexity of the board
     */
    public int getComplexity() {
        return this.complexity;
    }

    /**
     * Get the Tile at specified row and column
     *
     * @param row the specified row
     * @param col the specified col
     * @return the Tile at specified row and column
     */
    public GenericTile getGenericTile(int row, int col) {
        return genericTiles[row][col];
    }

    /**
     * Get the entire tiles managed by this Board.
     *
     * @return the entire tiles managed by this Board.
     */
    protected GenericTile[][] getGenericTiles() {
        return genericTiles;
    }

    /**
     * Set the current board the given board and notify the observer.
     *
     * @param genericBoard the board as a mould.
     */
    void applyBoard(GenericBoard genericBoard) {
        setGenericTiles(genericBoard.getGenericTiles());
        setChanged();
        notifyObservers();
    }

    /**
     * Set the complexity of this Board
     *
     * @param inComplexity the new complexity of the board
     */
    protected void setComplexity(int inComplexity) {
        this.complexity = inComplexity;
    }

    /**
     * Set the entire tiles managed by this board.
     *
     * @param inGenericTiles the new set of tiles managed by this board
     */
    protected void setGenericTiles(GenericTile[][] inGenericTiles) {
        this.genericTiles = inGenericTiles;
    }

    /**
     * Set the specific tile specified by it's row and column
     *
     * @param inGenericTiles the new Tile
     * @param row            the row of tile to replace
     * @param col            the col of tile to replace
     */
    protected void setGenericTile(GenericTile inGenericTiles, int row, int col) {
        this.genericTiles[row][col] = inGenericTiles;
    }


}
