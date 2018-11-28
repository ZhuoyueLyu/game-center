package group_0617.csc207.gamecentre;

import java.io.Serializable;

/**
 * A Generic Tile with background id
 */
public abstract class GenericTile implements Serializable {

    /**
     * The background
     */
    private int background;

    /**
     * Create an empty tile
     */
    protected GenericTile() {
    }

    /**
     * Create a Tile with specified background
     *
     * @param inBackground the background of the tile
     */
    protected GenericTile(int inBackground) {
        this.background = inBackground;
    }

    /**
     * Return the background of the tile
     *
     * @return the background of the tile
     */
    public int getBackground() {
        return background;
    }

    /**
     * Set the background of the tiles
     *
     * @param inBackground the new background of the tile
     */
    protected void setBackground(int inBackground) {
        this.background = inBackground;
    }
}