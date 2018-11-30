package group_0617.csc207.gamecentre.gameSlidingTiles;

import android.graphics.Bitmap;

import org.junit.Test;

import java.util.List;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameActivityControllerTest {

    @Test
    public void testCutImage() {
        GameActivityController controller = new GameActivityController();
        System.setProperty("dexmaker.dexcache",getTargetContext().getCacheDir().toString());
        Bitmap bitmap = mock(Bitmap.class);
        BoardManager boardManager = mock(BoardManager.class);
        when(bitmap.getWidth()).thenReturn(100);
        when(bitmap.getHeight()).thenReturn(100);
        when(boardManager.getBoard().getComplexity()).thenReturn(4);
        List<Bitmap> result = controller.cutImage(bitmap, boardManager);
        assertEquals(result.get(0).getWidth(),25);
    }

}