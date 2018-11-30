
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
     * Make a set of tiles that are in order. Complexity is specified
     *
     * @param inComplexity the complexity desired
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles(int inComplexity) {
        tiles = new ArrayList<>();
        int numTiles = inComplexity * inComplexity;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum + 1, tileNum));
        }

        return tiles;
    }

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
    private void setUpCorrect(int inComplexity) {
        List<Tile> tiles = makeTiles(inComplexity);
        board = new Board(tiles);
    }

    /**
     * Test whether numTiles
     */
    @Test
    public void testNumTiles() {
        setUpCorrect(4);
        assertEquals(board.getComplexity() * board.getComplexity(), board.numTiles());
        setUpCorrect(5);
        assertEquals(board.getComplexity() * board.getComplexity(), board.numTiles());
        setUpCorrect(10);
        assertEquals(board.getComplexity() * board.getComplexity(), board.numTiles());
    }

    /**
     * Test whether getTile works
     */
    @Test
    public void testGetTile() {
        setUpCorrect(4);
        assertEquals(1, board.getTile(0, 0).getId());
        assertEquals(board.getComplexity() + 2, board.getTile(1, 1).getId());
    }

    /**
     * Test whether swap tile works
     */
    @Test
    public void testSwapTiles() {
        setUpCorrect(4);
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
        boolean isSolvable;

        // Test make solvable when complexity is 4
        setUpCorrect(4);
        // Make Board Unsolvable
        board.swapTiles(0, 0, 0, 1);
        isSolvable = reflectIsSolvable();
        assertFalse(isSolvable);
        board.makeSolvable();
        isSolvable = reflectIsSolvable();
        assertTrue(isSolvable);

        // When complexity is 3
        setUpCorrect(3);
        board.swapTiles(0, 0, 0, 1);
        isSolvable = reflectIsSolvable();
        assertFalse(isSolvable);
        board.makeSolvable();
        isSolvable = reflectIsSolvable();
        assertTrue(isSolvable);

        // When complexity is 2
        setUpCorrect(2);
        board.swapTiles(0, 0, 0, 1);
        isSolvable = reflectIsSolvable();
        assertFalse(isSolvable);
        board.makeSolvable();
        isSolvable = reflectIsSolvable();
        assertTrue(isSolvable);

        // When complexity is 6
        setUpCorrect(6);
        board.swapTiles(0, 0, 0, 1);
        isSolvable = reflectIsSolvable();
        assertFalse(isSolvable);
        board.makeSolvable();
        isSolvable = reflectIsSolvable();
        assertTrue(isSolvable);
    }

    /**
     * Test MakeSolvable() for first.getId() == numTiles()
     */
    @Test
    public void testMakeSolvableForSpecialCase() {
        setUpCorrect(4);
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
        setUpCorrect(4);
        Iterator<Tile> i = board.iterator();
        assertTrue(i.hasNext());
        assertEquals(i.next(), tiles.get(0));

        i = board.iterator();
        assertEquals(i.next(), tiles.get(0));
    }


}

