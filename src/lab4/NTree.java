package lab4;

/**
 * N-дерево (N-арное дерево).
 *
 * Содержит:
 * - парсер скобочной записи
 * - автоматическую нумерацию вершин
 * - задачу 21 (перекраска вершин с нечётными номерами)
 * - вывод дерева
 */
public class NTree {

    private Node root;
    private int position;
    private String input;
    private int numberCounter;

    public NTree() {
        this.root = null;
    }

    public Node getRoot() {
        return root;
    }



    public boolean parse(String s) {
        if (s == null || s.isEmpty()) {
            root = null;
            return true;
        }

        this.input = s;
        this.position = 0;
        this.numberCounter = 1;
        this.root = parseNode();

        return position == input.length();
    }

    /**
     * Рекурсивно парсит один узел.
     */
    private Node parseNode() {
        if (position >= input.length()) {
            return null;
        }

        // 1. значение — ЗАГЛАВНЫЕ буквы
        StringBuilder dataSb = new StringBuilder();
        while (position < input.length()) {
            char c = input.charAt(position);
            if (c >= 'A' && c <= 'Z') {
                dataSb.append(c);
                position++;
            } else {
                break;
            }
        }

        if (dataSb.length() == 0) {
            return null;
        }

        // 2. цвет — одна строчная буква w/b/y
        if (position >= input.length()) {
            return null;
        }

        char color = input.charAt(position);
        if (color != 'w' && color != 'b' && color != 'y') {
            return null;
        }
        position++;

        // 3. создаём узел
        Node node = new Node(dataSb.toString(), color);

        // 4. присваиваем номер автоматически
        node.number = numberCounter;
        numberCounter++;

        // 5. читаем детей
        if (position < input.length() && input.charAt(position) == '(') {
            position++;

            while (true) {
                Node child = parseNode();
                if (child == null) {
                    break;
                }
                node.addChild(child);

                if (position < input.length() && input.charAt(position) == ',') {
                    position++;
                } else {
                    break;
                }
            }

            if (position < input.length() && input.charAt(position) == ')') {
                position++;
            }
        }

        return node;
    }


    /**
     * Задача 21.
     * Перекрашивает вершины с нечётными номерами:
     *   y → b, b → w, w → y
     */
    public void recolorOddNodes() {
        recolorOddNodes(root);
    }

    private void recolorOddNodes(Node node) {
        if (node == null) {
            return;
        }

        if (node.number % 2 != 0) {
            if (node.color == 'y') {
                node.color = 'b';
            } else if (node.color == 'b') {
                node.color = 'w';
            } else if (node.color == 'w') {
                node.color = 'y';
            }
        }

        for (int i = 0; i < node.getChildCount(); i++) {
            recolorOddNodes(node.getChild(i));
        }
    }


    public void printBracket() {
        printBracketNode(root);
        System.out.println();
    }

    private void printBracketNode(Node node) {
        if (node == null) {
            return;
        }
        System.out.print(node.data + node.number + node.color);
        if (node.getChildCount() > 0) {
            System.out.print("(");
            for (int i = 0; i < node.getChildCount(); i++) {
                if (i > 0) {
                    System.out.print(",");
                }
                printBracketNode(node.getChild(i));
            }
            System.out.print(")");
        }
    }


    public void printTree() {
        if (root == null) {
            System.out.println("(пустое дерево)");
            return;
        }
        System.out.println(root.data + root.number + root.color);
        printChildren(root, "");
    }

    private void printChildren(Node node, String prefix) {
        int count = node.getChildCount();
        for (int i = 0; i < count; i++) {
            Node child = node.getChild(i);
            boolean isLast = (i == count - 1);

            if (isLast) {
                System.out.println(prefix + "└── " + child.data + child.number + child.color);
                printChildren(child, prefix + "    ");
            } else {
                System.out.println(prefix + "├── " + child.data + child.number + child.color);
                printChildren(child, prefix + "│   ");
            }
        }
    }


    public void printTable() {
        System.out.println("Вершина | Номер | Цвет");
        System.out.println("--------|-------|------");
        printTableNode(root);
    }

    private void printTableNode(Node node) {
        if (node == null) {
            return;
        }
        System.out.printf("%-7s | %-5d | %c%n", node.data, node.number, node.color);

        for (int i = 0; i < node.getChildCount(); i++) {
            printTableNode(node.getChild(i));
        }
    }
}