package lab3;

/**
 * Класс для чтения данных из System.in.
 * Без библиотек — только System.in.read().
 *
 * Содержит:
 * - чтение строки
 * - чтение дерева в скобочной записи
 * - проверки на корректность
 */
public class InputReader {

    /**
     * Читает одну строку из System.in.
     * Возвращает null, если достигнут конец потока.
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
     * Читает дерево в скобочной записи с проверками.
     *
     * Проверки:
     * - строка не пустая
     * - строка содержит только допустимые символы:
     *   буквы, цифры, '(', ')', ','
     */
    public static String readTree() throws Exception {
        while (true) {
            System.out.print("Введите дерево: ");
            String line = readLine();

            if (line == null) {
                System.out.println("Ошибка: конец ввода.\n");
                continue;
            }

            line = line.trim();

            // проверка: пустая строка
            if (line.isEmpty()) {
                System.out.println("Ошибка: пустая строка. Повторите ввод.\n");
                continue;
            }

            // проверка: допустимые символы
            if (!hasValidChars(line)) {
                System.out.println("Ошибка: строка содержит недопустимые символы.");
                System.out.println("Разрешены только: буквы, цифры, '(', ')', ','.\n");
                continue;
            }

            // проверка: скобки сбалансированы
            if (!hasBalancedBrackets(line)) {
                System.out.println("Ошибка: скобки не сбалансированы.\n");
                continue;
            }

            // проверка: запятые стоят внутри скобок и не в начале/конце
            if (!hasCorrectCommas(line)) {
                System.out.println("Ошибка: неверное расположение запятых.\n");
                continue;
            }

            return line;
        }
    }

    /**
     * Проверяет, что все символы — допустимые.
     * Разрешены: буквы, цифры, '(', ')', ','
     */
    private static boolean hasValidChars(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            boolean isLetter = (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
            boolean isDigit = (c >= '0' && c <= '9');
            boolean isBracket = (c == '(' || c == ')' || c == ',');
            if (!isLetter && !isDigit && !isBracket) {
                return false;
            }
        }
        return true;
    }

    /**
     * Проверяет, что скобки сбалансированы.
     */
    private static boolean hasBalancedBrackets(String s) {
        int balance = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') balance++;
            if (c == ')') balance--;
            if (balance < 0) return false;
        }
        return balance == 0;
    }

    /**
     * Проверяет, что запятые стоят правильно:
     * - только внутри скобок
     * - не в начале и не в конце строки
     * - не идут подряд
     * - между запятыми должно быть значение (хотя бы пусто)
     */
    private static boolean hasCorrectCommas(String s) {
        // запятая не может быть первым или последним символом
        if (s.charAt(0) == ',' || s.charAt(s.length() - 1) == ',') {
            return false;
        }

        // запятая должна быть внутри скобок
        int balance = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') balance++;
            if (c == ')') balance--;
            if (c == ',' && balance == 0) {
                return false;
            }
        }

        // две запятые подряд — ошибка
        for (int i = 0; i < s.length() - 1; i++) {
            if (s.charAt(i) == ',' && s.charAt(i + 1) == ',') {
                return false;
            }
        }

        return true;
    }
}