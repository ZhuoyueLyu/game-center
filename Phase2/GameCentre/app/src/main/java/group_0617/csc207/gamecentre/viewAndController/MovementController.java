package group_0617.csc207.gamecentre.viewAndController;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import group_0617.csc207.gamecentre.GenericBoardManager;

/**
 * The controller which controller the movement of tiles
 */
class MovementController {

    /**
     * The boardManager for current game.
     */
    GenericBoardManager genericBoardManager = null;

    /**
     * The result to go to
     */
    Intent result;

    /**
     * A constructor for MovementController.
     */
    MovementController() {
    }

    /**
     * Process the tapping in a specific context
     *
     * @param context  the current context
     * @param position the position of the tapping
     */
    void processTapMovement(Context context, int position) {
        if (genericBoardManager.isValidTap(position)) {
            genericBoardManager.touchMove(position);
            if (genericBoardManager.puzzleSolved()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
                result = new Intent(context.getApplicationContext(), ResultBoardActivity.class);
                result.putExtra("SCORE", genericBoardManager.getScore());
                result.putExtra("currentGame", genericBoardManager.getCurrentGame());
                result.putExtra("complexity", genericBoardManager.getBoard().getComplexity());
                context.startActivity(result);
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Set the board manager to be a specific one
     *
     * @param inGenericBoardManager a specific boardManager (2048/card/sliding tiles)
     */
    void setGenericBoardManager(GenericBoardManager inGenericBoardManager) {
        this.genericBoardManager = inGenericBoardManager;
    }
}
