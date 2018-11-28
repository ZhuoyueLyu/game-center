package group_0617.csc207.gamecentre.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import group_0617.csc207.gamecentre.R;
import group_0617.csc207.gamecentre.dataBase.DatabaseHelper;

/**
 * The scoreboard activity which is responsible for displaying the scoreboard of current user
 * for all three games.
 */
public class ScoreboardActivity extends Activity {

    DatabaseHelper db;
    String[] listItem;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_user_scoreboard);

        db = new DatabaseHelper(this);
        listItem = new String[3];//3 is the number of game we have now
        StringBuilder slidingTilesData = new StringBuilder();
        StringBuilder twentyFortyEightData = new StringBuilder();
        StringBuilder memoryGameData = new StringBuilder();

        ListView lstview = findViewById(R.id.listview);
        // Inflate header view
        ViewGroup headerView = (ViewGroup) getLayoutInflater().
                inflate(R.layout.header,lstview,false);
        // Add header view to the ListView
        lstview.addHeaderView(headerView);
        buildDataString(slidingTilesData,
                "Sliding Tiles ","steasy","stmedium","sthard");

        //Add the data of 2048 game
        buildDataString(twentyFortyEightData,
                "2048","tfeasy","tfmedium","tfhard");

        //Add the data of Memory game
        buildDataString(memoryGameData,
                "Memory","cardeasy","cardmedium","cardhard");

        listItem[0] = slidingTilesData.toString();
        listItem[1] = twentyFortyEightData.toString();
        listItem[2] = memoryGameData.toString();

        // Bind data to the ListView
        ScoreboardListAdapter adapter = new ScoreboardListAdapter(this,R.layout.rowlayout,R.id.txtUsername,listItem);
        // Bind data to the ListView
        lstview.setAdapter(adapter);
    }

    /**
     * A method which is used to build a string that transfer the data of the specific game
     *
     * @param stringBuilder a string builder which carry the game data
     * @param game          the name of the game
     * @param easy          the string that represent this game under easy mode
     * @param medium        the string that represent this game under medium mode
     * @param hard          the string that represent this game under hard mode
     */
    private void buildDataString(StringBuilder stringBuilder,String game,String easy,String medium,String hard) {
        stringBuilder.append(game).append("__");
        stringBuilder.append(Integer.toString(db.getGameData
                (LoginActivity.currentUser,easy))).append("__");
        //Add the score for 4 by 4 sliding tiles game
        stringBuilder.append(Integer.toString(db.getGameData
                (LoginActivity.currentUser,medium))).append("__");
        //Add the score for 5 by 5 sliding tiles game
        stringBuilder.append(Integer.toString(db.getGameData
                (LoginActivity.currentUser,hard)));
    }

    /**
     * Make a toast message
     *
     * @param message the message that we want to toast
     */
    private void displayToast(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

}
