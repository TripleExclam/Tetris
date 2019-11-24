package tetris.board;

import tetris.block.TetrisBlock;
import tetris.block.Tile;
import tetris.game.Action;
import tetris.util.Matrix;

public class TetrisBoard {
    // Dimensional constants
    private static final int HEIGHT = 30;
    private static final int WIDTH = 24;
    private static final int INIT_SPEED = 50;

    private Matrix board;
    private TetrisBlock piece;
    private boolean speed;

    public TetrisBoard() {
        board = new Matrix(WIDTH, HEIGHT, Tile.EMP);
        piece = new TetrisBlock(Tile.RIT);
        speed = false;
    }

    public TetrisBlock getPiece() {
        return piece;
    }

    public Matrix getBoard() {
        return board;
    }

    public void setPiece(TetrisBlock block) {
        piece = block.copy();
    }

    /**
     * Update a block.
     *
     * @return Whether a new block is required
     */
    public boolean tickBlock(int tick) {
        if (tick % ((speed) ? INIT_SPEED / 5 : INIT_SPEED) != 0) {
            return false;
        }

        if (executeMoveDown()) {
            return false;
        }

        board.setPiece(piece.getTile(), piece.getyCoord(), piece.getxCoord());
        return true;
    }

    private boolean executeMoveDown() {
        if (board.checkIntersect(piece.getTile(), piece.getyCoord() + 1,
                piece.getxCoord()) && getPiece().getHighestY() < getHeight() - 1) {
            piece.setyCoord(piece.getyCoord() + 1);
            return true;
        }

        return false;
    }

    private boolean canMove() {
        if (getPiece().getLeftMostX() < 0 || getPiece().getRightMostX() >= getWidth()) {
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
        if (!canMove()) {
            move(move.getOpposite());
        }
        if (!board.checkIntersect(piece.getTile(), piece.getyCoord(), piece.getxCoord())) {
            move(move.getOpposite());
        }

    }

    public static int getHeight() {
        return HEIGHT;
    }

    public static int getWidth() {
        return WIDTH;
    }
}
