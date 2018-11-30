package group_0617.csc207.gamecentre.viewAndController;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import group_0617.csc207.gamecentre.R;
import group_0617.csc207.gamecentre.dataBase.Session;
import group_0617.csc207.gamecentre.game2048.StartingActivity2048;
import group_0617.csc207.gamecentre.gameMemory.CardStartingActivity;
import group_0617.csc207.gamecentre.gameSlidingTiles.StartingActivity;

/**
 * The game choice activity
 * Excluded from tests because it's a view class.
 */
public class GameChoiceActivity extends AppCompatActivity {

    /**
     * The session related to database
     */
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_choice);

        session = new Session(this);
        if (!session.loggedIn()) {
            logout();
        }

        addLogoutButtonListener();
        addSlidingtilesButtonListener();
        addOtherGame1ButtonListener();
        addOtherGame2ButtonListener();
        addscoreBoardButtonListener();
        addruleButtonListener();
    }

    /**
     * Activate the Slidingtiles Button.
     */
    private void addSlidingtilesButtonListener() {
        Button slidingtilesButton = findViewById(R.id.slidingTilesButton);
        slidingtilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startingScreen = new Intent(GameChoiceActivity.this, StartingActivity.class);
                startActivity(startingScreen);
            }
        });
    }

    /**
     * Activate the other game 1 Button.
     */
    private void addOtherGame1ButtonListener() {
        Button otherGame1Button = findViewById(R.id.the2048Button);
        otherGame1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startingScreen = new Intent(GameChoiceActivity.this, StartingActivity2048.class);
                startActivity(startingScreen);
            }
        });
    }

    /**
     * Activate the other game 2 Button.
     */
    private void addOtherGame2ButtonListener() {
        Button otherGame2Button = findViewById(R.id.theMemoryGameButton);
        otherGame2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startingScreen = new Intent(GameChoiceActivity.this, CardStartingActivity.class);
                startActivity(startingScreen);
            }
        });
    }

    /**
     * Activate the Logout Button.
     */
    private void addLogoutButtonListener() {
        Button btnLogout = findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });


    }

    /**
     * Activate the rules Button.
     */
    private void addruleButtonListener() {
        ImageButton ruleButton = findViewById(R.id.ruleButton);
        ruleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userScoreboardScreen =
                        new Intent(GameChoiceActivity.this, GameRulesActivity.class);
                startActivity(userScoreboardScreen);
            }
        });


    }

    /**
     * Activate the scoreBoard Button.
     */
    private void addscoreBoardButtonListener() {
        ImageButton btnscoreBoard = findViewById(R.id.btnscoreboard);
        btnscoreBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userScoreboardScreen =
                        new Intent(GameChoiceActivity.this, ScoreboardActivity.class);
                startActivity(userScoreboardScreen);
            }
        });


    }

    /**
     * Activate the logout button
     */

    private void logout() {
        session.setLoggedIn(false);
        finish();
        startActivity(new Intent(GameChoiceActivity.this, LoginActivity.class));
        LoginActivity.currentUser = "";
    }

    /**
     * Override the back key
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
