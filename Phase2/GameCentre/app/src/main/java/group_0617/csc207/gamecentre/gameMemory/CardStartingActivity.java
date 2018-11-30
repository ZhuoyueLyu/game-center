package group_0617.csc207.gamecentre.gameMemory;

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
 * The initial activity for the sliding puzzle tile game.
 */
public class CardStartingActivity extends GenericStartingActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setGenericBoardManager(new CardBoardManager((getCurrentComplexity() - 2) * 2));
        setGameName(CardBoardManager.GAME_NAME);
        super.onCreate(savedInstanceState);
        TextView view = findViewById(R.id.GameText);
        String greeting = "Welcome To Memory Game!  \n " +
                "Where you must identify the two cards that have the same face " +
                "Game is finished when all cards are identified.";
        view.setText(greeting);
    }

    /**
     * Activate the start button.
     */
    public void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGenericBoardManager(new CardBoardManager((getCurrentComplexity() - 2) * 2));
                saveToFile(getSaveFileName());
                switchToGame();
            }
        });
    }


    /**
     * Show the complexity of the game
     */
    @Override
    @SuppressLint("SetTextI18n")
    public void showComplexity(TextView complexity) {
        switch (getCurrentComplexity()) {
            case 3:
                complexity.setText("Tiny (2x2)");
                break;

            case 4:
                complexity.setText("Classic (4x4)");
                break;
            case 5:
                complexity.setText("Big (6x6)");
                break;
            default:
                complexity.setText("Classic (4x4)");

        }
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    public void switchToGame() {
        Intent tmp = new Intent(this, CardGameActivity.class);
        tmp.putExtra("saveFileName", getSaveFileName());
        startActivity(tmp);
    }

    /**
     * Activate the LeaderBoard button.
     */
    @Override
    protected void addLeaderBoardButtonListener() {
        ImageButton leaderBoardButton = findViewById(R.id.leaderboard);
        leaderBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameScoreboardScreen =
                        new Intent(CardStartingActivity.this, LeaderboardActivity.class);
                gameScoreboardScreen.putExtra("currentGame", "card");
                gameScoreboardScreen.putExtra("complexity", getCurrentComplexity());
                startActivity(gameScoreboardScreen);
            }
        });
    }
}
