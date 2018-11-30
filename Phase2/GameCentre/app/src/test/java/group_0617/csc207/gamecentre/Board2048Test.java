package group_0617.csc207.gamecentre;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import group_0617.csc207.gamecentre.game2048.Board2048;
import group_0617.csc207.gamecentre.game2048.GameActivity2048;
import group_0617.csc207.gamecentre.game2048.Tile2048;

import static org.junit.Assert.*;

public class Board2048Test {

    /**
     * The Board2048 to test
     */
    private Board2048 board2048;

    /**
     * The complexity of the current board tested
     */
    private int complexity = 4;

    /**
     * Set up a board like a normal game
     */
    private void setUpBoard2048(int[] numTiles) {
        List<Tile2048> tiles = new ArrayList<>();
        for (int row = 0; row != complexity; row++) {
            for (int col = 0; col != complexity; col++) {
                tiles.add(new Tile2048(numTiles[row * complexity + col], false));
            }
        }
        this.board2048 = new Board2048(tiles);
    }

    /**
     * Return an array showing board2048's all tiles' IDs.
     *
     * @return an array of IDs.
     */
    private int[] getNumTiles() {
        int[] resultTiles = new int[complexity * complexity];
        Tile2048[][] tiles = board2048.getTiles();
        for (int row = 0; row != complexity; row++) {
            for (int col = 0; col != complexity; col++) {
                resultTiles[row * complexity + col] = tiles[row][col].getId();
            }
        }
        return resultTiles;
    }

    /**
     * Test whether swipeMove works.
     */
    @Test
    public void testSwipeMove() {
        int[] numTiles = {4, 2, 2, 0,
                2, 0, 0, 2,
                0, 0, 2, 4,
                2, 4, 0, 0};
        setUpBoard2048(numTiles);

        board2048.swipeMove(GameActivity2048.LEFT);
        int[] expectedTiles1 = {4, 4, 0, 0,
                4, 0, 0, 0,
                2, 4, 0, 0,
                2, 4, 0, 0};
        Assert.assertArrayEquals(expectedTiles1, getNumTiles());

        board2048.swipeMove(GameActivity2048.UP);
        int[] expectedTiles2 = {8, 8, 0, 0,
                4, 4, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0};
        Assert.assertArrayEquals(expectedTiles2, getNumTiles());

        board2048.swipeMove(GameActivity2048.RIGHT);
        int[] expectedTiles3 = {0, 0, 0, 16,
                0, 0, 0, 8,
                0, 0, 0, 0,
                0, 0, 0, 0};
        Assert.assertArrayEquals(expectedTiles3, getNumTiles());

        board2048.swipeMove(GameActivity2048.DOWN);
        int[] expectedTiles4 = {0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 16,
                0, 0, 0, 8};
        Assert.assertArrayEquals(expectedTiles4, getNumTiles());
    }

    /**
     * Test whether getTile works.
     */
    @Test
    public void testGetTile() {
        int[] numTiles = {8, 2, 2, 0,
                2, 0, 0, 2,
                0, 0, 2, 4,
                2, 4, 0, 0};
        setUpBoard2048(numTiles);
        assertEquals(8, board2048.getTile(0, 0).getId());
    }

    /**
     * Test whether getTiles works.
     */
    @Test
    public void testGetTiles() {
        int[] numTiles = {4, 2, 2, 0,
                2, 0, 0, 2,
                0, 0, 2, 4,
                2, 4, 0, 0};
        setUpBoard2048(numTiles);
        assertArrayEquals(numTiles, getNumTiles());
    }

}