package group_0617.csc207.gamecentre;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

/**
 * A Generic Board that has complexity and Tiles
 */
public abstract class GenericBoard extends Observable implements Serializable {

    /**
     * The complexity of the board
     */
    private int complexity;

    /**
     * The tiles this board manages
     */
    private GenericTile[][] genericTiles;

    /**
     * Create an empty Board
     */
    GenericBoard() {}

    /**
     * Create a Board given Tiles and complexity
     * @param inGenericTiles the tiles given
     * @param inComplexity the complexity given
     */
    GenericBoard(GenericTile[][] inGenericTiles, int inComplexity) {
        this.complexity = inComplexity;
        this.genericTiles = inGenericTiles;
    }

    /**
     * Create a Board given Tiles
     * @param inGenericTiles the tiles given
     */
    GenericBoard(List<GenericTile> inGenericTiles) {
        Iterator<GenericTile> iter = inGenericTiles.iterator();

        this.complexity = (int) Math.sqrt((double)inGenericTiles.size());
        this.genericTiles = new GenericTile[complexity][complexity];
    }

    /**
     * Return the number of tiles
     * @return the number of tiles
     */
    int numTiles() {
        return this.complexity * this.complexity;
    }

    /**
     * Return the complexity of the board
     * @return the complexity of the board
     */
    int getComplexity() {
        return this.complexity;
    }

    /**
     * Get the Tile at specified row and column
     * @param row the specified row
     * @param col the specified col
     * @return the Tile at specified row and column
     */
    GenericTile getGenericTile(int row, int col) {
        return genericTiles[row][col];
    }

    /**
     * Get the entire tiles managed by this Board.
     * @return the entire tiles managed by this Board.
     */
    GenericTile[][] getGenericTiles() {
        return genericTiles;
    }

    /**
     * Set the complexity of this Board
     * @param inComplexity the new complexity of the board
     */
    void setComplexity(int inComplexity) {
        this.complexity = inComplexity;
    }

    /**
     * Set the entire tiles managed by this board.
     * @param inGenericTiles the new set of tiles managed by this board
     */
    void setGenericTiles(GenericTile[][] inGenericTiles) {
        this.genericTiles = inGenericTiles;
    }

    /**
     * Set the specific tile specified by it's row and column
     * @param inGenericTiles the new Tile
     * @param row the row of tile to replace
     * @param col the col of tile to replace
     */
    void setGenericTile(GenericTile inGenericTiles, int row, int col) {
        this.genericTiles[row][col] = inGenericTiles;
    }
}
