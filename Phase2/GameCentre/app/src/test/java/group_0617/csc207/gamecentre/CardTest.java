package group_0617.csc207.gamecentre;

import org.junit.Test;

import static org.junit.Assert.*;

public class CardTest {

    /**
     * The card to test
     */
    private Card card;

    private void setUpCard() {
        this.card = new Card(3);
    }
    @Test
    public void flip() {
        setUpCard();
        assertTrue(card.getIsCovered());
        card.flip();
        assertFalse(card.getIsCovered());
    }

    @Test
    public void getIsCovered() {
        setUpCard();
        assertTrue(card.getIsCovered());
    }

    @Test
    public void getBackground() {
        setUpCard();
        assertEquals(R.drawable.tile_4, card.getBackground());
    }

    @Test
    public void getDisplay() {
        setUpCard();
        assertEquals(R.drawable.tile_0, card.getDisplay());
        card.flip();
        assertEquals(card.getBackground(), card.getDisplay());
    }
}