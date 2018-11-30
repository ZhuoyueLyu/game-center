package group_0617.csc207.gamecentre;

import android.util.Log;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import group_0617.csc207.gamecentre.gameSlidingTiles.Board;
import group_0617.csc207.gamecentre.gameSlidingTiles.BoardManager;
import group_0617.csc207.gamecentre.gameSlidingTiles.Tile;

import static org.junit.Assert.*;

/**
 * Unit test for BoardManager
 */
public class BoardManagerTest {

    /**
     * The complexity to test
     */
    private int complexity = 4;

    /**
     * The board manager to test.
     */
    private BoardManager boardManager;

    /**
     * The board to test.
     */
    private Board board;

    /**
     * Does the reflection to access private method isSolvable of board
     *
     * @return whether current board is solvable
     */
    private boolean reflectIsSolvable() {
        boolean re = false;
        try {
            Method boardIsSolvable = board.getClass().getDeclaredMethod("isSolvable");
            boardIsSolvable.setAccessible(true);
            re = (boolean) boardIsSolvable.invoke(board);
        } catch (NoSuchMethodException e) {
            Log.e("BoardManagerTest", "Method isSolvable was not found");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return re;
    }

    /**
     * Make a set of tiles that are in order.
     *
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = complexity * complexity;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum + 1, tileNum));
        }

        return tiles;
    }

    /**
     * Make a solved Board specifying complexity
     *
     * @param inComplexity the complexity desired
     */
    private void setUpCorrect(int inComplexity) {
        List<Tile> tiles = makeTiles();
        complexity = inComplexity;
        board = new Board(tiles);
        boardManager = new BoardManager(complexity);
        boardManager.setLastTime(1);
        boardManager.setBoard(board);
    }

    /**
     * Test whether swapping two tiles makes a solved board unsolved.
     */
    @Test
    public void testIsSolved() {
        setUpCorrect(3);
        assertTrue(boardManager.puzzleSolved());
        board.swapTiles(0, 0, 0, 1);
        assertFalse(boardManager.puzzleSolved());
    }

    /**
     * Test whether isValidTap works.
     */
    @Test
    public void testIsValidTap() {
        setUpCorrect(4);
        assertTrue(boardManager.isValidTap(11));
        assertFalse(boardManager.isValidTap(15));
        assertFalse(boardManager.isValidTap(10));
    }

    /**
     * Test the touch move function by applying several moves
     */
    @Test
    public void testTouchMove() {
        setUpCorrect(4);
        //The case that the right position is empty
        boardManager.touchMove(14);
        //The case that the left position is empty
        boardManager.touchMove(15);
        //The case that the down position is empty
        boardManager.touchMove(11);
        //The case that the up position is empty
        boardManager.touchMove(15);
        assertEquals(16, board.getTile(3, 3).getId());

    }

    /**
     * Test the undo function
     */
    @Test
    public void testUndo() {
        setUpCorrect(4);
        //the case where no move has been made
        assertFalse(boardManager.undo());
        boardManager.touchMove(14);
        boardManager.touchMove(15);
        boardManager.touchMove(11);
        boardManager.touchMove(15);
        boardManager.undo();
        assertEquals(12, board.getTile(3, 3).getId());
        boardManager.undo();
        boardManager.undo();
        boardManager.undo();
        assertEquals(4, boardManager.getTimesOfUndo());
        assertEquals(16, board.getTile(3, 3).getId());
    }

    /**
     * Test the getScore method
     */
    @Test
    public void testGetScore() {
        setUpCorrect(5);
        assertEquals(0, boardManager.getScore());
    }

    /**
     * Test the getLastTime method
     */
    @Test
    public void getLastTime() {
        setUpCorrect(4);
        assertEquals(1, boardManager.getLastTime());
    }

    /**
     * Test the getCurrentGame method
     */
    @Test
    public void testGetCurrentGame() {
        setUpCorrect(6);
        assertEquals("st", boardManager.getCurrentGame());
    }

    /**
     * Test the makeSolvable method
     */
    @Test
    public void testMakeSolvable() {
        // Test whether makeSolvable works when board has complexity 4
        setUpCorrect(4);
        board.swapTiles(0, 0, 0, 1);
        assertFalse(reflectIsSolvable());
        boardManager.makeSolvable();
        assertTrue(reflectIsSolvable());

        // When board has complexity 3
        setUpCorrect(3);
        board.swapTiles(0, 0, 0, 1);
        assertFalse(reflectIsSolvable());
        boardManager.makeSolvable();
        assertTrue(reflectIsSolvable());

        // When board has complexity 5
        setUpCorrect(5);
        board.swapTiles(0, 0, 0, 1);
        assertFalse(reflectIsSolvable());
        boardManager.makeSolvable();
        assertTrue(reflectIsSolvable());
    }
}