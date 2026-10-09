package course;

/**
 * Утилита для перевода координат между шашечной нотацией и внутренними индексами.
 * Нотация: буква столбца (a-h) + цифра строки (1-8), например "c3".
 * Внутренние индексы: row 0..7 (0 — верх), col 0..7 (0 — лево).
 */
public class Coord {

    /**
     * Переводит строку вида "c3" во внутренние координаты.
     * Возвращает массив из двух чисел {row, col} или null, если строка некорректна.
     */
    public static int[] parse(String s) {
        if (s == null || s.length() != 2) return null;

        char letter = Character.toLowerCase(s.charAt(0));
        char digit = s.charAt(1);

        if (letter < 'a' || letter > 'h') return null;
        if (digit < '1' || digit > '8') return null;

        int col = letter - 'a';          // a=0, b=1, ..., h=7
        int row = 8 - (digit - '0');     // 8->0, 7->1, ..., 1->7

        return new int[]{row, col};
    }

    /**
     * Переводит внутренние координаты в строку вида "c3".
     */
    public static String format(int row, int col) {
        char letter = (char) ('a' + col);
        char digit = (char) ('0' + (8 - row));
        return "" + letter + digit;
    }
}