package group_0617.csc207.gamecentre;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class MovementController {

    private BoardManager boardManager = null;
    private CardBoardManager cardBoardManager = null;
    private GenericBoardManager genericBoardManager = null;

    public MovementController() {
    }

    public void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    public void setCardBoardManager(CardBoardManager cardBoardManager) {this.cardBoardManager = cardBoardManager;}

    public void setGenericBoardManager(GenericBoardManager inGenericBoardManager) {
        this.genericBoardManager = inGenericBoardManager;
    }

    public void processTapMovement(Context context, int position, boolean display) {
        if (genericBoardManager.isValidTap(position)) {
            genericBoardManager.touchMove(position);
            if (genericBoardManager.puzzleSolved()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
                Intent result = new Intent(context.getApplicationContext(), ResultBoardActivity.class);
                result.putExtra("SCORE", genericBoardManager.getScore());
                context.startActivity(result);
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }

    public void processCardTapMovement(Context context, int position, boolean display) {
        if (cardBoardManager.isValidTap(position)) {
            cardBoardManager.touchMove(position);
            if (cardBoardManager.puzzleSolved()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
                Intent result = new Intent(context.getApplicationContext(), ResultBoardActivity.class);
                result.putExtra("SCORE", cardBoardManager.getScore());
                context.startActivity(result);
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }

    public void processGenericTapMovement(Context context, int position, boolean display) {
        if (genericBoardManager.isValidTap(position)) {
            genericBoardManager.touchMove(position);
            if (genericBoardManager.puzzleSolved()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
                Intent result = new Intent(context.getApplicationContext(), ResultBoardActivity.class);
                result.putExtra("SCORE", genericBoardManager.getScore());
                context.startActivity(result);
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }
}
