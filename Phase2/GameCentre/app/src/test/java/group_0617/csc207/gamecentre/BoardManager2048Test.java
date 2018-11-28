package group_0617.csc207.gamecentre;

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

    private int complexity = 4;

    /**
     * Set up a Board specifying it's complexity
     */
    private void setUpBoardManager2048(int[] numTiles) {
        List<Tile2048> tiles = new ArrayList<>();
        for (int i = 0; i < complexity * complexity; i++) {
            tiles.add(new Tile2048(numTiles[i]));
        }
        this.boardManager2048 = new BoardManager2048(new Board2048(tiles));
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
        Board2048 boardFromMethod = boardManager2048.getBoard();
        assertEquals(boardManager2048.getBoard(), boardFromMethod);
    }

    /**
     * Test whether puzzleSolved works
     */
    @Test
    public void testPuzzleSolved() {
        int[] numTiles1 = {2, 4, 8, 16,
                32, 64, 128, 256,
                512, 1024, 2, 8,
                2, 2, 2, 2};
        setUpBoardManager2048(numTiles1);
        boardManager2048.setLastTime(100);
        boardManager2048.touchMove(Game2048Activity.LEFT);
        assertFalse(boardManager2048.puzzleSolved());

        int[] numTiles2 = {2, 4, 8, 16,
                32, 64, 128, 256,
                512, 1024, 1024, 8,
                4, 2, 2, 1};
        setUpBoardManager2048(numTiles2);
        boardManager2048.setLastTime(100);
        boardManager2048.touchMove(Game2048Activity.RIGHT);
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
        int[] numTiles = {2, 2, 2, 2,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0};
        setUpBoardManager2048(numTiles);
        boardManager2048.touchMove(Game2048Activity.RIGHT);
        assertEquals(1, boardManager2048.getStepNum());
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
        assertEquals(0, boardManager2048.getLastTime());
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
        assertEquals(1, boardManager2048.getTimesOfUndo());
    }
}