package group_0617.csc207.gamecentre;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.ArrayList;

/**
 * initialize database
 * We learned how to write code of database from online tutorial:
 * https://www.youtube.com/watch?v=aQAIMY-HzL8&t=245s
 * And we added the methods and fields we want as follows
 * Date of Retrieval: November 3, 2018
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    /**
     * We tried to avoid using static variable here,
     * but it seems that the SQlite can only use static final variable?
     */
    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private static final String DB_NAME = "gameCentre.db";
    private static final int DB_VERSION = 2;

    private static final String USER_TABLE = "users";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASS = "password";
    // Score of slidingtiles under easy, medium, hard mode text
    private static final String COLUMN_STEASY = "steasy";
    private static final String COLUMN_STMEDIUM = "stmedium";
    private static final String COLUMN_STHARD = "sthard";

    /**
     * create table users(
     * id integer primary key autoincrement,
     * username text,
     * password text
     * steasy integer
     * stmedium integer
     * sthard integer
     * );
     */
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + USER_TABLE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USERNAME + " TEXT,"
            + COLUMN_PASS + " TEXT,"
            + COLUMN_STEASY + " INTEGER,"
            + COLUMN_STMEDIUM + " INTEGER,"
            + COLUMN_STHARD + " INTEGER);";

    DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);
    }

    /**
     * Storing user details in database
     */
    void addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASS, password);
        values.put(COLUMN_STEASY, 0);
        values.put(COLUMN_STMEDIUM, 0);
        values.put(COLUMN_STHARD, 0);

        long id = db.insert(USER_TABLE, null, values);
        db.close();

        Log.d(TAG, "user inserted" + id);
    }

    /**
     * This method is used to check whether a given username and passward combination exist or not
     *
     * @param username
     * @param password
     * @return boolean, whether this user exist or not
     */
    boolean getUser(String username, String password) {
        String selectQuery = "select * from  " + USER_TABLE + " where " +
                COLUMN_USERNAME + " = " + "'" + username + "'" + " and " + COLUMN_PASS + " = " + "'" + password + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            return true;
        }
        cursor.close();
        db.close();

        return false;
    }

    /**
     * This method is used to check whether a given username exist or not
     *
     * @param username
     * @return boolean
     */
    boolean checkUserExist(String username) {
        //HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "select * from  " + USER_TABLE + " where " +
                COLUMN_USERNAME + " = " + "'" + username + "'";
//        SQLiteDatabase db = this.getReadableDatabase();
//        //cursor is used to read from the database
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        // Move to first row
//        cursor.moveToFirst();
//        if (cursor.getCount() > 0) {
//            return true;
//        }
//        cursor.close();
//        db.close();

        return true;
    }

    /**
     * This method is used to update the score of Sliding tiles game under different difficulties
     *
     * @param username
     * @param difficulty
     * @param score
     */
    void addSTdata(String username, String difficulty, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String where = COLUMN_USERNAME + " = " + "'" + username + "'";
        switch (difficulty) {
            case "easy":
                values.put(COLUMN_STEASY, Math.max(score, getSTdata(username, difficulty)));
                break;
            case "medium":
                values.put(COLUMN_STMEDIUM, Math.max(score, getSTdata(username, difficulty)));
                break;
            case "hard":
                values.put(COLUMN_STHARD, Math.max(score, getSTdata(username, difficulty)));
                break;
            default:
        }
        db.update(USER_TABLE, values, where, null);
        db.close();


    }


    /**
     * This method is used to return the sore of a given user under give game difficulty
     *
     * @param username
     * @param stdifficulty
     * @return sore
     */
    int getSTdata(String username, String stdifficulty) {

        String selectQuery = "select * from  " + USER_TABLE + " where " +
                COLUMN_USERNAME + " = " + "'" + username + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        //cursor is used to read from the database
        Cursor cursor = db.rawQuery(selectQuery, null);
        int result = 0;
        int stIndex = 0;
        if (cursor.moveToFirst()) {
            switch (stdifficulty) {
                case "easy":
                    stIndex = cursor.getColumnIndex(COLUMN_STEASY);
                    result = Integer.valueOf(cursor.getString(stIndex));
                    break;
                case "medium":
                    stIndex = cursor.getColumnIndex(COLUMN_STMEDIUM);
                    result = Integer.valueOf(cursor.getString(stIndex));
                    break;
                case "hard":
                    stIndex = cursor.getColumnIndex(COLUMN_STHARD);
                    result = Integer.valueOf(cursor.getString(stIndex));
                    break;
                default:
            }
        }
        cursor.close();
        return result;
    }

    /**
     * This method is used to get the game score of Sliding tiles for different users under the same
     * level of difficulty
     *
     * @param stdifficulty
     * @return an arraylist of tuple which can be used to re-arrange and generate the leaderboard
     */
    ArrayList<Tuple<String, Integer>> getSTLeaderboardData(String stdifficulty) {

        ArrayList<Tuple<String, Integer>> leaderBoardData = new ArrayList<Tuple<String, Integer>>();
        String selectQuery = "select * from  " + USER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        int stIndex = cursor.getColumnIndex("st" + stdifficulty);

        if (cursor.moveToFirst()) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                // The Cursor is now set to the right position
                leaderBoardData.add(new Tuple(cursor.getString(1),
                        Integer.valueOf(cursor.getString(stIndex))));
            }
        }
        cursor.close();
        return leaderBoardData;
    }

}
