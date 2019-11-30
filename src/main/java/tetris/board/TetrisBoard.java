package tetris.board;

import tetris.block.TetrisBlock;
import tetris.block.Tile;
import tetris.game.Action;
import tetris.util.Matrix;

import java.util.Random;

public class TetrisBoard {
    // Dimensional constants
    private static final int HEIGHT = 20;
    private static final int WIDTH = 12;
    private static final int SPEED_INCREASE = 2;
    private Random RANDOM;

    // Number of raw ticks before Board tick
    private int speedValue = 50;

    // The visible game space
    private Matrix board;

    // Blocks to be drawn on and around the board.
    private TetrisBlock currentPiece;
    private TetrisBlock nextPiece;

    // Whether or not the block is accelerated
    private boolean speed;


    /**
     * Initialises a new board with randomly selected pieces.
     */
    public TetrisBoard() {
        RANDOM = new Random(69);
        board = new Matrix(WIDTH, HEIGHT, Tile.EMP);
        currentPiece = new TetrisBlock(Tile.getRandom(RANDOM));
        nextPiece = new TetrisBlock(Tile.getRandom(RANDOM));
        speed = false;
    }

    public TetrisBoard(Matrix board) {
        this();
        this.board = board;
    }

    public TetrisBoard copy() {
        return new TetrisBoard(board.copy());
    }

    /**
     * Retrieve the next pieces, with default positioning.
     *
     * @return The next block to draw.
     */
    public TetrisBlock getNextPiece() {
        TetrisBlock block = nextPiece.copy();

        block.setxCoord(nextPiece.getLength() / 2);
        block.setyCoord(nextPiece.getLength() / 2);

        return block;
    }

    public TetrisBlock getPiece() {
        return currentPiece;
    }

    public Matrix getBoard() {
        return board;
    }

    public void setPiece() {
        currentPiece = nextPiece;
        nextPiece = new TetrisBlock(Tile.getRandom(RANDOM));
    }

    /**
     * Tick the board
     *
     * @param tick
     * @return
     */
    public boolean tickBoard(int tick) {
        int time = (speed) ? SPEED_INCREASE : speedValue;

        if (tick % (time) == 0) {
            return executeMoveDown(); // If a block runs into an obstacle true.
        }

        return false;
    }

    public void lockPiece(TetrisBlock currentPiece) {
        Matrix board = currentPiece.getTile();

        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                if (!board.get(i, j).equals(Tile.EMP)) {
                    this.board.set(currentPiece.getyCoord() + i,
                            currentPiece.getxCoord()  + j, board.get(i, j));
                }
            }
        }

    }

    /**
     * Move the current piece down one tile.
     *
     * @return true if piece is lowered, false otherwise
     */
    private boolean executeMoveDown() {
        currentPiece.setyCoord(currentPiece.getyCoord() + 1);
        if (isIntersecting() || getPiece().getHighestY() >= getHeight() || !canMove()) {

            currentPiece.setyCoord(currentPiece.getyCoord() - 1);
            lockPiece(getPiece());
            return true;
        }

        return false;
    }

    public boolean isIntersecting() {
        TetrisBlock block = getPiece();
        int height = Math.min(block.getyCoord() + block.getLength(), getHeight());
        int width = Math.min(block.getxCoord()  + block.getLength(), getWidth());
        int startHeight = Math.max(0, block.getyCoord());
        int startWidth = Math.max(0, block.getxCoord());

        for (int i = startHeight; i < height; i++) {
            for (int j = startWidth; j < width; j++) {
                if (!board.isEmptyCell(i, j) && !block.getTile().isEmptyCell(
                        i - block.getyCoord(), j - block.getxCoord())) {
                    return true;
                }
            }
        }
        return false;


    }

    public void increaseLevel() {
        if (speedValue > 10) {
            speedValue--;
        }
    }

    private boolean canMove() {
        if (getPiece().getLeftMostX() < 0 || getPiece().getRightMostX() >= getWidth()
                || getPiece().getHighestY() >= getHeight()) {
            return false;
        }
        return true;
    }

    public void move(Action move) {
        switch (move)  {
            case LEFT:
                getPiece().setxCoord(getPiece().getxCoord() - 1);
                break;
            case RIGHT:
                getPiece().setxCoord(getPiece().getxCoord() + 1);
                break;
            case ROTATE_LEFT:
                getPiece().rotate(Action.ROTATE_LEFT);
                break;
            case ROTATE_RIGHT:
                getPiece().rotate(Action.ROTATE_RIGHT);
                break;
            case DOWN:
                speed = true;
                break;
            case UP:
                speed = false; // When down is released
                break;
            case NONE:
                return;
        }

        if (!canMove() || isIntersecting()) {
            move(move.getOpposite());
        }
    }

    public int clearLines() {
        int row = getHeight() - 1;
        int cleared = 0;
        int rowCount;

        // No row can contain tiles above an empty row.
        while ((rowCount = board.getRowCount(row)) != 0) {
            if (rowCount == getWidth()) {
                cleared++;
                board.collapseRow(row++); // Row remains unchanged collapse.
            }
            row--;
        }

        return cleared;
    }

    public static int getHeight() {
        return HEIGHT;
    }

    public static int getWidth() {
        return WIDTH;
    }
}
