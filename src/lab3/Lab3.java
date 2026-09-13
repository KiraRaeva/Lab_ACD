package lab3;

/**
 * Задача 21.
 * Дано бинарное дерево.
 * Вывести количество листьев дерева, являющихся правыми дочерними вершинами.
 */
public class Lab3 {

    public static void main(String[] args) throws Exception {
        System.out.println("Задача 21 (ЛР №3): подсчёт правых листьев в бинарном дереве\n");

        System.out.println("Дерево вводится в скобочной записи.");
        System.out.println("Формат: значение(левое,правое)");
        System.out.println("Пример: A(B(D,E),C(,F))");
        System.out.println("Пустое поддерево — пропуск (например, A(,C))");
        System.out.println("Значения — буквы, цифры или их комбинации.");
        System.out.println();

        // 1. Ввод дерева
        String treeStr = InputReader.readTree();

        // 2. Парсинг дерева
        BinaryTree tree = new BinaryTree();
        boolean ok = tree.parse(treeStr);

        if (!ok) {
            System.out.println("Ошибка: не удалось разобрать дерево.");
            System.out.println("Проверьте правильность скобочной записи.");
            return;
        }

        // 3. Вывод дерева (для проверки)
        System.out.print("\nВведённое дерево: ");
        tree.print();

        // 4. Решение задачи 21
        int count = tree.countRightLeaves();

        // 5. Вывод результата
        System.out.println("\nКоличество правых листьев: " + count);
    }
}