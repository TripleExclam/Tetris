package tetris.board;

import tetris.block.TetrisBlock;
import tetris.block.Tile;
import tetris.game.Action;
import tetris.util.Matrix;

public class TetrisBoard {
    // Dimensional constants
    private static final int HEIGHT = 20;
    private static final int WIDTH = 12;

    private int speedValue = 50;
    private Matrix board;
    private TetrisBlock currentPiece;
    private TetrisBlock nextPiece;
    private boolean speed;

    public TetrisBoard() {
        board = new Matrix(WIDTH, HEIGHT, Tile.EMP);
        currentPiece = new TetrisBlock(Tile.getRandom());
        nextPiece = new TetrisBlock(Tile.getRandom());
        speed = false;
    }

    public TetrisBlock getNextPiece() {
        TetrisBlock block = nextPiece.copy();

        block.setxCoord(1);
        block.setyCoord(1);

        return block;
    }

    public TetrisBlock getPiece() {
        return currentPiece;
    }

    public Matrix getBoard() {
        return board;
    }

    public void setPiece(TetrisBlock block) {
        currentPiece = nextPiece;
        nextPiece = block;
    }

    /**
     * Update a block.
     *
     * @return Whether a new block is required
     */
    public boolean tickBlock(int tick) {
        if (tick % ((speed) ? speedValue / 5 : speedValue) != 0) {
            return false;
        }

        if (executeMoveDown()) {
            return false;
        }

        board.setPiece(currentPiece.getTile(), currentPiece.getyCoord(), currentPiece.getxCoord());
        return true;
    }

    public void increaseLevel() {
        speedValue -= 5;
    }

    private boolean executeMoveDown() {
        if (board.checkIntersect(currentPiece.getTile(), currentPiece.getyCoord() + 1,
                currentPiece.getxCoord()) && getPiece().getHighestY() < getHeight() - 1) {
            currentPiece.setyCoord(currentPiece.getyCoord() + 1);
            return true;
        }

        return false;
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
                getPiece().getTile().leftRotate();
                break;
            case ROTATE_RIGHT:
                getPiece().getTile().rightRotate();
                break;
            case DOWN:
                speed = true;
                break;
            case UP:
                speed = false; // When down is released
                break;
        }

        if (!canMove() || !board.checkIntersect(currentPiece.getTile(), currentPiece.getyCoord(), currentPiece.getxCoord())) {
            move(move.getOpposite());
        }

    }

    public int clearLines() {
        int row = getHeight() - 1;
        int cleared = 0;
        int rowCount;

        while ((rowCount = board.getRowCount(row)) != 0) {
            if (rowCount == getWidth()) {
                cleared++;
                board.collapseRow(row++);
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
