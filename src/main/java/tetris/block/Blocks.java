package tetris.block;

import tetris.util.SquareMatrix;

public abstract class Blocks {
    private static final int LENGTH = 5;
    private SquareMatrix tile;
    private Tile type;

    public Blocks(Tile type) {
        tile = new SquareMatrix(LENGTH, type);
        this.type = type;
    }

    public SquareMatrix getTile() {
        return tile;
    }

    public Tile getType() {
        return type;
    }

    public static int getLength() {
        return LENGTH;
    }
}
