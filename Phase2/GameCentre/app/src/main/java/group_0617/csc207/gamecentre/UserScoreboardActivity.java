package group_0617.csc207.gamecentre;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class UserScoreboardActivity extends Activity{

    DatabaseHelper db;
    String[] listItem;
    ArrayAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_user_scoreboard);

        db = new DatabaseHelper(this);
        listItem = new String[3];//3 is the number of game we have now
        StringBuilder stData = new StringBuilder();
        StringBuilder other1Data = new StringBuilder();
        StringBuilder other2Data = new StringBuilder();

        ListView lstview=(ListView)findViewById(R.id.listview);
        // Inflate header view
        ViewGroup headerView = (ViewGroup)getLayoutInflater().inflate(R.layout.header, lstview,false);
        // Add header view to the ListView
        lstview.addHeaderView(headerView);
        // Get the string array defined in strings.xml file
        // Create an adapter to bind data to the ListView
        //Add the data of SlidingTiles game
            //Add the score for 3 by 3 sliding tiles game
            stData.append("Sliding Tiles " + "__");
        stData.append(Integer.toString(db.getSTdata(LoginActivity.currentUser, "easy"))+"__");
        //Add the score for 4 by 4 sliding tiles game
        stData.append(Integer.toString(db.getSTdata(LoginActivity.currentUser, "medium"))+"__");
        //Add the score for 5 by 5 sliding tiles game
        stData.append(Integer.toString(db.getSTdata(LoginActivity.currentUser, "hard")));

        //Add the data of other1 game
        other1Data.append("Other Game 1" + "__");
        //Add the score for easy
        other1Data.append(Integer.toString(0)+"__");
        //Add the score for medium
        other1Data.append(Integer.toString(0)+"__");
        //Add the score for hard
        other1Data.append(Integer.toString(0));

        //Add the data of other2 game
        other2Data.append("Other Game 2" + "__");
        //Add the score for easy
        other2Data.append(Integer.toString(0)+"__");
        //Add the score for medium
        other2Data.append(Integer.toString(0)+"__");
        //Add the score for hard
        other2Data.append(Integer.toString(0));

        listItem[0] = stData.toString();
        listItem[1] = other1Data.toString();
        listItem[2] = other2Data.toString();

//        LstAdapter adapter=new LstAdapter(this,R.layout.rowlayout,R.id.txtUsername,items);
        // Bind data to the ListView
        LstAdapter adapter=new LstAdapter(this,R.layout.rowlayout,R.id.txtUsername,listItem);
        // Bind data to the ListView
        lstview.setAdapter(adapter);
    }
    private void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}
