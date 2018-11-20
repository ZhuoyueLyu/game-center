package group_0617.csc207.gamecentre;

import org.junit.Test;

import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class BoardTest {

    /**
     * The board for testing
     */
    private Board board;

    /**
     * Make a set of tiles that are in order.
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = Board.NUM_ROWS * Board.NUM_COLS;
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

    @Test
    public void testNumTiles() {
        setUpCorrect();
        assertEquals(board.NUM_COLS*board.NUM_COLS, board.numTiles());
    }

    @Test
    public void testGetTile() {
        setUpCorrect();
        assertEquals(1, board.getTile(0, 0).getId());
        assertEquals(board.NUM_COLS + 2, board.getTile(1, 1).getId());
    }

    @Test
    public void testSwapTiles() {
        setUpCorrect();
        assertEquals(1, board.getTile(0, 0).getId());
        assertEquals(board.NUM_COLS + 2, board.getTile(1, 1).getId());
        board.swapTiles(0, 0, 1, 1);
        assertEquals(1, board.getTile(1, 1).getId());
        assertEquals(board.NUM_COLS + 2, board.getTile(0, 0).getId());
    }

    @Test
    public void testIsSolvable() {
        setUpCorrect();
        assertTrue(board.isSolvable());
        board.swapTiles(board.NUM_ROWS-1, board.NUM_COLS-1, board.NUM_ROWS-1, board.NUM_COLS-2);
        assertTrue(board.isSolvable());
        board.swapTiles(board.NUM_ROWS-1, board.NUM_COLS-1, board.NUM_ROWS-1, board.NUM_COLS-2);
        board.swapTiles(board.NUM_ROWS-1, board.NUM_COLS-3, board.NUM_ROWS-1, board.NUM_COLS-2);
        assertFalse(board.isSolvable());
    }

    @Test
    public void testMakeSolvable() {
        setUpCorrect();
        board.swapTiles(board.NUM_ROWS-1, board.NUM_COLS-3, board.NUM_ROWS-1, board.NUM_COLS-2);
        board.makeSolvable();
        assertTrue(board.isSolvable());
    }

    // Not sure if is needed
    @Test
    public void testToString() {
    }

    @Test
    public void testIterator() {
    }
}