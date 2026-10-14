package lab6;

/**
 * Главный класс лабораторной работы №6.
 * Задача 18: пирамидальная сортировка.
 *
 * Отсортировать последовательность так, чтобы вначале шли
 * отсортированные чётные элементы, а затем отсортированные нечётные.
 */
public class Lab6 {

    public static void main(String[] args) throws Exception {
        System.out.println("================================================");
        System.out.println("Задача 18 (ЛР №6): пирамидальная сортировка");
        System.out.println("================================================\n");

        System.out.println("Сначала идут отсортированные чётные,");
        System.out.println("затем — отсортированные нечётные.");
        System.out.println("Вводятся только положительные числа.\n");

        // 1. ввод массива
        int[] arr = InputReader.readArray();

        // 2. вывод исходного массива
        System.out.print("\nИсходный массив: ");
        printArray(arr);

        // 3. решение задачи 18
        int[] result = HeapSort.solveTask18(arr);

        // 4. вывод результата
        System.out.print("Результат:       ");
        printArray(result);

        // 5. отдельно показать чётные и нечётные
        System.out.println("\nРазбор:");
        System.out.print("  Чётные (отсортированы): ");
        printEvenOdd(result, true);
        System.out.print("  Нечётные (отсортированы): ");
        printEvenOdd(result, false);
    }

    /**
     * Выводит массив в формате [1, 2, 3, 4].
     */
    private static void printArray(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    /**
     * Выводит только чётные или только нечётные элементы.
     */
    private static void printEvenOdd(int[] arr, boolean even) {
        System.out.print("[");
        boolean first = true;
        for (int i = 0; i < arr.length; i++) {
            boolean isEven = (arr[i] % 2 == 0);
            if (isEven == even) {
                if (!first) {
                    System.out.print(", ");
                }
                System.out.print(arr[i]);
                first = false;
            }
        }
        System.out.println("]");
    }
}