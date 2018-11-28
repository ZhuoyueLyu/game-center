package group_0617.csc207.gamecentre.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import group_0617.csc207.gamecentre.R;
import group_0617.csc207.gamecentre.dataBase.DatabaseHelper;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_board);
        db = new DatabaseHelper(this);
        TextView scoreLabel = findViewById(R.id.scoreLabel);
        TextView highScoreLabel = findViewById(R.id.highScoreLabel);

        int score = getIntent().getIntExtra("SCORE",0);
        String currentGame = getIntent().getStringExtra("currentGame");
        int gameComplexity = getIntent().getIntExtra("complexity",4);

        scoreLabel.setText(score + "");
        SharedPreferences settings = getSharedPreferences("GAME_DATA",Context.MODE_PRIVATE);
        int highScore = settings.getInt("HIGH_SCORE",0);

        //Display the score
        if (score > highScore) {
            highScoreLabel.setText("High Score: " + score);

            // Save
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGH_SCORE",score);
            editor.commit();
        } else {
            highScoreLabel.setText("High Score: " + highScore);
        }

        //Write the data into the Database
        writeData(score,currentGame,gameComplexity);
    }

    /**
     * Write the data into the Database
     * @param score the score that the user earned in this game
     * @param currentGame the name of the current game
     * @param gameComplexity the complexity of the current game 3(easy), 4(medium), 5(hard)
     */
    private void writeData(int score,String currentGame,int gameComplexity) {
        switch (gameComplexity) {
            case 3:
                db.addGameData(LoginActivity.currentUser,currentGame+"easy",score);
                break;
            case 4:
                db.addGameData(LoginActivity.currentUser,currentGame+"medium",score);
                break;
            case 5:
                db.addGameData(LoginActivity.currentUser,currentGame+"hard",score);
                break;
            default:
                break;

        }
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
