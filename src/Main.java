/**
 * =====================================================================
 * Лабораторная работа №1: Списки. Стеки. Очереди.
 * =====================================================================
 * Вариант 21:
 * Определить, расположены ли элементы в двусвязном списке симметричным образом.
 * =====================================================================
 */


public class Main {
    public static void main(String[] args) {
        System.out.println("Задача №21: проверка симметричности двусвязного списка\n");

        // тест 1: симметричный (нечетное количество)
        DoublyLinkedList list1 = new DoublyLinkedList();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        list1.add(2);
        list1.add(1);
        System.out.print("Список: ");
        list1.printList();
        System.out.println("Симметричный? " + (list1.isSymmetric() ? "да" : "нет") + "\n");

        // тест 2: симметричный (четное количество)
        DoublyLinkedList list2 = new DoublyLinkedList();
        list2.add(1);
        list2.add(2);
        list2.add(2);
        list2.add(1);
        System.out.print("Список: ");
        list2.printList();
        System.out.println("Симметричный? " + (list2.isSymmetric() ? "да" : "нет") + "\n");

        // тест 3: не симметричный
        DoublyLinkedList list3 = new DoublyLinkedList();
        list3.add(1);
        list3.add(2);
        list3.add(3);
        list3.add(4);
        list3.add(5);
        System.out.print("Список: ");
        list3.printList();
        System.out.println("Симметричный? " + (list3.isSymmetric() ? "да" : "нет") + "\n");

        // тест 4: один элемент
        DoublyLinkedList list4 = new DoublyLinkedList();
        list4.add(42);
        System.out.print("Список: ");
        list4.printList();
        System.out.println("Симметричный? " + (list4.isSymmetric() ? "да" : "нет") + "\n");

        // тест 5: пустой список
        DoublyLinkedList list5 = new DoublyLinkedList();
        System.out.print("Список: ");
        list5.printList();
        System.out.println("Симметричный? " + (list5.isSymmetric() ? "да" : "нет") + "\n");
    }
}