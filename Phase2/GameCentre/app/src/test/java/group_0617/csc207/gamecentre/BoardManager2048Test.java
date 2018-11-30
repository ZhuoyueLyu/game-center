package group_0617.csc207.gamecentre;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import group_0617.csc207.gamecentre.game2048.Board2048;
import group_0617.csc207.gamecentre.game2048.BoardManager2048;
import group_0617.csc207.gamecentre.game2048.Game2048Activity;
import group_0617.csc207.gamecentre.game2048.Tile2048;

import static org.junit.Assert.*;


/**
 * Unit test for CardBoard Manager
 */
public class BoardManager2048Test {

    /**
     * The cardBoardManager to test
     */
    private BoardManager2048 boardManager2048;

    /**
     * The current complexity tested
     */
    private int complexity = 4;

    /**
     * Set up a Board specifying it's complexity
     */
    private void setUpBoardManager2048(int[] numTiles) {
        List<Tile2048> tiles = new ArrayList<>();
        for (int i = 0; i < complexity * complexity; i++) {
            tiles.add(new Tile2048(numTiles[i], false));
        }
        this.boardManager2048 = new BoardManager2048(new Board2048(tiles));
    }

    /**
     * Return an array showing board2048's all tiles' IDs.
     *
     * @return an array of IDs.
     */
    private int[] getNumTiles() {
        int[] resultTiles = new int[complexity * complexity];
        Tile2048[][] tiles = ((Board2048) boardManager2048.getBoard()).getTiles();
        for (int row = 0; row != complexity; row++) {
            for (int col = 0; col != complexity; col++) {
                resultTiles[row * complexity + col] = tiles[row][col].getId();
            }
        }
        return resultTiles;
    }

    /**
     * Test whether CardBoardManager's constructor works
     */
    @Test
    public void testConstructor() {
        boardManager2048 = new BoardManager2048(complexity);
        assertEquals(4, boardManager2048.getBoard().getComplexity());
    }

    /**
     * Test whether getBoard works
     */
    @Test
    public void testGetBoard() {
        int[] numTiles = {2, 4, 8, 16,
                32, 64, 128, 256,
                512, 1024, 2048, 4096,
                8192, 16384, 32768, 65536};
        setUpBoardManager2048(numTiles);
        Board2048 boardFromMethod = (Board2048) boardManager2048.getBoard();
        assertEquals(boardManager2048.getBoard(), boardFromMethod);
    }

    /**
     * Test whether puzzleSolved works
     */
    @Test
    public void testPuzzleSolved() {
        int[] numTiles1 = {2, 8, 16, 256,
                32, 64, 128, 256,
                512, 1024, 2, 8,
                2, 2, 8, 2};
        setUpBoardManager2048(numTiles1);
        assertFalse(boardManager2048.puzzleSolved());

        int[] numTiles2 = {2, 4, 8, 16,
                32, 64, 128, 256,
                512, 1024, 4096, 8,
                4, 16, 2, 1};
        setUpBoardManager2048(numTiles2);
        assertTrue(boardManager2048.puzzleSolved());
    }

    /**
     * Test whether isValidTap works
     */
    @Test
    public void testIsValidTap() {
        int[] numTiles1 = {1, 0, 0, 0,
                1, 0, 0, 0,
                1, 0, 0, 0,
                3, 0, 0, 0};
        setUpBoardManager2048(numTiles1);
        assertFalse(boardManager2048.isValidTap(Game2048Activity.LEFT));
        assertTrue(boardManager2048.isValidTap(Game2048Activity.UP));
        assertTrue(boardManager2048.isValidTap(Game2048Activity.RIGHT));
        assertTrue(boardManager2048.isValidTap(Game2048Activity.DOWN));

        int[] numTiles2 = {0, 0, 0, 2,
                0, 0, 0, 4,
                0, 0, 0, 8,
                0, 0, 0, 16};
        setUpBoardManager2048(numTiles2);
        assertFalse(boardManager2048.isValidTap(Game2048Activity.UP));
        assertFalse(boardManager2048.isValidTap(Game2048Activity.RIGHT));
        assertFalse(boardManager2048.isValidTap(Game2048Activity.DOWN));
        assertTrue(boardManager2048.isValidTap(Game2048Activity.LEFT));
    }

    /**
     * Test whether touchMove works
     */
    @Test
    public void testTouchMove() {
        int[] numTiles = {4, 2, 2, 2,
                16, 32, 64, 128,
                128, 64, 32, 16,
                2, 4, 8, 1024};
        setUpBoardManager2048(numTiles);
        boardManager2048.touchMove(Game2048Activity.RIGHT);
        int[] expectedTiles = {2, 4, 2, 4,
                16, 32, 64, 128,
                128, 64, 32, 16,
                2, 4, 8, 1024};
        Assert.assertArrayEquals(expectedTiles, getNumTiles());
    }

    /**
     * Test whether getScore works
     */
    @Test
    public void testGetScore() {
        int[] numTiles = {2, 2, 2, 2,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0};
        setUpBoardManager2048(numTiles);
        assertEquals(0, boardManager2048.getScore());
    }

    /**
     * Test whether getCurrentName works
     */
    @Test
    public void testGetCurrentName() {
        int[] numTiles = {2, 2, 2, 2,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0};
        setUpBoardManager2048(numTiles);
        assertEquals("tf", boardManager2048.getCurrentGame());
    }

    /**
     * Test whether getLastTime works
     */
    @Test
    public void testGetLastTime() {
        int[] numTiles = {2, 2, 2, 2,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0};
        setUpBoardManager2048(numTiles);
        boardManager2048.setLastTime(100);
        assertEquals(100, boardManager2048.getLastTime());
    }

    /**
     * Test whether undo works.
     */
    @Test
    public void testUndo() {
        int[] numTiles = {2, 2, 2, 2,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0};
        setUpBoardManager2048(numTiles);
        assertFalse(boardManager2048.undo());

        boardManager2048.touchMove(Game2048Activity.RIGHT);
        boardManager2048.undo();
        int[] expectedTiles = {2, 2, 2, 2,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0};
        Assert.assertArrayEquals(expectedTiles, getNumTiles());
        assertEquals(1, boardManager2048.getTimesOfUndo());
    }
}