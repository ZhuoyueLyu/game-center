//package group_0617.csc207.gamecentre;
//
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//import java.util.List;
//import java.util.ArrayList;
//
//public class BoardTest {
//
//    /**
//     * The board for testing
//     */
//    private Board board;
//
//    /**
//     * The current complexity
//     */
//    private int complexity = 4;
//
//    /**
//     * Make a set of tiles that are in order.
//     * @return a set of tiles that are in order
//     */
//    private List<Tile> makeTiles(int chosenComplexity) {
//        List<Tile> tiles = new ArrayList<>();
//        final int numTiles = chosenComplexity * chosenComplexity;
//        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
//            tiles.add(new Tile(tileNum + 1, tileNum));
//        }
//
//        return tiles;
//    }
//
//    /**
//     * Make a solved Board.
//     */
//    private void setUpCorrect() {
//        List<Tile> tiles = makeTiles(complexity);
//        board = new Board(tiles);
//    }
//
//    /**
//     * Make a unsolvable Board
//     */
//    private void setUpUnsolvable() {
//
//    }
//
//    @Test
//    public void testNumTiles() {
//        setUpCorrect();
//        assertEquals(board.getComplexity()*board.getComplexity(), board.numTiles());
//    }
//
//    @Test
//    public void testGetTile() {
//        setUpCorrect();
//        assertEquals(1, board.getTile(0, 0).getId());
//        assertEquals(board.getComplexity() + 2, board.getTile(1, 1).getId());
//    }
//
//    @Test
//    public void testSwapTiles() {
//        setUpCorrect();
//        assertEquals(1, board.getTile(0, 0).getId());
//        assertEquals(board.getComplexity() + 2, board.getTile(1, 1).getId());
//        board.swapTiles(0, 0, 1, 1);
//        assertEquals(1, board.getTile(1, 1).getId());
//        assertEquals(board.getComplexity() + 2, board.getTile(0, 0).getId());
//    }
//
//    @Test
//    public void testIsSolvable() {
//        setUpCorrect();
//        assertTrue(board.isSolvable());
//        board.swapTiles(board.getComplexity()-1, board.getComplexity()-1, board.getComplexity()-1, board.getComplexity()-2);
//        assertTrue(board.isSolvable());
//        board.swapTiles(board.getComplexity()-1, board.getComplexity()-1, board.getComplexity()-1, board.getComplexity()-2);
//        board.swapTiles(board.getComplexity()-1, board.getComplexity()-3, board.getComplexity()-1, board.getComplexity()-2);
//        assertFalse(board.isSolvable());
//    }
//
//    @Test
//    public void testMakeSolvable() {
//        setUpCorrect();
//        board.swapTiles(board.getComplexity()-1, board.getComplexity()-3, board.getComplexity()-1, board.getComplexity()-2);
//        board.makeSolvable();
//        assertTrue(board.isSolvable());
//    }
//
//    // Not sure if is needed
//    @Test
//    public void testToString() {
//    }
//
//    @Test
//    public void testIterator() {
//    }
//}