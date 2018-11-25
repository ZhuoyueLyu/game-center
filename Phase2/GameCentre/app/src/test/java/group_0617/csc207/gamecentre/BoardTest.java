
package group_0617.csc207.gamecentre;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;

/**
 * Unit test for Board
 */
public class BoardTest {

    /**
     * The board for testing
     */
    private Board board;

    /**
     * The complexity for testing
     */
    private int complexity = 4;

    /**
     * Make a set of tiles that are in order.
     *
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles() {
        List<Tile> tiles = new ArrayList<>();
        int numTiles = complexity * complexity;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum + 1, tileNum));
        }

        return tiles;
    }

    /**
     * Make a solved Board.
     */
    private void setUpCorrect() {
        List<Tile> tiles = makeTiles();
        board = new Board(tiles);
    }

    /**
     * Make a unsolvable Board
     */
    private void setUpUnsolvable() {

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
        int complexity = board.getComplexity();
        board.swapTiles(complexity - 1, complexity - 3, complexity - 1,
                complexity - 2);
        board.makeSolvable();
        //assertTrue(board.isSolvable());
    }
}

