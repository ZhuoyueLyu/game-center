package group_0617.csc207.gamecentre.gameSlidingTiles;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import group_0617.csc207.gamecentre.activities.CustomAdapter;
import group_0617.csc207.gamecentre.activities.GameChoiceActivity;
import group_0617.csc207.gamecentre.activities.GestureDetectGridView;
import group_0617.csc207.gamecentre.activities.LoginActivity;
import group_0617.csc207.gamecentre.R;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * The game activity
 */
public class GameActivity extends AppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    private BoardManager boardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * The number of loaded image.
     */
    private static int RESULT_LOAD_IMAGE = 1;

    /**
     * Constants for swiping directions. Should be an enum, probably.
     */
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * The timer for the game.
     */
    private Timer timer = new Timer("GameActivityTimer");

    /**
     * The timer counts for the timer.
     */
    public int counts = 0;

    /**
     * The timer task for the timer.
     */
    private TimerTask timerTask = null;

    /**
     * A list containing Bitmap.
     */
    private List<Bitmap> bitmapList;

    /**
     * Start the game timer at startValue.
     *
     * @param startValue the start value to set the counts of timer
     */
    private void startTimer(int startValue) {
        counts = startValue;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        counts++;
                        TextView score = (TextView) findViewById(R.id.Score);
                        score.setText("Time: " + counts + " s");
                        boardManager.setLastTime(counts);
                        saveToFile("save_file_" + GameChoiceActivity.currentGame + "_" +
                                boardManager.getBoard().getComplexity() + "_" + LoginActivity.currentUser);
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, new Date(), 1000);
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
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String saveFileName = bundle.getString("tempSaveFileName");
        loadFromFile(saveFileName);
        createTileButtons(this);
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
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        Board board = (Board) boardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != boardManager.getBoard().getComplexity(); row++) {
            for (int col = 0; col != boardManager.getBoard().getComplexity(); col++) {
                Button tmp = new Button(context);
                Tile curTile = board.getTile(row, col);
                if (bitmapList == null) {
                    tmp.setBackgroundResource(curTile.getBackground());
                } else if (curTile.getId() != board.getComplexity() * board.getComplexity()) {
                    BitmapDrawable d = new BitmapDrawable(getResources(), bitmapList.get(curTile.getId()));
                    tmp.setBackground(d);
                } else {
                    tmp.setBackgroundResource(R.drawable.tile_grey);
                }
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Board board = (Board) boardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / boardManager.getBoard().getComplexity();
            int col = nextPos % boardManager.getBoard().getComplexity();
            Tile curTile = board.getTile(row, col);
            if (bitmapList == null) {
                b.setBackgroundResource(curTile.getBackground());
            } else if (curTile.getId() != board.getComplexity() * board.getComplexity()) {
                BitmapDrawable d = new BitmapDrawable(getResources(), bitmapList.get(curTile.getId()));
                b.setBackground(d);
            } else {
                b.setBackgroundResource(R.drawable.tile_grey);
            }
            nextPos++;
        }
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
        boardManager.setLastTime(stopTimer());
    }

    /**
     * Dispatch onResume() to fragments.
     */
    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("start: " + boardManager.getLastTime());
        startTimer(boardManager.getLastTime());
    }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {
        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                boardManager = (BoardManager) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
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
                    bitmapList = cutImage(pic);
                    updateTileButtons();
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

    /**
     * Cut the picture evenly and return the list of pieces.
     *
     * @param picture a Bitmap picture
     * @return the list of Bitmap pieces.
     */
    private List<Bitmap> cutImage(Bitmap picture) {
        List<Bitmap> newPieces = new ArrayList<Bitmap>();
        int w = picture.getWidth();
        int h = picture.getHeight();
        int boxWidth = w / boardManager.getBoard().getComplexity();
        int boxHeight = h / boardManager.getBoard().getComplexity();
        for (int i = 0; i < boardManager.getBoard().getComplexity(); i++) {
            for (int j = 0; j < boardManager.getBoard().getComplexity(); j++) {
                Bitmap pictureFragment = Bitmap.createBitmap(picture, j * boxWidth, i * boxHeight, boxWidth, boxHeight);
                newPieces.add(pictureFragment);
            }
        }
        return newPieces;
    }

}
