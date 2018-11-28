package group_0617.csc207.gamecentre.game2048;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Button;

import java.io.Serializable;
import group_0617.csc207.gamecentre.GenericTile;
import group_0617.csc207.gamecentre.R;
/**
 * A Tile in a 2048 puzzle.
 */
public class Tile2048 extends GenericTile implements Serializable {

    /**
     * The unique id.
     */
    private int id;

    /**
     * A tile with a background id; look up and set the id.
     *
     * @param backgroundId the id of the background
     */
    public Tile2048(int backgroundId, boolean isNew) {
        //super(backgroundId);
        // This looks so ugly.
        this.id = backgroundId;
        switch (backgroundId) {
            case 0:
                setBackground(R.drawable.tile2048_0);
                break;
            case 2:
                if (isNew){
                    setBackground(R.drawable.tile2048_0);
                }
                else {
                    setBackground(R.drawable.tile2048_0);
                }
                break;
            case 4:
                setBackground(R.drawable.tile2048_4);
                break;
            case 8:
                setBackground(R.drawable.tile2048_8);
                break;
            case 16:
                setBackground(R.drawable.tile2048_16);
                break;
            case 32:
                setBackground(R.drawable.tile2048_32);
                break;
            case 64:
                setBackground(R.drawable.tile2048_64);
                break;
//            case 128:
//                setBackground(R.drawable.tile_7);
//                break;
//            case 256:
//                setBackground(R.drawable.tile_8);
//                break;
//            case 512:
//                setBackground(R.drawable.tile_9);
//                break;
//            case 1024:
//                setBackground(R.drawable.tile_10);
//                break;
//            case 2048:
//                setBackground(R.drawable.tile_11);
//                break;
//            case 4096:
//                setBackground(R.drawable.tile_12);
//                break;
//            case 8192:
//                setBackground(R.drawable.tile_13);
//                break;
//            case 16384:
//                setBackground(R.drawable.tile_14);
//                break;
//            case 32768:
//                setBackground(R.drawable.tile_15);
//                break;
//            case 65536:
//                setBackground(R.drawable.tile_16);
//                break;
            default:
                setBackground(R.drawable.tile_grey);
        }
    }

    /**
     * Return the tile id.
     *
     * @return the tile id
     */
    public int getId() {
        return id;
    }
}
