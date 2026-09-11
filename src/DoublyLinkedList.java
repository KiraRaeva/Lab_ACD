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


    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
    }


    public boolean isEmpty() {
        return head == null;
    }


    public void add(int value) {
        Node newNode = new Node(value);

        if (isEmpty()) {

            head = newNode;
            tail = newNode;
        } else {

            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }


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
     * Проверяет, является ли список симметричным.
     * Использует два указателя: с начала и с конца.
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