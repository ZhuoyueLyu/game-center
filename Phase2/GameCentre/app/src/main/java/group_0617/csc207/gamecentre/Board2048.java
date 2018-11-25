package group_0617.csc207.gamecentre;

import java.util.ArrayList;
import java.util.Observable;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * The sliding tiles board.
 * Which implement Iterable<Tile> and Serializable.
 */
public class Board2048 extends GenericBoard implements Serializable {

    /**
     * l
     * The tiles on the board in row-major order.
     */
    private Tile2048[][] tiles = new Tile2048[getComplexity()][getComplexity()];


    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == getComplexity() * getComplexity()
     *
     * @param tiles the tiles for the board
     */
    Board2048(List<Tile2048> tiles) {
        int complexity = (int) Math.sqrt((double) tiles.size());
        setComplexity(complexity);

        for (int row = 0; row != getComplexity(); row++) {
            for (int col = 0; col != getComplexity(); col++) {
                this.tiles[row][col] = tiles.get(row * getComplexity() + col);
            }
        }
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    Tile2048 getTile(int row, int col) {
        return tiles[row][col];
    }

    public void leftCombine(Tile2048[] line) {
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
            line[index2] = new Tile2048(newLine.get(index2));
        }
    }

    public void rightCombine(Tile2048[] line) {
        Tile2048[] reverseLine = new Tile2048[line.length];
        for (int j = 0; j < line.length; j++) {
            reverseLine[j] = line[line.length - 1 - j];
        }
        leftCombine(reverseLine);
        for (int k = 0; k < line.length; k++) {
            line[k] = reverseLine[line.length - 1 - k];
        }
    }


    void swipeMove(int dir) {
        Tile2048[][] columnTiles = new Tile2048[getComplexity()][getComplexity()];
        for (int row = 0; row != getComplexity(); row++) {
            for (int col = 0; col != getComplexity(); col++) {
                columnTiles[row][col] = this.tiles[col][row];
            }
        }
        switch (dir) {
            case Game2048Activity.UP:
                for (int i = 0; i < getComplexity(); i++) {
                    leftCombine(columnTiles[i]);
                }
                for (int row = 0; row != getComplexity(); row++) {
                    for (int col = 0; col != getComplexity(); col++) {
                        tiles[row][col] = columnTiles[col][row];
                    }
                }
                break;
            case Game2048Activity.DOWN:
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
                        tiles[row][col] = columnTiles[col][row];
                    }
                }
                break;
            case Game2048Activity.LEFT:
                for (int i = 0; i < getComplexity(); i++) {
                    leftCombine(tiles[i]);
                }
                break;
            case Game2048Activity.RIGHT:
                for (int i = 0; i < getComplexity(); i++) {
                    Tile2048[] reverseRow = new Tile2048[getComplexity()];
                    for (int j = 0; j < getComplexity(); j++) {
                        reverseRow[j] = tiles[i][getComplexity() - 1 - j];
                    }
                    leftCombine(reverseRow);
                    for (int k = 0; k < getComplexity(); k++) {
                        tiles[i][k] = reverseRow[getComplexity() - 1 - k];
                    }
                }
                break;
        }
//        setChanged();
//        notifyObservers();
    }

    public void setTileId(int position, int id) {
        int x = position / getComplexity();
        int y = position % getComplexity();
        this.tiles[x][y] = new Tile2048(id);
    }

    public void addRandomTile() {
        List<Integer> emptyTiles = new ArrayList<>();
        for (int i = 0; i < numTiles(); i++) {
            int row = i / getComplexity();
            int col = i % getComplexity();
            if (getTile(row, col).getId() == 0) {
                emptyTiles.add(i);
            }
        }
        int randomPosition = emptyTiles.get(new Random().nextInt(emptyTiles.size()));
//        int[] numToChoose = {2, 2, 2, 2, 4};
//        int randomNumber = numToChoose[(new Random()).nextInt(5)];
        setTileId(randomPosition, 2);
        setChanged();
        notifyObservers();
    }

    void revertBoard(Board2048 board2048){
        for (int i = 0; i < getComplexity(); i++) {
            for (int j = 0; j < getComplexity(); j++) {
                this.tiles[i][j] = new Tile2048(board2048.getTile(i, j).getId());
            }
        }
        setChanged();
        notifyObservers();
    }

    public Tile2048[][] getTiles() {
        return tiles;
    }

    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    int numTiles() {
        return getComplexity() * getComplexity();
    }

    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }


}