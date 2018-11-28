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
    /**
     * A temporary save file.
     */
    public String tempSaveFileName = "save_file_tmp_" + GameChoiceActivity.currentGame + "_" + LoginActivity.currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setGenericBoardManager(new BoardManager(getCurrentComplexity()));
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
        tmp.putExtra("tempSaveFileName", tempSaveFileName);
        saveToFile(tempSaveFileName);
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
                BoardManager boardManager = (BoardManager) input.readObject();
                if (boardManager != null) {
                    setGenericBoardManager((BoardManager) input.readObject());
                } else {
                    re = false;
                }
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
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
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(getGenericBoardManager());
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
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
