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

import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import group_0617.csc207.gamecentre.viewAndController.CustomAdapter;
import group_0617.csc207.gamecentre.viewAndController.GestureDetectGridView;

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
    private final GenericBoardManagerSaveLoader saveLoader = GenericBoardManagerSaveLoader.getInstance();

    /**
     * The file path to save to
     */
    private String saveFileName;

    /**
     * The buttons to display.
     */
    private List<Button> tileButtons;

    /**
     * Grid View that calculated column height and width based on device size
     */
    private GestureDetectGridView gridView;

    /**
     * The column height and width
     */
    private static int columnWidth, columnHeight;

    /**
     * The timer of the game.
     */
    private final Timer timer = new Timer("GameActivityTimer");

    /**
     * The counts of seconds passed
     */
    private int counts = 0;

    /**
     * The task of the timer
     */
    private TimerTask timerTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile(saveFileName);
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
        saveToFile(saveFileName);
    }

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
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

    /**
     * Return the grid view of this game activity
     *
     * @return the grid view of this game activity
     */
    public GestureDetectGridView getGridView() {
        return gridView;
    }

    /**
     * Return the generic board manager of this game
     *
     * @return the generic board manager of this game
     */
    public GenericBoardManager getGenericBoardManager() {
        return genericBoardManager;
    }

    /**
     * Return the list of buttons for tiles
     *
     * @return the list of buttons for tiles
     */
    public List<Button> getTileButtons() {
        return tileButtons;
    }

    /**
     * Set the buttons of the tiles
     *
     * @param tileButtons list of the buttons of the tiles
     */
    public void setTileButtons(List<Button> tileButtons) {
        this.tileButtons = tileButtons;
    }

    /**
     * Set the path to save file to
     *
     * @param saveFileName the path to save file to
     */
    protected void setSaveFileName(String saveFileName) {
        this.saveFileName = saveFileName;
    }

}
