package group_0617.csc207.gamecentre.gameSlidingTiles;

import android.support.annotation.NonNull;

import group_0617.csc207.gamecentre.GenericTile;
import group_0617.csc207.gamecentre.R;

/**
 * A Tile in a sliding tiles puzzle.
 */
public class Tile extends GenericTile implements Comparable<Tile> {

    /**
     * The unique id.
     */
    private int id;

    /**
     * A tile with a background id; look up and set the id.
     *
     * @param backgroundId           the id used to specify background of the tile
     * @param currentBoardComplexity the complexity of current board
     */
    public Tile(int backgroundId, int currentBoardComplexity) {
        id = backgroundId;
        switch (backgroundId) {
            case 1:
                setBackground(R.drawable.tile_1);
                break;
            case 2:
                setBackground(R.drawable.tile_2);
                break;
            case 3:
                setBackground(R.drawable.tile_3);
                break;
            case 4:
                setBackground(R.drawable.tile_4);
                break;
            case 5:
                setBackground(R.drawable.tile_5);
                break;
            case 6:
                setBackground(R.drawable.tile_6);
                break;
            case 7:
                setBackground(R.drawable.tile_7);
                break;
            case 8:
                setBackground(R.drawable.tile_8);
                break;
            case 9:
                if (currentBoardComplexity == 3) {
                    setBackground(R.drawable.tile_0);
                } else {
                    setBackground(R.drawable.tile_9);
                }
                break;
            case 10:
                setBackground(R.drawable.tile_10);
                break;
            case 11:
                setBackground(R.drawable.tile_11);
                break;
            case 12:
                setBackground(R.drawable.tile_12);
                break;
            case 13:
                setBackground(R.drawable.tile_13);
                break;
            case 14:
                setBackground(R.drawable.tile_14);
                break;
            case 15:
                setBackground(R.drawable.tile_15);
                break;
            case 16:
                if (currentBoardComplexity == 4) {
                    setBackground(R.drawable.tile_0);
                } else {
                    setBackground(R.drawable.tile_16);
                }
                break;
            case 17:
                setBackground(R.drawable.tile_17);
                break;
            case 18:
                setBackground(R.drawable.tile_18);
                break;
            case 19:
                setBackground(R.drawable.tile_19);
                break;
            case 20:
                setBackground(R.drawable.tile_20);
                break;
            case 21:
                setBackground(R.drawable.tile_21);
                break;
            case 22:
                setBackground(R.drawable.tile_22);
                break;
            case 23:
                setBackground(R.drawable.tile_23);
                break;
            case 24:
                setBackground(R.drawable.tile_24);
                break;
            case 25:
                setBackground(R.drawable.tile_0);
                break;
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

    @Override
    public int compareTo(@NonNull Tile o) {
        return o.id - this.id;
    }
}
