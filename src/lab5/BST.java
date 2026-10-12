package lab5;

/**
 * Бинарное дерево поиска (BST).
 * Используется для разрешения коллизий в хэш-таблице.
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

    /**
     * Вставляет элемент в дерево.
     */
    public boolean insert(String key, String value) {
        if (root == null) {
            root = new Node(key, value);
            size++;
            return true;
        }

        Node current = root;
        while (true) {
            int cmp = key.compareTo(current.key);

            if (cmp == 0) {
                current.value = value;
                return false;
            } else if (cmp < 0) {
                if (current.left == null) {
                    current.left = new Node(key, value);
                    size++;
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new Node(key, value);
                    size++;
                    return true;
                }
                current = current.right;
            }
        }
    }

    public Node search(String key) {
        Node current = root;
        while (current != null) {
            int cmp = key.compareTo(current.key);
            if (cmp == 0) {
                return current;
            } else if (cmp < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }


    public boolean remove(String key) {
        Node parent = null;
        Node current = root;

        // Поиск удаляемого узла
        while (current != null) {
            int cmp = key.compareTo(current.key);
            if (cmp == 0) break;
            parent = current;
            if (cmp < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        if (current == null) {
            return false;
        }

        // Случай 1: У узла два ребенка
        if (current.left != null && current.right != null) {
            Node minParent = current;
            Node minNode = current.right;
            while (minNode.left != null) {
                minParent = minNode;
                minNode = minNode.left;
            }

            // Копируем данные из замещающего узла
            current.key = minNode.key;
            current.value = minNode.value;

            // Теперь перенаправляем удаление на minNode
            current = minNode;
            parent = minParent;
        }

        // Случай 2 и 3: У узла 0 или 1 ребенок
        Node child = (current.left != null) ? current.left : current.right;

        if (parent == null) {
            root = child;
        } else if (parent.left == current) {
            parent.left = child;
        } else {
            parent.right = child;
        }

        size--;
        return true;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int getSize() {
        return size;
    }

    /**
     * Выводит дерево в виде структуры с отступами.
     */
    public void print() {
        if (root == null) {
            System.out.println("    (пусто)");
            return;
        }
        System.out.println(root.key + " → " + root.value);
        printChildren(root, "");
    }

    private void printChildren(Node node, String prefix) {
        if (node == null) return;

        if (node.left != null) {
            if (node.right != null) {
                System.out.println(prefix + "├── " + node.left.key + " → " + node.left.value);
                printChildren(node.left, prefix + "│   ");
            } else {
                System.out.println(prefix + "└── " + node.left.key + " → " + node.left.value);
                printChildren(node.left, prefix + "    ");
            }
        }

        if (node.right != null) {
            System.out.println(prefix + "└── " + node.right.key + " → " + node.right.value);
            printChildren(node.right, prefix + "    ");
        }
    }
}
