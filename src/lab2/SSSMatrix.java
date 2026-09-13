package lab2;

/**
 * Разреженная структурно-симметричная матрица (SSS).
 */
public class SSSMatrix {

    private int[][] matrix;  // двумерный массив
    private int size;        // размер (матрица квадратная size x size)


    public SSSMatrix(int size) {
        this.size = size;
        this.matrix = new int[size][size];
    }


    public int getSize() {
        return size;
    }


    public void set(int i, int j, int value) {
        matrix[i][j] = value;
    }


    public int get(int i, int j) {
        return matrix[i][j];
    }



    public boolean isStructurallySymmetric() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boolean upper = matrix[i][j] != 0;
                boolean lower = matrix[j][i] != 0;
                if (upper != lower) {
                    return false;
                }
            }
        }
        return true;
    }


    public void print() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }
}