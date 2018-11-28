package group_0617.csc207.gamecentre.dataBase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * The class that switch/check the mode between login and log out
 */
public class Session {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public Session(Context ctx) {
        prefs = ctx.getSharedPreferences("myapp",Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    /**
     * Set the mode to be log in
     *
     * @param isLoggedIn whether we've logged in or not
     */
    public void setLoggedin(boolean isLoggedIn) {
        editor.putBoolean("loggedInmode", isLoggedIn);
        editor.commit();
    }

    /**
     * Check the mode to be log in
     *
     * @return whether we've logged in or not
     */
    public boolean loggedin() {
        return prefs.getBoolean("loggedInmode",false);
    }
}