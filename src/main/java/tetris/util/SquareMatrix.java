package tetris.util;

import tetris.block.Tile;

public class SquareMatrix extends Matrix {

    public SquareMatrix(int length, Tile type) {
        super(length, length, type);
    }

    public int getLength() {
        return getHeight();
    }

    public static void main(String[] args) {
        int len = Tile.values().length;
        SquareMatrix[] tiles = new SquareMatrix[len];

        for (int i = 0; i < len; i++) {
        }




    }



}
