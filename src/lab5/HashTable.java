package lab5;

/**
 * Хэш-таблица.
 *
 * Ключ — СНИЛС (11 цифр).
 * Значение — ФИО.
 * Хэш-функция — деление: key % size.
 * Коллизии разрешаются через BST.
 *
 * При заполнении > 0.75 — рехэширование.
 */
public class HashTable {

    private BST[] table;       // массив BST
    private int size;          // текущий размер таблицы
    private int count;         // количество элементов
    private static final double LOAD_FACTOR = 0.75;

    /**
     * Конструктор: начальный размер 10.
     */
    public HashTable() {
        this.size = 10;
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

    // ================================================================
    // ХЭШ-ФУНКЦИЯ (деление)
    // ================================================================
    /**
     * Хэш-функция деления.
     * Преобразует СНИЛС (строку цифр) в число и берёт остаток от деления.
     */
    private int hash(String key) {
        long number = 0;
        for (int i = 0; i < key.length(); i++) {
            number = number * 10 + (key.charAt(i) - '0');
        }
        // остаток от деления, всегда положительный
        int index = (int) (number % size);
        if (index < 0) {
            index += size;
        }
        return index;
    }

    // ================================================================
    // ВСТАВКА
    // ================================================================
    public void insert(String key, String value) {
        int index = hash(key);

        if (table[index] == null) {
            table[index] = new BST();
        }

        boolean added = table[index].insert(key, value);
        if (added) {
            count++;
        }

        // проверка коэффициента заполнения
        if (getLoadFactor() >= LOAD_FACTOR) {
            rehash();
        }
    }

    // ================================================================
    // ПОИСК
    // ================================================================
    public String search(String key) {
        int index = hash(key);
        if (table[index] == null) {
            return null;
        }
        return table[index].search(key);
    }

    // ================================================================
    // УДАЛЕНИЕ
    // ================================================================
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

    // ================================================================
    // РЕХЭШИРОВАНИЕ
    // ================================================================
    /**
     * Увеличивает размер таблицы до следующего простого числа
     * и перераспределяет все элементы.
     */
    public void rehash() {
        int newSize = nextPrime(size * 2);
        BST[] oldTable = table;
        int oldSize = size;

        table = new BST[newSize];
        size = newSize;
        count = 0;

        // перебираем все элементы старой таблицы
        for (int i = 0; i < oldSize; i++) {
            if (oldTable[i] != null) {
                reinsertAll(oldTable[i].getRoot());
            }
        }
    }

    /**
     * Рекурсивно обходит BST и вставляет все элементы в новую таблицу.
     */
    private void reinsertAll(Node node) {
        if (node == null) {
            return;
        }
        reinsertAll(node.left);
        insert(node.key, node.value);
        reinsertAll(node.right);
    }

    /**
     * Находит следующее простое число >= n.
     */
    private int nextPrime(int n) {
        while (!isPrime(n)) {
            n++;
        }
        return n;
    }

    /**
     * Проверяет, является ли число простым.
     */
    private boolean isPrime(int n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    // ================================================================
    // ВЫВОД
    // ================================================================
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