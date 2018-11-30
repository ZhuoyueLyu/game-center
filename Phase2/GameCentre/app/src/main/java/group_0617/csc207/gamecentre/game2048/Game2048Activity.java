package group_0617.csc207.gamecentre.game2048;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Observer;

import group_0617.csc207.gamecentre.GenericGameActivity;
import group_0617.csc207.gamecentre.R;

/**
 * The game activity
 */
public class Game2048Activity extends GenericGameActivity implements Observer {

    /**
     * Constants for swiping directions. Should be an enum, probably.
     */
    public static final int UP = 101;
    public static final int DOWN = 102;
    public static final int LEFT = 103;
    public static final int RIGHT = 104;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_2048);
        super.onCreate(savedInstanceState);
        getGridView().setAbleToFling(true);
        addUndoButtonListener();
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
        };
    }

    @Override
     public void loadFromFile(String fileName) {
        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                setGenericBoardManager((BoardManager2048) input.readObject());
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


}
