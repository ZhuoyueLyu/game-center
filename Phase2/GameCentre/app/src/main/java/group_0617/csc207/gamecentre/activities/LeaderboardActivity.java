package group_0617.csc207.gamecentre.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import group_0617.csc207.gamecentre.R;
import group_0617.csc207.gamecentre.dataBase.DatabaseHelper;
import group_0617.csc207.gamecentre.dataBase.Tuple;

/**
The activity which is responsible for displaying the leaderBoard
 Excluded from tests because it's a view class.
 */
public class LeaderboardActivity extends Activity {

    DatabaseHelper db;
    String[] listItem;
    List<Tuple<String, Integer>> leaderBoardData;
    LeaderboardActivityController controller;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_game_leaderboard);
        String currentGame = getIntent().getStringExtra("currentGame");
        int gameComplexity = getIntent().getIntExtra("complexity",4);

        db = new DatabaseHelper(this);
        //Retrieve the game data from database (per game/per difficulty)
        controller.getDataFromDatabase(currentGame,gameComplexity,this);

        ListView listView = (ListView) findViewById(R.id.listview);
        // Inflate header view
        ViewGroup headerView = (ViewGroup) getLayoutInflater().
                inflate(R.layout.header_leaderboard,listView,false);
        // Add header view to the ListView
        listView.addHeaderView(headerView);
        //Rank the score of a given game (descending)
        controller.RankTheScore(this);
        LeaderboardListViewAdapter adapter = new LeaderboardListViewAdapter
                (this,R.layout.rowlayout_leaderboard,R.id.txtLeaderRank,listItem);
        // Bind data to the ListView
        listView.setAdapter(adapter);

    }


}
