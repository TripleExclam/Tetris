package tetris.block;

import tetris.util.SquareMatrix;

public abstract class Block {
    private SquareMatrix tile;
    private Tile type;

    public Block(Tile type) {
        tile = new SquareMatrix(type.getDimension(), type);
        this.type = type;
    }

    public SquareMatrix getTile() {
        return tile;
    }

    public Tile getType() {
        return type;
    }

}
