package group_0617.csc207.gamecentre;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class BoardManagerTest {


    private int complexity = 4;


    /**
     * The board manager for testing.
     */
    BoardManager boardManager;

    Board board;

    /**
     * Make a set of tiles that are in order.
     *
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = complexity * complexity;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum + 1,tileNum));
        }

        return tiles;
    }

    /**
     * Make a solved Board.
     */
    private void setUpCorrect() {
        List<Tile> tiles = makeTiles();
        board = new Board(tiles);
        boardManager = new BoardManager(complexity);
        boardManager.setLastTime(1);
        boardManager.setTimesOfUndo(1);
        boardManager.setBoard(board);
    }


    /**
     * Test whether swapping two tiles makes a solved board unsolved.
     */
    @Test
    public void testIsSolved() {
        setUpCorrect();
        assertTrue(boardManager.puzzleSolved());
        board.swapTiles(0,0,0,1);
        assertFalse(boardManager.puzzleSolved());
    }

//    /**
//     * Test whether swapping the first two tiles works.
//     */
//    @Test
//    public void testSwapFirstTwo() {
//        setUpCorrect();
//        assertEquals(1,boardManager.getBoard().getGenericTile(0,0).getId());
//        assertEquals(2,boardManager.getBoard().getTile(0,1).getId());
//        boardManager.getBoard().swapTiles(0,0,0,1);
//        assertEquals(2,boardManager.getBoard().getTile(0,0).getId());
//        assertEquals(1,boardManager.getBoard().getTile(0,1).getId());
//    }

//    /**
//     * Test whether swapping the last two tiles works.
//     */
//    @Test
//    public void testSwapLastTwo() {
//        setUpCorrect();
//        assertEquals(15,boardManager.getBoard().getTile(3,2).getId());
//        assertEquals(16,boardManager.getBoard().getTile(3,3).getId());
//        boardManager.getBoard().swapTiles(3,3,3,2);
//        assertEquals(16,boardManager.getBoard().getTile(3,2).getId());
//        assertEquals(15,boardManager.getBoard().getTile(3,3).getId());
//    }
//
    /**
     * Test whether isValidHelp works.
     */
    @Test
    public void testIsValidTap() {
        setUpCorrect();
        assertTrue(boardManager.isValidTap(11));
        assertFalse(boardManager.isValidTap(15));
        assertFalse(boardManager.isValidTap(10));
    }


//    @Test
//    public void puzzleSolved() {
//    }
//
//    @Test
//    public void isValidTap() {
//    }
//
    @Test
    public void testTouchMove() {
        setUpCorrect();
        //The case that the right position is empty
        boardManager.touchMove(14);
        //The case that the left position is empty
        boardManager.touchMove(15);
        //The case that the down position is empty
        boardManager.touchMove(11);
        //The case that the up position is empty
        boardManager.touchMove(15);
        assertEquals(16,board.getTile(3, 3).getId());

    }

    /**
     * Test the undo function
     */
    @Test
    public void undo() {
        setUpCorrect();
        //The case that the right position is empty
        boardManager.touchMove(14);
        //The case that the left position is empty
        boardManager.touchMove(15);
        //The case that the down position is empty
        boardManager.touchMove(11);
        //The case that the up position is empty
        boardManager.touchMove(15);
        assertEquals(16,board.getTile(3, 3).getId());
    }
//
//
//    @Test
//    public void setLastTime() {
//    }
//
//    @Test
//    public void getTimesOfUndo() {
//    }
//
    /**
     * Test the getScore method
     */
    @Test
    public void testGetScore() {
        setUpCorrect();
        assertEquals(0, boardManager.getScore());
    }
    /**
     * Test the getLastTime method
     */
     @Test
     public void getLastTime() {
         setUpCorrect();
         assertEquals(1, boardManager.getScore());
     }
    /**
     * Test the getCurrentGame method
     */
    @Test
    public void testGetCurrentGame() {
        setUpCorrect();
        assertEquals("st", boardManager.getCurrentGame());
    }
}