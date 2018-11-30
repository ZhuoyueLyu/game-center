package group_0617.csc207.gamecentre;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import group_0617.csc207.gamecentre.game2048.Board2048;
import group_0617.csc207.gamecentre.game2048.BoardManager2048;
import group_0617.csc207.gamecentre.game2048.GameActivity2048;
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
     * Set up a Board specifying numTiles.
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
    private int[] getNumTiles(Board2048 board2048) {
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
     * Test whether CardBoardManager's constructor works
     */
    @Test
    public void testConstructor() {
        boardManager2048 = new BoardManager2048(3);
        assertEquals(3, boardManager2048.getBoard().getComplexity());
        boardManager2048 = new BoardManager2048(4);
        assertEquals(4, boardManager2048.getBoard().getComplexity());
        boardManager2048 = new BoardManager2048(5);
        assertEquals(5, boardManager2048.getBoard().getComplexity());
    }

    /**
     * Test whether getBoard works
     */
    @Test
    public void testGetBoard() {
        this.complexity = 3;
        int[] numTiles3 = {2, 4, 8,
                32, 64, 128,
                512, 1024, 2048};
        setUpBoardManager2048(numTiles3);
        Assert.assertArrayEquals(numTiles3, getNumTiles((Board2048) boardManager2048.getBoard()));

        this.complexity = 4;
        int[] numTiles4 = {2, 4, 8, 16,
                32, 64, 128, 256,
                512, 1024, 2048, 4096,
                8192, 16384, 32768, 65536};
        setUpBoardManager2048(numTiles4);
        Assert.assertArrayEquals(numTiles4, getNumTiles((Board2048) boardManager2048.getBoard()));

        this.complexity = 5;
        int[] numTiles5 = {2, 4, 8, 16, 2,
                32, 64, 128, 256, 2,
                512, 1024, 2048, 4096, 4,
                8192, 16384, 32768, 65536, 8,
                2, 4, 8, 2, 4};
        setUpBoardManager2048(numTiles5);
        Assert.assertArrayEquals(numTiles5, getNumTiles((Board2048) boardManager2048.getBoard()));
    }

    /**
     * Test whether puzzleSolved works
     */
    @Test
    public void testPuzzleSolved() {
        this.complexity = 3;
        int[] numTiles3f = {2, 8, 16,
                512, 1024, 2,
                2, 2, 8};
        setUpBoardManager2048(numTiles3f);
        assertFalse(boardManager2048.puzzleSolved());
        int[] numTiles3t = {2, 8, 16,
                512, 1024, 2,
                2, 16, 8};
        setUpBoardManager2048(numTiles3t);
        assertTrue(boardManager2048.puzzleSolved());

        this.complexity = 4;
        int[] numTiles4f = {2, 8, 16, 256,
                32, 64, 128, 256,
                512, 1024, 2, 8,
                2, 2, 8, 2};
        setUpBoardManager2048(numTiles4f);
        assertFalse(boardManager2048.puzzleSolved());

        int[] numTiles4t = {2, 4, 8, 16,
                32, 64, 128, 256,
                512, 1024, 4096, 8,
                4, 16, 2, 1};
        setUpBoardManager2048(numTiles4t);
        assertTrue(boardManager2048.puzzleSolved());

        this.complexity = 5;
        int[] numTiles5f = {2, 4, 8, 16, 2,
                32, 64, 128, 256, 2,
                512, 1024, 2048, 4096, 4,
                8192, 16384, 32768, 65536, 8,
                2, 4, 8, 2, 4};
        setUpBoardManager2048(numTiles5f);
        assertFalse(boardManager2048.puzzleSolved());

        int[] numTiles5t = {2, 4, 8, 16, 2,
                32, 64, 128, 256, 32,
                512, 1024, 2048, 4096, 4,
                8192, 16384, 32768, 65536, 8,
                2, 4, 8, 2, 4};
        setUpBoardManager2048(numTiles5t);
        assertTrue(boardManager2048.puzzleSolved());

    }

    /**
     * Test whether isValidTap works
     */
    @Test
    public void testIsValidTap() {
        this.complexity = 3;
        int[] numTiles3 = {1, 0, 0,
                1, 0, 0,
                1, 0, 0,};
        setUpBoardManager2048(numTiles3);
        assertFalse(boardManager2048.isValidTap(GameActivity2048.LEFT));
        assertTrue(boardManager2048.isValidTap(GameActivity2048.UP));
        assertTrue(boardManager2048.isValidTap(GameActivity2048.RIGHT));
        assertTrue(boardManager2048.isValidTap(GameActivity2048.DOWN));

        int[] numTiles3_ = {0, 0, 2,
                0, 0, 4,
                0, 0, 8};
        setUpBoardManager2048(numTiles3_);
        assertFalse(boardManager2048.isValidTap(GameActivity2048.UP));
        assertFalse(boardManager2048.isValidTap(GameActivity2048.RIGHT));
        assertFalse(boardManager2048.isValidTap(GameActivity2048.DOWN));
        assertTrue(boardManager2048.isValidTap(GameActivity2048.LEFT));

        this.complexity = 4;
        int[] numTiles4 = {1, 0, 0, 0,
                1, 0, 0, 0,
                1, 0, 0, 0,
                3, 0, 0, 0};
        setUpBoardManager2048(numTiles4);
        assertFalse(boardManager2048.isValidTap(GameActivity2048.LEFT));
        assertTrue(boardManager2048.isValidTap(GameActivity2048.UP));
        assertTrue(boardManager2048.isValidTap(GameActivity2048.RIGHT));
        assertTrue(boardManager2048.isValidTap(GameActivity2048.DOWN));

        int[] numTiles4_ = {0, 0, 0, 2,
                0, 0, 0, 4,
                0, 0, 0, 8,
                0, 0, 0, 16};
        setUpBoardManager2048(numTiles4_);
        assertFalse(boardManager2048.isValidTap(GameActivity2048.UP));
        assertFalse(boardManager2048.isValidTap(GameActivity2048.RIGHT));
        assertFalse(boardManager2048.isValidTap(GameActivity2048.DOWN));
        assertTrue(boardManager2048.isValidTap(GameActivity2048.LEFT));

        this.complexity = 5;
        int[] numTiles5 = {1, 0, 0, 0, 0,
                1, 0, 0, 0, 0,
                1, 0, 0, 0, 0,
                4, 0, 0, 0, 0,
                4, 0, 0, 0, 0};
        setUpBoardManager2048(numTiles5);
        assertFalse(boardManager2048.isValidTap(GameActivity2048.LEFT));
        assertTrue(boardManager2048.isValidTap(GameActivity2048.UP));
        assertTrue(boardManager2048.isValidTap(GameActivity2048.RIGHT));
        assertTrue(boardManager2048.isValidTap(GameActivity2048.DOWN));

        int[] numTiles5_ = {0, 0, 0, 0, 2,
                0, 0, 0, 0, 4,
                0, 0, 0, 0, 8,
                0, 0, 0, 0, 16,
                0, 0, 0, 0, 2};
        setUpBoardManager2048(numTiles5_);
        assertFalse(boardManager2048.isValidTap(GameActivity2048.UP));
        assertFalse(boardManager2048.isValidTap(GameActivity2048.RIGHT));
        assertFalse(boardManager2048.isValidTap(GameActivity2048.DOWN));
        assertTrue(boardManager2048.isValidTap(GameActivity2048.LEFT));
    }

    /**
     * Test whether touchMove works
     */
    @Test
    public void testTouchMove() {
        this.complexity = 3;
        int[] numTiles3 = {4, 2, 2,
                16, 32, 64,
                128, 64, 32,};
        setUpBoardManager2048(numTiles3);
        boardManager2048.touchMove(GameActivity2048.LEFT);
        int[] expectedTiles3 = {4, 4, 2,
                16, 32, 64,
                128, 64, 32,};
        Assert.assertArrayEquals(expectedTiles3, getNumTiles((Board2048) boardManager2048.getBoard()));

        this.complexity = 4;
        int[] numTiles4 = {4, 2, 2, 2,
                16, 32, 64, 128,
                128, 64, 32, 16,
                2, 4, 8, 1024};
        setUpBoardManager2048(numTiles4);
        boardManager2048.touchMove(GameActivity2048.RIGHT);
        int[] expectedTiles4 = {2, 4, 2, 4,
                16, 32, 64, 128,
                128, 64, 32, 16,
                2, 4, 8, 1024};
        Assert.assertArrayEquals(expectedTiles4, getNumTiles((Board2048) boardManager2048.getBoard()));

        this.complexity = 5;
        int[] numTiles5 = {4, 2, 2, 2, 4,
                16, 32, 64, 128, 4,
                128, 64, 32, 16, 2,
                2, 4, 8, 1024, 4,
                4, 8, 128, 256, 8};
        setUpBoardManager2048(numTiles5);
        boardManager2048.touchMove(GameActivity2048.UP);
        int[] expectedTiles5 = {4, 2, 2, 2, 8,
                16, 32, 64, 128, 2,
                128, 64, 32, 16, 4,
                2, 4, 8, 1024, 8,
                4, 8, 128, 256, 2};
        Assert.assertArrayEquals(expectedTiles5, getNumTiles((Board2048) boardManager2048.getBoard()));
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
        this.complexity = 3;
        int[] numTiles3 = {2, 2, 2,
                0, 0, 0,
                0, 0, 0};
        setUpBoardManager2048(numTiles3);
        assertFalse(boardManager2048.undo());

        boardManager2048.touchMove(GameActivity2048.RIGHT);
        boardManager2048.undo();
        int[] expectedTiles3 = {2, 2, 2,
                0, 0, 0,
                0, 0, 0};
        Assert.assertArrayEquals(expectedTiles3, getNumTiles((Board2048) boardManager2048.getBoard()));
        assertEquals(1, boardManager2048.getTimesOfUndo());

        this.complexity = 4;
        int[] numTiles4 = {2, 2, 2, 2,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0};
        setUpBoardManager2048(numTiles4);
        assertFalse(boardManager2048.undo());

        boardManager2048.touchMove(GameActivity2048.RIGHT);
        boardManager2048.touchMove(GameActivity2048.RIGHT);
        boardManager2048.undo();
        boardManager2048.undo();
        int[] expectedTiles4 = {2, 2, 2, 2,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0};
        Assert.assertArrayEquals(expectedTiles4, getNumTiles((Board2048) boardManager2048.getBoard()));
        assertEquals(2, boardManager2048.getTimesOfUndo());

        this.complexity = 5;
        int[] numTiles5 = {0, 0, 0, 0, 2,
                0, 0, 0, 0, 4,
                0, 0, 0, 0, 8,
                0, 0, 0, 0, 16,
                0, 0, 0, 0, 2};
        setUpBoardManager2048(numTiles5);
        assertFalse(boardManager2048.undo());

        boardManager2048.touchMove(GameActivity2048.RIGHT);
        boardManager2048.touchMove(GameActivity2048.LEFT);
        boardManager2048.touchMove(GameActivity2048.RIGHT);
        boardManager2048.undo();
        boardManager2048.undo();
        boardManager2048.undo();
        int[] expectedTiles5 = {0, 0, 0, 0, 2,
                0, 0, 0, 0, 4,
                0, 0, 0, 0, 8,
                0, 0, 0, 0, 16,
                0, 0, 0, 0, 2};
        Assert.assertArrayEquals(expectedTiles5, getNumTiles((Board2048) boardManager2048.getBoard()));
        assertEquals(3, boardManager2048.getTimesOfUndo());

    }

}
