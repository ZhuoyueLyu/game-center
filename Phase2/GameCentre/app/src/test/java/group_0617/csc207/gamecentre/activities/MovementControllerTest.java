package group_0617.csc207.gamecentre.activities;

import android.content.Context;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import group_0617.csc207.gamecentre.gameSlidingTiles.Board;
import group_0617.csc207.gamecentre.gameSlidingTiles.BoardManager;
import group_0617.csc207.gamecentre.gameSlidingTiles.Tile;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class MovementControllerTest {

//    Context context = mock(Context.class);

    private int complexity = 4;

    /**
     * The board manager for testing.
     */
    private BoardManager boardManager;

    private Board board;

    private MovementController controller;

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
        controller = new MovementController();
//        controller.setGenericBoardManager(boardManager);
    }


    /**
     * Test the setGenericBoardManager() function
     */
    @Test
    public void testSetGenericBoardManager() {
        setUpCorrect();
        controller.setGenericBoardManager(boardManager);
        assertEquals(controller.getGenericBoardManager(), boardManager);
    }

    /**
     * Make sure that the tap movement function is okay
     */
    @Test
    public void processTapMovement() {
        setUpCorrect();
        Context context = mock(Context.class);
        //The case where the tap is not valid
        controller.processTapMovement(context, 10);
        assertEquals(16,board.getTile(3,3).getId());
        //The case that the right position is empty
        controller.processTapMovement(context, 14);
        assertEquals(15,board.getTile(3,3).getId());
        //The case that the left position is empty (and win)
        controller.processTapMovement(context, 15);


    }
}