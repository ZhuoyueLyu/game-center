package group_0617.csc207.gamecentre;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class CardBoardTest {

    /**
     * The CardBoard to test
     */
    private CardBoard cardBoard;

    /**
     * The complexity of the current board tested
     */
    private int complexity = 4;

    /**
     * Set up a card board like a normal game
     */
    private void setUpCardBoard(){
        this.cardBoard = new CardBoard(complexity);
    }

    /**
     * Set up a specific card board for testing purpose
     */
    private void setUpSpecificCardBoard() {
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(1));
        cards.add(new Card(3));
        cards.add(new Card(1));
        cards.add(new Card(3));
        cards.add(new Card(2));
        cards.add(new Card(4));
        cards.add(new Card(2));
        cards.add(new Card(4));
        cards.add(new Card(0));
        cardBoard = new CardBoard(cards);
    }

    @Test
    public void testFlipCard() {
        setUpCardBoard();
        cardBoard.flipCard(2);
        assertFalse(cardBoard.getCard(0, 2).getIsCovered());
        cardBoard.flipCard(2);
        assertTrue(cardBoard.getCard(0, 2).getIsCovered());
    }

    @Test
    public void testGetCard() {
        setUpSpecificCardBoard();
        assertEquals(R.drawable.tile_2, cardBoard.getCard(0, 0).getBackground());
    }

    @Test
    public void testGetComplexity() {
        setUpCardBoard();
        assertEquals(complexity, cardBoard.getComplexity());
        setUpSpecificCardBoard();
        assertEquals(3, cardBoard.getComplexity());
    }

    @Test
    public void testIterator() {
        setUpSpecificCardBoard();
        Iterator<Card> iter = cardBoard.iterator();
        assertEquals(R.drawable.tile_2, iter.next().getBackground());
        for (int i = 0; i < 8; i++) {
            iter.next();
        }
        assertFalse(iter.hasNext());
    }
}