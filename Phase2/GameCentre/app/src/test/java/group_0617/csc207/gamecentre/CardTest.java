package group_0617.csc207.gamecentre;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import group_0617.csc207.gamecentre.gameMemory.Card;

import static org.junit.Assert.*;

/**
 * Unit test for Card
 */
public class CardTest {

    /**
     * The card to test
     */
    private Card card;

    /**
     * The anticipated cover of card
     */
    private final int cover = R.drawable.question;

    /**
     * Set up the card to test
     */
    private void setUpCard() {
        this.card = new Card(3);
    }

    /**
     * Set up card initially flipped
     */
    private void setUpSpecificCard() {
        this.card = new Card(2, false);
    }

    /**
     * Test whether constructor works
     */
    @Test
    public void testCard() {
        setUpCard();
        assertTrue(card.getIsCovered());
        setUpSpecificCard();
        assertFalse(card.getIsCovered());
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
            card = new Card(i);
            assertEquals((long) backgrounds.get(i), card.getBackground());
        }

        // executes default that set background to tile0
        card = new Card(1000);
        assertEquals(cover, card.getBackground());
    }


    /**
     * Test whether flipping works
     */
    @Test
    public void testFlip() {
        setUpCard();
        assertTrue(card.getIsCovered());
        card.flip();
        assertFalse(card.getIsCovered());
    }

    /**
     * Test whether is covered works
     */
    @Test
    public void testGetIsCovered() {
        setUpCard();
        assertTrue(card.getIsCovered());
    }

    /**
     * Test whether getting background works
     */
    @Test
    public void testGetBackground() {
        setUpCard();
        assertEquals(R.drawable.tile_4, card.getBackground());
    }

    /**
     * Test whether getting display works
     */
    @Test
    public void testGetDisplay() {
        setUpCard();
        assertEquals(cover, card.getDisplay());
        card.flip();
        assertEquals(card.getBackground(), card.getDisplay());
    }
}