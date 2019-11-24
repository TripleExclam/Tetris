package tetris.util;

import tetris.block.Tile;

public class SquareMatrix extends Matrix {

    public SquareMatrix(int length, Tile type) {
        super(length, length, type);
    }

    public void leftRotate() {
        Tile temp;
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                if (j > i) {
                    temp = get(j, i);
                    set(j, i, get(i, j));
                    set(i, j, temp);
                }
            }
        }
        reflectX();
    }

    public void rightRotate() {
        Tile temp;
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                if (i + j < getWidth()) {
                    temp = get(j, i);
                    set(j, i, get(getWidth() - i - 1, getWidth() - j - 1));
                    set(getWidth() - i - 1, getWidth() - j - 1, temp);
                }
            }
        }
        reflectX();
    }

    public void reflectY() {
        Tile temp;
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                if (j <= getWidth() / 2) {
                    temp = get(i, j);
                    set(i, j, get(i, getWidth() - j - 1));
                    set(i, getWidth() - j - 1, temp);


                }
            }
        }
    }

    public void reflectX() {
        Tile temp;
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                if (i < getWidth() / 2) {
                    temp = get(i, j);
                    set(i, j, get(getWidth() - i - 1, j));
                    set(getWidth() - i - 1, j, temp);
                }
            }
        }
    }

    public static void main(String[] args) {
        Matrix test = new Matrix(12, 30, Tile.EMP);


        SquareMatrix square = new SquareMatrix(5, Tile.BAR);
        square.leftRotate();
        SquareMatrix zag = new SquareMatrix(5, Tile.ZAG);

        System.out.println(zag);
        zag.rightRotate();
        System.out.println(square);

    }



}
