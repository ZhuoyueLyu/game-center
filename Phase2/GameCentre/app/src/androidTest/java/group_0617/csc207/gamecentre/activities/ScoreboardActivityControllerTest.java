package group_0617.csc207.gamecentre.activities;

import org.junit.Before;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.*;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


import group_0617.csc207.gamecentre.dataBase.DatabaseHelper;
import group_0617.csc207.gamecentre.dataBase.Tuple;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;

public class ScoreboardActivityControllerTest {
    private ScoreboardActivity scoreboardActivity;
    private String[] listItem;
    private DatabaseHelper db;

    /**
     * make sure that the score can be ranked in ascending order
     */
    @Test
    public void testAddGameData() {

        List<Tuple<String, Integer>> leaderBoardData = new ArrayList<Tuple<String, Integer>>();
        String[] listItem = new String[3];

        ScoreboardActivityController controller = new ScoreboardActivityController();
        System.setProperty("dexmaker.dexcache",getTargetContext().getCacheDir().toString());
        ScoreboardActivity scoreboardActivity = mock(ScoreboardActivity.class);
        scoreboardActivity.listItem = listItem;


        StringBuilder slidingTilesData = new StringBuilder();
        StringBuilder twentyFortyEightData = new StringBuilder();
        StringBuilder memoryGameData = new StringBuilder();

        DatabaseHelper db = mock(DatabaseHelper.class);
        when(db.getGameData(LoginActivity.currentUser,"steasy")).thenReturn(1);
        when(db.getGameData(LoginActivity.currentUser,"stmedium")).thenReturn(2);
        when(db.getGameData(LoginActivity.currentUser,"sthard")).thenReturn(3);
        when(db.getGameData(LoginActivity.currentUser,"tfeasy")).thenReturn(4);
        when(db.getGameData(LoginActivity.currentUser,"tfmedium")).thenReturn(5);
        when(db.getGameData(LoginActivity.currentUser,"tfhard")).thenReturn(6);
        when(db.getGameData(LoginActivity.currentUser,"cardeasy")).thenReturn(7);
        when(db.getGameData(LoginActivity.currentUser,"cardmedium")).thenReturn(8);
        when(db.getGameData(LoginActivity.currentUser,"cardhard")).thenReturn(9);
        scoreboardActivity.db = db;

        controller.AddGameData(slidingTilesData,twentyFortyEightData,memoryGameData,db,scoreboardActivity);
        assertEquals(scoreboardActivity.listItem[0],"Sliding Tiles __1__2__3");
        assertEquals(scoreboardActivity.listItem[1],"2048__4__5__6");
        assertEquals(scoreboardActivity.listItem[2],"Memory__7__8__9");

    }


}