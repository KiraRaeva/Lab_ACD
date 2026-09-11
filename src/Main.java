import java.util.Scanner;

/**
 * Главный класс.
 * Читает список целых чисел с клавиатуры (через Scanner)
 * и проверяет, является ли он симметричным (задача 21).
 *
 * Защита от некорректного ввода:
 * - принимаются только целые числа
 * - ограничение на количество элементов
 * - защита от пустого ввода
 */
public class Main {

    // максимальное количество элементов в списке
    private static final int MAX_SIZE = 1000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Задача 21: проверка симметричности двусвязного списка\n");

        // --- шаг 1: ввод количества элементов ---
        int n = readSize(scanner);

        // --- шаг 2: ввод самих элементов ---
        DoublyLinkedList list = new DoublyLinkedList();

        if (n > 0) {
            System.out.println("Введите " + n + " целых чисел:");
            for (int i = 0; i < n; i++) {
                int value = readValue(scanner, i + 1);
                list.add(value);
            }
        }

        // --- шаг 3: вывод результата ---
        System.out.print("\nВведённый список: ");
        list.printList();
        System.out.println("Симметричный? " + (list.isSymmetric() ? "да" : "нет"));

        scanner.close();
    }

    /**
     * Читает количество элементов с проверками:
     * - число не может быть отрицательным
     * - число не может быть больше MAX_SIZE
     * @param scanner - объект Scanner для чтения
     * @return корректное количество элементов
     */
    private static int readSize(Scanner scanner) {
        while (true) {
            System.out.print("Введите количество элементов (0.." + MAX_SIZE + "): ");

            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: введите целое число.");
                scanner.next(); // пропускаем некорректный ввод
                continue;
            }

            int n = scanner.nextInt();

            if (n < 0) {
                System.out.println("Ошибка: количество не может быть отрицательным.");
            } else if (n > MAX_SIZE) {
                System.out.println("Ошибка: слишком много элементов (максимум " + MAX_SIZE + ").");
            } else {
                return n;
            }
        }
    }

    /**
     * Читает одно значение элемента с проверками.
     * @param scanner - объект Scanner для чтения
     * @param index - номер элемента (для сообщения)
     * @return корректное целое число
     */
    private static int readValue(Scanner scanner, int index) {
        while (true) {
            System.out.print("Элемент " + index + ": ");

            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: введите целое число.");
                scanner.next(); // пропускаем некорректный ввод
                continue;
            }

            return scanner.nextInt();
        }
    }
}