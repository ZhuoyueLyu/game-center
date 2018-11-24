package group_0617.csc207.gamecentre;


import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class DatabaseHelperInstrumentedTest {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    @Before
    public void setUp(){
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

    @Test
    public void onCreate() {
    }

    @Test
    public void onUpgrade() {
    }

    /**
     * Test whether we have added a user and password combination successfully or not
     */
    @Test
    public void testAddUserSuccessfully() {
        databaseHelper.addUser("testName", "testPassword");
        assertTrue(databaseHelper.getUser("testName", "testPassword"));
        assertFalse(databaseHelper.getUser("testName", "wrongPassword"));
        assertFalse(databaseHelper.getUser("wrongName", "testPassword"));
    }

    /**sS
     * Test whether the user name exist or not
     */

    @Test
    public void checkUserExist() {
        assertTrue(databaseHelper.checkUserExist("testName"));
        assertFalse(databaseHelper.checkUserExist("pineApple"));
    }


    @Test
    public void addSTdata() {
    }

    @Test
    public void getSTdata() {
    }

    @Test
    public void getSTLeaderboardData() {
    }






//
//
//
//    @Test
//    public void testShouldAddExpenseType() throws Exception {
//        databaseHelper.createRate("AUD", 1.2);
//        List<Rate> rate = databaseHelper.getAllRates();
//
//        assertThat(rate.size(), is(1));
//        assertTrue(rate.get(0).toString().equals("AUD"));
//        assertTrue(rate.get(0).getValue().equals(1.2));
//    }
//
//    @Test
//    public void testDeleteAll() {
//        databaseHelper.deleteAll();
//        List<Rate> rate = databaseHelper.getAllRates();
//
//        assertThat(rate.size(), is(0));
//    }
//
//    @Test
//    public void testDeleteOnlyOne() {
//        databaseHelper.createRate("AUD", 1.2);
//        List<Rate> rate = databaseHelper.getAllRates();
//
//        assertThat(rate.size(), is(1));
//
//        databaseHelper.deleteRate(rate.get(0));
//        rate = databaseHelper.getAllRates();
//
//        assertThat(rate.size(), is(0));
//    }
//
//    @Test
//    public void testAddAndDelete() {
//        databaseHelper.deleteAll();
//        databaseHelper.createRate("AUD", 1.2);
//        databaseHelper.createRate("JPY", 1.993);
//        databaseHelper.createRate("BGN", 1.66);
//
//        List<Rate> rate = databaseHelper.getAllRates();
//        assertThat(rate.size(), is(3));
//
//        databaseHelper.deleteRate(rate.get(0));
//        databaseHelper.deleteRate(rate.get(1));
//
//        rate = databaseHelper.getAllRates();
//        assertThat(rate.size(), is(1));
//    }
}





//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
//public class DatabaseHelperTest {
//
//    @Test
//    public void onCreate() {
//    }
//
//    @Test
//    public void onUpgrade() {
//    }
//
//    @Test
//    public void addUser() {
//    }
//
//    @Test
//    public void getUser() {
//    }
//
//    @Test
//    public void checkUserExist() {
//    }
//
//    @Test
//    public void addSTdata() {
//    }
//
//    @Test
//    public void getSTdata() {
//    }
//
//    @Test
//    public void getSTLeaderboardData() {
//    }
//}
