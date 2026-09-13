package lab2;

/**

 * Условие:
 * Дана разреженная матрица (SSS) и число b.
 * Матрица просматривается слева направо, сверху вниз.
 * На места ненулевых элементов матрицы вначале поместить
 * все её ненулевые элементы большие b,
 * а затем ненулевые элементы меньшие b.
 * Элементы не сортировать.
 */
public class Task21 {



    public static void solve(SSSMatrix matrix, int b) {
        int size = matrix.getSize();

        //Считаем количество ненулевых элементов
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix.get(i, j) != 0) {
                    count++;
                }
            }
        }


        if (count == 0) {
            return;
        }


        int[] rows = new int[count];    // строки
        int[] cols = new int[count];    // столбцы
        int[] vals = new int[count];    // значения
        int idx = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix.get(i, j) != 0) {
                    rows[idx] = i;
                    cols[idx] = j;
                    vals[idx] = matrix.get(i, j);
                    idx++;
                }
            }
        }


        int[] newVals = new int[count];
        int pos = 0;

        //группа 1: > b
        for (int k = 0; k < count; k++) {
            if (vals[k] > b) {
                newVals[pos++] = vals[k];
            }
        }

        //группа 2: < b
        for (int k = 0; k < count; k++) {
            if (vals[k] < b) {
                newVals[pos++] = vals[k];
            }
        }

        //группа 3: = b
        for (int k = 0; k < count; k++) {
            if (vals[k] == b) {
                newVals[pos++] = vals[k];
            }
        }

        //Записываем новые значения на те же позиции
        for (int k = 0; k < count; k++) {
            matrix.set(rows[k], cols[k], newVals[k]);
        }
    }
}