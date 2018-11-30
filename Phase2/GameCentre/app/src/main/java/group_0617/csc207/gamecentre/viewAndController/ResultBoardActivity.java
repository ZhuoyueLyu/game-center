package group_0617.csc207.gamecentre.viewAndController;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import group_0617.csc207.gamecentre.R;
import group_0617.csc207.gamecentre.dataBase.DatabaseHelper;
import group_0617.csc207.gamecentre.gameMemory.CardBoardManager;
import group_0617.csc207.gamecentre.gameSlidingTiles.StartingActivity;

/**
 * The result board activity
 * <p>
 * A ResultBoard class that monitors and updates the scores and high score.
 * This was copied and adapted from a video from codingwithsara from the website:
 * https://www.youtube.com/watch?v=WYWsVJTmWbY
 * Date of retrieval: November 3, 2018
 */
public class ResultBoardActivity extends AppCompatActivity {

    /**
     * The database to save high score
     */
    DatabaseHelper db;
    /**
     * The ResultBoardActivityController
     */
    ResultBoardActivityController controller;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new ResultBoardActivityController();
        setContentView(R.layout.activity_result_board);
        db = new DatabaseHelper(this);
        TextView scoreLabel = findViewById(R.id.scoreLabel);
        TextView highScoreLabel = findViewById(R.id.highScoreLabel);

        int score = getIntent().getIntExtra("SCORE",0);
        String currentGame = getIntent().getStringExtra("currentGame");
        int gameComplexity;
        if (currentGame.equals(CardBoardManager.GAME_NAME)) {
            gameComplexity = getIntent().getIntExtra("complexity", 4) / 2 + 2;
        } else {
            gameComplexity = getIntent().getIntExtra("complexity", 4);
        }

        scoreLabel.setText(score + "");
        SharedPreferences settings = getSharedPreferences("GAME_DATA",Context.MODE_PRIVATE);
        int highScore = settings.getInt("HIGH_SCORE",0);
        controller.compareGameScore(highScoreLabel,score,settings,highScore);
        //Write the data into the Database
        controller.writeData(score,currentGame,gameComplexity, db);
    }

    /**
     * Active the tryAgain button.
     *
     * @param view the current view
     */
    public void tryAgain(View view) {
        startActivity(new Intent(getApplicationContext(),StartingActivity.class));
    }

    /**
     * Active the backToMain button.
     *
     * @param view the current view
     */
    public void backToMain(View view) {
        startActivity(new Intent(getApplicationContext(),GameChoiceActivity.class));
    }

}
