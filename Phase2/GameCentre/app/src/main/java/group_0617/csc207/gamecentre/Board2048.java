package group_0617.csc207.gamecentre;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.Switch;

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
public class Board2048 extends Observable implements Serializable {

    /**
     * The number of rows.
     */
    static int NUM_ROWS = 4;

    /**
     * The number of rows.
     */
    static int NUM_COLS = 4;

    /**
     * l
     * The tiles on the board in row-major order.
     */
    private Tile2048[][] tiles = new Tile2048[NUM_ROWS][NUM_COLS];

    private Tile2048[][] lastTiles = new Tile2048[NUM_ROWS][NUM_COLS];


    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    Board2048(List<Tile2048> tiles) {

        for (int row = 0; row != Board.NUM_ROWS; row++) {
            for (int col = 0; col != Board.NUM_COLS; col++) {
                this.tiles[row][col] = tiles.get(row * Board.NUM_COLS + col);
            }
        }
    }

    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    int numTiles() {
        return NUM_ROWS * NUM_COLS;
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
        for (int i = 0, j = 0; i < NUM_ROWS && j < NUM_ROWS; ) {
            if (line[i].getId() == 0) {
                i++;
            } else {
                j = i + 1;
                while (j < NUM_ROWS && line[j].getId() == 0) {
                    j++;
                }
                if (j < NUM_ROWS && line[j].getId() == line[i].getId()) {
                    newLine.add(line[i].getId() * 2);
                    i = j + 1;
                } else if (j < NUM_ROWS && line[j].getId() != line[i].getId()) {
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
        for (int index2 = 0; index2 < Board.NUM_ROWS; index2++) {
            line[index2] = new Tile2048(newLine.get(index2));
        }
    }

    public void rightCombine(Tile2048[] line) {
        for (int j = 0; j <= line.length/2; j++) {
            Tile2048 t = line[j];
            line[j] = line[line.length - 1 - j];
            line[line.length - 1 - j] = t;
        }
        leftCombine(line);
        for (int j = 0; j <= line.length/2; j++) {
            Tile2048 t = line[j];
            line[j] = line[line.length - 1 - j];
            line[line.length - 1 - j] = t;
        }
    }


    void swipeMove(int dir) {
        lastTiles = tiles.clone();
        Tile2048[][] columnTiles = new Tile2048[NUM_ROWS][NUM_COLS];
        for (int row = 0; row != Board.NUM_ROWS; row++) {
            for (int col = 0; col != Board.NUM_COLS; col++) {
                columnTiles[row][col] = this.tiles[col][row];
            }
        }
        switch (dir) {
            case Game2048Activity.UP:
                for (int i = 0; i < NUM_ROWS; i++) {
                    leftCombine(columnTiles[i]);
                }
                for (int row = 0; row != Board.NUM_ROWS; row++) {
                    for (int col = 0; col != Board.NUM_COLS; col++) {
                        tiles[row][col] = columnTiles[col][row];
                    }
                }
                break;
            case Game2048Activity.DOWN:
                for (int i = 0; i < NUM_ROWS; i++) {
                    Tile2048[] reverseColumn = new Tile2048[NUM_COLS];
                    for (int j = 0; j < NUM_COLS; j++) {
                        reverseColumn[j] = columnTiles[i][NUM_COLS - 1 - j];
                    }
                    leftCombine(reverseColumn);
                    for (int k = 0; k < NUM_COLS; k++) {
                        columnTiles[i][k] = reverseColumn[NUM_COLS - 1 - k];
                    }
                }
                for (int row = 0; row != Board.NUM_ROWS; row++) {
                    for (int col = 0; col != Board.NUM_COLS; col++) {
                        tiles[row][col] = columnTiles[col][row];
                    }
                }
                break;
            case Game2048Activity.LEFT:
                for (int i = 0; i < NUM_ROWS; i++) {
                    leftCombine(tiles[i]);
                }
                break;
            case Game2048Activity.RIGHT:
                for (int i = 0; i < NUM_ROWS; i++) {
                    Tile2048[] reverseRow = new Tile2048[NUM_COLS];
                    for (int j = 0; j < NUM_COLS; j++) {
                        reverseRow[j] = tiles[i][NUM_COLS - 1 - j];
                    }
                    leftCombine(reverseRow);
                    for (int k = 0; k < NUM_COLS; k++) {
                        tiles[i][k] = reverseRow[NUM_COLS - 1 - k];
                    }
                }
                break;
        }
//        setChanged();
//        notifyObservers();
    }

    public void setTileId(int position, int id) {
        int x = position / Board2048.NUM_ROWS;
        int y = position % Board2048.NUM_ROWS;
        this.tiles[x][y] = new Tile2048(id);
    }

    public void addRandomTile() {
        List<Integer> emptyTiles = new ArrayList<>();
        for (int i = 0; i < numTiles(); i++) {
            int row = i / Board2048.NUM_ROWS;
            int col = i % Board2048.NUM_ROWS;
            if (getTile(row, col).getId() == 0) {
                emptyTiles.add(i);
            }
        }
        int randomPosition = emptyTiles.get(new Random().nextInt(emptyTiles.size()));
        int[] numToChoose = {2, 2, 2, 2, 4};
        int randomNumber = numToChoose[(new Random()).nextInt(5)];
        setTileId(randomPosition, randomNumber);
        setChanged();
        notifyObservers();
    }

    public Tile2048[][] getTiles() {
        return tiles;
    }

    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }


}