package group_0617.csc207.gamecentre;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ScoreboardActivity extends Activity {

    DatabaseHelper db;
    String[] listItem;
    ArrayAdapter adapter;

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
        //Add the data of SlidingTiles game
        //Add the score for 3 by 3 sliding tiles game
        slidingTilesData.append("Sliding Tiles " + "__");
        slidingTilesData.append(Integer.toString(db.getGameData
                (LoginActivity.currentUser,"steasy"))).append("__");
        //Add the score for 4 by 4 sliding tiles game
        slidingTilesData.append(Integer.toString(db.getGameData
                (LoginActivity.currentUser,"stmedium"))).append("__");
        //Add the score for 5 by 5 sliding tiles game
        slidingTilesData.append(Integer.toString(db.getGameData
                (LoginActivity.currentUser,"sthard")));


        //Add the data of 2048 game
        twentyFortyEightData.append("2048" + "__");
        //Add the score for 2048 game under easy mode
        twentyFortyEightData.append(Integer.toString(db.getGameData
                (LoginActivity.currentUser,"tfeasy"))).append("__");
        //Add the score for 2048 game under medium mode
        twentyFortyEightData.append(Integer.toString(db.getGameData
                (LoginActivity.currentUser,"tfmedium"))).append("__");
        //Add the score for 2048 game under hard mode
        twentyFortyEightData.append(Integer.toString(db.getGameData
                (LoginActivity.currentUser,"tfhard")));

        //Add the data of Memory game
        memoryGameData.append("Memory" + "__");
        //Add the score for easy
        memoryGameData.append(Integer.toString(db.getGameData
                (LoginActivity.currentUser,"cardeasy"))).append("__");
        //Add the score for medium
        memoryGameData.append(Integer.toString(db.getGameData
                (LoginActivity.currentUser,"cardmedium"))).append("__");
        //Add the score for hard
        memoryGameData.append(Integer.toString(db.getGameData
                (LoginActivity.currentUser,"cardhard")));

        listItem[0] = slidingTilesData.toString();
        listItem[1] = twentyFortyEightData.toString();
        listItem[2] = memoryGameData.toString();

        // Bind data to the ListView
        ScoreboardListAdapter adapter = new ScoreboardListAdapter(this,R.layout.rowlayout,R.id.txtUsername,listItem);
        // Bind data to the ListView
        lstview.setAdapter(adapter);
    }

    private void displayToast(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

}
