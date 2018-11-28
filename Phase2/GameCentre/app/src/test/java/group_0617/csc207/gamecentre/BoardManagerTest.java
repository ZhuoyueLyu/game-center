package group_0617.csc207.gamecentre;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import group_0617.csc207.gamecentre.gameSlidingTiles.Board;
import group_0617.csc207.gamecentre.gameSlidingTiles.BoardManager;
import group_0617.csc207.gamecentre.gameSlidingTiles.Tile;

import static org.junit.Assert.*;

public class BoardManagerTest {


    private int complexity = 4;

    /**
     * The board manager for testing.
     */
    private BoardManager boardManager;

    private Board board;

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

    /**
     * Test whether isValidTap works.
     */
    @Test
    public void testIsValidTap() {
        setUpCorrect();
        assertTrue(boardManager.isValidTap(11));
        assertFalse(boardManager.isValidTap(15));
        assertFalse(boardManager.isValidTap(10));
    }

    /**
     * Test the touch move function by applying several moves
     */
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
        assertEquals(16,board.getTile(3,3).getId());

    }

    /**
     * Test the undo function
     */
    @Test
    public void testUndo() {
        setUpCorrect();
        //the case where no move has been made
        assertFalse(boardManager.undo());
        boardManager.touchMove(14);
        boardManager.touchMove(15);
        boardManager.touchMove(11);
        boardManager.touchMove(15);
        boardManager.undo();
        assertEquals(12,board.getTile(3,3).getId());
        boardManager.undo();
        boardManager.undo();
        boardManager.undo();
        //We initialize the times of undo to be 1 (for the sake of testing),
        // and we undo 4 times, so it's 5
        assertEquals(4 + 1,boardManager.getTimesOfUndo());
        assertEquals(16,board.getTile(3,3).getId());
    }

    /**
     * Test the getScore method
     */
    @Test
    public void testGetScore() {
        setUpCorrect();
        assertEquals(0,boardManager.getScore());
    }

    /**
     * Test the getLastTime method
     */
    @Test
    public void getLastTime() {
        setUpCorrect();
        assertEquals(1,boardManager.getLastTime());
    }

    /**
     * Test the getCurrentGame method
     */
    @Test
    public void testGetCurrentGame() {
        setUpCorrect();
        assertEquals("st",boardManager.getCurrentGame());
    }
}