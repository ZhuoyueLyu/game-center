package group_0617.csc207.gamecentre.gameMemory;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

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
public class CardGameActivity extends GenericGameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_memory_game);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
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

    @Override
    public void display(){
        super.display();
        TextView realScore = findViewById(R.id.RealScore);
        String text = "Score: " + getGenericBoardManager().getScore();
        realScore.setText(text);
    }
}