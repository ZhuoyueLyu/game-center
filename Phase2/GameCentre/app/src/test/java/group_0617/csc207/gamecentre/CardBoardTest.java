package group_0617.csc207.gamecentre;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import group_0617.csc207.gamecentre.gameMemory.Card;
import group_0617.csc207.gamecentre.gameMemory.CardBoard;

import static org.junit.Assert.*;

/**
 * The unit test for CardBoard
 */
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
     * The anticipated cover of Card
     */
    private final int cover = R.drawable.question;

    /**
     * Set up a card board like a normal game
     */
    private void setUpCardBoard(){
        this.cardBoard = new CardBoard(complexity);
    }

    /**
     * Set up a self defined card board for testing purpose
     * Insufficient amount of cards is given
     */
    private void setUpSpecificCardBoard() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(1));
        cards.add(new Card(3));
        cards.add(new Card(1));
        cards.add(new Card(3));
        cards.add(new Card(2));
        cards.add(new Card(4));
        cards.add(new Card(2));
        cardBoard = new CardBoard(cards);
    }

    /**
     * Set up a default card board
     */
    private void setUpDefaultBoard() {
        cardBoard = new CardBoard();
    }

    /**
     * Test whether constructor works
     */
    @Test
    public void testCardBoard() {
        setUpDefaultBoard();
        assertEquals(4, cardBoard.getComplexity());

        // See if giving insufficient number of cares cause trouble
        setUpSpecificCardBoard();
        assertEquals(3, cardBoard.getComplexity());
        // Card should have cover as default background even if it is not given
        assertEquals(cover, cardBoard.getCard(2, 2).getBackground());
        setUpCardBoard();
        assertEquals(4, cardBoard.getComplexity());
    }

    /**
     * Test whether flipping card works
     */
    @Test
    public void testFlipCard() {
        setUpCardBoard();
        cardBoard.flipCard(2);
        assertFalse(cardBoard.getCard(0, 2).getIsCovered());
        cardBoard.flipCard(2);
        assertTrue(cardBoard.getCard(0, 2).getIsCovered());
    }

    /**
     * Test whether getting card works
     */
    @Test
    public void testGetCard() {
        setUpSpecificCardBoard();
        assertEquals(R.drawable.tile_2, cardBoard.getCard(0, 0).getBackground());
    }

    /**
     * Test whether getting complexity works
     */
    @Test
    public void testGetComplexity() {
        setUpCardBoard();
        assertEquals(complexity, cardBoard.getComplexity());
        setUpSpecificCardBoard();
        assertEquals(3, cardBoard.getComplexity());
    }

    /**
     * Test whether iterator works
     */
    @Test
    public void testIterator() {
        setUpSpecificCardBoard();
        Iterator<Card> iterator = cardBoard.iterator();
        assertEquals(R.drawable.tile_2, iterator.next().getBackground());
        for (int i = 0; i < 8; i++) {
            iterator.next();
        }
        assertFalse(iterator.hasNext());
    }
}