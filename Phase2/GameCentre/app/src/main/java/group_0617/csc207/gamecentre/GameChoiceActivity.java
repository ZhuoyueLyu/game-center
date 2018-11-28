package group_0617.csc207.gamecentre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * The game choice activity
 */
public class GameChoiceActivity extends AppCompatActivity {

    private Session session;

    public static String currentGame = "Slidingtiles";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_choice);
        session = new Session(this);
        if (!session.loggedin()) {
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
        Button slidingtilesButton = findViewById(R.id.slidingtilesButton);
        slidingtilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startingScreen = new Intent(GameChoiceActivity.this, StartingActivity.class);
                currentGame = "Slidingtiles";
                startActivity(startingScreen);
            }
        });
    }

    /**
     * Activate the other game 1 Button.
     */
    private void addOtherGame1ButtonListener() {
        Button otherGame1Button = findViewById(R.id.therGame1Button);
        otherGame1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startingScreen = new Intent(GameChoiceActivity.this, StartingActivity2048.class);
                currentGame = "2048";
                startActivity(startingScreen);
            }
        });
    }

    /**
     * Activate the other game 2 Button.
     */
    private void addOtherGame2ButtonListener() {
        Button otherGame2Button = findViewById(R.id.therGame2Button);
        otherGame2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startingScreen = new Intent(GameChoiceActivity.this, StartingActivityOfCard.class);
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
        session.setLoggedin(false);
        finish();
        startActivity(new Intent(GameChoiceActivity.this, LoginActivity.class));
        LoginActivity.currentUser = "";
    }

    /**
     * Display the given message
     *
     * @param message the given message
     */
    private void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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
