package tetris.block;

import tetris.board.TetrisBoard;

public class TetrisBlock extends Blocks {
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
        this(type, TetrisBoard.getWidth() / 2 - Blocks.getLength() / 2 - 1,
                -Blocks.getLength() / 2);
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
                if (!getTile().get(i, j).equals(Tile.EMP)) {
                    return yCoord + i;
                }
            }
        }
        return yCoord;
    }

    public int getLeftMostX() {
        for (int i = 0; i < getTile().getWidth(); i++) {
            for (int j = 0; j < getTile().getHeight(); j++) {
                if (!getTile().get(j, i).equals(Tile.EMP)) {
                    return xCoord + i;
                }
            }
        }
        return xCoord;
    }

    public int getRightMostX() {
        for (int i = getTile().getWidth() - 1; i >= 0; i--) {
            for (int j = 0; j < getTile().getHeight(); j++) {
                if (!getTile().get(j, i).equals(Tile.EMP)) {
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
        return getTile().get(y - yCoord, x - xCoord) == Tile.EMP;
    }

}
