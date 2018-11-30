package group_0617.csc207.gamecentre.game2048;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import group_0617.csc207.gamecentre.activities.CustomAdapter;
import group_0617.csc207.gamecentre.activities.GestureDetectGridView;
import group_0617.csc207.gamecentre.activities.LoginActivity;
import group_0617.csc207.gamecentre.R;
import group_0617.csc207.gamecentre.gameSlidingTiles.StartingActivity;

/**
 * The game activity
 */
public class Game2048Activity extends AppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    private BoardManager2048 boardManager2048;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * Constants for swiping directions. Should be an enum, probably.
     */
    public static final int UP = 101;
    public static final int DOWN = 102;
    public static final int LEFT = 103;
    public static final int RIGHT = 104;

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * The timer stuffs for the game.
     */
    private Timer timer = new Timer("GameActivityTimer");
    public int counts = 0;
    private TimerTask timerTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile(StartingActivity2048.TEMP_SAVE_FILENAME);
        createTileButtons(this);
        setContentView(R.layout.activity_2048);
        addUndoButtonListener();

        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(boardManager2048.getBoard().getComplexity());
        gridView.setAbleToFling(true);
        gridView.setGenericBoardManager(boardManager2048);
        boardManager2048.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / boardManager2048.getBoard().getComplexity();
                        columnHeight = displayHeight / boardManager2048.getBoard().getComplexity();

                        display();
                    }
                });
    }

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
        TextView realScore = findViewById(R.id.RealScore);
        realScore.setText("Score: " + boardManager2048.getScore());
    }

    /**
     * Start the game timer at startValue.
     *
     * @param startValue the start value to set the counts of timer
     */
    private void startTimer(int startValue) {
        counts = startValue;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        counts++;
                        TextView score = findViewById(R.id.Score);
                        score.setText("Time: " + counts + " s");
                        boardManager2048.setLastTime(counts);
                        saveToFile("save_file_" +
                                boardManager2048.getBoard().getComplexity() + "_" + LoginActivity.currentUser);
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, new Date(), 1000);
    }

    /**
     * Stop the timer and return counts (the stop time).
     *
     * @return the stop time counts.
     */
    private int stopTimer() {
        timerTask.cancel();
        timerTask = null;
        return counts;
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
        boardManager2048.setLastTime(stopTimer());
    }

    /**
     * Dispatch onResumee() to fragments.
     */
    @Override
    protected void onResume() {
        super.onResume();
        startTimer(boardManager2048.getLastTime());
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        Board2048 board2048 = (Board2048) boardManager2048.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != boardManager2048.getBoard().getComplexity(); row++) {
            for (int col = 0; col != boardManager2048.getBoard().getComplexity(); col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board2048.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Board2048 board2048 = (Board2048) boardManager2048.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / boardManager2048.getBoard().getComplexity();
            int col = nextPos % boardManager2048.getBoard().getComplexity();
            b.setBackgroundResource(board2048.getTile(row, col).getBackground());
            nextPos++;
        }
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
                boardManager2048 = (BoardManager2048) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
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
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(boardManager2048);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Activate the Undo button.
     */
    private void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.btnundo);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean undoAvailable = boardManager2048.undo();
                if (undoAvailable) {
                    displayToast("Undo successfully! You have made " + boardManager2048.getTimesOfUndo() + " times of undo.");
                } else {
                    displayToast("Undo failed! This is the original board.");
                }
            }
        });
    }

    /**
     * Display that a toast of undo.
     */
    private void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
    }


}
