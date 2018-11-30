package group_0617.csc207.gamecentre.viewAndController;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import group_0617.csc207.gamecentre.dataBase.DatabaseHelper;
import group_0617.csc207.gamecentre.dataBase.Tuple;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test for LeaderBoardActivityController
 */
public class LeaderboardActivityControllerTest {

    /**
     * The controller to test
     */
    private LeaderboardActivityController controller;

    /**
     * make sure that the score can be ranked in ascending order
     */
    @Test
    public void testRankTheScore() {

        List<Tuple<String, Integer>> leaderBoardData = new ArrayList<Tuple<String, Integer>>();
        String[] listItem = new String[10];
        leaderBoardData.add(new Tuple<>("user1", 100));
        leaderBoardData.add(new Tuple<>("user2", 300));
        leaderBoardData.add(new Tuple<>("user3", 200));
        controller = new LeaderboardActivityController();
        System.setProperty("dexmaker.dexcache", getTargetContext().getCacheDir().toString());
        LeaderboardActivity leaderboardActivity = mock(LeaderboardActivity.class);
        leaderboardActivity.leaderBoardData = leaderBoardData;
        leaderboardActivity.listItem = listItem;

        controller.RankTheScore(leaderboardActivity);
        assertEquals(leaderboardActivity.listItem[0], "1__user2__300");
        assertEquals(leaderboardActivity.listItem[1], "2__user3__200");
        assertEquals(leaderboardActivity.listItem[2], "3__user1__100");

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
        System.setProperty("dexmaker.dexcache", getTargetContext().getCacheDir().toString());

        testGameDataForEasy.add(new Tuple<>("user1", 100));
        testGameDataForEasy.add(new Tuple<>("user2", 300));
        testGameDataForEasy.add(new Tuple<>("user3", 200));

        testGameDataForHard.add(new Tuple<>("user1", 50));
        testGameDataForHard.add(new Tuple<>("user2", 90));
        testGameDataForHard.add(new Tuple<>("user3", 80));

        LeaderboardActivity leaderboardActivity = mock(LeaderboardActivity.class);
        leaderboardActivity.leaderBoardData = leaderBoardData;
        leaderboardActivity.listItem = listItem;

        DatabaseHelper db = mock(DatabaseHelper.class);
        when(db.getLeaderboardData("st" + "easy")).thenReturn(testGameDataForEasy);
        when(db.getLeaderboardData("st" + "hard")).thenReturn(testGameDataForHard);
        leaderboardActivity.db = db;

        //We will get the data for the game under hard and easy mode respectively
        controller.getDataFromDatabase("st", 3, leaderboardActivity);
        assertEquals(leaderboardActivity.leaderBoardData, testGameDataForEasy);
        controller.getDataFromDatabase("st", 4, leaderboardActivity);
        assertThat(leaderboardActivity.leaderBoardData, not(testGameDataForEasy));
        controller.getDataFromDatabase("st", 5, leaderboardActivity);
        controller.getDataFromDatabase("st", 0, leaderboardActivity);

    }
}