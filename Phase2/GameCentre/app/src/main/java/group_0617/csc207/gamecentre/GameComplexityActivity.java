package group_0617.csc207.gamecentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * The game complexity activity
 */
public class GameComplexityActivity extends AppCompatActivity {
    public static String gameComplexity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_complexity);
        addEasyButtonListener();
        addMediumButtonListener();
        addHardButtonListener();
    }

    /**
     * Activate the easy button.
     */
    private void addEasyButtonListener() {
        Button easyButton = findViewById(R.id.easyButton);
        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Board.NUM_COLS = 3;
                Board.NUM_ROWS = 3;
                switchToStartingActivity();
                gameComplexity = "easy";
            }
        });
    }

    /**
     * Activate the medium button.
     */
    private void addMediumButtonListener() {
        Button mediumButton = findViewById(R.id.mediumButton);
        mediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Board.NUM_COLS = 4;
                Board.NUM_ROWS = 4;
                switchToStartingActivity();
                gameComplexity = "medium";
            }
        });
    }

    /**
     * Activate the hard button.
     */
    private void addHardButtonListener() {
        Button hardButton = findViewById(R.id.hardButton);
        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Board.NUM_COLS = 5;
                Board.NUM_ROWS = 5;
                switchToStartingActivity();
                gameComplexity = "hard";
            }
        });
    }

    /**
     * Switch to starting activity screen to choose how to start the game.
     */
    private void switchToStartingActivity() {
        Intent startingActivityScreen = new Intent(this, StartingActivity.class);
        startActivity(startingActivityScreen);
    }
}
