package group_0617.csc207.gamecentre.activities;

import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getTargetContext;

import android.content.SharedPreferences;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


import group_0617.csc207.gamecentre.dataBase.DatabaseHelper;
import group_0617.csc207.gamecentre.dataBase.Tuple;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;

public class ResultBoardActivityControllerTest {
    private ResultBoardActivity resultBoardActivity;
    private DatabaseHelper db;


    /**
     * make sure that we can write data from database properly
     */
    @Test
    public void testWriteData() {
        ResultBoardActivityController controller = new ResultBoardActivityController();
        System.setProperty("dexmaker.dexcache",getTargetContext().getCacheDir().toString());

        resultBoardActivity = new ResultBoardActivity();
        DatabaseHelper db = mock(DatabaseHelper.class);
        doNothing().when(db).addGameData(LoginActivity.currentUser,"st" + "medium",100);
        doNothing().when(db).addGameData(LoginActivity.currentUser,"st" + "hard",200);
        doNothing().when(db).addGameData(LoginActivity.currentUser,"st" + "easy",20);
        resultBoardActivity.db = db;
        //We will get the data for the game under hard and easy mode respectively
        controller.writeData(100,"st",4,db);
        controller.writeData(200,"st",5,db);
        controller.writeData(20,"st",3,db);

        verify(db,times(1)).addGameData(LoginActivity.currentUser,"st" + "medium",100);
        verify(db,times(1)).addGameData(LoginActivity.currentUser,"st" + "hard",200);
        verify(db,times(1)).addGameData(LoginActivity.currentUser,"st" + "easy",20);
    }
    @Test
    public void testCompareTheScore() {
        ResultBoardActivityController controller = new ResultBoardActivityController();
        controller = new ResultBoardActivityController();
        System.setProperty("dexmaker.dexcache",getTargetContext().getCacheDir().toString());

        TextView highScoreLabel = mock(TextView.class);
        SharedPreferences settings = mock(SharedPreferences.class);
        SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);
        doReturn(editor).when(settings).edit();

        when(editor.putInt("HIGH_SCORE",10)).thenReturn(editor);
        when(editor.putInt("HIGH_SCORE",30)).thenReturn(editor);
        doNothing().when(editor).apply();

        controller.compareGameScore(highScoreLabel,10,settings,30);
        controller.compareGameScore(highScoreLabel,10,settings,5);



        verify(highScoreLabel,times(1)).setText("High Score: " + 30);
        verify(highScoreLabel,times(1)).setText("High Score: " + 10);

    }

    }
