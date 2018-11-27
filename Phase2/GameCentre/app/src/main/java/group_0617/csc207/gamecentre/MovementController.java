package group_0617.csc207.gamecentre;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class MovementController {

    private GenericBoardManager genericBoardManager = null;

    public MovementController() {
    }

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
                result.putExtra("currentGame", genericBoardManager.getCurrentGame());
                result.putExtra("complexity", genericBoardManager.getBoard().getComplexity());
                context.startActivity(result);
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }
}
