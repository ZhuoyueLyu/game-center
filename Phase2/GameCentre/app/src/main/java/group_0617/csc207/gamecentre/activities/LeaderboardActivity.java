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
 */
public class LeaderboardActivity extends Activity {

    DatabaseHelper db;
    String[] listItem;
    List<Tuple<String, Integer>> leaderBoardData;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_game_leaderboard);
        String currentGame = getIntent().getStringExtra("currentGame");
        int gameComplexity = getIntent().getIntExtra("complexity",4);

        db = new DatabaseHelper(this);
        //Retrieve the game data from database (per game/per difficulty)
        switch (gameComplexity) {
            case 3:
                leaderBoardData = db.getLeaderboardData(currentGame + "easy");
                break;
            case 4:
                leaderBoardData = db.getLeaderboardData(currentGame + "medium");
                break;
            case 5:
                leaderBoardData = db.getLeaderboardData(currentGame + "hard");
                break;
            default:
                break;
        }

        ListView listView = (ListView) findViewById(R.id.listview);
        // Inflate header view
        ViewGroup headerView = (ViewGroup) getLayoutInflater().
                inflate(R.layout.header_leaderboard,listView,false);
        // Add header view to the ListView
        listView.addHeaderView(headerView);
        //Rank the score of a given game (descending)
        RankTheScore();
        LeaderboardListViewAdapter adapter = new LeaderboardListViewAdapter
                (this,R.layout.rowlayout_leaderboard,R.id.txtLeaderRank,listItem);
        // Bind data to the ListView
        listView.setAdapter(adapter);

    }

    /**
     * Rank the score of a given game (descending)
     */
    private void RankTheScore() {
        int n = leaderBoardData.size();
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (leaderBoardData.get(j - 1).getY() < leaderBoardData.get(j).getY()) {
                    String tmp_x = leaderBoardData.get(j - 1).getX();
                    int tmp_y = leaderBoardData.get(j - 1).getY();
                    leaderBoardData.get(j - 1).setX(leaderBoardData.get(j).getX());
                    leaderBoardData.get(j - 1).setY(leaderBoardData.get(j).getY());
                    leaderBoardData.get(j).setX(tmp_x);
                    leaderBoardData.get(j).setY(tmp_y);
                }
            }
        }
        int len = leaderBoardData.size();
        listItem = new String[Math.min(10,len)];
        for (int i = 0; i < 10; i++) {
            if (i <= len - 1) {
                listItem[i] = (Integer.toString(i + 1) + "__" + leaderBoardData.get(i).getX() + "__" +
                        leaderBoardData.get(i).getY());
            }
        }
    }


}
