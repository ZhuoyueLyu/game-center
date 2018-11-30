package group_0617.csc207.gamecentre.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * initialize database
 * We learned how to write code of database from online tutorial:
 * https://www.youtube.com/watch?v=aQAIMY-HzL8&t=245s
 * And we added the methods and fields we want as follows
 * Date of Retrieval: November 3, 2018
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private static final String DB_NAME = "gameCentre.db";
    private static final int DB_VERSION = 2;

    private static final String USER_TABLE = "users";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASS = "password";
    // Score of SlidingTiles game under easy, medium, hard mode
    private static final String COLUMN_STEASY = "steasy";
    private static final String COLUMN_STMEDIUM = "stmedium";
    private static final String COLUMN_STHARD = "sthard";
    // Score of 2048 game under easy, medium, hard mode
    private static final String COLUMN_TFEASY = "tfeasy";
    private static final String COLUMN_TFMEDIUM = "tfmedium";
    private static final String COLUMN_TFHARD = "tfhard";
    // Score of Memory game under easy, medium, hard mode
    private static final String COLUMN_CARDEASY = "cardeasy";
    private static final String COLUMN_CARDMEDIUM = "cardmedium";
    private static final String COLUMN_CARDHARD = "cardhard";

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
            + COLUMN_STHARD + " INTEGER,"
            + COLUMN_TFEASY + " INTEGER,"
            + COLUMN_TFMEDIUM + " INTEGER,"
            + COLUMN_TFHARD + " INTEGER,"
            + COLUMN_CARDEASY + " INTEGER,"
            + COLUMN_CARDMEDIUM + " INTEGER,"
            + COLUMN_CARDHARD + " INTEGER);";

    public DatabaseHelper(@Nullable Context context) {
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
    public void addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASS, password);
        values.put(COLUMN_STEASY, 0);
        values.put(COLUMN_STMEDIUM, 0);
        values.put(COLUMN_STHARD, 0);
        values.put(COLUMN_TFEASY, 0);
        values.put(COLUMN_TFMEDIUM, 0);
        values.put(COLUMN_TFHARD, 0);
        values.put(COLUMN_CARDEASY, 0);
        values.put(COLUMN_CARDMEDIUM, 0);
        values.put(COLUMN_CARDHARD, 0);

        long id = db.insert(USER_TABLE, null, values);
        db.close();

        Log.d(TAG, "user inserted" + id);
    }

    /**
     * This method is used to check whether a given username and password combination exist or not
     *
     * @param username the username
     * @param password the password
     * @return boolean, whether this user and password combination exist or not
     */
    public boolean getUser(String username, String password) {
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
     * @param username the username
     * @return boolean whether this username exist or not
     */
    public boolean checkUserExist(String username) {
        String selectQuery = "select * from  " + USER_TABLE + " where " +
                COLUMN_USERNAME + " = " + "'" + username + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        //cursor is used to read from the database
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        if (cursor.moveToFirst()) {
            if (cursor.getCount() > 0) {
                return true;
            }
        }
        cursor.close();
        db.close();
        return false;
    }


    /**
     * This method is used to update the score of different games under different difficulties.
     *
     * @param username the name of the user
     * @param column   the name of the column where we want to put the score in
     * @param score    the score of that game
     */
    public void addGameData(String username, String column, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String where = COLUMN_USERNAME + " = " + "'" + username + "'";
        //Compare whether the new score is higher then this user's previous score or not.
        values.put(column, Math.max(score, getGameData(username, column)));
        db.update(USER_TABLE, values, where, null);
        db.close();
    }


    /**
     * This method is used to return the sore of a given user under give game and difficulty
     *
     * @param username the name of the user
     * @param column   the name of the column where we want to put the score in
     * @return the score of a given user under give game and difficulty
     */
    public int getGameData(String username, String column) {

        String selectQuery = "select * from  " + USER_TABLE + " where " +
                COLUMN_USERNAME + " = " + "'" + username + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        //cursor is used to read from the database
        Cursor cursor = db.rawQuery(selectQuery, null);
        int result = 0;
        int index;
        if (cursor.moveToFirst()) {
            index = cursor.getColumnIndex(column);
            result = Integer.valueOf(cursor.getString(index));
        }
        cursor.close();
        return result;
    }

    /**
     * This method is used to get the game score of Sliding tiles for different users under the same
     * level of difficulty
     *
     * @param column the String which represent the column where we want to find the data
     * @return an arraylist of tuple which can be used to re-arrange and generate the leaderboard
     */
    public List<Tuple<String, Integer>> getLeaderboardData(String column) {

        List<Tuple<String, Integer>> leaderBoardData = new ArrayList<>();
        String selectQuery = "select * from  " + USER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int Index = cursor.getColumnIndex(column);

        if (cursor.moveToFirst()) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                // The Cursor is now set to the right position
                String username = cursor.getString(1);
                int score =  Integer.valueOf(cursor.getString(Index));
                leaderBoardData.add(new Tuple<>(username,score));
            }
        }
        cursor.close();
        return leaderBoardData;
    }

}
