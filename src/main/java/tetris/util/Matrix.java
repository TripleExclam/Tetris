package tetris.util;

import tetris.block.Blocks;
import tetris.block.Tile;

public class Matrix{

    private Tile[][] matrix;

    public Matrix(int width, int height, Tile type) {
        matrix = new Tile[height][width];
        mapTile(type);
    }

    private void mapTile(Tile type) {

        int encoding = type.getEncoding();

        for (int i = getHeight(); i > 0; i--) {
            for (int j = getWidth(); j > 0; j--) {
                set(getHeight() - i, getWidth() - j,
                        (((encoding >> (i * getWidth() - j)) & 1) == 1) ? type : Tile.EMP);
            }
        }
    }

    public boolean checkIntersect(Matrix toAdd, int top, int left) {
        int height = Math.min(top + toAdd.getHeight(), getHeight());
        int width = Math.min(left + toAdd.getWidth(), getWidth());
        int startHeight = Math.max(0, top);
        int startWidth = Math.max(0, left);

        for (int i = startHeight; i < height; i++) {
            for (int j = startWidth; j < width; j++) {
                if (!get(i, j).equals(Tile.EMP) &&
                        !toAdd.get(i - top, j - left).equals(Tile.EMP)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setPiece(Matrix setPiece, int top, int left) {
        for (int i = 0; i < Blocks.getLength(); i++) {
            for (int j = 0; j < Blocks.getLength(); j++) {
                if (!setPiece.get(i, j).equals(Tile.EMP)) {
                    set(top + i, left + j, setPiece.get(i, j));
                }
            }
        }
    }

    public int getWidth() {
        return (getHeight() > 0 ) ? matrix[0].length : 0;
    }

    public int getHeight() {
        return matrix.length;
    }

    public Tile get(int row, int column) {
        return matrix[row][column];
    }

    public void set(int row, int column, Tile value) {
        matrix[row][column] = value;
    }

    @Override
    public String toString() {

        StringBuilder matrix = new StringBuilder();

        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {

                if (!get(i, j).equals(Tile.EMP)) {
                    matrix.append(get(i, j).toString());
                } else {
                    matrix.append(" e ");
                }
                matrix.append(" ");

            }
            matrix.append("\n");
        }

        return matrix.toString();
    }

    public static void main(String[] args) {
        Matrix test = new Matrix(12, 30, Tile.EMP);
        System.out.println(test);
    }



}
