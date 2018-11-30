package group_0617.csc207.gamecentre.gameSlidingTiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import java.util.List;

import group_0617.csc207.gamecentre.R;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test for GameActivityController
 */
public class GameActivityControllerTest {

    /**
     * Test if cutImage works
     */
    @Test
    public void testCutImage() {
        GameActivityController controller = new GameActivityController();
        Context context = InstrumentationRegistry.getTargetContext();
        System.setProperty("dexmaker.dexcache", getTargetContext().getCacheDir().toString());
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile2048_0);
        BoardManager boardManager = mock(BoardManager.class);
        Board board = mock(Board.class);
        when(boardManager.getBoard()).thenReturn(board);
        when(board.getComplexity()).thenReturn(4);
        List<Bitmap> result = controller.cutImage(bitmap, boardManager);
        assertEquals(result.get(0).getWidth(), bitmap.getWidth() / 4);
    }

}