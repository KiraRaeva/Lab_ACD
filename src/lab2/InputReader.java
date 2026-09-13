package lab2;

/**
 * Класс для чтения данных из System.in.
 * Реализует ввод без библиотек (только System.in.read()).
 */
public class InputReader {

    // максимальный размер матрицы
    public static final int MAX_SIZE = 50;

    // максимальная длина одного числа (в символах)
    private static final int MAX_DIGITS = 9;




    public static String readLine() throws Exception {
        StringBuilder sb = new StringBuilder();
        int ch;
        while ((ch = System.in.read()) != -1) {
            if (ch == '\n') {
                break;
            }
            if (ch != '\r') {
                sb.append((char) ch);
            }
        }
        if (ch == -1 && sb.length() == 0) {
            return null;
        }
        return sb.toString();
    }


    public static int readIntAny() throws Exception {
        String line = readLine();

        if (line == null) {
            return Integer.MIN_VALUE;
        }

        line = line.trim();

        if (line.isEmpty()) {
            return Integer.MIN_VALUE;
        }

        int start = 0;
        int sign = 1;

        // минус в начале
        if (line.charAt(0) == '-') {
            sign = -1;
            start = 1;
        }
        // плюс в начале — игнорируем
        else if (line.charAt(0) == '+') {
            start = 1;
        }

        // после знака должна быть хотя бы одна цифра
        if (start >= line.length()) {
            return Integer.MIN_VALUE;
        }

        // все символы после знака должны быть цифрами
        for (int i = start; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c < '0' || c > '9') {
                return Integer.MIN_VALUE;
            }
        }

        // длина числа
        int digitCount = line.length() - start;
        if (digitCount > MAX_DIGITS) {
            return Integer.MIN_VALUE;
        }

        // преобразуем в число вручную
        long number = 0;
        for (int i = start; i < line.length(); i++) {
            number = number * 10 + (line.charAt(i) - '0');
        }
        number = sign * number;

        // проверка диапазона int
        if (number > Integer.MAX_VALUE || number < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }

        return (int) number;
    }



    public static int readSize() throws Exception {
        while (true) {
            System.out.print("Введите размер матрицы (1.." + MAX_SIZE + "): ");
            int n = readIntAny();

            if (n == Integer.MIN_VALUE) {
                System.out.println("Ошибка: введите корректное целое число.\n");
            } else if (n < 1) {
                System.out.println("Ошибка: размер должен быть >= 1.\n");
            } else if (n > MAX_SIZE) {
                System.out.println("Ошибка: слишком большой размер (максимум " + MAX_SIZE + ").\n");
            } else {
                return n;
            }
        }
    }



    public static int readValue(int i, int j) throws Exception {
        while (true) {
            System.out.print("A[" + i + "][" + j + "] = ");
            int value = readIntAny();

            if (value == Integer.MIN_VALUE) {
                System.out.println("Ошибка: введите корректное целое число.");
            } else {
                return value;
            }
        }
    }


    public static SSSMatrix readMatrix(int size) throws Exception {
        while (true) {
            SSSMatrix matrix = new SSSMatrix(size);

            System.out.println("Введите матрицу " + size + "x" + size
                    + " (по строкам, " + size + " чисел в строке):");

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    int value = readValue(i, j);
                    matrix.set(i, j, value);
                }
            }

            // проверка структурной симметрии
            if (matrix.isStructurallySymmetric()) {
                return matrix;
            } else {
                System.out.println("\nОшибка: матрица НЕ структурно-симметрична.");
                System.out.println("Для SSS-матрицы должно быть: A[i][j] != 0 <=> A[j][i] != 0");
                System.out.println("Повторите ввод матрицы.\n");
            }
        }
    }


    /**
     * Читает число b (любое целое).
     */
    public static int readB() throws Exception {
        while (true) {
            System.out.print("Введите число b: ");
            int b = readIntAny();

            if (b == Integer.MIN_VALUE) {
                System.out.println("Ошибка: введите корректное целое число.\n");
            } else {
                return b;
            }
        }
    }
}