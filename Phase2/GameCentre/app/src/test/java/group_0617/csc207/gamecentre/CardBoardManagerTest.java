package group_0617.csc207.gamecentre;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Unit test for CardBoard Manager
 */
public class CardBoardManagerTest {

    /**
     * The cardBoardManager to test
     */
    private CardBoardManager cardBoardManager = new CardBoardManager(4);

    private void setUpNewBoard() {
        this.cardBoardManager = new CardBoardManager(4);
    }

    private void setUpSpecificBoard() {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            cards.add(new Card(i));
            cards.add(new Card(i));
        }
        this.cardBoardManager = new CardBoardManager(new CardBoard(cards));
    }

    @Test
    public void testGetBoard() {
        // CardBoard cardboard = CardBoardManager.getBoard();
    }

    @Test
    public void testPuzzleSolved() {
    }

    @Test
    public void testIsValidTap() {
        setUpSpecificBoard();
        assertTrue(cardBoardManager.isValidTap(1));
        cardBoardManager.touchMove(1);
        assertFalse(cardBoardManager.isValidTap(1));
    }

    @Test
    public void testTouchMove() {
        setUpSpecificBoard();
        cardBoardManager.touchMove(0);
        assertFalse(cardBoardManager.getCardAtPos(0).getIsCovered());
        cardBoardManager.touchMove(0);
        assertFalse(cardBoardManager.getCardAtPos(0).getIsCovered());
        cardBoardManager.touchMove(2);
        assertTrue(cardBoardManager.getCardAtPos(2).getIsCovered());
    }

}
/*
    @Test
    public void testHasIdentified() {
        setUpSpecificBoard();
        cardBoardManager.setFirst(cardBoardManager.getCardAtPos(0));
        cardBoardManager.setSecond(cardBoardManager.getCardAtPos(1));
        assertTrue(cardBoardManager.hasIdentified());
        setUpNewBoard();
        cardBoardManager.touchMove(0);
        assertTrue(cardBoardManager.getCardAtPos(0).getBackground() == R.drawable.tile_1);
        cardBoardManager.touchMove(15);
        assertTrue(cardBoardManager.getCardAtPos(15).getBackground() == R.drawable.tile_8);
        assertFalse(cardBoardManager.hasIdentified());
    }

    @Test
    public void testIsFull() {
        setUpSpecificBoard();
        cardBoardManager.touchMove(0);
        cardBoardManager.touchMove(1);
        assertTrue(cardBoardManager.isFull());
        setUpNewBoard();
        cardBoardManager.touchMove(2);
        assertFalse(cardBoardManager.isFull());
        cardBoardManager.touchMove(3);
        assertTrue(cardBoardManager.isFull());
    }

    @Test
    public void testUpdateChosenCards() {
        setUpSpecificBoard();
        cardBoardManager.touchMove(0);
        cardBoardManager.touchMove(1);
        assertTrue(cardBoardManager.isFull());
        cardBoardManager.updateChosenCards();
        assertFalse(cardBoardManager.isFull());
        cardBoardManager.touchMove(3);
        cardBoardManager.touchMove(15);
        assertTrue(cardBoardManager.isFull());
        assertFalse(cardBoardManager.hasIdentified());
        cardBoardManager.updateChosenCards();
        assertTrue(cardBoardManager.getCardAtPos(3).getIsCovered());
        assertTrue(cardBoardManager.getCardAtPos(15).getIsCovered());
        assertFalse(cardBoardManager.isFull());

    }

    @Test
    public void testGetFirst() {
        setUpSpecificBoard();
        cardBoardManager.touchMove(0);
        assertEquals(cardBoardManager.getCardAtPos(0), cardBoardManager.getFirst());
    }

    @Test
    public void testGetSecond() {
        setUpSpecificBoard();
        cardBoardManager.touchMove(0);
        cardBoardManager.touchMove(1);
        assertEquals(cardBoardManager.getCardAtPos(1), cardBoardManager.getSecond());
    }

    @Test
    public void testGetCardAtPos() {
    }

    @Test
    public void getScore() {
    }
}
*/