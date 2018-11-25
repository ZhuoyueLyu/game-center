package group_0617.csc207.gamecentre;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class StartingActivity2048 extends GenericStartingActivity {

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
    private String SAVE_FILENAME = "save_file_" + GameChoiceActivity.currentGame + "_" +
            complexity + "_" + LoginActivity.currentUser;

    /**
     * A temporary save file.
     */
    public static String TEMP_SAVE_FILENAME = "save_file_tmp_" + GameChoiceActivity.currentGame  + "_" + LoginActivity.currentUser;

    /**
     * The board manager.
     */
    //private BoardManager2048 boardManager2048;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //boardManager2048 = new BoardManager2048();
        setGenericBoardManager(new BoardManager2048());
    }

    /**
     * Choose the complexity of the game
     */
    @SuppressLint("SetTextI18n")
    public void ChooseComplexity(TextView complexity) {
        switch (positionOfChoice){
            case 0:
                getGenericBoardManager().getBoard().setComplexity(3);
                gameComplexity = "easy";
                this.complexity = 3;
                complexity.setText("Easy (3x3)");break;

            case 1:
                getGenericBoardManager().getBoard().setComplexity(4);
                gameComplexity = "medium";
                this.complexity = 4;
                complexity.setText("Medium (4x4)");break;
            case 2:
                getGenericBoardManager().getBoard().setComplexity(5);
                gameComplexity = "hard";
                this.complexity = 5;
                complexity.setText("Hard (5x5)");break;
            default:
                getGenericBoardManager().getBoard().setComplexity(4);
                gameComplexity = "medium";
                this.complexity = 4;
                complexity.setText("Medium (4x4)");

        }
    }

    /**
     * Activate the start button.
     */
    public void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //boardManager2048 = new BoardManager2048();
                setGenericBoardManager(new BoardManager2048());
                switchToGame();
            }
        });
    }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    public void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                //boardManager2048 = (BoardManager2048) input.readObject();
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
            //outputStream.writeObject(boardManager2048);
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
        saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }
}