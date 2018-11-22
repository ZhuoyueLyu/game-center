package group_0617.csc207.gamecentre;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Button;

import java.io.Serializable;

import group_0617.csc207.gamecentre.R;

/**
 * A Tile in a 2048 puzzle.
 */
public class Tile2048 extends Tile implements Serializable {

    /**
     * The background id to find the tile image.
     */
    private int background;

    /**
     * The unique id.
     */
    private int id;

//    /**
//     * A Tile with id and background. The background may not have a corresponding image.
//     *
//     * @param id         the id
//     * @param background the background
//     */
//    public Tile2048(int id, int background) {
//        //super(id, background);
//    }

    /**
     * A tile with a background id; look up and set the id.
     *
     * @param backgroundId
     */
    public Tile2048(int backgroundId) {
        super(backgroundId);
        // This looks so ugly.
        this.id = backgroundId;
        switch (backgroundId) {
            case 0:
                background = R.drawable.tile_0;
                break;
            case 2:
                background = R.drawable.tile_1;
                break;
            case 4:
                background = R.drawable.tile_2;
                break;
            case 8:
                background = R.drawable.tile_3;
                break;
            case 16:
                background = R.drawable.tile_4;
                break;
            case 32:
                background = R.drawable.tile_5;
                break;
            case 64:
                background = R.drawable.tile_6;
                break;
            case 128:
                background = R.drawable.tile_7;
                break;
            case 256:
                background = R.drawable.tile_8;
                break;
            case 512:
                background = R.drawable.tile_0;
                break;
            case 1024:
                background = R.drawable.tile_10;
                break;
            case 2048:
                background = R.drawable.tile_11;
                break;
            case 4096:
                background = R.drawable.tile_12;
                break;
            case 8192:
                background = R.drawable.tile_13;
                break;
            case 16384:
                background = R.drawable.tile_14;
                break;
            default:
                background = R.drawable.tile_0;
        }
    }

    /**
     * Return the background id.
     *
     * @return the background id
     */
    public int getBackground() {
        return background;
    }

    /**
     * Return the tile id.
     *
     * @return the tile id
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    @Override
//    public int compareTo(@NonNull Tile2048 o) {
//        return o.id - this.id;
//    }
}
