package group_0617.csc207.gamecentre;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

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

    /**
     * The name of the game
     */
    private static final String GAME_NAME = "MemoryGame";

    /**
     * Current complexity
     */
    private int complexity = 4;

    /**
     * Update the buttons and set adapter
     */
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(cardButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.cardBoardManager = new CardBoardManager(this.complexity);
        createTileButtons(this);
        setContentView(R.layout.activity_memory_game);

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
        Iterator<Card> iter = ((CardBoard) cardBoard).iterator();

        while (iter.hasNext()) {
            Card curCard = iter.next();
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
        try {

            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(nameToSave, MODE_PRIVATE));
            outputStream.writeObject(cardBoardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
        String nameToSave = GAME_NAME + this.complexity;
        saveToFile(nameToSave);
    }
}