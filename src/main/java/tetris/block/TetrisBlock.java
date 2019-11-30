package tetris.block;

import tetris.board.TetrisBoard;
import tetris.game.Action;
import tetris.util.MatrixOperation;

public class TetrisBlock extends Block {
    private static final MatrixOperation LEFT = (matrix, x, y) -> {
        Tile temp;
        if (x > y) {
            temp = matrix.get(x, y);
            matrix.set(x, y, matrix.get(y, x));
            matrix.set(y, x, temp);
        }
    };

    private static final MatrixOperation RIGHT = (matrix, x, y) -> {
        Tile temp;
        if (x + y < matrix.getWidth()) {
            temp = matrix.get(y, x);
            matrix.set(y, x, matrix.get(matrix.getWidth() - x - 1,
                    matrix.getWidth() - y - 1));
            matrix.set(matrix.getWidth() - x - 1,
                    matrix.getWidth() - y - 1, temp);
        }
    };

    private static final MatrixOperation FLIP_X = (matrix, x, y) -> {
        Tile temp;
        if (y < matrix.getHeight() / 2) {
            temp = matrix.get(y, x);
            matrix.set(y, x, matrix.get(matrix.getHeight() - y - 1, x));
            matrix.set(matrix.getHeight() - y - 1, x, temp);
        }
    };

    private int xCoord;
    private int yCoord;

    public TetrisBlock(Tile type, int xCoord, int yCoord) {
        super(type);
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public TetrisBlock copy() {
        return new TetrisBlock(getType(), getxCoord(), getyCoord());
    }

    public TetrisBlock(Tile type) {
        this(type, TetrisBoard.getWidth() / 2 - type.getDimension() / 2, -4);
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public int getHighestY() {
        for (int i = getTile().getHeight() - 1; i > 0; i--) {
            for (int j = 0; j < getTile().getWidth(); j++) {
                if (!isEmpty(j, i)) {
                    return yCoord + i;
                }
            }
        }
        return yCoord;
    }

    public int getLeftMostX() {
        for (int i = 0; i < getTile().getWidth(); i++) {
            for (int j = 0; j < getTile().getHeight(); j++) {
                if (!isEmpty(i, j)) {
                    return xCoord + i;
                }
            }
        }
        return xCoord;
    }

    public int getLength() {
        return getTile().getLength();
    }

    public int getRightMostX() {
        for (int i = getTile().getWidth() - 1; i >= 0; i--) {
            for (int j = 0; j < getTile().getHeight(); j++) {
                if (!isEmpty(i, j)) {
                    return xCoord + i;
                }
            }
        }
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public boolean isEmpty(int x, int y) {
        return getTile().isEmptyCell(y, x);
    }

    @Override
    public boolean equals(Object obj) {
        TetrisBlock block = (TetrisBlock) obj;

        return getType().ordinal() == block.getType().ordinal();
    }

    public void rotate(Action rotation) {
        if (getType().equals(Tile.BAR)) {
            getTile().manipulate(RIGHT);
            return;
        }

        if (rotation.equals(Action.ROTATE_RIGHT)) {
            getTile().manipulate(RIGHT);
        } else {
            getTile().manipulate(LEFT);
        }

        getTile().manipulate(FLIP_X);
    }
}
