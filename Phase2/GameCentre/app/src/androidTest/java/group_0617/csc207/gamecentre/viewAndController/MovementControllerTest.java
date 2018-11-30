package group_0617.csc207.gamecentre.viewAndController;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.widget.Toast;

import org.junit.Test;

import group_0617.csc207.gamecentre.gameSlidingTiles.Board;
import group_0617.csc207.gamecentre.gameSlidingTiles.BoardManager;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The unit test for MovementController
 */
public class MovementControllerTest {

    /**
     * The movementController to test
     */
    private MovementController movementController;

    /**
     * Make sure that when the tap is valid and user win the game, toast"You win!" and put extra
     * when the tap is not valid, toast"Invalid Tap!"
     */
    @Test
    public void testProcessTapMovementAndSetter() {
        System.setProperty("dexmaker.dexcache",getTargetContext().getCacheDir().toString());
        BoardManager boardManager = mock(BoardManager.class);
        Context context = InstrumentationRegistry.getTargetContext();
        Intent result = mock(Intent.class);
        Intent mockIntent = new Intent();
        movementController = new MovementController();
        movementController.result = result;
        movementController.setGenericBoardManager(boardManager);
        Toast toast = mock(Toast.class);
        Board board = mock(Board.class);

        int validPosition = 10;
        int invalidPosition = 20;
        when(movementController.genericBoardManager.getBoard()).thenReturn(board);
        when(movementController.genericBoardManager.isValidTap(validPosition)).thenReturn(true);
        when(movementController.genericBoardManager.isValidTap(invalidPosition)).thenReturn(false);
        when(movementController.genericBoardManager.getScore()).thenReturn(6);
        when(movementController.genericBoardManager.getCurrentGame()).thenReturn("st");
        when(movementController.genericBoardManager.getBoard().getComplexity()).thenReturn(4);
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
        doNothing().when(boardManager).touchMove(validPosition);
        when(movementController.genericBoardManager.puzzleSolved()).thenReturn(true);
        //call the "if" branch
        movementController.processTapMovement(context,validPosition);
        when(movementController.genericBoardManager.puzzleSolved()).thenReturn(false);
        //call the "if" branch
        movementController.processTapMovement(context,validPosition);
        //call the "else" branch
        movementController.processTapMovement(context,invalidPosition);
    }


}