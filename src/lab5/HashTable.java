package lab5;

/**
 * Хэш-таблица с разрешением коллизий через BST.
 */
public class HashTable {

    private BST[] table;
    private int size;
    private int count;
    private static final double LOAD_FACTOR = 0.75;

    public HashTable() {
        this.size = 11; // Начальный размер — простое число
        this.table = new BST[size];
        this.count = 0;
    }

    public int getSize() {
        return size;
    }

    public int getCount() {
        return count;
    }

    public double getLoadFactor() {
        return (double) count / size;
    }

    private int hash(String key) {
        long number = 0;
        for (int i = 0; i < key.length(); i++) {
            number = number * 10 + (key.charAt(i) - '0');
        }
        int index = (int) (number % size);
        if (index < 0) {
            index += size;
        }
        return index;
    }

    public void insert(String key, String value) {
        int index = hash(key);

        if (table[index] == null) {
            table[index] = new BST();
        }

        boolean added = table[index].insert(key, value);
        if (added) {
            count++;
        }

        if (getLoadFactor() >= LOAD_FACTOR) {
            rehash();
        }
    }

    private void insertWithoutRehash(String key, String value) {
        int index = hash(key);

        if (table[index] == null) {
            table[index] = new BST();
        }

        boolean added = table[index].insert(key, value);
        if (added) {
            count++;
        }
    }

    public String search(String key) {
        int index = hash(key);
        if (table[index] == null) {
            return null;
        }
        Node node = table[index].search(key);
        return (node == null) ? null : node.value;
    }

    public boolean remove(String key) {
        int index = hash(key);
        if (table[index] == null) {
            return false;
        }
        boolean removed = table[index].remove(key);
        if (removed) {
            count--;
            if (table[index].isEmpty()) {
                table[index] = null;
            }
        }
        return removed;
    }

    /**
     * Рехэширование. Увеличивает таблицу и перевставляет элементы.
     */
    public void rehash() {
        int newSize = nextPrime(size * 2);
        BST[] oldTable = table;
        int oldSize = size;

        table = new BST[newSize];
        size = newSize;
        count = 0;

        for (int i = 0; i < oldSize; i++) {
            if (oldTable[i] != null && !oldTable[i].isEmpty()) {
                // Избавились от костыльного массива-стека fixed-size, используем чистую рекурсию
                traverseAndReinsert(oldTable[i].getRoot());
            }
        }
    }

    /**
     * Рекурсивный обход старого дерева для перевставки
     */
    private void traverseAndReinsert(Node node) {
        if (node == null) return;

        // Вставляем текущий
        insertWithoutRehash(node.key, node.value);

        // Идем к детям
        traverseAndReinsert(node.left);
        traverseAndReinsert(node.right);
    }

    private int nextPrime(int n) {
        while (!isPrime(n)) {
            n++;
        }
        return n;
    }

    private boolean isPrime(int n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public void print() {
        System.out.println("Размер таблицы: " + size);
        System.out.println("Количество элементов: " + count);
        System.out.printf("Коэффициент заполнения: %.2f%n", getLoadFactor());
        System.out.println();

        for (int i = 0; i < size; i++) {
            if (table[i] == null) {
                System.out.println("Ячейка " + i + ": пусто");
            } else {
                System.out.println("Ячейка " + i + ": (элементов: " + table[i].getSize() + ")");
                table[i].print();
            }
        }
    }
}
