package group_0617.csc207.gamecentre;


import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class DatabaseHelperInstrumentedTest {

    private DatabaseHelper databaseHelper;
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

//    @Test
//    public void onCreate() {
//    }
//
//    @Test
//    public void onUpgrade() {
//    }

    /**
     * Test whether we have added a user and password combination successfully or not
     */
    @Test
    public void testAddAndGetUserInformation() {
        databaseHelper.addUser("testName","testPassword");
        databaseHelper.addUser("testName2","testPassword2");
        assertTrue(databaseHelper.getUser("testName","testPassword"));
        assertFalse(databaseHelper.getUser("testName","wrongPassword"));
        assertFalse(databaseHelper.getUser("wrongName","testPassword"));
    }

    /**
     * Test whether the user name exist or not
     */

    @Test
    public void testUserExistance() {
        assertTrue(databaseHelper.checkUserExist("testName"));
        assertTrue(databaseHelper.checkUserExist("testName2"));
        assertFalse(databaseHelper.checkUserExist("pineApple"));
    }

    /**
     * Test whether we can add and get the data for slidingTiles successfully or not
     */
    @Test
    public void testAddAndGetSTdata() {
        databaseHelper.addSTdata("testName","easy",123);
        databaseHelper.addSTdata("testName","medium",454);
        databaseHelper.addSTdata("testName","hard",290);
        assertThat(databaseHelper.getSTdata("testName","easy"),is(123));
        assertThat(databaseHelper.getSTdata("testName","medium"),is(454));
        assertThat(databaseHelper.getSTdata("testName","hard"),not(0));
        //For a newly created user2, the score should be set to 0 by default.
        assertThat(databaseHelper.getSTdata("testName2","hard"),is(0));


    }


    @Test
    public void getSTLeaderboardData() {
    }

}


