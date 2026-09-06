/**
 * Класс, описывающий узел двусвязного списка.
 */
public class Node {
    int data;      // Данные узла
    Node next;     // Ссылка на следующий узел
    Node prev;     // Ссылка на предыдущий узел

    public Node(int value) {
        this.data = value;
        this.next = null;
        this.prev = null;
    }
}