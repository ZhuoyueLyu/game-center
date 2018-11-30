package group_0617.csc207.gamecentre.gameSlidingTiles;

import android.graphics.Bitmap;
import java.util.ArrayList;
import java.util.List;

public class GameActivityController {


    /**
     * Cut the picture evenly and return the list of pieces.
     *
     * @param picture a Bitmap picture
     * @param boardManager the boardManager to which bitMap will be sent
     * @return the list of Bitmap pieces.
     */
    List<Bitmap> cutImage(Bitmap picture, BoardManager boardManager) {
        List<Bitmap> newPieces = new ArrayList<>();
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
}
