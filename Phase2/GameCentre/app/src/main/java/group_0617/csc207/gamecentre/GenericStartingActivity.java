package group_0617.csc207.gamecentre;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import group_0617.csc207.gamecentre.viewAndController.GameRulesActivity;
import group_0617.csc207.gamecentre.viewAndController.LoginActivity;

/**
 * The initial activity for the sliding puzzle tile game.
 */
abstract public class GenericStartingActivity extends AppCompatActivity {

    /**
     * Complexity of choice
     */
    private int currentComplexity = 4;

    /**
     * The name of the game
     */
    private String gameName;

    /**
     * The main save file.
     */
    private String saveFileName;

    /**
     * The board manager.
     */
    private GenericBoardManager genericBoardManager;

    /**
     * The save loader to save and load board manager.
     */
    private final GenericBoardManagerSaveLoader saveLoader = GenericBoardManagerSaveLoader.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        saveToFile(saveFileName);
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
     * Return the current saveFileName
     *
     * @return the current saveFileName
     */
    protected String getSaveFileName() {
        return saveFileName;
    }

    /**
     * Renew the saving paths based on new configuration
     */
    private void setSavePath() {
        this.saveFileName = "save_file_" + gameName + "_" + currentComplexity
                + "_" + LoginActivity.currentUser;
    }

    /**
     * Set the complexity of the game to start
     *
     * @param currentComplexity the complexity of the game to start
     */
    private void setCurrentComplexity(int currentComplexity) {
        this.currentComplexity = currentComplexity;
        setSavePath();
    }

    /**
     * Set the name of the current game
     *
     * @param gameName the name of the current game
     */
    protected void setGameName(String gameName) {
        this.gameName = gameName;
        setSavePath();
    }

    /**
     * Activate the left arrow button.
     */

    private void addLeftArrowButtonListener() {

        ImageButton Button = findViewById(R.id.leftArrow);
        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView complexity = findViewById(R.id.Complexity);
                if (currentComplexity > 3) {
                    setCurrentComplexity(currentComplexity - 1);
                    showComplexity(complexity);
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
                TextView complexity = findViewById(R.id.Complexity);
                if (currentComplexity < 5) {
                    setCurrentComplexity(currentComplexity + 1);
                    showComplexity(complexity);
                }
            }
        });
    }

    /**
     * Show the complexity of the game
     */
    @SuppressLint("SetTextI18n")
    public void showComplexity(TextView complexity) {
        switch (currentComplexity) {
            case 3:
                complexity.setText("Tiny (3x3)");
                break;
            case 4:
                complexity.setText("Classic (4x4)");
                break;
            case 5:
                complexity.setText("Big (5x5)");
                break;
            default:
                complexity.setText("Classic (4x4)");
        }
    }

    /**
     * Activate the start button.
     */
    protected abstract void addStartButtonListener();

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadFromFile(saveFileName)) {
                    makeToastText("Loaded Game");
                    switchToGame();
                }
            }
        });
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile(saveFileName);
                makeToastText("Game Saved");
            }
        });
    }

    protected abstract void addLeaderBoardButtonListener();

    /**
     * Activate the rules button.
     */
    private void addRulesButtonListener() {
        ImageButton Button = findViewById(R.id.gameRulesOfST);
        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameScoreboardScreen =
                        new Intent(GenericStartingActivity.this, GameRulesActivity.class);
                startActivity(gameScoreboardScreen);
            }
        });
    }


    /**
     * Display message with a toast.
     */
    private void makeToastText(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadFromFile(saveFileName);
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    abstract public void switchToGame();

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    private boolean loadFromFile(String fileName) {
        GenericBoardManager loadedBoardManager =
                saveLoader.loadGenericBoardManager(fileName, this);
        if (loadedBoardManager == null) {
            makeToastText("No Saved Game!");
            return false;
        }
        genericBoardManager = loadedBoardManager;
        return true;
    }

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    protected void saveToFile(String fileName) {
        saveLoader.saveGenericBoardManager(genericBoardManager,
                fileName, this);
    }

    /**
     * Set the board manager
     *
     * @param genericBoardManager the board manager that we want to set this. to
     */
    public void setGenericBoardManager(GenericBoardManager genericBoardManager) {
        this.genericBoardManager = genericBoardManager;
    }

    /**
     * Return the complexity of the current game
     *
     * @return the complexity of the current game
     */
    public int getCurrentComplexity() {
        return currentComplexity;
    }
}
