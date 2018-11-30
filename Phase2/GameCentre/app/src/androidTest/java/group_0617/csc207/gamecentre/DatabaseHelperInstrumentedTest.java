package group_0617.csc207.gamecentre;


import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import group_0617.csc207.gamecentre.dataBase.DatabaseHelper;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * The unit test for databaseHelper
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseHelperInstrumentedTest {

    /**
     * The databaseHelper to test
     */
    private DatabaseHelper databaseHelper;

    /**
     * The database used
     */
    private SQLiteDatabase db;

    @Before
    public void setUp() {
        databaseHelper = new DatabaseHelper(
                InstrumentationRegistry.getTargetContext());
        db = databaseHelper.getWritableDatabase();
    }

    @After
    public void finish() {
        databaseHelper.close();
    }

    @Test
    public void testPreConditions() {
        assertNotNull(databaseHelper);
    }

    /**
     * Test whether we have added a user and password combination successfully or not
     */
    @Test
    public void testAddAndGetUserInformation() {
        databaseHelper.addUser("testName", "testPassword");
        databaseHelper.addUser("testName2", "testPassword2");
        assertTrue(databaseHelper.getUser("testName", "testPassword"));
        assertFalse(databaseHelper.getUser("testName", "wrongPassword"));
        assertFalse(databaseHelper.getUser("wrongName", "testPassword"));
    }

    /**
     * Test whether the user name exist or not
     */

    @Test
    public void testUserExistence() {
        databaseHelper.addUser("testName", "testPassword");
        databaseHelper.addUser("testName2", "testPassword2");
        assertTrue(databaseHelper.checkUserExist("testName"));
        assertTrue(databaseHelper.checkUserExist("testName2"));
        assertFalse(databaseHelper.checkUserExist("pineApple"));
        assertFalse(databaseHelper.checkUserExist("wrongName"));
    }

    /**
     * Test whether we can add and get the data for slidingTiles successfully or not
     */
    @Test
    public void testAddAndGetGameData() {
        databaseHelper.addUser("testName", "testPassword");
        databaseHelper.addUser("testName2", "testPassword2");
        databaseHelper.addGameData("testName", "steasy", 12300000);
        databaseHelper.addGameData("testName", "tfmedium", 454);
        databaseHelper.addGameData("testName", "sthard", 290);
        assertThat(databaseHelper.getGameData("testName", "steasy"), is(12300000));
        assertThat(databaseHelper.getGameData("testName", "stmedium"), is(0));
        assertThat(databaseHelper.getGameData("testName", "sthard"), is(290));
        assertThat(databaseHelper.getGameData("testName", "tfeasy"), is(0));
        assertThat(databaseHelper.getGameData("testName", "tfmedium"), is(454));
        assertThat(databaseHelper.getGameData("testName", "tfhard"), is(0));
        assertThat(databaseHelper.getGameData("testName", "cardeasy"), is(0));
        assertThat(databaseHelper.getGameData("testName", "cardmedium"), is(0));
        assertThat(databaseHelper.getGameData("testName", "cardhard"), is(0));
        //For a newly created user2, the score should be set to 0 by default.
        assertThat(databaseHelper.getGameData("testName2", "sthard"), is(0));
    }


    @Test
    public void getLeaderboardData() {
        databaseHelper.addUser("testName", "testPassword");
        databaseHelper.addUser("testName2", "testPassword2");
        databaseHelper.addGameData("testName", "steasy", 12300000);
        databaseHelper.addGameData("testName2", "steasy", 300);
        databaseHelper.addGameData("testName2", "tfeasy", 300);
        assertEquals(databaseHelper.getLeaderboardData("steasy").get(0).getY(), (Integer) 12300000);
        assertThat(databaseHelper.getLeaderboardData("steasy"),
                not(databaseHelper.getLeaderboardData("tfeasy")));
    }


    /**
     * This is an override method, actually there is no need for testing,
     * here we just want to touch those method so we achieve higher coverage
     */
    @Test
    public void testOnUpgrade() {
        databaseHelper.onUpgrade(db, 1, 2);
        assertEquals(2, db.getVersion());
    }


}


