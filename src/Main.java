/**
 * Главный класс.
 * Читает список целых чисел с клавиатуры (через System.in.read)
 * и проверяет, является ли он симметричным (задача 21).
 */
public class Main {

    // максимальное количество элементов в списке
    private static final int MAX_SIZE = 1000;

    // максимальная длина одного числа (в символах)
    private static final int MAX_DIGITS = 9;

    public static void main(String[] args) throws Exception {
        System.out.println("Задача 21: проверка симметричности двусвязного списка\n");

        int n = readSize();


        DoublyLinkedList list = new DoublyLinkedList();

        if (n > 0) {
            System.out.println("Введите " + n + " целых чисел (каждое на новой строке):");
            for (int i = 0; i < n; i++) {
                int value = readValue(i + 1);
                list.add(value);
            }
        }


        System.out.print("\nВведённый список: ");
        list.printList();
        System.out.println("Симметричный? " + (list.isSymmetric() ? "да" : "нет"));
    }


    private static int readSize() throws Exception {
        while (true) {
            System.out.print("Введите количество элементов (0.." + MAX_SIZE + "): ");
            int n = readInt();

            if (n == Integer.MIN_VALUE) {
                System.out.println("Ошибка: введите корректное целое число.\n");
            } else if (n < 0) {
                System.out.println("Ошибка: количество не может быть отрицательным.\n");
            } else if (n > MAX_SIZE) {
                System.out.println("Ошибка: слишком много элементов (максимум " + MAX_SIZE + ").\n");
            } else {
                return n;
            }
        }
    }

    /**
     * Читает одно значение элемента с проверками.
     * @param index - номер элемента (для сообщения)
     * @return корректное целое число
     */
    private static int readValue(int index) throws Exception {
        while (true) {
            System.out.print("Элемент " + index + ": ");
            int value = readInt();

            if (value == Integer.MIN_VALUE) {
                System.out.println("Ошибка: введите корректное целое число.");
            } else {
                return value;
            }
        }
    }

    /**
     * Читает одну строку из System.in.
     * Возвращает null, если достигнут конец потока.
     */
    private static String readLine() throws Exception {
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
     * Читает одно целое число из System.in.
     *
     * Логика:
     * 1. Читает строку целиком.
     * 2. Убирает пробелы в начале и конце.
     * 3. Проверяет, что строка состоит ТОЛЬКО из цифр (и, возможно, минуса в начале).
     * 4. Если есть лишние символы (буквы, точки, плюсы) — ошибка.
     * 5. Проверяет длину числа (не больше MAX_DIGITS).
     * 6. Проверяет, что число влезает в int.
     *
     * Возвращает:
     *   - число, если всё корректно
     *   - Integer.MIN_VALUE, если ввод некорректен
     */
    private static int readInt() throws Exception {
        String line = readLine();

        // конец потока
        if (line == null) {
            return Integer.MIN_VALUE;
        }

        // убираем пробелы в начале и конце
        line = line.trim();

        // пустая строка (пользователь нажал Enter)
        if (line.isEmpty()) {
            return Integer.MIN_VALUE;
        }

        // проверяем, что строка — корректное число
        int start = 0;
        int sign = 1;

        // минус в начале
        if (line.charAt(0) == '-') {
            sign = -1;
            start = 1;
        }

        // после минуса должна быть хотя бы одна цифра
        if (start >= line.length()) {
            return Integer.MIN_VALUE;
        }

        // проверяем, что все символы — цифры
        for (int i = start; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c < '0' || c > '9') {
                return Integer.MIN_VALUE; // нашли не-цифру → ошибка
            }
        }

        // проверяем длину числа
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

        // проверяем, влезает ли в int
        if (number > Integer.MAX_VALUE || number < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }

        return (int) number;
    }
}