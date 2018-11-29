package group_0617.csc207.gamecentre.gameSlidingTiles;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import group_0617.csc207.gamecentre.GenericBoard;

/**
 * The sliding tiles board.
 * Which implement Iterable<Tile> and Serializable.
 */
public class Board extends GenericBoard implements Iterable<Tile> {

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) is a perfect square
     *
     * @param inTiles the tiles for the board
     */
    public Board(List<Tile> inTiles) {
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
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    public Tile getTile(int row,int col) {
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
    public void swapTiles(int row1,int col1,int row2,int col2) {
        Tile originalTile = getTile(row1, col1);
        setGenericTile(getTile(row2, col2), row1, col1);
        setGenericTile(originalTile, row2, col2);
        setChanged();
        notifyObservers();
    }

    /**
     * Give the position of next smaller tile, if there is not a smaller tile return -1
     * @param pos the position to start finding smaller tile
     * @param id the id to compare to tell whether current tile is smaller
     * @return the position of next smaller tile
     */
    private int findPosOfNextSmallerTile(int pos, int id) {
        Tile nextTile;
        while (pos < numTiles()) {
            nextTile = getTile(pos / getComplexity(), pos % getComplexity());
            if (nextTile.getId() < id) {
                return pos;
            }
            pos++;
        }

        // Meaning no smaller tile is found
        return -1;
    }

    /**
     * Given a list of integers, this method returns the sum.
     * @param listToSumOver the list with integers to sum over
     * @return the sum
     */
    private int sumOverIntegerList(List<Integer> listToSumOver) {
        int sum = 0;
        for (Integer integer : listToSumOver) {
            sum += integer;
        }
        return sum;
    }

    /**
     * Return the sum of polarity over all tiles in the board.
     *
     * @return the sum of polarity over all tiles in the board.
     */
    private int sumOverInversion() {
        List<Integer> invList = new ArrayList<>();
        int posToConsider = numTiles() - 2;
        for (int i = 0; i < posToConsider + 2; i++) {
            invList.add(0);
        }

        Tile curTile;
        int smallerTilePos;
        while (posToConsider >= 0) {
            curTile = getTile(posToConsider / getComplexity(), posToConsider % getComplexity());
            if (curTile.getId() != numTiles()) {
                smallerTilePos = findPosOfNextSmallerTile(posToConsider + 1, curTile.getId());
                if (smallerTilePos != -1) {
                    invList.set(posToConsider, invList.get(smallerTilePos) + 1);
                }
            }
            posToConsider--;
        }
        return sumOverIntegerList(invList);
    }

    /**
     * Check if the board is solvable
     *
     * @return if the board is solvable
     */
    private boolean isSolvable() {
        boolean isEvenPol = sumOverInversion() % 2 == 0;
        int complexity = getComplexity();
        return complexity % 2 == 1 && isEvenPol || complexity % 2 == 0 && blankOnOddRowFromBottom() == isEvenPol;
    }

    /**
     * This method check if the board is solvable. If not, it makes the board solvable
     */
    public void makeSolvable() {
        if (!isSolvable()) {
            Tile first = getTile(0, 0);
            Tile second = getTile(0, 1);
            int complexity = getComplexity();
            if (first.getId() == numTiles() || second.getId() == numTiles()) {
                swapTiles(complexity - 1, complexity - 1, complexity - 1, complexity - 2);
            } else {
                swapTiles(0, 0, 0, 1);
            }
        }
    }

    /**
     * Check if blank tile is on odd row counting from bottom
     *
     * @return if blank tile is on odd row counting from bottom
     */
    private boolean blankOnOddRowFromBottom() {
        boolean re = false;
        int complexity = getComplexity();
        for (int row = complexity - 1; row >= 0; row++) {
            if ((complexity - row) % 2 == 1) {
                for (int col = 0; col < complexity; col++) {
                    Tile curTile = getTile(row, col);
                    if (curTile.getId() == numTiles()) {
                        re = true;
                    }
                }
            }
        }
        return re;
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
         * The index of next tile to return
         */
        int nextIndex = 0;

        @Override
        public boolean hasNext() {
            return nextIndex != numTiles();
        }

        @Override
        public Tile next() {
            Tile result = getTile(nextIndex / getComplexity(), nextIndex % getComplexity());
            nextIndex++;
            return result;
        }

    }
}