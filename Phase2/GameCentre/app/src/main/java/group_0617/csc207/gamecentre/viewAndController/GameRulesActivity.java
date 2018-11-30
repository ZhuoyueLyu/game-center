package group_0617.csc207.gamecentre.viewAndController;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import group_0617.csc207.gamecentre.R;

/**
 * Show the game rule
 * Excluded from tests because it's a view class.
 */
public class GameRulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_rules);
    }

}