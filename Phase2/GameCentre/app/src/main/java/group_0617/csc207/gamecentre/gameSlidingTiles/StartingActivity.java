package group_0617.csc207.gamecentre.gameSlidingTiles;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import group_0617.csc207.gamecentre.activities.GameChoiceActivity;
import group_0617.csc207.gamecentre.activities.GenericStartingActivity;
import group_0617.csc207.gamecentre.activities.LeaderboardActivity;
import group_0617.csc207.gamecentre.activities.LoginActivity;
import group_0617.csc207.gamecentre.R;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class StartingActivity extends GenericStartingActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setGenericBoardManager(new BoardManager(getCurrentComplexity()));
        setGameName(BoardManager.GAME_NAME);
        super.onCreate(savedInstanceState);
    }


    /**
     * Activate the start button.
     */
    public void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGenericBoardManager(new BoardManager(getCurrentComplexity()));
                switchToGame();
            }
        });
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    public void switchToGame() {
        Intent tmp = new Intent(this, GameActivity.class);
        tmp.putExtra("tempSaveFileName", getTempSaveFileName());
        tmp.putExtra("saveFileName", getSaveFileName());
        saveToFile(getTempSaveFileName());
        startActivity(tmp);
    }

    /**
     * Activate the Leaderboard button.
     */
    @Override
    protected void addLeaderBoardButtonListener() {
        ImageButton leaderboardButton = findViewById(R.id.leaderboard);
        leaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameScoreboardScreen =
                        new Intent(StartingActivity.this, LeaderboardActivity.class);
                gameScoreboardScreen.putExtra("currentGame", "st");
                gameScoreboardScreen.putExtra("complexity", getCurrentComplexity());
                startActivity(gameScoreboardScreen);
            }
        });
    }
}
