package group_0617.csc207.gamecentre;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class StartingActivity extends AppCompatActivity {

    public static String gameComplexity = "medium";

    /**
     * Complexity of choice
     */
    private int complexity = 4;

    /**
     * counting the times user click the arrow, start with medium which is index 1
     */
    private int positionOfChoice = 1;

    /**
     * The main save file.
     */
    private String save_file_name;
    /**
     * A temporary save file.
     */
    public static String TEMP_SAVE_FILENAME;
    /**
     * The board manager.
     */
    private BoardManager boardManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boardManager = new BoardManager(complexity);
        this.save_file_name = "save_file_" + boardManager.getBoard().getComplexity() + "_" + LoginActivity.currentUser;
        TEMP_SAVE_FILENAME = "save_file_tmp_" + boardManager.getBoard().getComplexity() + "_" + LoginActivity.currentUser;
        saveToFile(TEMP_SAVE_FILENAME);

        setContentView(R.layout.activity_starting_);
        addStartButtonListener();
        addLoadButtonListener();
        addSaveButtonListener();
        addLeaderBoardButtonListener();
        addRulesButtonListener();
        addLeftArrowButtonListener();
        addRightArrowButtonListener();
    }

    /**
     * Activate the left arrow button.
     */

    private void addLeftArrowButtonListener() {


        ImageButton Button = findViewById(R.id.leftArrow);
        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView complexity = (TextView) findViewById(R.id.Complexity);
                if (positionOfChoice - 1 >= 0) {
                    positionOfChoice--;
                    ChooseComplexity(complexity);
                }
            }
        });

    }

    /**
     * Activate the right arrow button.
     */
    private void addRightArrowButtonListener() {

        ImageButton Button = findViewById(R.id.rightArrow);
        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView complexity = (TextView) findViewById(R.id.Complexity);
                if (positionOfChoice + 1 <= 2) {
                    positionOfChoice++;
                    ChooseComplexity(complexity);

                }
            }
        });


    }

/**
 * Choose the complexity of the game
 */
    @SuppressLint("SetTextI18n")
    private void ChooseComplexity(TextView complexity) {
        switch (positionOfChoice){
            case 0:
                boardManager.getBoard().setComplexity(3);
                gameComplexity = "easy";
                this.complexity = 3;
                complexity.setText("Easy (3x3)");break;

            case 1:
                boardManager.getBoard().setComplexity(4);
                gameComplexity = "medium";
                this.complexity = 4;
                complexity.setText("Medium (4x4)");break;
            case 2:
                boardManager.getBoard().setComplexity(5);
                gameComplexity = "hard";
                this.complexity = 5;
                complexity.setText("Hard (5x5)");break;
            default:
                boardManager.getBoard().setComplexity(4);
                gameComplexity = "medium";
                this.complexity = 4;
                complexity.setText("Medium (4x4)");

        }
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = new BoardManager(complexity);
                switchToGame();
            }
        });
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromFile(save_file_name);
                saveToFile(TEMP_SAVE_FILENAME);
                makeToastLoadedText();
                switchToGame();
            }
        });
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this,"Loaded Game",Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile(save_file_name);
                saveToFile(TEMP_SAVE_FILENAME);
                makeToastSavedText();
            }
        });
    }

    /**
     * Activate the Leaderboard button.
     */
    private void addLeaderBoardButtonListener() {
        ImageButton leaderboardButton = findViewById(R.id.leaderboard);
        leaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameScoreboardScreen =
                        new Intent(StartingActivity.this,LeaderboardActivity.class);
                startActivity(gameScoreboardScreen);
            }
        });
    }

    /**
     * Activate the rules button.
     */
    private void addRulesButtonListener() {
        ImageButton Button = findViewById(R.id.gameRulesOfST);
        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameScoreboardScreen =
                        new Intent(StartingActivity.this,GameRulesActivity.class);
                startActivity(gameScoreboardScreen);
            }
        });
    }


    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(this,"Game Saved",Toast.LENGTH_SHORT).show();
    }

    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadFromFile(TEMP_SAVE_FILENAME);
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this,GameActivity.class);
        saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                boardManager = (BoardManager) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity","File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity","Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity","File contained unexpected data type: " + e.toString());
        }
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
            outputStream.writeObject(boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception","File write failed: " + e.toString());
        }
    }

}
