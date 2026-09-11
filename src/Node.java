

public class Node {
    int data;      // значение узла
    Node next;     // ссылка на следующий узел
    Node prev;     // ссылка на предыдущий узел


    public Node(int value) {
        this.data = value;
        this.next = null;
        this.prev = null;
    }
}