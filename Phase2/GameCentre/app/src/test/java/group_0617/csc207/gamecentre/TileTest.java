package group_0617.csc207.gamecentre;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import group_0617.csc207.gamecentre.gameSlidingTiles.Tile;

import static org.junit.Assert.*;

/**
 * Unit test for Tile
 */
public class TileTest {

    /**
     * The tile to test
     */
    private Tile tile;

    /**
     * Initiate the tile to test
     */
    private void setUpTile() {
        tile = new Tile(1, 4);
    }

    /**
     * Test whether the constructor works
     */
    @Test
    public void testConstructor() {
        List<Integer> backgrounds = new ArrayList<>();
        backgrounds.add(R.drawable.tile_1);
        backgrounds.add(R.drawable.tile_2);
        backgrounds.add(R.drawable.tile_3);
        backgrounds.add(R.drawable.tile_4);
        backgrounds.add(R.drawable.tile_5);
        backgrounds.add(R.drawable.tile_6);
        backgrounds.add(R.drawable.tile_7);
        backgrounds.add(R.drawable.tile_8);
        backgrounds.add(R.drawable.tile_9);
        backgrounds.add(R.drawable.tile_10);
        backgrounds.add(R.drawable.tile_11);
        backgrounds.add(R.drawable.tile_12);
        backgrounds.add(R.drawable.tile_13);
        backgrounds.add(R.drawable.tile_14);
        backgrounds.add(R.drawable.tile_15);
        backgrounds.add(R.drawable.tile_16);
        backgrounds.add(R.drawable.tile_17);
        backgrounds.add(R.drawable.tile_18);
        backgrounds.add(R.drawable.tile_19);
        backgrounds.add(R.drawable.tile_20);
        backgrounds.add(R.drawable.tile_21);
        backgrounds.add(R.drawable.tile_22);
        backgrounds.add(R.drawable.tile_23);
        backgrounds.add(R.drawable.tile_24);
        // Test all constructors
        for (int i = 0; i < 24; i++) {
            tile = new Tile(i + 1, 5);
            assertEquals((long) backgrounds.get(i), tile.getBackground());
        }
        tile = new Tile(25, 5);
        assertEquals(R.drawable.tile_0, tile.getBackground());
        tile = new Tile(9, 3);
        assertEquals(R.drawable.tile_0, tile.getBackground());
        tile = new Tile(16, 4);
        assertEquals(R.drawable.tile_0, tile.getBackground());

        // Give something undefined
        tile = new Tile(1000, 7);
        assertEquals(R.drawable.tile_grey, tile.getBackground());

    }

    /**
     * Test whether getId works
     */
    @Test
    public void testGetId() {
        setUpTile();
        assertEquals(1, tile.getId());
    }

    /**
     * Test whether compareTo works
     */
    @Test
    public void testCompareTo() {
        setUpTile();
        Tile tileToCompare = new Tile(3, 4);
        assertEquals(2, tile.compareTo(tileToCompare));
    }
}