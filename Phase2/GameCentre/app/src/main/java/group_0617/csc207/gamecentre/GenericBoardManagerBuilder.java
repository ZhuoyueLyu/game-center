package group_0617.csc207.gamecentre;

import group_0617.csc207.gamecentre.gameMemory.CardBoardManager;
import group_0617.csc207.gamecentre.gameSlidingTiles.BoardManager;
import group_0617.csc207.gamecentre.game2048.BoardManager2048;

/**
 * The builder that builds specified type of BoardManager
 */
public class GenericBoardManagerBuilder {

    /**
     * The only instance of GenericBoardManagerBuilder
     */
    private GenericBoardManagerBuilder theBuilder = null;

    /**
     * An empty constructor that does nothing
     */
    private GenericBoardManagerBuilder() {
    }

    /**
     * Return the only instance of GenericBoardManagerBuilder
     * @return the only instance of GenericBoardManagerBuilder
     */
    public GenericBoardManagerBuilder getInstance() {
        if (theBuilder == null) {
            theBuilder = new GenericBoardManagerBuilder();
        }
        return theBuilder;
    }

    /**
     * Return the specified version of BoardManager given complexity.
     * @param game the string that specified the BoardManager to return
     * @param complexity the complexity of the GenericBoard of the returned BoardManager
     * @return the GenericBoardManager of the type specified
     */
    public GenericBoardManager getGenericBoardManager(String game, int complexity) {
        GenericBoardManager re;
        switch (game) {
            case BoardManager2048.GAME_NAME:
                re = new BoardManager2048(complexity);
                break;
            case BoardManager.GAME_NAME:
                re = new BoardManager(complexity);
                break;
            case CardBoardManager.GAME_NAME:
                re = new CardBoardManager(complexity);
                break;
            default:
                re = null;
        }
        return re;
    }
}
