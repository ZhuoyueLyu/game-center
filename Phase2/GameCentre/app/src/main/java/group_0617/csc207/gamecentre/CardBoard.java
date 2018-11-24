package group_0617.csc207.gamecentre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

/**
 * Contains card.
 */
//public class CardBoard extends Observable implements Serializable, Iterable<Card>{
public class CardBoard extends GenericBoard implements Iterable<Card>{

    private static final int DEFAULT_COMPLEXITY = 4;

    /**
     * On default, creates a board of complexity 4
     */
    CardBoard() {
        this(DEFAULT_COMPLEXITY);
    }

    /**
     * Creates cardboard given a desired complexity
     *
     * @param inComplexity the complexity desired of this board
     */
    CardBoard(int inComplexity) {
        setComplexity(inComplexity);
        Card[][] cards = new Card[inComplexity][inComplexity];
        int numOfUniqueCards = inComplexity * inComplexity / 2;
        List<Card> newCards = new ArrayList<Card>();
        for (int i = 0; i < numOfUniqueCards; i++) {
            newCards.add(new Card(i));
            newCards.add(new Card(i));
        }
        // Collections.shuffle(newCards);

        for (int j = 0; j < newCards.size(); j++) {
            cards[j / inComplexity][j % inComplexity] = newCards.get(j);
        }
        setGenericTiles(cards);
    }

    /**
     * Creates cardboard given a list of cards.
     *
     * @param inCards the cards to put in the newly created Cardboard
     */
    CardBoard(List<Card> inCards) {
        setComplexity((int)Math.sqrt((double)inCards.size()));
        Card[][] cards = new Card[getComplexity()][getComplexity()];
        Iterator<Card> iter = inCards.iterator();
        for (int row = 0; row < getComplexity(); row++) {
            for (int col = 0; col < getComplexity(); col++) {
                if (iter.hasNext()) {
                    cards[row][col] = iter.next();
                } else {
                    cards[row][col] = new Card(-1);
                }
            }
        }
    }

    /**
     * flip the card at specified location
     *
     * @param pos the position of card to flip assuming row major numbering
     */
    void flipCard(int pos) {
        getCard(pos/getComplexity(), pos%getComplexity()).flip();
        update();
    }

    /**
     * Update the current board and notify all observers
     */
    void update() {
        setChanged();
        notifyObservers();
    }

    /**
     * Get the card at location specified by given row and column.
     *
     * @param row the row to get card from
     * @param col the column to get card from
     * @return the card at specified location
     */
    Card getCard(int row, int col) {
        return (Card) getGenericTile(row, col);
    }

    @Override
    public Iterator<Card> iterator() {
        return new CardBoardIterator();
    }

    /**
     * Iterates through the cards of this cardboard
     */
    private class CardBoardIterator implements Iterator<Card> {

        /**
         * The index of next item to return
         */
        int nextIndex = 0;

        @Override
        public boolean hasNext() {
            return this.nextIndex != numTiles();
        }

        @Override
        public Card next() {
            Card re = getCard(nextIndex/getComplexity(), nextIndex%getComplexity());
            nextIndex++;
            return re;
        }
    }
}