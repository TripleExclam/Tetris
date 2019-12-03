package tetris.util;

import tetris.block.Tile;

public class Matrix{

    private Tile[][] matrix;

    public Matrix(int width, int height, Tile type) {
        matrix = new Tile[height][width];
        mapTile(type);
    }

    public Matrix copy() {
        Matrix mat = new Matrix(getWidth(), getHeight(), Tile.EMP);
        mat.manipulate(((matrix, x, y) -> matrix.set(y, x, this.get(y, x))));
        return mat;
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

    public void manipulate(MatrixOperation operation) {
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                operation.action(this, j, i);
            }
        }
    }

    public int getRowCount(int row) {
        if (row >= getHeight() || row < 0) {
            return 0;
        }
        int rowCount = 0;

        for (int i = 0; i < getWidth(); i++) {
            if (!get(row, i).equals(Tile.EMP)) {
                rowCount++;
            }
        }

        return rowCount;
    }

    public void collapseRow(int row) {
        for (int i = row; i > 0; i--) {
            for (int j = 0; j < getWidth(); j++) {
                set(i, j, get(i - 1, j));
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
        if (row < 0 || row >= getHeight() || column < 0 || column >= getWidth()) {
            return;
        }

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

    public boolean isEmpty(int row) {

        for (int j = 0; j < getWidth(); j++) {
            if (!isEmptyCell(row, j)) {
                return false;
            }
        }

        return true;
    }

    public boolean isEmptyCell(int row, int col) {
        return get(row, col).equals(Tile.EMP);
    }

    public static void main(String[] args) {
        Matrix test = new Matrix(12, 30, Tile.EMP);
        System.out.println(test);
    }



}
