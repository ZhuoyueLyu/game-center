package group_0617.csc207.gamecentre.viewAndController;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ListView;

import group_0617.csc207.gamecentre.R;
import group_0617.csc207.gamecentre.dataBase.DatabaseHelper;

/**
 * The scoreboard activity which is responsible for displaying the scoreboard of current user
 * for all three games.
 */
public class ScoreboardActivity extends Activity {
    /**
     * Database helper
     */
    DatabaseHelper db;
    /**
     * the string[] that contains game data
     */
    String[] listItem;
    /**
     * ScoreboardActivityController
     */
    ScoreboardActivityController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new ScoreboardActivityController();
        setContentView(R.layout.avtivity_user_scoreboard);

        db = new DatabaseHelper(this);
        listItem = new String[3];//3 is the number of game we have now
        StringBuilder slidingTilesData = new StringBuilder();
        StringBuilder twentyFortyEightData = new StringBuilder();
        StringBuilder memoryGameData = new StringBuilder();

        ListView listview = findViewById(R.id.listview);
        // Inflate header view
        ViewGroup headerView = (ViewGroup) getLayoutInflater().
                inflate(R.layout.header,listview,false);
        // Add header view to the ListView
        listview.addHeaderView(headerView);
        controller.AddGameData(slidingTilesData,twentyFortyEightData,memoryGameData,db,this);
        // Bind data to the ListView
        ScoreboardListAdapter adapter = new ScoreboardListAdapter(this,R.layout.rowlayout,R.id.txtUsername,listItem);
        // Bind data to the ListView
        listview.setAdapter(adapter);
    }

}
