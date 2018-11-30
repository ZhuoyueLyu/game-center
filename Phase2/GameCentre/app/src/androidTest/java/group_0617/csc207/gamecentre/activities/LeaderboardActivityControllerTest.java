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

import group_0617.csc207.gamecentre.dataBase.DatabaseHelper;
import group_0617.csc207.gamecentre.dataBase.Tuple;
import group_0617.csc207.gamecentre.gameSlidingTiles.Board;
import group_0617.csc207.gamecentre.gameSlidingTiles.BoardManager;
import group_0617.csc207.gamecentre.gameSlidingTiles.Tile;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;

public class LeaderboardActivityControllerTest {
    private LeaderboardActivityController controller;
    private List<Tuple<String, Integer>> leaderBoardData;
    private String[] listItem;
    private DatabaseHelper db;

    /**
     * make sure that the score can be ranked in ascending order
     */
    @Test
    public void testRankTheScore() {

        List<Tuple<String, Integer>> leaderBoardData = new ArrayList<Tuple<String, Integer>>();
        String[] listItem = new String[10];
        leaderBoardData.add(new Tuple<>("user1",100));
        leaderBoardData.add(new Tuple<>("user2",300));
        leaderBoardData.add(new Tuple<>("user3",200));
        controller = new LeaderboardActivityController();
        System.setProperty("dexmaker.dexcache",getTargetContext().getCacheDir().toString());
        LeaderboardActivity leaderboardActivity = mock(LeaderboardActivity.class);
        leaderboardActivity.leaderBoardData = leaderBoardData;
        leaderboardActivity.listItem = listItem;

        controller.RankTheScore(leaderboardActivity);
        assertEquals(leaderboardActivity.listItem[0],"1__user2__300");
        assertEquals(leaderboardActivity.listItem[1],"2__user3__200");
        assertEquals(leaderboardActivity.listItem[2],"3__user1__100");

    }

    /**
     * make sure that we can get data from database properly
     */
    @Test
    public void testGetDataFromDatabase() {
        List<Tuple<String, Integer>> leaderBoardData = new ArrayList<Tuple<String, Integer>>();
        List<Tuple<String, Integer>> testGameDataForEasy = new ArrayList<Tuple<String, Integer>>();
        List<Tuple<String, Integer>> testGameDataForHard = new ArrayList<Tuple<String, Integer>>();
        String[] listItem = new String[10];
        controller = new LeaderboardActivityController();
        System.setProperty("dexmaker.dexcache",getTargetContext().getCacheDir().toString());

        testGameDataForEasy.add(new Tuple<>("user1",100));
        testGameDataForEasy.add(new Tuple<>("user2",300));
        testGameDataForEasy.add(new Tuple<>("user3",200));

        testGameDataForHard.add(new Tuple<>("user1",50));
        testGameDataForHard.add(new Tuple<>("user2",90));
        testGameDataForHard.add(new Tuple<>("user3",80));

        LeaderboardActivity leaderboardActivity = mock(LeaderboardActivity.class);
        leaderboardActivity.leaderBoardData = leaderBoardData;
        leaderboardActivity.listItem = listItem;

//        DatabaseHelper db = mock(DatabaseHelper.class);
//        when(db.getLeaderboardData("st" + "easy")).thenReturn(testGameDataForEasy);
//        when(db.getLeaderboardData("st" + "hard")).thenReturn(testGameDataForHard);

        DatabaseHelper db = mock(DatabaseHelper.class);
        when(db.getLeaderboardData("st" + "easy")).thenReturn(testGameDataForEasy);
        when(db.getLeaderboardData("st" + "hard")).thenReturn(testGameDataForHard);
        leaderboardActivity.db = db;

        //We will get the data for the game under hard and easy mode respectively
        controller.getDataFromDatabase("st",3,leaderboardActivity);
        assertEquals(leaderboardActivity.leaderBoardData,testGameDataForEasy);
        controller.getDataFromDatabase("st",5,leaderboardActivity);
        assertEquals(leaderboardActivity.leaderBoardData,testGameDataForHard);

    }
}