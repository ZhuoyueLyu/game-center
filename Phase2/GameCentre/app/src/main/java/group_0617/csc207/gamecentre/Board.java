package group_0617.csc207.gamecentre;

import android.support.annotation.NonNull;

import java.util.Observable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * The sliding tiles board.
 * Which implement Iterable<Tile> and Serializable.
 */
public class Board extends Observable implements Serializable{

    /**
     * The number of rows and columns.
     */
    private int complexity;

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles = new Tile[complexity][complexity];

    Board(){ }

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == complexity * complexity
     *
     * @param tiles the tiles for the board
     */
    Board(List<Tile> tiles) {
        Iterator<Tile> iter = tiles.iterator();
        for (int row = 0; row != complexity; row++) {
            for (int col = 0; col != complexity; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    int numTiles() {
        return complexity * complexity;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    Tile getTile(int row, int col) {
        return tiles[row][col];
    }


    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    void changeTo(Board board){
        for (int row = 0; row != complexity; row++) {
            for (int col = 0; col != complexity; col++) {
                tiles[row][col] = board.getTile(row, col);
            }
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Return the complexity of the board
     * @return the complexity of the board
     */
    int getComplexity() {
        return this.complexity;
    }

    /**
     * Set the complexity of the board given new complexity
     * @param inComplexity the new complexity
     */
    void setComplexity(int inComplexity) { this.complexity = inComplexity; }

}