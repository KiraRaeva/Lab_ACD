package lab5;

/**
 * Бинарное дерево поиска (BST).
 * Используется для разрешения коллизий в хэш-таблице.
 *
 * Правила BST:
 *   - левый ребёнок меньше родителя
 *   - правый ребёнок больше родителя
 */
public class BST {

    private Node root;
    private int size;

    public BST() {
        this.root = null;
        this.size = 0;
    }

    public Node getRoot() {
        return root;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return root == null;
    }

    // ================================================================
    // ВСТАВКА
    // ================================================================
    /**
     * Вставляет элемент в дерево.
     * Если ключ уже есть — обновляет значение.
     * @return true, если добавлен новый узел, false если обновлён
     */
    public boolean insert(String key, String value) {
        if (root == null) {
            root = new Node(key, value);
            size++;
            return true;
        }
        return insertNode(root, key, value);
    }

    private boolean insertNode(Node node, String key, String value) {
        int cmp = key.compareTo(node.key);

        if (cmp == 0) {
            // ключ уже есть — обновляем значение
            node.value = value;
            return false;
        } else if (cmp < 0) {
            // идём влево
            if (node.left == null) {
                node.left = new Node(key, value);
                size++;
                return true;
            }
            return insertNode(node.left, key, value);
        } else {
            // идём вправо
            if (node.right == null) {
                node.right = new Node(key, value);
                size++;
                return true;
            }
            return insertNode(node.right, key, value);
        }
    }

    // ================================================================
    // ПОИСК
    // ================================================================
    /**
     * Ищет элемент по ключу.
     * @return значение, или null если не найдено
     */
    public String search(String key) {
        Node node = searchNode(root, key);
        return (node == null) ? null : node.value;
    }

    private Node searchNode(Node node, String key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return node;
        } else if (cmp < 0) {
            return searchNode(node.left, key);
        } else {
            return searchNode(node.right, key);
        }
    }

    // ================================================================
    // УДАЛЕНИЕ
    // ================================================================
    /**
     * Удаляет элемент по ключу.
     * @return true, если элемент был найден и удалён
     */
    public boolean remove(String key) {
        if (search(key) == null) {
            return false;
        }
        root = removeNode(root, key);
        size--;
        return true;
    }

    private Node removeNode(Node node, String key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);

        if (cmp < 0) {
            node.left = removeNode(node.left, key);
        } else if (cmp > 0) {
            node.right = removeNode(node.right, key);
        } else {
            // нашли узел для удаления

            // случай 1: нет детей
            if (node.left == null && node.right == null) {
                return null;
            }

            // случай 2: один ребёнок
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }

            // случай 3: два ребёнка
            // находим минимальный в правом поддереве
            Node minRight = findMin(node.right);
            node.key = minRight.key;
            node.value = minRight.value;
            node.right = removeNode(node.right, minRight.key);
        }

        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // ================================================================
    // ВЫВОД
    // ================================================================
    /**
     * Выводит дерево с отступами.
     */
    public void print() {
        if (root == null) {
            System.out.println("    (пусто)");
            return;
        }
        printNode(root, "    ");
    }

    private void printNode(Node node, String prefix) {
        if (node == null) {
            return;
        }
        System.out.println(prefix + node.key + " → " + node.value);
        if (node.left != null) {
            System.out.println(prefix + "├── L:");
            printNode(node.left, prefix + "│   ");
        }
        if (node.right != null) {
            System.out.println(prefix + "└── R:");
            printNode(node.right, prefix + "    ");
        }
    }
}