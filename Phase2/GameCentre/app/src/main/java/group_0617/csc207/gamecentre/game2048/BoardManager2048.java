package group_0617.csc207.gamecentre.game2048;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import group_0617.csc207.gamecentre.GenericBoardManager;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
public class BoardManager2048 extends GenericBoardManager implements Serializable {

    /**
     * The board being managed.
     */
    private Board2048 board2048;

    /**
     * The number of undos.
     */
    private int timesOfUndo = 0;

    /**
     * The time of last game's timer counts..
     */
    private int lastTime = 0;

    /**
     * The score which outputs after solving game.
     */
    private int score = 0;

    /**
     * The stack of all previous reversed moves.
     */
    private Stack<Board2048> boardStack = new Stack<>();

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board2048 the board
     */
    public BoardManager2048(Board2048 board2048) {
        super(board2048);
        this.board2048 = board2048;
    }

    /**
     * Return the current board.
     */
    public Board2048 getBoard() {
        return board2048;
    }

    /**
     * Manage a new shuffled board.
     */
    public BoardManager2048(int complexity) {
        List<Tile2048> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum < complexity * complexity; tileNum++) {
            tiles.add(new Tile2048(0));
        }
        this.board2048 = new Board2048(tiles);
        board2048.addRandomTile();
        board2048.addRandomTile();
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    public boolean puzzleSolved() {
        for (int i = 0; i < board2048.getComplexity(); i++) {
            for (int j = 0; j < board2048.getComplexity(); j++) {
                if (board2048.getTile(i,j).getId() == (int) Math.pow(2, board2048.getComplexity()*5-9)) {
                    score = 10000 / (getStepNum() + timesOfUndo) / lastTime;
                    lastTime = 0;
                    timesOfUndo = 0;
                    boardStack = new Stack<>();
                    return true;
                }
            }
        }

//        int[] moves = {Game2048Activity.UP, Game2048Activity.DOWN, Game2048Activity.LEFT, Game2048Activity.RIGHT};
//        for (int move: moves){
//            if (isValidTap(move)){
//                stepCounter = 0;
//                lastTime = 0;
//                timesOfUndo = 0;
//                return true;
//            }
//        }

        return false;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param move the direction to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    public boolean isValidTap(int move) {
        Tile2048[][] tiles = board2048.getTiles();
        Tile2048[][] columnTiles = new Tile2048[board2048.getComplexity()][board2048.getComplexity()];
        for (int row = 0; row != board2048.getComplexity(); row++) {
            for (int col = 0; col != board2048.getComplexity(); col++) {
                columnTiles[row][col] = tiles[col][row];
            }
        }
        switch (move) {
            case Game2048Activity.LEFT:
                for (Tile2048[] line : tiles) {
                    Tile2048[] lineCopy = line.clone();
                    board2048.leftCombine(lineCopy);
                    for (int i = 0; i < line.length; i++){
                        if (line[i].getId() != lineCopy[i].getId()){
                            return true;
                        }
                    }
                }
                break;
            case Game2048Activity.RIGHT:
                for (Tile2048[] line : tiles) {
                    Tile2048[] lineCopy = line.clone();
//                    board2048.rightCombine(line);
                    Tile2048[] reverseLine = new Tile2048[line.length];
                    for (int j = 0; j < line.length; j++) {
                        reverseLine[j] = line[line.length - 1 - j];
                    }
                    board2048.leftCombine(reverseLine);
                    for (int k = 0; k < line.length; k++) {
                        line[k] = reverseLine[line.length - 1 - k];
                    }
                    for (int i = 0; i < line.length; i++){
                        if (line[i].getId() != lineCopy[i].getId()){
                            return true;
                        }
                    }
                }
                break;
            case Game2048Activity.UP:
                for (Tile2048[] line : columnTiles) {
                    Tile2048[] lineCopy = line.clone();
                    board2048.leftCombine(line);
                    for (int i = 0; i < line.length; i++){
                        if (line[i].getId() != lineCopy[i].getId()){
                            return true;
                        }
                    }
                }
                break;
            case Game2048Activity.DOWN:
                for (Tile2048[] line : columnTiles) {
                    Tile2048[] lineCopy = line.clone();
//                    board2048.rightCombine(line);
                    Tile2048[] reverseLine = new Tile2048[line.length];
                    for (int j = 0; j < line.length; j++) {
                        reverseLine[j] = line[line.length - 1 - j];
                    }
                    board2048.leftCombine(reverseLine);
                    for (int k = 0; k < line.length; k++) {
                        line[k] = reverseLine[line.length - 1 - k];
                    }
                    for (int i = 0; i < line.length; i++){
                        if (line[i].getId() != lineCopy[i].getId()){
                            return true;
                        }
                    }
                }
                break;
        }
        return false;
    }

    public void touchMove(int move) {

        List<Tile2048> tiles = new ArrayList<>();
        for (int i = 0; i < board2048.getComplexity(); i++) {
            for (int j = 0; j < board2048.getComplexity(); j++) {
                tiles.add(new Tile2048(board2048.getTile(i, j).getId()));
            }
        }
        Board2048 newBoard = new Board2048(tiles);
        boardStack.add(newBoard);

        board2048.swipeMove(move);
        board2048.addRandomTile();
    }

    /**
     * Undo last move and return true if undo successfully, false if it has been initial states.
     *
     * @return whether the current can be undoed.
     */
    public boolean undo() {
        if (boardStack.isEmpty()) {
            return false;
        } else {
            Board2048 newBoard2048 = boardStack.pop();
            board2048.applyBoard(newBoard2048);
            timesOfUndo++;
            return true;
        }
    }

    public int getStepNum(){
        return timesOfUndo+boardStack.size();
    }

    /**
     * Return the last timer counts.
     */
    public int getLastTime() {
        return lastTime;
    }

    /**
     * Set lastTime at lastTime.
     */
    public void setLastTime(int lastTime) {
        this.lastTime = lastTime;
    }

    /**
     * Return the times of undos.
     */
    public int getTimesOfUndo() {
        return this.timesOfUndo;
    }
    /**
     * Return the score.
     */
    public int getScore() {
        return score;
    }

    @Override
    public String getCurrentGame() {
        return "tf";
    }

}


