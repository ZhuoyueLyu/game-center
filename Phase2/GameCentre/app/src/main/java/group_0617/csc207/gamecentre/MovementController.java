package group_0617.csc207.gamecentre;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class MovementController {

    private BoardManager boardManager = null;
    private BoardManager2048 boardManager2048 = null;
    private BoardManagerSlidingtiles boardManagerSlidingtiles = null;

    public MovementController() {
    }

//    public void setBoardManager(BoardManager boardManager) {
//        this.boardManager = boardManager;
//    }

    public void setBoardManager(BoardManagerSlidingtiles boardManagerSlidingtiles) {
        this.boardManagerSlidingtiles = boardManagerSlidingtiles;
    }

    public void setBoardManager2048(BoardManager2048 boardManager2048) {
        this.boardManager2048 = boardManager2048;
    }

//    public void processTapMovement(Context context, int position, boolean display) {
//        if (boardManager.isValidTap(position)) {
//            boardManager.touchMove(position);
//            if (boardManager.puzzleSolved()) {
//                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
//                Intent result = new Intent(context.getApplicationContext(), ResultBoardActivity.class);
//                result.putExtra("SCORE", boardManager.getScore());
//                context.startActivity(result);
//            }
//        } else {
//            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
//        }
//    }

    public void processTapMovement(Context context, int position, boolean display) {
        if (boardManagerSlidingtiles.isValidTap(position)) {
            boardManagerSlidingtiles.touchMove(position);
            if (boardManagerSlidingtiles.puzzleSolved()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
                Intent result = new Intent(context.getApplicationContext(), ResultBoardActivity.class);
                result.putExtra("SCORE", boardManagerSlidingtiles.getScore());
                context.startActivity(result);
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }

//    public void processTapMovement(Context context, int position, boolean display) {
//        if (boardManager2048.isValidTap(position)) {
//            boardManager2048.touchMove(position);
//            if (boardManager2048.puzzleSolved()) {
//                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
//                Intent result = new Intent(context.getApplicationContext(), ResultBoardActivity.class);
//                result.putExtra("SCORE", boardManager2048.getScore());
//                context.startActivity(result);
//            }
//            else if (boardManager2048.isGameOver()){
//                Toast.makeText(context, "GAME OVER!", Toast.LENGTH_SHORT).show();
//                Intent result = new Intent(context.getApplicationContext(), ResultBoardActivity.class);
//                result.putExtra("SCORE", boardManager2048.getScore());
//                context.startActivity(result);
//            }
//        } else {
//            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
//        }
//    }

}
