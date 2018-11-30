package group_0617.csc207.gamecentre.game2048;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import group_0617.csc207.gamecentre.GenericStartingActivity;
import group_0617.csc207.gamecentre.viewAndController.LeaderboardActivity;
import group_0617.csc207.gamecentre.R;

/**
 * The starting activity of 2048
 */
public class StartingActivity2048 extends GenericStartingActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BoardManager2048 boardManager2048 = new BoardManager2048(getCurrentComplexity());
        setGenericBoardManager(boardManager2048);
        setGameName(boardManager2048.getCurrentGame());
        super.onCreate(savedInstanceState);
        TextView view = findViewById(R.id.GameText);
        view.setText("Welcome To 2048!  \n" +
                "The game's objective is to slide numbered tiles on a grid to combine " +
                "them to create a tile with the number 2048.");
    }

    /**
     * Activate the start button.
     */
    public void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGenericBoardManager(new BoardManager2048(getCurrentComplexity()));
                saveToFile(getSaveFileName());
                switchToGame();
            }
        });
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    public void switchToGame() {
        Intent tmp = new Intent(this, GameActivity2048.class);
        tmp.putExtra("saveFileName", getSaveFileName());
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
                        new Intent(StartingActivity2048.this, LeaderboardActivity.class);
                gameScoreboardScreen.putExtra("currentGame", "tf");
                gameScoreboardScreen.putExtra("complexity", getCurrentComplexity());
                startActivity(gameScoreboardScreen);
            }
        });
    }
}
