package lab6;

/**
 * Класс для чтения данных из System.in.
 */
public class InputReader {

    // максимальный размер массива
    private static final int MAX_SIZE = 100;

    // максимальное значение по модулю
    private static final int MAX_ABS_VALUE = 10000;

    // максимальное количество цифр в числе
    private static final int MAX_DIGITS = 5;

    // ================================================================
    // ЧТЕНИЕ СТРОКИ
    // ================================================================
    public static String readLine() throws Exception {
        StringBuilder sb = new StringBuilder();
        int ch;
        while ((ch = System.in.read()) != -1) {
            if (ch == '\n') break;
            if (ch != '\r') sb.append((char) ch);
        }
        if (ch == -1 && sb.length() == 0) return null;
        return sb.toString();
    }

    // ================================================================
    // ЧТЕНИЕ ЦЕЛОГО ЧИСЛА (со знаком)
    // ================================================================
    /**
     * Читает одно целое число.
     */
    public static int readInt() throws Exception {
        String line = readLine();
        if (line == null) return Integer.MIN_VALUE;

        line = line.trim();
        if (line.isEmpty()) return Integer.MIN_VALUE;

        int start = 0;
        int sign = 1;

        // обработка знака
        if (line.charAt(0) == '-') {
            sign = -1;
            start = 1;
        } else if (line.charAt(0) == '+') {
            start = 1;
        }

        // после знака должна быть хотя бы одна цифра
        if (start >= line.length()) return Integer.MIN_VALUE;

        // только цифры
        for (int i = start; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c < '0' || c > '9') return Integer.MIN_VALUE;
        }

        // ограничение длины
        int digitCount = line.length() - start;
        if (digitCount > MAX_DIGITS) return Integer.MIN_VALUE;

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
            System.out.print("Введите размер массива (1.." + MAX_SIZE + "): ");
            int n = readInt();

            if (n == Integer.MIN_VALUE) {
                System.out.println("Ошибка: введите целое число от 1 до " + MAX_SIZE + ".\n");
            } else if (n < 1) {
                System.out.println("Ошибка: размер должен быть >= 1.\n");
            } else if (n > MAX_SIZE) {
                System.out.println("Ошибка: размер должен быть <= " + MAX_SIZE + ".\n");
            } else {
                return n;
            }
        }
    }

    public static int readElement(int index) throws Exception {
        while (true) {
            System.out.print("Элемент " + index + ": ");
            int value = readInt();

            if (value == Integer.MIN_VALUE) {
                System.out.println("Ошибка: введите целое число.");
            } else if (value > MAX_ABS_VALUE || value < -MAX_ABS_VALUE) {
                System.out.println("Ошибка: значение должно быть в диапазоне [-"
                        + MAX_ABS_VALUE + ".." + MAX_ABS_VALUE + "].");
            } else {
                return value;
            }
        }
    }

    public static int[] readArray() throws Exception {
        int n = readSize();
        int[] arr = new int[n];

        System.out.println("Введите " + n + " целых чисел (могут быть отрицательные):");
        for (int i = 0; i < n; i++) {
            arr[i] = readElement(i + 1);
        }

        return arr;
    }
}