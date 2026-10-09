package lab5;

/**
 * Класс для чтения данных из System.in.
 * Без библиотек — только System.in.read().
 *
 * Проверки:
 * - СНИЛС: ровно 11 цифр
 * - ФИО: не пустая строка, только буквы и пробелы
 * - команды меню: 1..7
 */
public class InputReader {

    /**
     * Читает одну строку из System.in.
     */
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

    /**
     * Читает СНИЛС (11 цифр).
     */
    public static String readSnils() throws Exception {
        while (true) {
            System.out.print("Введите СНИЛС (11 цифр): ");
            String line = readLine();

            if (line == null) {
                System.out.println("Ошибка: конец ввода.\n");
                continue;
            }

            line = line.trim();

            if (line.isEmpty()) {
                System.out.println("Ошибка: пустая строка.\n");
                continue;
            }

            if (!isSnils(line)) {
                System.out.println("Ошибка: СНИЛС должен состоять ровно из 11 цифр.\n");
                continue;
            }

            return line;
        }
    }

    /**
     * Проверяет, что строка — корректный СНИЛС.
     */
    private static boolean isSnils(String s) {
        if (s.length() != 11) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    /**
     * Читает ФИО.
     */
    public static String readFio() throws Exception {
        while (true) {
            System.out.print("Введите ФИО: ");
            String line = readLine();

            if (line == null) {
                System.out.println("Ошибка: конец ввода.\n");
                continue;
            }

            line = line.trim();

            if (line.isEmpty()) {
                System.out.println("Ошибка: пустая строка.\n");
                continue;
            }

            if (!isFio(line)) {
                System.out.println("Ошибка: ФИО должно содержать только буквы и пробелы.\n");
                continue;
            }

            return line;
        }
    }

    /**
     * Проверяет, что строка — корректное ФИО.
     */
    private static boolean isFio(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            boolean isLetter = (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')
                    || (c >= 'А' && c <= 'Я') || (c >= 'а' && c <= 'я');
            boolean isSpace = (c == ' ');
            if (!isLetter && !isSpace) {
                return false;
            }
        }
        return true;
    }

    /**
     * Читает команду меню (1..7).
     */
    public static int readMenuChoice() throws Exception {
        while (true) {
            System.out.print("Ваш выбор: ");
            String line = readLine();

            if (line == null) {
                return -1;
            }

            line = line.trim();

            if (line.isEmpty()) {
                System.out.println("Ошибка: пустая строка.\n");
                continue;
            }

            if (line.length() != 1) {
                System.out.println("Ошибка: введите одну цифру от 1 до 7.\n");
                continue;
            }

            char c = line.charAt(0);
            if (c < '1' || c > '7') {
                System.out.println("Ошибка: команда от 1 до 7.\n");
                continue;
            }

            return c - '0';
        }
    }
}