package group_0617.csc207.gamecentre;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import group_0617.csc207.gamecentre.activities.CustomAdapter;
import group_0617.csc207.gamecentre.activities.GestureDetectGridView;
import group_0617.csc207.gamecentre.activities.LoginActivity;

/**
 * A generic game activity
 */
public abstract class GenericGameActivity extends AppCompatActivity implements Observer {

    /**
     * The board manager of this game activity
     */
    private GenericBoardManager genericBoardManager;

    /**
     * The singleton that save and load board manager
     */
    private GenericBoardManagerSaveLoader saveLoader = GenericBoardManagerSaveLoader.getInstance();

    /**
     * The file path to save to
     */
    private String saveFileName;

    /**
     * The temporary path to get initial board manager from
     */
    private String tempSaveFileName;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * Grid View that calculated column height and width based on device size
     */
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * The timer stuffs for the game.
     */
    private Timer timer = new Timer("GameActivityTimer");
    private int counts = 0;
    private TimerTask timerTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile(tempSaveFileName);
        createTileButtons(this);

        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(genericBoardManager.getBoard().getComplexity());
        gridView.setGenericBoardManager(genericBoardManager);
        genericBoardManager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / genericBoardManager.getBoard().getComplexity();
                        columnHeight = displayHeight / genericBoardManager.getBoard().getComplexity();

                        display();
                    }
                });
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
    }

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    private void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
        TextView realScore = findViewById(R.id.RealScore);
        realScore.setText("Score: " + genericBoardManager.getScore());
        //TODO: save stuff.
        saveToFile("save_file_" +
                genericBoardManager.getBoard().getComplexity() + "_" + LoginActivity.currentUser);
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
                        genericBoardManager.setLastTime(counts);
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
        saveToFile(saveFileName);
        genericBoardManager.setLastTime(stopTimer());
    }

    /**
     * Dispatch onResume() to fragments.
     */
    @Override
    protected void onResume() {
        super.onResume();
        startTimer(genericBoardManager.getLastTime());
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    public abstract void createTileButtons(Context context);

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    public abstract void updateTileButtons();

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    private void saveToFile(String fileName) {
        saveLoader.saveGenericBoardManager(genericBoardManager,
                fileName, this);
    }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {
        genericBoardManager = saveLoader.loadGenericBoardManager(fileName, this);
    }

    /**
     * Activate the Undo button.
     */
    public void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.btnundo);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean undoAvailable = getGenericBoardManager().undo();
                if (undoAvailable) {
                    Toast.makeText(getApplicationContext(), "Undo successfully! You have made "
                            + getGenericBoardManager().getTimesOfUndo() +
                            " times of undo.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Undo failed! This is the original board.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public GestureDetectGridView getGridView() {
        return gridView;
    }

    public GenericBoardManager getGenericBoardManager() {
        return genericBoardManager;
    }

    protected void setGenericBoardManager(GenericBoardManager genericBoardManager) {
        this.genericBoardManager = genericBoardManager;
    }

    public ArrayList<Button> getTileButtons() {
        return tileButtons;
    }

    public void setTileButtons(ArrayList<Button> tileButtons) {
        this.tileButtons = tileButtons;
    }

    protected void setSaveFileName(String saveFileName) {
        this.saveFileName = saveFileName;
    }

    protected void setTempSaveFileName(String tempSaveFileName) {
        this.tempSaveFileName = tempSaveFileName;
    }
}
