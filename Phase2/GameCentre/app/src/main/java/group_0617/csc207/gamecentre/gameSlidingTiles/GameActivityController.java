package group_0617.csc207.gamecentre.gameSlidingTiles;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.TimerTask;

import group_0617.csc207.gamecentre.R;
import group_0617.csc207.gamecentre.activities.GameChoiceActivity;
import group_0617.csc207.gamecentre.activities.LoginActivity;

public class GameActivityController {

    /**
     * Cut the picture evenly and return the list of pieces.
     *
     * @param picture a Bitmap picture
     * @return the list of Bitmap pieces.
     */
    List<Bitmap> cutImage(Bitmap picture, BoardManager boardManager) {
        List<Bitmap> newPieces = new ArrayList<Bitmap>();
        int w = picture.getWidth();
        int h = picture.getHeight();
        int boxWidth = w / boardManager.getBoard().getComplexity();
        int boxHeight = h / boardManager.getBoard().getComplexity();
        for (int i = 0; i < boardManager.getBoard().getComplexity(); i++) {
            for (int j = 0; j < boardManager.getBoard().getComplexity(); j++) {
                Bitmap pictureFragment = Bitmap.createBitmap(picture, j * boxWidth, i * boxHeight, boxWidth, boxHeight);
                newPieces.add(pictureFragment);
            }
        }
        return newPieces;
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     * @param gameActivity the gameActivity
     */
    void updateTileButtons(GameActivity gameActivity) {
        Board board = (Board) gameActivity.boardManager.getBoard();
        int nextPos = 0;
        for (Button b : gameActivity.tileButtons) {
            int row = nextPos / gameActivity.boardManager.getBoard().getComplexity();
            int col = nextPos % gameActivity.boardManager.getBoard().getComplexity();
            Tile curTile = board.getTile(row, col);
            if (gameActivity.bitmapList == null) {
                b.setBackgroundResource(curTile.getBackground());
            } else if (curTile.getId() != board.getComplexity() * board.getComplexity()) {
                BitmapDrawable d = new BitmapDrawable(gameActivity.getResources(), gameActivity.bitmapList.get(curTile.getId()));
                b.setBackground(d);
            } else {
                b.setBackgroundResource(R.drawable.tile_grey);
            }
            nextPos++;
        }
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     * @param gameActivity
     */
    void createTileButtons(Context context,GameActivity gameActivity) {
        Board board = (Board) gameActivity.boardManager.getBoard();
        gameActivity.tileButtons = new ArrayList<>();
        for (int row = 0; row != gameActivity.boardManager.getBoard().getComplexity(); row++) {
            for (int col = 0; col != gameActivity.boardManager.getBoard().getComplexity(); col++) {
                Button tmp = new Button(context);
                Tile curTile = board.getTile(row, col);
                if (gameActivity.bitmapList == null) {
                    tmp.setBackgroundResource(curTile.getBackground());
                } else if (curTile.getId() != board.getComplexity() * board.getComplexity()) {
                    BitmapDrawable d = new BitmapDrawable(gameActivity.getResources(), gameActivity.bitmapList.get(curTile.getId()));
                    tmp.setBackground(d);
                } else {
                    tmp.setBackgroundResource(R.drawable.tile_grey);
                }
                gameActivity.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Start the game timer at startValue.
     *  @param startValue the start value to set the counts of timer
     * @param gameActivity
     */
    void startTimer(int startValue,final GameActivity gameActivity) {
        gameActivity.counts = startValue;
        gameActivity.timerTask = new TimerTask() {
            @Override
            public void run() {
                gameActivity.runOnUiThread(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        gameActivity.counts++;
                        TextView score = (TextView) appCompatActivity.findViewById(R.id.Score);
                        score.setText("Time: " + gameActivity.counts + " s");
                        gameActivity.boardManager.setLastTime(gameActivity.counts);
                        gameActivity.saveLoader.saveGenericBoardManager(gameActivity.boardManager,"save_file_" +
                                GameChoiceActivity.currentGame + "_" +
                                gameActivity.boardManager.getBoard().getComplexity() + "_" +
                                LoginActivity.currentUser,contextWrapper.getApplicationContext() );
                    }
                });
            }
        };
        gameActivity.timer.scheduleAtFixedRate(gameActivity.timerTask, new Date(), 1000);
    }
}
