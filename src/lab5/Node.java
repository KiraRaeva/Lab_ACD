package lab5;

/**
 * Узел бинарного дерева поиска (BST).
 * Хранит ключ (СНИЛС), значение (ФИО) и ссылки на детей.
 */
public class Node {

    String key;      // СНИЛС (11 цифр)
    String value;    // ФИО
    Node left;       // левый ребёнок (меньше)
    Node right;      // правый ребёнок (больше)

    public Node(String key, String value) {
        this.key = key;
        this.value = value;
        this.left = null;
        this.right = null;
    }
}