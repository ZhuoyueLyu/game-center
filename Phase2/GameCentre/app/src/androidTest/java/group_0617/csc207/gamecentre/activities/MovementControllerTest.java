package group_0617.csc207.gamecentre.activities;

import org.junit.Test;

import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.*;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.widget.Toast;

import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import group_0617.csc207.gamecentre.GenericBoardManager;
import group_0617.csc207.gamecentre.gameSlidingTiles.Board;
import group_0617.csc207.gamecentre.gameSlidingTiles.BoardManager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MovementControllerTest {
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
        Looper.prepare();
        doNothing().when(boardManager).touchMove(validPosition);

        //call the "if" branch
        movementController.processTapMovement(context,validPosition);
        //call the "else" branch
        movementController.processTapMovement(context,invalidPosition);
        verify(toast,times(1));
        Toast.makeText(context,"YOU WIN!",anyInt()).show();
    }


}