/**
 * Двусвязный список.
 * Содержит только операции, необходимые для задачи 21:
 * - создание списка
 * - добавление элемента в конец
 * - вывод списка
 * - проверка на симметричность
 */
public class DoublyLinkedList {
    private Node head;    // ссылка на первый узел
    private Node tail;    // ссылка на последний узел

    /**
     * Конструктор: создание пустого списка
     */
    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
    }

    /**
     * Проверяет, пуст ли список.
     * @return true, если список пуст
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Добавляет новый элемент в конец списка.
     * @param value - значение для добавления
     */
    public void add(int value) {
        Node newNode = new Node(value);

        if (isEmpty()) {
            // если список пуст, новый узел становится и головой, и хвостом
            head = newNode;
            tail = newNode;
        } else {
            // добавляем в конец
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    /**
     * Выводит все элементы списка в консоль.
     * Формат: [1, 2, 3, 4, 5]
     */
    public void printList() {
        if (isEmpty()) {
            System.out.println("[]");
            return;
        }

        Node current = head;
        System.out.print("[");
        while (current != null) {
            System.out.print(current.data);
            if (current.next != null) {
                System.out.print(", ");
            }
            current = current.next;
        }
        System.out.println("]");
    }

    /**
     * Проверяет, является ли список симметричным (палиндромом).
     * Использует два указателя: с начала и с конца.
     * @return true, если список симметричный
     */
    public boolean isSymmetric() {
        // пустой список считаем симметричным
        if (isEmpty()) {
            return true;
        }

        // один элемент всегда симметричен
        if (head == tail) {
            return true;
        }

        Node left = head;   // указатель с начала
        Node right = tail;  // указатель с конца

        // пока не встретились и не пересеклись
        while (left != right && left.prev != right) {
            if (left.data != right.data) {
                return false;
            }
            left = left.next;
            right = right.prev;
        }

        // проверка центральной пары
        if (left == right) {
            return true;
        } else {
            return left.data == right.data;
        }
    }
}