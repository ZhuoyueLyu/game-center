package group_0617.csc207.gamecentre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * The initial activity for the login interface.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //initialize buttons and text
    private Button login;
    private Button register;
    private EditText username;
    private EditText password;
    private DatabaseHelper db;
    private Session session;

    public static String currentUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button) findViewById(R.id.btnLogin);
        register = (Button) findViewById(R.id.btnReg);
        username = findViewById(R.id.UsernameText);
        password = findViewById(R.id.PasswordText);
        db = new DatabaseHelper(this);
        session = new Session(this);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    /**
     * Initialize onClick
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                login(v);
                break;
            case R.id.btnReg:
                register(v);
                startActivity(new Intent(this,GameChoiceActivity.class));
                break;
            default:

        }

    }

    /**
     * Initialize login method
     */

    public void login(View view) {
        String usernameString = username.getText().toString();
        String passwordString = password.getText().toString();
        if(!(db.checkUserExist(usernameString))){
            displayToast("User doesn't exist, please register!");
        }else{
            if (db.getUser(usernameString, passwordString)) {
                session.setLoggedin(true);
                displayToast("Login successfully");
                finish();
                startActivity(new Intent(this,GameChoiceActivity.class));
                currentUser = usernameString;
            } else {
                Toast.makeText(getApplicationContext(), "Wrong username/password", Toast.LENGTH_SHORT).show();
            }
        }}

    /**
     * Check the user information and store them into Database
     * @param view the current view
     */
    public void register(View view) {
        String usernameString = username.getText().toString();
        String passwordString = password.getText().toString();


        if (db.checkUserExist(usernameString)) {
            displayToast("Username exist, please login!");

        }
        else {

            if (usernameString.isEmpty() || passwordString.isEmpty()) {
                displayToast("Username/password field empty");
            }else{
                db.addUser(usernameString, passwordString);
                currentUser = usernameString;
                displayToast("Registered successfully, please login");
                finish();}
        }
    }

    /**
     * toasting message
     * @param message the message that we want to send
     */
    private void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

}


