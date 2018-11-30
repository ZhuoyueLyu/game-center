package group_0617.csc207.gamecentre;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import group_0617.csc207.gamecentre.gameMemory.Card;
import group_0617.csc207.gamecentre.gameMemory.CardBoard;
import group_0617.csc207.gamecentre.gameMemory.CardBoardManager;

import static org.junit.Assert.*;


/**
 * Unit test for CardBoard Manager
 */
public class CardBoardManagerTest {

    /**
     * The cardBoardManager to test
     */
    private CardBoardManager cardBoardManager;

    /**
     * The cardBoard in the cardBoardManager
     */
    private CardBoard cardBoard;

    /**
     * Set up a Board specifying its complexity
     */
    private void setUpBoardWithComplexity() {
        this.cardBoardManager = new CardBoardManager(3);
    }

    /**
     * Set up specific cardBoard and cardBoardManager to test
     * This board has complexity 4
     */
    private void setUpSpecificBoard() {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            cards.add(new Card(i));
            cards.add(new Card(i));
        }
        this.cardBoard = new CardBoard(cards);
        this.cardBoardManager = new CardBoardManager(cardBoard);
    }

    /**
     * Test whether CardBoardManager's constructor works
     */
    @Test
    public void testConstructor() {
        setUpBoardWithComplexity();
        assertEquals(3, cardBoardManager.getBoard().getComplexity());
        setUpSpecificBoard();
        assertEquals(4, cardBoardManager.getBoard().getComplexity());
    }

    /**
     * Test whether getBoard works
     */
    @Test
    public void testGetBoard() {
        setUpSpecificBoard();
        CardBoard boardFromMethod = (CardBoard) cardBoardManager.getBoard();
        assertEquals(cardBoard, boardFromMethod);
    }

    /**
     * Test whether puzzleSolved works
     */
    @Test
    public void testPuzzleSolved() {
        setUpSpecificBoard();
        assertFalse(cardBoardManager.puzzleSolved());
        int complexity = cardBoard.getComplexity();
        for (int i = 0; i < complexity * complexity; i++) {
            cardBoardManager.touchMove(i);
        }
        assertTrue(cardBoardManager.puzzleSolved());
    }

    /**
     * Test whether isValidTap works
     */
    @Test
    public void testIsValidTap() {
        setUpSpecificBoard();
        assertTrue(cardBoardManager.isValidTap(1));
        cardBoardManager.touchMove(1);
        assertFalse(cardBoardManager.isValidTap(1));
    }

    /**
     * Test whether touchMove works
     */
    @Test
    public void testTouchMove() {
        setUpSpecificBoard();
        cardBoardManager.touchMove(0);
        assertFalse(cardBoardManager.getCardAtPos(0).getIsCovered());
        cardBoardManager.touchMove(2);
        assertTrue(cardBoardManager.getCardAtPos(2).getIsCovered());
    }

    /**
     * Test whether getCardAtPos works
     */
    @Test
    public void testGetCardAtPos() {
        setUpSpecificBoard();
        Card fromBoard = (Card) cardBoard.getGenericTile(0, 1);
        Card fromManager = cardBoardManager.getCardAtPos(1);
        assertEquals(fromBoard, fromManager);
    }

    /**
     * Test whether getScore works
     */
    @Test
    public void getScore() {
        setUpSpecificBoard();
        for (int i = 0; i < 4; i++) {
            cardBoardManager.touchMove(i * 2);
        }
        assertEquals((int) (10000 * Math.exp(-4 * 0.01 / 4)), cardBoardManager.getScore());
    }

    /**
     * Test whether getLastTime works
     */
    @Test
    public void testGetLastTime() {
        setUpSpecificBoard();
        cardBoardManager.setLastTime(100);
        assertEquals(100, cardBoardManager.getLastTime());
    }

    /**
     * Test the getCurrentGame method
     */
    @Test
    public void testGetCurrentGame() {
        setUpSpecificBoard();
        assertEquals(CardBoardManager.GAME_NAME, cardBoardManager.getCurrentGame());
    }
}