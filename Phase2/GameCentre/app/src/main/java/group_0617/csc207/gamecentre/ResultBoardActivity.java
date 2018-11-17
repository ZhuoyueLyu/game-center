package group_0617.csc207.gamecentre;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
        TextView scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        TextView highScoreLabel = (TextView) findViewById(R.id.highScoreLabel);

        int score = getIntent().getIntExtra("SCORE", 0);
        scoreLabel.setText(score + "");

        SharedPreferences settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        int highScore = settings.getInt("HIGH_SCORE", 0);

        if (score > highScore) {
            highScoreLabel.setText("High Score: " + score);

            // Save
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGH_SCORE", score);
            editor.commit();
        } else {
            highScoreLabel.setText("High Score: " + highScore);
        }

        db.addSTdata(LoginActivity.currentUser, StartingActivity.gameComplexity, score);
    }

    /**
     * Active the tryAgain button.
     *
     * @param view the current view
     */
    public void tryAgain(View view) {
        startActivity(new Intent(getApplicationContext(), StartingActivity.class));
    }

    /**
     * Active the backToMain button.
     *
     * @param view the current view
     */
    public void backToMain(View view) {
        startActivity(new Intent(getApplicationContext(), GameChoiceActivity.class));
    }
}
