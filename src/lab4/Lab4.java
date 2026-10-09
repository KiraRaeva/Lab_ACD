package lab4;

/**
 * Главный класс лабораторной работы №4.
 * Задача 21: перекраска вершин с нечётными номерами в N-дереве.
 */
public class Lab4 {

    public static void main(String[] args) throws Exception {
        System.out.println("================================================");
        System.out.println("Задача 21 (ЛР №4): N-дерево, перекраска");
        System.out.println("================================================\n");

        System.out.println("Формат ввода: скобочная запись.");
        System.out.println("Каждая вершина: ЗАГЛАВНЫЕ БУКВЫ + строчная буква цвета");
        System.out.println("  - значение: A-Z (заглавные)");
        System.out.println("  - цвет: w=белый, b=чёрный, y=жёлтый (строчные)");
        System.out.println("Пример: Ay(Bb,Cw)");
        System.out.println("Пустая строка — пустое дерево.\n");

        String treeStr = InputReader.readTree();

        NTree tree = new NTree();
        if (!tree.parse(treeStr)) {
            System.out.println("Ошибка: не удалось разобрать дерево.");
            return;
        }

        System.out.println("\n================================================");
        System.out.println("ВВЕДЁННОЕ ДЕРЕВО");
        System.out.println("================================================");
        System.out.println("\nСкобочная запись (с номерами):");
        tree.printBracket();
        System.out.println("\nСтруктура:");
        tree.printTree();
        System.out.println("\nТаблица вершин:");
        tree.printTable();

        tree.recolorOddNodes();

        System.out.println("\n================================================");
        System.out.println("ПОСЛЕ ПЕРЕКРАСКИ");
        System.out.println("================================================");
        System.out.println("\nСкобочная запись (с номерами):");
        tree.printBracket();
        System.out.println("\nСтруктура:");
        tree.printTree();
        System.out.println("\nТаблица вершин:");
        tree.printTable();
    }
}