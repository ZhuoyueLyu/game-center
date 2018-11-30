package group_0617.csc207.gamecentre.gameMemory;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import group_0617.csc207.gamecentre.GenericBoardManagerSaveLoader;
import group_0617.csc207.gamecentre.GenericGameActivity;
import group_0617.csc207.gamecentre.GenericBoard;
import group_0617.csc207.gamecentre.activities.GestureDetectGridView;
import group_0617.csc207.gamecentre.R;

/**
 * The Activity in Memory Game
 */
public class CardGameActivity extends GenericGameActivity{

    private int complexity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_memory_game);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        complexity = bundle.getInt("complexity");
        setSaveFileName(bundle.getString("saveFileName"));
        setTempSaveFileName(bundle.getString("tempSaveFileName"));
        super.onCreate(savedInstanceState);
        getGridView().setAbleToFling(false);

    }

    /**
     * Create the buttons for displaying cards
     *
     * @param context the context
     */
    public void createTileButtons(Context context) {
        GenericBoard cardBoard = getGenericBoardManager().getBoard();
        ArrayList<Button> cardButtons = new ArrayList<>();
        for (Card curCard : ((CardBoard) cardBoard)) {
            Button tmp = new Button(context);
            tmp.setBackgroundResource(curCard.getBackground());
            cardButtons.add(tmp);
        }
        setTileButtons(cardButtons);
    }

    /**
     * Update the image of the buttons
     */
    public void updateTileButtons() {
        Iterator<Card> iter = ((CardBoard) getGenericBoardManager().getBoard()).iterator();
        for (Button b : getTileButtons()) {
            Card curCard = iter.next();
            b.setBackgroundResource(curCard.getDisplay());
        }
    }

//    /**
//     * Save the current state of the game
//     */
//    public void saveToFile(String nameToSave) {
//        saveLoader.saveGenericBoardManager(cardBoardManager,
//                nameToSave, this);
//    }

//    /**
//     * Load the board manager from fileName.
//     *
//     * @param fileName the name of the file
//     */
//    public void loadFromFile(String fileName) {
//        setGenericBoardManager((CardBoardManager)
//                saveLoader.loadGenericBoardManager(fileName, this));
//    }
}