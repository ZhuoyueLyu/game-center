package group_0617.csc207.gamecentre.gameSlidingTiles;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import group_0617.csc207.gamecentre.GenericBoardManagerSaveLoader;
import group_0617.csc207.gamecentre.activities.CustomAdapter;
import group_0617.csc207.gamecentre.activities.GestureDetectGridView;
import group_0617.csc207.gamecentre.R;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * The game activity
 */
public class GameActivity extends AppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    BoardManager boardManager;

    /**
     * The buttons to display.
     */
    ArrayList<Button> tileButtons;

    /**
     * The number of loaded image.
     */
    private static int RESULT_LOAD_IMAGE = 1;

    private GameActivityController controller;

    GenericBoardManagerSaveLoader saveLoader;

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * The timer for the game.
     */
    Timer timer = new Timer("GameActivityTimer");

    /**
     * The timer counts for the timer.
     */
    public int counts = 0;

    /**
     * The timer task for the timer.
     */
    TimerTask timerTask = null;

    /**
     * A list containing Bitmap.
     */
    List<Bitmap> bitmapList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String saveFileName = bundle.getString("tempSaveFileName");
        saveLoader.loadGenericBoardManager(saveFileName, this);
        boardManager.makeSolvable();
        controller.createTileButtons(this,this);
        setContentView(R.layout.activity_main);
        addUndoButtonListener();
        addUploadButtonListener();

        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(boardManager.getBoard().getComplexity());
        gridView.setAbleToFling(false);
        gridView.setGenericBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / boardManager.getBoard().getComplexity();
                        columnHeight = displayHeight / boardManager.getBoard().getComplexity();

                        display();
                    }
                });
    }

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    public void display() {
        controller.updateTileButtons(this);
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    /**
     * Stop the timer and return counts (the stop time).
     *
     * @return the stop time counts.
     */
    public int stopTimer() {
        timerTask.cancel();
        timerTask = null;
        return counts;
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveLoader.saveGenericBoardManager(boardManager,StartingActivity.TEMP_SAVE_FILENAME,this );
        boardManager.setLastTime(stopTimer());
    }

    /**
     * Dispatch onResume() to fragments.
     */
    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("start: " + boardManager.getLastTime());
        controller.startTimer(boardManager.getLastTime(),this);
    }

    /**
     * Activate the Undo button.
     */
    private void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.btnundo);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean undoAvailable = boardManager.undo();
                if (undoAvailable) {
                    displayToast("Undo successfully! You have made " + boardManager.getTimesOfUndo() + " times of undo.");
                } else {
                    displayToast("Undo failed! This is the original board.");
                }
            }
        });
    }


    /**
     * Display that a toast of undo.
     */
    private void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
    }

    /**
     * Active Upload Button.
     */
    private void addUploadButtonListener() {
        Button uploadButton = findViewById(R.id.UploadButton);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
    }

    /**
     * http://viralpatel.net/blogs/pick-image-from-galary-android-app/
     * 12:11 05/11/2018 Jiahe Lyu
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, galleryPermissions)) {
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                if (selectedImage != null) {
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    ImageView imgView = (ImageView) findViewById(R.id.imgView);
                    Bitmap pic = BitmapFactory.decodeFile(picturePath);
                    imgView.setImageBitmap(pic);
                    bitmapList = controller.cutImage(pic, boardManager);
                    controller.updateTileButtons(this);
                    displayToast("Upload picture successfully!");
                }
            }
        } else {
            EasyPermissions.requestPermissions(this, "Access for storage",
                    101, galleryPermissions);
        }
    }

    // https://github.com/googlesamples/easypermissions
    // 14:20 05/11/2018 Jiahe Lyu
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

}
