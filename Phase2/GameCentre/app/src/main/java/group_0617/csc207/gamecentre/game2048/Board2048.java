package group_0617.csc207.gamecentre.game2048;

import java.util.ArrayList;
import java.io.Serializable;
import java.util.List;
import java.util.Random;

import group_0617.csc207.gamecentre.GenericBoard;

/**
 * The sliding tiles board.
 * Which implement Iterable<Tile> and Serializable.
 */
public class Board2048 extends GenericBoard implements Serializable {

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == getComplexity() * getComplexity()
     *
     * @param tiles the tiles for the board
     */
    public Board2048(List<Tile2048> tiles) {
        int complexity = (int) Math.sqrt((double) tiles.size());
        setComplexity(complexity);

        Tile2048[][] newTiles = new Tile2048[complexity][complexity];
        for (int row = 0; row != complexity; row++) {
            for (int col = 0; col != complexity; col++) {
                newTiles[row][col] = tiles.get(row * complexity + col);
            }
        }
        setGenericTiles(newTiles);
    }

    /**
     * Modify line to concatenate "adjacent" same tiles towards the left.
     *
     * @param line the array of Tile2048 to be modified.
     */
    void leftCombine(Tile2048[] line) {
        List<Integer> newLine = new ArrayList<>();
        for (int i = 0, j = 0; i < getComplexity() && j < getComplexity(); ) {
            if (line[i].getId() == 0) {
                i++;
            } else {
                j = i + 1;
                while (j < getComplexity() && line[j].getId() == 0) {
                    j++;
                }
                if (j < getComplexity() && line[j].getId() == line[i].getId()) {
                    newLine.add(line[i].getId() * 2);
                    i = j + 1;
                } else if (j < getComplexity() && line[j].getId() != line[i].getId()) {
                    newLine.add(line[i].getId());
                    i = j;
                } else {
                    newLine.add(line[i].getId());
                    i++;
                }
            }
        }
        for (int index1 = newLine.size(); index1 < numTiles(); index1++) {
            newLine.add(0);
        }
        for (int index2 = 0; index2 < getComplexity(); index2++) {
            line[index2] = new Tile2048(newLine.get(index2), false);
        }
    }

    /**
     * Modify Tile2048s by concatenate Tile2048s with same values in the dir direction(swipe).
     *
     * @param dir the direction of swipe.
     */
    public void swipeMove(int dir) {
        Tile2048[][] tiles = (Tile2048[][]) getGenericTiles();
        Tile2048[][] columnTiles = new Tile2048[getComplexity()][getComplexity()];
        for (int row = 0; row != getComplexity(); row++) {
            for (int col = 0; col != getComplexity(); col++) {
                columnTiles[row][col] = tiles[col][row];
            }
        }

        switch (dir) {
            case GameActivity2048.UP:
                for (int i = 0; i < getComplexity(); i++) {
                    leftCombine(columnTiles[i]);
                }
                for (int row = 0; row != getComplexity(); row++) {
                    for (int col = 0; col != getComplexity(); col++) {
                        tiles[row][col] = columnTiles[col][row];
                    }
                }
                break;
            case GameActivity2048.DOWN:
                for (int i = 0; i < getComplexity(); i++) {
                    Tile2048[] reverseColumn = new Tile2048[getComplexity()];
                    for (int j = 0; j < getComplexity(); j++) {
                        reverseColumn[j] = columnTiles[i][getComplexity() - 1 - j];
                    }
                    leftCombine(reverseColumn);
                    for (int k = 0; k < getComplexity(); k++) {
                        columnTiles[i][k] = reverseColumn[getComplexity() - 1 - k];
                    }
                }
                for (int row = 0; row != getComplexity(); row++) {
                    for (int col = 0; col != getComplexity(); col++) {
                        setGenericTile(columnTiles[col][row], row, col);
                    }
                }
                break;
            case GameActivity2048.LEFT:
                for (int i = 0; i < getComplexity(); i++) {
                    leftCombine(tiles[i]);
                }
                break;
            case GameActivity2048.RIGHT:
                for (int i = 0; i < getComplexity(); i++) {
                    Tile2048[] reverseRow = new Tile2048[getComplexity()];
                    for (int j = 0; j < getComplexity(); j++) {
                        reverseRow[j] = getTile(i, getComplexity() - 1 - j);
                    }
                    leftCombine(reverseRow);
                    for (int k = 0; k < getComplexity(); k++) {
                        setGenericTile(reverseRow[getComplexity() - 1 - k], i, k);
                    }
                }
                break;
        }

        setGenericTiles(tiles);
    }

    /**
     * Randomly add a Tile2048 with value of 2 to an empty tile.
     */
    void addRandomTile() {
        List<Integer> emptyTiles = new ArrayList<>();
        for (int i = 0; i < numTiles(); i++) {
            int row = i / getComplexity();
            int col = i % getComplexity();
            if (getTile(row, col).getId() == 0) {
                emptyTiles.add(i);
            }
        }
        int randomPosition = emptyTiles.get(new Random().nextInt(emptyTiles.size()));
        setGenericTile(new Tile2048(2, true), randomPosition / getComplexity(), randomPosition % getComplexity());
        setChanged();
        notifyObservers();
    }

    /**
     * Return the tiles of Board2048.
     *
     * @return the current tiles.
     */
    public Tile2048[][] getTiles() {
        return (Tile2048[][]) getGenericTiles();
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    public Tile2048 getTile(int row, int col) {
        return (Tile2048) getGenericTile(row, col);
    }

}