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

/**
 * The initial activity for the sliding puzzle tile game.
 */
abstract public class GenericStartingActivity extends AppCompatActivity {

    /**
     * Complexity of choice
     */
    private int currentComplexity = 4;

    /**
     * The main save file.
     */
    private String SAVE_FILENAME = "save_file_" + GameChoiceActivity.currentGame + "_" +
            currentComplexity + "_" + LoginActivity.currentUser;

    /**
     * A temporary save file.
     */
    public static String TEMP_SAVE_FILENAME = "save_file_tmp_" + GameChoiceActivity.currentGame + "_"  + "_" + LoginActivity.currentUser;

    /**
     * The board manager.
     */
    private GenericBoardManager genericBoardManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //boardManager = new BoardManager(complexity);
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
                if (currentComplexity > 3) {
                    currentComplexity--;
                    showComplexity(complexity);
                    setSaveFileName("save_file_" + GameChoiceActivity.currentGame + "_" +
                            currentComplexity + "_" + LoginActivity.currentUser);
                }
            }
        });

    }

    /**
     * Activate the right arrow button.
     *
     */
    private void addRightArrowButtonListener() {

        ImageButton Button = findViewById(R.id.rightArrow);
        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView complexity = (TextView) findViewById(R.id.Complexity);
                if (currentComplexity < 5) {
                    currentComplexity++;
                    showComplexity(complexity);
                    setSaveFileName("save_file_" + GameChoiceActivity.currentGame + "_" +
                            currentComplexity + "_" + LoginActivity.currentUser);
                }
            }
        });
    }

    /**
     * Show the complexity of the game
     */
    @SuppressLint("SetTextI18n")
    public void showComplexity(TextView complexity){
        switch (currentComplexity){
            case 3:
                complexity.setText("Easy (3x3)");
                break;

            case 4:
                complexity.setText("Medium (4x4)");
                break;
            case 5:
                complexity.setText("Hard (5x5)");
                break;
            default:
                complexity.setText("Medium (4x4)");

        }
    }

    /**
     * Activate the start button.
     */
    abstract void addStartButtonListener();

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadFromFile(SAVE_FILENAME)) {
                    saveToFile(TEMP_SAVE_FILENAME);
                    makeToastLoadedText();
                    switchToGame();
                }
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
                saveToFile(SAVE_FILENAME);
                saveToFile(TEMP_SAVE_FILENAME);
                makeToastSavedText();
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
                        new Intent(GenericStartingActivity.this,GameRulesActivity.class);
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
    abstract public void switchToGame();

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    abstract public boolean loadFromFile(String fileName) ;

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    abstract public void saveToFile(String fileName);

    /**
     * Return this board manager
     * @return this board manager
     */
    public GenericBoardManager getGenericBoardManager() {
        return genericBoardManager;
    }

    /**
     * Set the board manager
     * @param genericBoardManager the board manager that we want to set this. to
     */
    public void setGenericBoardManager(GenericBoardManager genericBoardManager) {
        this.genericBoardManager = genericBoardManager;
    }

    /**
     * Return the complexity of the current game
     * @return the complexity of the current game
     */
    public int getCurrentComplexity() {
        return currentComplexity;
    }

    /**
     * Set the name of the save_file
     * @param SAVE_FILENAME the name of save_file
     */
    public void setSaveFileName(String SAVE_FILENAME) {
        this.SAVE_FILENAME = SAVE_FILENAME;
    }
}
