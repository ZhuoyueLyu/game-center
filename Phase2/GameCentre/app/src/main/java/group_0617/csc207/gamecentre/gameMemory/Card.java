package group_0617.csc207.gamecentre.gameMemory;

import group_0617.csc207.gamecentre.GenericTile;
import group_0617.csc207.gamecentre.R;

/**
 * Cards in the memory game
 */
public class Card extends GenericTile {

    /**
     * Whether or not the current card is covered.
     */
    private boolean isCovered;

    /**
     * The cover of all cards
     */
    private final static int cover = R.drawable.question;

    /**
     * Create the card with corresponding image specified by inBackground
     *
     * @param inBackground the input that specify the card's image
     */
    public Card(int inBackground) {
        switch (inBackground + 1) {
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
                setBackground(R.drawable.tile_9);
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
                setBackground(R.drawable.tile_16);
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
            default:
                setBackground(cover);
        }
        this.isCovered = true;
    }

    /**
     * Create the card with corresponding image specified by inBackground and whether
     * it is covered.
     *
     * @param inBackground the input that specify the card's image
     * @param inIsCovered  specify whether cards is covered
     */
    public Card(int inBackground, boolean inIsCovered) {
        super(inBackground);
        this.isCovered = inIsCovered;
    }

    /**
     * Flip this card.
     */
    public void flip() {
        this.isCovered = !this.isCovered;
    }

    /**
     * Check whether the card is covered.
     *
     * @return whether the card is covered
     */
    public boolean getIsCovered() {
        return this.isCovered;
    }

    /**
     * Return the image that card should display now
     *
     * @return the image that card should display now
     */
    public int getDisplay() {
        if (getIsCovered()) {
            return Card.cover;
        } else {
            return this.getBackground();
        }
    }
}