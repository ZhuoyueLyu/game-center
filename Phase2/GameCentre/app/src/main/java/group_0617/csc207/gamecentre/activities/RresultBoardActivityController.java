package group_0617.csc207.gamecentre.activities;

import android.content.SharedPreferences;
import android.widget.TextView;

import group_0617.csc207.gamecentre.dataBase.DatabaseHelper;

public class RresultBoardActivityController {
    /**
     * Write the data into the Database
     * @param score the score that the user earned in this game
     * @param currentGame the name of the current game
     * @param gameComplexity the complexity of the current game 3(easy), 4(medium), 5(hard)
     * @param db
     */
    void writeData(int score,String currentGame,int gameComplexity,DatabaseHelper db) {
        switch (gameComplexity) {
            case 3:
                db.addGameData(LoginActivity.currentUser,currentGame+"easy",score);
                break;
            case 4:
                db.addGameData(LoginActivity.currentUser,currentGame+"medium",score);
                break;
            case 5:
                db.addGameData(LoginActivity.currentUser,currentGame+"hard",score);
                break;
            default:
                break;
        }
    }
    /**
     * Comparing the score and show the highest score
     * @param highScoreLabel highScoreLabel
     * @param score score that the user get
     * @param settings sharedPreferences setting
     * @param highScore highest score
     */
    void compareGameScore(TextView highScoreLabel,int score,SharedPreferences settings,int highScore) {
        //Display the score
        if (score > highScore) {
            highScoreLabel.setText("High Score: " + score);

            // Save
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGH_SCORE",score);
            editor.apply();
        } else {
            highScoreLabel.setText("High Score: " + highScore);
        }
    }
}
