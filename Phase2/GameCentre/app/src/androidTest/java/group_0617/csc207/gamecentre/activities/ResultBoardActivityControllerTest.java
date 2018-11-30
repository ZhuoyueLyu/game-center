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
        List<Tuple<String, Integer>> leaderBoardData = new ArrayList<Tuple<String, Integer>>();
        List<Tuple<String, Integer>> testGameDataForEasy = new ArrayList<Tuple<String, Integer>>();
        List<Tuple<String, Integer>> testGameDataForHard = new ArrayList<Tuple<String, Integer>>();
        String[] listItem = new String[10];
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
}