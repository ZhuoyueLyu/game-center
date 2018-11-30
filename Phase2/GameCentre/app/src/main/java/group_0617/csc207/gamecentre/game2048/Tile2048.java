package group_0617.csc207.gamecentre.game2048;

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
    private final int id;

    /**
     * A tile with a background id; look up and set the id.
     *
     * @param backgroundId the id of the background
     */
    public Tile2048(int backgroundId, boolean isNew) {
        this.id = backgroundId;

        switch (backgroundId) {
            case 0:
                setBackground(R.drawable.tile2048_0);
                break;
            case 2:
                if (isNew) {
                    setBackground(R.drawable.tile2048_02);
                } else {
                    setBackground(R.drawable.tile2048_2);
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
            case 128:
                setBackground(R.drawable.tile2048_128);
                break;
            case 256:
                setBackground(R.drawable.tile2048_256);
                break;
            case 512:
                setBackground(R.drawable.tile2048_512);
                break;
            case 1024:
                setBackground(R.drawable.tile2048_1024);
                break;
            case 2048:
                setBackground(R.drawable.tile2048_2048);
                break;
            case 4096:
                setBackground(R.drawable.tile2048_4096);
                break;
            case 8192:
                setBackground(R.drawable.tile2048_8192);
                break;
            case 16384:
                setBackground(R.drawable.tile2048_16384);
                break;
            case 32768:
                setBackground(R.drawable.tile2048_32768);
                break;
            case 65536:
                setBackground(R.drawable.tile2048_65536);
                break;
            default:
                setBackground(R.drawable.tile2048_0);
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
