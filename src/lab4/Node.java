package lab4;


public class Node {

    String data;        // значение (заглавные буквы: A, AB, XYZ)
    int number;         // номер вершины (присваивается автоматически)
    char color;         // цвет: 'w', 'b', 'y'
    Node[] children;    // массив детей
    int childCount;     // текущее количество детей


    public Node(String data, char color) {
        this.data = data;
        this.color = color;
        this.number = 0;
        this.children = new Node[10];
        this.childCount = 0;
    }


    public void addChild(Node child) {
        if (childCount < children.length) {
            children[childCount] = child;
            childCount++;
        }
    }


    public Node getChild(int index) {
        if (index >= 0 && index < childCount) {
            return children[index];
        }
        return null;
    }


    public int getChildCount() {
        return childCount;
    }

    /**
     * Проверяет, является ли узел листом.
     */
    public boolean isLeaf() {
        return childCount == 0;
    }
}