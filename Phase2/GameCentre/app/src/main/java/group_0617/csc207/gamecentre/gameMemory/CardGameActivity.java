package group_0617.csc207.gamecentre.gameMemory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import group_0617.csc207.gamecentre.GenericBoardManager;
import group_0617.csc207.gamecentre.GenericBoardManagerSaveLoader;
import group_0617.csc207.gamecentre.activities.CustomAdapter;
import group_0617.csc207.gamecentre.GenericBoard;
import group_0617.csc207.gamecentre.activities.GestureDetectGridView;
import group_0617.csc207.gamecentre.R;
import group_0617.csc207.gamecentre.activities.LoginActivity;
import group_0617.csc207.gamecentre.gameSlidingTiles.StartingActivity;

/**
 * The Activity in Memory Game
 */
public class CardGameActivity extends AppCompatActivity implements Observer {

    /**
     * The board to manage
     */
    private CardBoardManager cardBoardManager;

    /**
     * The buttons to display
     */
    private ArrayList<Button> cardButtons;

    /**
     * Grid and View that fit device size
     */
    private GestureDetectGridView gridView;
    private int columnHeight, columnWidth;
    private int complexity;

    private String saveFileName;

    private String tempSaveFileName;

    private GenericBoardManagerSaveLoader saveLoader = GenericBoardManagerSaveLoader.getInstance();

    /**
     * The timer stuffs for the game.
     */
    private Timer timer = new Timer("GameActivityTimer");
    public int counts = 0;
    private TimerTask timerTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        complexity = bundle.getInt("complexity");
        tempSaveFileName = bundle.getString("tempSaveFileName");
        saveFileName = bundle.getString("saveFileName");
        super.onCreate(savedInstanceState);
        loadFromFile(tempSaveFileName);
        createTileButtons(this);
        setContentView(R.layout.activity_memory_game);
        cardBoardManager.getBoard().addObserver(this);
        initializeGridView();
    }

    /**
     * Update the buttons and set adapter
     */
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(cardButtons, columnWidth, columnHeight));
        TextView realScore = findViewById(R.id.RealScore);
        realScore.setText("Score: " + cardBoardManager.getScore());
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
                        cardBoardManager.setLastTime(counts);
                        saveToFile(saveFileName);
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
    public int stopTimer() {
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
        cardBoardManager.setLastTime(stopTimer());
    }

    /**
     * Dispatch onResume() to fragments.
     */
    @Override
    protected void onResume() {
        super.onResume();
        startTimer(cardBoardManager.getLastTime());
    }

    /**
     * Initialize grid view of the game
     */
    private void initializeGridView() {
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(cardBoardManager.getBoard().getComplexity());
        gridView.setGenericBoardManager(cardBoardManager);
        cardBoardManager.getBoard().addObserver(this);

        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / cardBoardManager.getBoard().getComplexity();
                        columnHeight = displayHeight / cardBoardManager.getBoard().getComplexity();

                        display();
                    }
                });
    }

    /**
     * Create the buttons for displaying cards
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        GenericBoard cardBoard = cardBoardManager.getBoard();
        this.cardButtons = new ArrayList<>();

        for (Card curCard : ((CardBoard) cardBoard)) {
            Button tmp = new Button(context);
            tmp.setBackgroundResource(curCard.getBackground());
            this.cardButtons.add(tmp);
        }
    }

    /**
     * Update the image of the buttons
     */
    private void updateTileButtons() {
        Iterator<Card> iter = ((CardBoard) cardBoardManager.getBoard()).iterator();
        for (Button b : cardButtons) {
            Card curCard = iter.next();
            b.setBackgroundResource(curCard.getDisplay());
        }
    }

    /**
     * Save the current state of the game
     */
    private void saveToFile(String nameToSave) {
        saveLoader.saveGenericBoardManager(cardBoardManager,
                nameToSave, this);
    }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    public void loadFromFile(String fileName) {
        cardBoardManager = (CardBoardManager)
                saveLoader.loadGenericBoardManager(fileName, this);
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
        saveToFile(saveFileName);
    }
}