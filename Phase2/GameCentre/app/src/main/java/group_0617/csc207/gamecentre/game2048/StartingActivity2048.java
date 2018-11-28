package group_0617.csc207.gamecentre.game2048;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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

public class StartingActivity2048 extends GenericStartingActivity {

    /**
     * A temporary save file.
     */
    public static String TEMP_SAVE_FILENAME = "save_file_tmp_" + GameChoiceActivity.currentGame  + "_" + LoginActivity.currentUser;



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setGenericBoardManager(new BoardManager2048(getCurrentComplexity()));
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
                switchToGame();
            }
        });
    }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    public boolean loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                setGenericBoardManager((BoardManager2048) input.readObject());
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity","File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity","Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity","File contained unexpected data type: " + e.toString());
        }

        return true;
    }

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName,MODE_PRIVATE));
            outputStream.writeObject(getGenericBoardManager());
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception","File write failed: " + e.toString());
        }
    }


    /**
     * Switch to the GameActivity view to play the game.
     */
    public void switchToGame() {
        Intent tmp = new Intent(this, Game2048Activity.class);
        saveToFile(StartingActivity2048.TEMP_SAVE_FILENAME);
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
                        new Intent(StartingActivity2048.this,LeaderboardActivity.class);
                gameScoreboardScreen.putExtra("currentGame", "tf");
                gameScoreboardScreen.putExtra("complexity", getCurrentComplexity());
                startActivity(gameScoreboardScreen);
            }
        });
    }
}