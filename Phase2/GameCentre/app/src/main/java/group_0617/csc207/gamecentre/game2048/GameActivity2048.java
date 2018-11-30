package group_0617.csc207.gamecentre.game2048;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import group_0617.csc207.gamecentre.GenericGameActivity;
import group_0617.csc207.gamecentre.R;

/**
 * The game activity for 2048.
 */
public class GameActivity2048 extends GenericGameActivity {

    /**
     * Constants for swiping directions.
     */
    public static final int UP = 101;
    public static final int DOWN = 102;
    public static final int LEFT = 103;
    public static final int RIGHT = 104;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_2048);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        setSaveFileName(bundle.getString("saveFileName"));
        super.onCreate(savedInstanceState);
        getGridView().setAbleToFling(true);
        addUndoButtonListener();
    }

    @Override
    public void display() {
        super.display();
        TextView realScore = findViewById(R.id.RealScore);
        String text = "Score: " + getGenericBoardManager().getScore();
        realScore.setText(text);
    }

    @Override
    public void createTileButtons(Context context) {
        Board2048 board2048 = (Board2048) getGenericBoardManager().getBoard();
        ArrayList<Button> buttons = new ArrayList<>();
        for (int row = 0; row != getGenericBoardManager().getBoard().getComplexity(); row++) {
            for (int col = 0; col != getGenericBoardManager().getBoard().getComplexity(); col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board2048.getTile(row, col).getBackground());
                buttons.add(tmp);
            }
        }
        setTileButtons(buttons);
    }

    @Override
    public void updateTileButtons() {
        Board2048 board2048 = (Board2048) getGenericBoardManager().getBoard();
        int nextPos = 0;
        for (Button b : getTileButtons()) {
            int row = nextPos / getGenericBoardManager().getBoard().getComplexity();
            int col = nextPos % getGenericBoardManager().getBoard().getComplexity();
            b.setBackgroundResource(board2048.getTile(row, col).getBackground());
            nextPos++;
        }
    }
}
