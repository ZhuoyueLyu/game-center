package group_0617.csc207.gamecentre.game2048;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import group_0617.csc207.gamecentre.GenericBoardManager;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
public class BoardManager2048 extends GenericBoardManager implements Serializable {

    /**
     * Name of 2048 game
     */
    public static final String GAME_NAME = "tf";

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board2048 the board
     */
    public BoardManager2048(Board2048 board2048) {
        super(board2048);
    }

    /**
     * Manage a new shuffled board.
     */
    public BoardManager2048(int complexity) {
        List<Tile2048> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum < complexity * complexity; tileNum++) {
            tiles.add(new Tile2048(0, true));
        }
        Board2048 board2048 = new Board2048(tiles);
        board2048.addRandomTile();
        board2048.addRandomTile();
        setBoard(board2048);
    }

    @Override
    public boolean puzzleSolved() {
        int[] moves = {GameActivity2048.UP, GameActivity2048.DOWN, GameActivity2048.LEFT, GameActivity2048.RIGHT};
        for (int move : moves) {
            if (isValidTap(move)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isValidTap(int move) {
        Board2048 board2048 = (Board2048) getBoard();
        Tile2048[][] tiles = board2048.getTiles();
        Tile2048[][] columnTiles = new Tile2048[board2048.getComplexity()][board2048.getComplexity()];
        for (int row = 0; row != board2048.getComplexity(); row++) {
            for (int col = 0; col != board2048.getComplexity(); col++) {
                columnTiles[row][col] = tiles[col][row];
            }
        }

        switch (move) {
            case GameActivity2048.LEFT:
                for (Tile2048[] line : tiles) {
                    Tile2048[] lineCopy = line.clone();
                    board2048.leftCombine(lineCopy);
                    for (int i = 0; i < line.length; i++) {
                        if (line[i].getId() != lineCopy[i].getId()) {
                            return true;
                        }
                    }
                }
                break;
            case GameActivity2048.RIGHT:
                for (Tile2048[] line : tiles) {
                    Tile2048[] lineCopy = line.clone();
                    Tile2048[] reverseLine = new Tile2048[line.length];
                    for (int j = 0; j < line.length; j++) {
                        reverseLine[j] = line[line.length - 1 - j];
                    }
                    board2048.leftCombine(reverseLine);
                    for (int k = 0; k < line.length; k++) {
                        line[k] = reverseLine[line.length - 1 - k];
                    }
                    for (int i = 0; i < line.length; i++) {
                        if (line[i].getId() != lineCopy[i].getId()) {
                            return true;
                        }
                    }
                }
                break;
            case GameActivity2048.UP:
                for (Tile2048[] line : columnTiles) {
                    Tile2048[] lineCopy = line.clone();
                    board2048.leftCombine(line);
                    for (int i = 0; i < line.length; i++) {
                        if (line[i].getId() != lineCopy[i].getId()) {
                            return true;
                        }
                    }
                }
                break;
            case GameActivity2048.DOWN:
                for (Tile2048[] line : columnTiles) {
                    Tile2048[] lineCopy = line.clone();
                    Tile2048[] reverseLine = new Tile2048[line.length];
                    for (int j = 0; j < line.length; j++) {
                        reverseLine[j] = line[line.length - 1 - j];
                    }
                    board2048.leftCombine(reverseLine);
                    for (int k = 0; k < line.length; k++) {
                        line[k] = reverseLine[line.length - 1 - k];
                    }
                    for (int i = 0; i < line.length; i++) {
                        if (line[i].getId() != lineCopy[i].getId()) {
                            return true;
                        }
                    }
                }
                break;
        }
        return false;
    }

    @Override
    public void touchMove(int move) {
        Board2048 board2048 = (Board2048) getBoard();
        List<Tile2048> tiles = new ArrayList<>();
        for (int i = 0; i < getBoard().getComplexity(); i++) {
            for (int j = 0; j < getBoard().getComplexity(); j++) {
                tiles.add(board2048.getTile(i, j));
            }
        }
        getBoardStack().add(new Board2048(tiles));

        board2048.swipeMove(move);

        int sum = 0;
        for (int i = 0; i < board2048.getComplexity(); i++) {
            for (int j = 0; j < board2048.getComplexity(); j++) {
                sum += board2048.getTile(i, j).getId();
            }
        }
        setScore(sum - getTimesOfUndo() * getBoard().getComplexity());

        board2048.addRandomTile();
    }

    @Override
    public String getCurrentGame() {
        return GAME_NAME;
    }

}


