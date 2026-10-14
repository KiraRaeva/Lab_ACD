package lab6;

/**
 * Класс для чтения данных из System.in.
 * Без библиотек — только System.in.read().
 */
public class InputReader {

    private static final int MAX_SIZE = 100;
    private static final int MAX_VALUE = 10000;
    private static final int MAX_DIGITS = 5;

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

    /**
     * Читает одно положительное целое число.
     * Возвращает -1 при ЛЮБОЙ ошибке валидации (буквы, пустота, длина).
     */
    public static int readPositiveInt() throws Exception {
        String line = readLine();
        if (line == null) return -1;

        line = line.trim();
        if (line.isEmpty()) return -1;

        // Валидация: только цифры
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c < '0' || c > '9') return -1;
        }

        if (line.length() > MAX_DIGITS) return -1;

        long number = 0;
        for (int i = 0; i < line.length(); i++) {
            number = number * 10 + (line.charAt(i) - '0');
        }

        if (number > Integer.MAX_VALUE) return -1;
        return (int) number;
    }

    public static int readSize() throws Exception {
        while (true) {
            System.out.print("Введите размер массива (1.." + MAX_SIZE + "): ");
            int n = readPositiveInt();

            if (n < 0) {
                System.out.println("Ошибка: введите корректное целое число.\n");
            } else if (n < 1) {
                System.out.println("Ошибка: размер должен быть >= 1.\n");
            } else if (n > MAX_SIZE) {
                System.out.println("Ошибка: размер должен быть <= " + MAX_SIZE + ".\n");
            } else {
                return n; // Возврат только при успехе
            }
        }
    }

    public static int readElement(int index) throws Exception {
        while (true) {
            System.out.print("Элемент " + index + ": ");
            int value = readPositiveInt();

            // ИСПРАВЛЕНО: Теперь метод не пропускает управление дальше,
            // пока не будет введено полностью валидное число
            if (value < 0) {
                System.out.println("Ошибка: введите целое положительное число.");
            } else if (value > MAX_VALUE) {
                System.out.println("Ошибка: значение должно быть <= " + MAX_VALUE + ".");
            } else {
                return value;
            }
        }
    }

    public static int[] readArray() throws Exception {
        int n = readSize();
        int[] arr = new int[n];

        System.out.println("Введите " + n + " положительных чисел:");
        for (int i = 0; i < n; i++) {
            arr[i] = readElement(i + 1);
        }

        return arr;
    }
}
