package group_0617.csc207.gamecentre.gameMemory;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
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
public class StartingActivityOfCard extends GenericStartingActivity {

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
                complexity.setText("Easy (2x2)");
                break;

            case 4:
                complexity.setText("Medium (4x4)");
                break;
            case 5:
                complexity.setText("Hard (6x6)");
                break;
            default:
                complexity.setText("Medium (4x4)");

        }
    }


    /**
     * Switch to the GameActivity view to play the game.
     */
    public void switchToGame() {
        Intent tmp = new Intent(this, CardGameActivity.class);
        tmp.putExtra("tempSaveFileName", getTempSaveFileName());
        tmp.putExtra("complexity",(getCurrentComplexity() - 2) * 2);
        saveToFile(getTempSaveFileName());
        startActivity(tmp);
    }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    public boolean loadFromFile(String fileName) {
        boolean re = true;
        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                CardBoardManager cardBoardManager = (CardBoardManager) input.readObject();
                if (cardBoardManager != null) {
                    setGenericBoardManager(cardBoardManager);
                } else {
                    re = false;
                }
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("Card Starting activity","File not found: " + e.toString());
            Toast.makeText(this,"File not found",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("Card Starting activity","Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("Card Stating activity","File contained unexpected data type: " + e.toString());
        }
        return re;
    }

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName , MODE_PRIVATE));
            outputStream.writeObject(getGenericBoardManager());
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception","File write failed: " + e.toString());
        }
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
                        new Intent(StartingActivityOfCard.this,LeaderboardActivity.class);
                gameScoreboardScreen.putExtra("currentGame","card");
                gameScoreboardScreen.putExtra("complexity",getCurrentComplexity());
                startActivity(gameScoreboardScreen);
            }
        });
    }
}
