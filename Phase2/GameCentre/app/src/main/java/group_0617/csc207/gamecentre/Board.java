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
<<<<<<< HEAD
    private Tile[][] tiles;

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) is a perfect square
=======
    private Tile[][] tiles = new Tile[complexity][complexity];

    Board(){ }

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == complexity * complexity
>>>>>>> ljh
     *
     * @param tiles the tiles for the board
     */
    Board(List<Tile> tiles) {
        Iterator<Tile> iter = tiles.iterator();
<<<<<<< HEAD

        this.complexity = (int) Math.sqrt((double)tiles.size());
        this.tiles = new Tile[complexity][complexity];
        for (int row = 0; row != this.complexity; row++) {
            for (int col = 0; col != this.complexity; col++) {
=======
        for (int row = 0; row != complexity; row++) {
            for (int col = 0; col != complexity; col++) {
>>>>>>> ljh
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
<<<<<<< HEAD
        return this.complexity * this.complexity;
    }

    /**
     * Return the complexity of the board
     * @return the complexity of the board
     */
    int getComplexity() {
        return this.complexity;
=======
        return complexity * complexity;
>>>>>>> ljh
    }

    /**
     * Set the complexity of the board given new complexity
     * @param inComplexity the new complexity
     */
    void setComplexity(int inComplexity) { this.complexity = inComplexity; }

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


<<<<<<< HEAD
    /**
     * Count the number of inversion for current tile specified by row and column number
     * @param row the row of current tile
     * @param col the column of current tile
     * @return the number of inversion for current tile
     */
    private int countInversion(int row, int col){
        int inv = 0;
        int pos = row * this.complexity + col;
        int posToCompare = pos+1;
        Tile currentTile = getTile(row, col);
        Tile tileToCompare;
        while (posToCompare < numTiles()) {
            tileToCompare = getTile(posToCompare/this.complexity, posToCompare%this.complexity);
            if (tileToCompare.getId() != numTiles()) {
                if (currentTile.getId() > tileToCompare.getId()) {
                    inv++;
                }
            }
            posToCompare++;
        }

        return inv;
    }

    /**
     * Return the sum of polarity over all tiles in the blank.
     *
     * @return the sum of polarity over all tiles in the blank.
     */
    private int sumOverPolarity() {

        int totInv = 0;
        for (int i = 0; i < this.complexity; i++) {
            for (int j = 0; j < this.complexity; j++) {
                if (getTile(i, j).getId() != numTiles()) {
                    totInv = totInv + countInversion(i, j);
                }
            }
        }
        return totInv;
    }

    /**
     * Check if the board is solvable
     * @return if the board is solvable
     */
    public boolean isSolvable() {
        boolean isEvenPol = sumOverPolarity()%2 == 0;
        return this.complexity%2 == 1 && isEvenPol || this.complexity%2 == 0 && blankOnOddRowFromBottom() == isEvenPol;
=======
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
>>>>>>> ljh
    }

    /**
     * Return the complexity of the board
     * @return the complexity of the board
     */
<<<<<<< HEAD
    public void makeSolvable() {
        if (!isSolvable()) {
            if (getTile(0, 0).getId() == numTiles() || getTile(1, 0).getId() == numTiles()) {
                swapTiles(this.complexity-1, this.complexity-1, this.complexity-1, this.complexity-2);
            } else {
                swapTiles(0, 0, 1, 0);
            }
        }
=======
    int getComplexity() {
        return this.complexity;
>>>>>>> ljh
    }

    /**
     * Set the complexity of the board given new complexity
     * @param inComplexity the new complexity
     */
<<<<<<< HEAD
    private boolean blankOnOddRowFromBottom() {
        boolean re = false;
        for (int row = this.complexity -1; row >= 0; row++) {
            if ((this.complexity - row) % 2 == 1) {
                for (int col = 0; col < this.complexity; col++) {
                    if (getTile(row, col).getId() == numTiles()) {
                        re = true;
                    }
                }
            }
        }
        return re;
    }

    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return new BoardIterator();
    }

    private class BoardIterator implements Iterator<Tile> {
        int rowIndex = 0;
        int columnIndex = 0;

        @Override
        public boolean hasNext() {
            return rowIndex != complexity - 1 ||
                    columnIndex != complexity - 1;
        }

        @Override
        public Tile next() {
            Tile result = tiles[rowIndex][columnIndex];
            if (rowIndex != complexity - 1) {
                if (columnIndex != complexity - 1) {
                    columnIndex++;
                } else {
                    rowIndex++;
                    columnIndex = 0;
                }
            } else {
                if (columnIndex != complexity - 1) {
                    columnIndex++;
                }
            }
            return result;
        }

    }
=======
    void setComplexity(int inComplexity) { this.complexity = inComplexity; }
>>>>>>> ljh

}