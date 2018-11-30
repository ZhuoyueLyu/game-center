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
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import group_0617.csc207.gamecentre.GenericGameActivity;
import group_0617.csc207.gamecentre.R;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * The game activity
 */
public class GameActivity extends GenericGameActivity {

    /**
     * The number of loaded image.
     */
    private static int RESULT_LOAD_IMAGE = 1;

    /**
     * A list containing Bitmap.
     */
    List<Bitmap> bitmapList;

    private GameActivityController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        controller = new GameActivityController();
        assert bundle != null;
        setSaveFileName(bundle.getString("saveFileName"));
        super.onCreate(savedInstanceState);
        getGridView().setAbleToFling(false);
        addUndoButtonListener();
        addUploadButtonListener();
        ((BoardManager) getGenericBoardManager()).makeSolvable();
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

    @Override
    public void createTileButtons(Context context) {
        Board board = (Board) getGenericBoardManager().getBoard();
        ArrayList<Button> tileButtons = new ArrayList<>();
        for (int row = 0; row != getGenericBoardManager().getBoard().getComplexity(); row++) {
            for (int col = 0; col != getGenericBoardManager().getBoard().getComplexity(); col++) {
                Button tmp = new Button(context);
                Tile curTile = board.getTile(row, col);
                if (bitmapList == null) {
                    tmp.setBackgroundResource(curTile.getBackground());
                } else if (curTile.getId() < board.getComplexity() * board.getComplexity()) {
                    BitmapDrawable d = new BitmapDrawable(getResources(), bitmapList.get(curTile.getId()-1));
                    tmp.setBackground(d);
                } else {
                    tmp.setBackgroundResource(R.drawable.tile_grey);
                }
                tileButtons.add(tmp);
            }
        }
        setTileButtons(tileButtons);
    }

    @Override
    public void updateTileButtons() {
        Board board = (Board) getGenericBoardManager().getBoard();
        int nextPos = 0;
        for (Button b : getTileButtons()) {
            int row = nextPos / getGenericBoardManager().getBoard().getComplexity();
            int col = nextPos % getGenericBoardManager().getBoard().getComplexity();
            Tile curTile = board.getTile(row, col);
            if (bitmapList == null) {
                b.setBackgroundResource(curTile.getBackground());
            } else if (curTile.getId() < board.getComplexity() * board.getComplexity()) {
                BitmapDrawable d = new BitmapDrawable(getResources(), bitmapList.get(curTile.getId()-1));
                b.setBackground(d);
            } else {
                b.setBackgroundResource(R.drawable.tile_grey);
            }
            nextPos++;
        }
    }

    /**
     * Cite from  http://viralpatel.net/blogs/pick-image-from-galary-android-app/
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

                    ImageView imgView = findViewById(R.id.imgView);
                    Bitmap pic = BitmapFactory.decodeFile(picturePath);
                    imgView.setImageBitmap(pic);
                    bitmapList = controller.cutImage(pic, (BoardManager) getGenericBoardManager());
                    updateTileButtons();
                    Toast.makeText(getApplicationContext(), "Upload picture successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            EasyPermissions.requestPermissions(this, "Access for storage",
                    101, galleryPermissions);
        }
    }

    // Cite from  https://github.com/googlesamples/easypermissions
    // 14:20 05/11/2018 Jiahe Lyu
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

}