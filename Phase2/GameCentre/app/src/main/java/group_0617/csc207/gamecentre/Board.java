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
public class Board extends GenericBoard implements Iterable<Tile>{

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) is a perfect square
     *
     * @param inTiles the tiles for the board
     */
    Board(List<Tile> inTiles) {
        Iterator<Tile> iter = inTiles.iterator();

        int complexity = (int) Math.sqrt((double) inTiles.size());
        setComplexity(complexity);
        Tile[][] tiles = new Tile[complexity][complexity];
        for (int row = 0; row != complexity; row++) {
            for (int col = 0; col != complexity; col++) {
                tiles[row][col] = iter.next();
            }
        }
        setGenericTiles(tiles);
    }

    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    int numTiles() {
        return getComplexity() * getComplexity();
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    Tile getTile(int row, int col) {
        return (Tile) getGenericTile(row, col);
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    void swapTiles(int row1, int col1, int row2, int col2) {
        Tile originalTile = getTile(row1, col1);
        setGenericTile(getTile(row2, col2), row1, col1);
        setGenericTile(originalTile, row2, col2);
        setChanged();
        notifyObservers();
    }

    
    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString((Tile[][]) getGenericTiles()) +
                '}';
    }

    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return new BoardIterator();
    }

    /**
     * Iterates through the tiles of this board.
     */
    private class BoardIterator implements Iterator<Tile> {

        /**
         * The current row index
         */
        int rowIndex = 0;

        /**
         * The current col index
         */
        int columnIndex = 0;

        @Override
        public boolean hasNext() {
            return rowIndex != getComplexity() - 1 ||
                    columnIndex != getComplexity() - 1;
        }

        @Override
        public Tile next() {
            Tile result = getTile(rowIndex, columnIndex);
            if (rowIndex != getComplexity() - 1) {
                if (columnIndex != getComplexity() - 1) {
                    columnIndex++;
                } else {
                    rowIndex++;
                    columnIndex = 0;
                }
            } else {
                if (columnIndex != getComplexity() - 1) {
                    columnIndex++;
                }
            }
            return result;
        }

    }
}