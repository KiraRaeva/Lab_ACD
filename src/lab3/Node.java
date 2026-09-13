package lab3;
/**
 * Узел бинарного дерева.
 * Содержит значение (строку) и ссылки на левого и правого ребёнка.
 */
public class Node {

    String data;    // значение узла (может быть "A", "12", "AB", "A1")
    Node left;      // ссылка на левого ребёнка
    Node right;     // ссылка на правого ребёнка


    public Node(String data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}