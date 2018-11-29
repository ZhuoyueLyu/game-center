
package group_0617.csc207.gamecentre;

import android.util.Log;

import org.junit.Test;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import group_0617.csc207.gamecentre.gameSlidingTiles.Board;
import group_0617.csc207.gamecentre.gameSlidingTiles.Tile;

/**
 * Unit test for Board
 */
public class BoardTest {

    /**
     * The board for testing
     */
    private Board board;

    /**
     * The tiles for testing
     */
    private List<Tile> tiles;

    /**
     * Make a set of tiles that are in order.
     *
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles() {
        tiles = new ArrayList<>();
        int complexity = 4;
        int numTiles = complexity * complexity;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum + 1, tileNum));
        }

        return tiles;
    }

    /**
     * Does the reflection to access private method isSolvable of board
     */
    private boolean reflectIsSolvable() {
        boolean re = false;
        try {
            Method boardIsSolvable = board.getClass().getDeclaredMethod("isSolvable");
            boardIsSolvable.setAccessible(true);
            re = (boolean) boardIsSolvable.invoke(board);
        } catch (NoSuchMethodException e) {
            Log.e("BoardTest", "Methods isSolvable was not found");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return re;
    }

    /**
     * Make a solved Board.
     */
    private void setUpCorrect() {
        List<Tile> tiles = makeTiles();
        board = new Board(tiles);
    }

    /**
     * Test whether numTiles
     */
    @Test
    public void testNumTiles() {
        setUpCorrect();
        assertEquals(board.getComplexity() * board.getComplexity(), board.numTiles());
    }

    /**
     * Test whether getTile works
     */
    @Test
    public void testGetTile() {
        setUpCorrect();
        assertEquals(1, board.getTile(0, 0).getId());
        assertEquals(board.getComplexity() + 2, board.getTile(1, 1).getId());
    }

    /**
     * Test whether swap tile works
     */
    @Test
    public void testSwapTiles() {
        setUpCorrect();
        assertEquals(1, board.getTile(0, 0).getId());
        assertEquals(board.getComplexity() + 2, board.getTile(1, 1).getId());
        board.swapTiles(0, 0, 1, 1);
        assertEquals(1, board.getTile(1, 1).getId());
        assertEquals(board.getComplexity() + 2, board.getTile(0, 0).getId());
    }

    /**
     * Test whether make solvable works
     */
    @Test
    public void testMakeSolvable() {
        setUpCorrect();
        board.swapTiles(0, 0, 0, 1);
        boolean isSolved = reflectIsSolvable();
        assertFalse(isSolved);
        board.makeSolvable();
        isSolved = reflectIsSolvable();
        assertTrue(isSolved);
    }

    /**
     * Test MakeSolvable() for first.getId() == numTiles()
     */
    @Test
    public void testMakeSolvableForSpecialCase() {
        setUpCorrect();
        int complexity = board.getComplexity();
        board.swapTiles(0, 0, complexity - 1,
                complexity - 1);
        boolean isSolved = reflectIsSolvable();
        assertFalse(isSolved);
        board.makeSolvable();
        isSolved = reflectIsSolvable();
        assertTrue(isSolved);
    }


    /**
     * Test whether Board is Iterable.
     */
    @Test
    public void testBoardIterableSimple() {
        setUpCorrect();
        assertTrue(board instanceof Iterable);
        if (board instanceof Iterable) {
            Iterable<Tile> it = (Iterable<Tile>) board;
            Iterator<Tile> i = it.iterator();
            assert (i.hasNext());
            assertEquals(i.next(), tiles.get(0));

            i = it.iterator();
            assertEquals(i.next(), tiles.get(0));
        }
    }


}

