/**
 * Двусвязный список.
 * - создание списка
 * - добавление элемента в конец
 * - вывод списка
 * - проверка на симметричность
 */
public class DoublyLinkedList {
    private Node head;
    private Node tail;

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

    public boolean isSymmetric() {
        if (isEmpty() || head == tail) {
            return true;
        }
        Node left = head;
        Node right = tail;
        while (left != right && left.prev != right) {
            if (left.data != right.data) {
                return false;
            }
            left = left.next;
            right = right.prev;
        }
        return left == right || left.data == right.data;
    }
}