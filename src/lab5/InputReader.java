package lab5;

/**
 * Класс для чтения данных из System.in.
 * Без библиотек — только System.in.read().
 * Проверки.
 */
public class InputReader {

    /**
     /**
     * Читает одну строку из System.in, собирая байты вручную.
     */
    public static String readLine() throws Exception {
        byte[] buffer = new byte[1024];
        int bytesRead = 0;
        int ch;

        while ((ch = System.in.read()) != -1) {
            if (ch == '\n') {
                break;
            }
            if (ch != '\r') {
                // Если буфер переполнился, расширяем его
                if (bytesRead >= buffer.length) {
                    byte[] newBuffer = new byte[buffer.length * 2];
                    System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
                    buffer = newBuffer;
                }
                buffer[bytesRead++] = (byte) ch;
            }
        }

        if (ch == -1 && bytesRead == 0) {
            return null;
        }

        // Преобразуем накопленные байты в строку UTF-8
        return new String(buffer, 0, bytesRead, "UTF-8");
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
    /**
     * Проверяет, что строка — корректное ФИО (только русские/английские буквы и пробелы).
     */
    private static boolean isFio(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            // Английские буквы A-Z и a-z
            boolean isEnglishLetter = (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');

            // Основной диапазон русских букв А-Я и а-я
            boolean isRussianLetter = (c >= 'А' && c <= 'Я') || (c >= 'а' && c <= 'я');



            // Пробел
            boolean isSpace = (c == ' ');

            // Если символ не подошёл ни под одно правило — строка невалидна
            if (!isEnglishLetter && !isRussianLetter  && !isSpace) {
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