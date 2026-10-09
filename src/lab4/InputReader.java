package lab4;

/**
 * Класс для чтения данных из System.in.
 * Без библиотек — только System.in.read().
 *
 * Проверяет корректность скобочной записи N-дерева
 */
public class InputReader {

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

    public static String readTree() throws Exception {
        while (true) {
            System.out.print("Введите дерево: ");
            String line = readLine();

            if (line == null) {
                System.out.println("Ошибка: конец ввода.\n");
                continue;
            }

            line = line.trim();

            if (line.isEmpty()) {
                System.out.println("Ошибка: пустая строка. Повторите ввод.\n");
                continue;
            }

            if (!hasValidChars(line)) {
                System.out.println("Ошибка: недопустимые символы.");
                System.out.println("Разрешены: заглавные буквы (A-Z), строчные w/b/y, '(', ')', ','.\n");
                continue;
            }

            if (!hasBalancedBrackets(line)) {
                System.out.println("Ошибка: скобки не сбалансированы.\n");
                continue;
            }

            if (!hasCorrectCommas(line)) {
                System.out.println("Ошибка: неверное расположение запятых.\n");
                continue;
            }

            return line;
        }
    }

    private static boolean hasValidChars(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            boolean isUpper = (c >= 'A' && c <= 'Z');
            boolean isColor = (c == 'w' || c == 'b' || c == 'y');
            boolean isBracket = (c == '(' || c == ')' || c == ',');
            if (!isUpper && !isColor && !isBracket) {
                return false;
            }
        }
        return true;
    }

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

    private static boolean hasCorrectCommas(String s) {
        if (s.charAt(0) == ',' || s.charAt(s.length() - 1) == ',') {
            return false;
        }

        int balance = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') balance++;
            if (c == ')') balance--;
            if (c == ',' && balance == 0) return false;
        }

        for (int i = 0; i < s.length() - 1; i++) {
            if (s.charAt(i) == ',' && s.charAt(i + 1) == ',') return false;
        }

        return true;
    }
}