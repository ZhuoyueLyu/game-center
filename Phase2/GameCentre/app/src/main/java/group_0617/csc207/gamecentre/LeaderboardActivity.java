package group_0617.csc207.gamecentre;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class LeaderboardActivity extends Activity {

    DatabaseHelper db;
    String[] listItem;
    ArrayList<Tuple<String, Integer>> leaderBoardData;
    public static boolean DESC = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_game_leaderboard);

        db = new DatabaseHelper(this);
        leaderBoardData = db.getSTLeaderboardData(GameComplexityActivity.gameComplexity);
        StringBuilder stData = new StringBuilder();
        ListView lstview = (ListView) findViewById(R.id.listview);
        // Inflate header view
        ViewGroup headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.header_leaderboard,lstview,false);
        // Add header view to the ListView
        lstview.addHeaderView(headerView);


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

        LeaderboardListViewAdapter adapter = new LeaderboardListViewAdapter(this,R.layout.rowlayout_leaderboard,R.id.txtLeaderRank,listItem);
        // Bind data to the ListView
        lstview.setAdapter(adapter);

    }

    private void displayToast(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }


}
