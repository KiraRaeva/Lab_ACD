/**
 * =====================================================================
 * Лабораторная работа №1: Списки. Стеки. Очереди.
 * =====================================================================
 * Вариант 21:
 * Определить, расположены ли элементы в двусвязном списке симметричным образом.
 * =====================================================================
 */

class Node {
    int data;
    Node next;
    Node prev;

    public Node(int value){
        this.data = value;
        this.next = null;
        this.prev = null;
    }
}
class DoublyLinkedList {

    private Node head;
    private Node tail;
    private int size;

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
    public boolean isEmpty() {
        return head == null;
    }

    public int getSize() {
        return size;
    }
