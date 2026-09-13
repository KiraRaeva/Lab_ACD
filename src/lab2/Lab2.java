package lab2;

/**
 * Главный класс лабораторной работы №2.
 * Управляет программой: ввод, обработка, вывод.
 */
public class Lab2 {
    public static void main(String[] args) throws Exception {
        System.out.println("Задача 21: SSS-матрица\n");

        // 1. Ввод размера матрицы
        int n = InputReader.readSize();

        // 2. Ввод матрицы с проверкой SSS
        SSSMatrix matrix = InputReader.readMatrix(n);

        // 3. Ввод числа b
        int b = InputReader.readB();

        // 4. Вывод исходной матрицы
        System.out.println("\nИсходная матрица:");
        matrix.print();

        // 5. Решение задачи 21
        Task21.solve(matrix, b);

        // 6. Вывод результата
        System.out.println("\nМатрица после обработки (задача 21):");
        matrix.print();
    }
}