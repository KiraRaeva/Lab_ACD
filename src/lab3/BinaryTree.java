package lab3;

public class BinaryTree {

    private Node root;        // корень дерева
    private int position;     // текущая позиция при парсинге
    private String input;     // входная строка


    public BinaryTree() {
        this.root = null;
    }

    public Node getRoot() {
        return root;
    }


    /**
     * Парсит скобочную запись и строит дерево.
     *
     * Формат:
     *   значение
     *   значение(левое,правое)
     *   значение(левое,)
     *   значение(,правое)
     *   (пусто)
     */

    public boolean parse(String s) {
        if (s == null || s.isEmpty()) {
            root = null;
            return true;
        }

        this.input = s;
        this.position = 0;
        this.root = parseNode();

        // проверяем, что вся строка разобрана
        return position == input.length();
    }



    private Node parseNode() {
        // конец строки
        if (position >= input.length()) {
            return null;
        }

        // пустое поддерево
        if (input.charAt(position) == ')') {
            return null;
        }


        StringBuilder sb = new StringBuilder();
        while (position < input.length()) {
            char c = input.charAt(position);
            if (c == '(' || c == ',' || c == ')') {
                break;
            }
            sb.append(c);
            position++;
        }

        // Если значение пустое — это ошибка
        if (sb.length() == 0) {
            return null;
        }

        Node node = new Node(sb.toString());

        if (position < input.length() && input.charAt(position) == '(') {
            position++;


            node.left = parseNode();
            if (position < input.length() && input.charAt(position) == ',') {
                position++;
            }


            node.right = parseNode();


            if (position < input.length() && input.charAt(position) == ')') {
                position++;
            }
        }

        return node;
    }



    /**
     * Задача 21.
     * Считает количество листьев, которые являются правыми дочерними вершинами.
     */
    public int countRightLeaves() {
        // корень не является чьим-либо ребёнком
        return countRightLeaves(root, false);
    }


    private int countRightLeaves(Node node, boolean isRight) {
        // пустое поддерево
        if (node == null) {
            return 0;
        }


        boolean isLeaf = (node.left == null && node.right == null);

        if (isLeaf) {

            return isRight ? 1 : 0;
        }


        int leftCount = countRightLeaves(node.left, false);
        int rightCount = countRightLeaves(node.right, true);

        return leftCount + rightCount;
    }


    /**
     * Выводит дерево в виде скобочной записи (для отладки).
     */
    public void print() {
        printNode(root);
        System.out.println();
    }

    private void printNode(Node node) {
        if (node == null) {
            return;
        }

        System.out.print(node.data);

        if (node.left != null || node.right != null) {
            System.out.print("(");
            printNode(node.left);
            System.out.print(",");
            printNode(node.right);
            System.out.print(")");
        }
    }
}