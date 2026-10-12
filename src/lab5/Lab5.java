package lab5;

public class Lab5 {

    public static void main(String[] args) throws Exception {
        System.out.println("================================================");
        System.out.println("ЛР №5: Хэш-таблица (коллизии через BST)");
        System.out.println("================================================\n");

        System.out.println("Ключ: СНИЛС (11 цифр).");
        System.out.println("Значение: ФИО.");
        System.out.println("Хэш-функция: деление (key % size).");
        System.out.println("Коллизии: BST в каждой ячейке.");
        System.out.println("Коэффициент заполнения для рехэша: 0.75.\n");

        HashTable table = new HashTable();

        // Тестовые данные
        table.insert("12345678900", "Потаенков Владос");
        table.insert("12345678901", "Раева Кира");
        table.insert("12345678902", "Собака Пиратость");
        table.insert("12345678903", "Рио Киа 164");
        table.insert("12345678904", "Тайлер Дерден");

        //12345678911 и 12345678922 - для коллизии
        System.out.println("Стартовый размер таблицы: 11.");
        System.out.println("Добавьте еще элементы, чтобы сработало автоматическое рехэширование.\n");

        while (true) {
            printMenu();
            int choice = InputReader.readMenuChoice();

            if (choice == -1) {
                System.out.println("\nКонец ввода. Выход.");
                break;
            }

            System.out.println();

            if (choice == 1) {
                String snils = InputReader.readSnils();
                String fio = InputReader.readFio();
                table.insert(snils, fio);
                System.out.println("Элемент добавлен.\n");

            } else if (choice == 2) {
                String snils = InputReader.readSnils();
                String fio = table.search(snils);
                if (fio == null) {
                    System.out.println("Элемент не найден.\n");
                } else {
                    System.out.println("Найдено: " + snils + " → " + fio + "\n");
                }

            } else if (choice == 3) {
                String snils = InputReader.readSnils();
                boolean removed = table.remove(snils);
                if (removed) {
                    System.out.println("Элемент удалён.\n");
                } else {
                    System.out.println("Элемент не найден.\n");
                }

            } else if (choice == 4) {
                table.print();
                System.out.println();

            } else if (choice == 5) {
                table.rehash();
                System.out.println("Рехэширование выполнено. Новый размер: " + table.getSize() + "\n");

            } else if (choice == 6) {
                System.out.println("Размер таблицы: " + table.getSize());
                System.out.println("Количество элементов: " + table.getCount());
                System.out.printf("Коэффициент заполнения: %.2f%n%n", table.getLoadFactor());

            } else if (choice == 7) {
                System.out.println("Выход.");
                break;
            }
        }
    }

    private static void printMenu() {
        System.out.println("----------------------------------------");
        System.out.println("1 — Добавить элемент");
        System.out.println("2 — Найти элемент");
        System.out.println("3 — Удалить элемент");
        System.out.println("4 — Вывести таблицу");
        System.out.println("5 — Рехэшировать");
        System.out.println("6 — Показать статистику");
        System.out.println("7 — Выход");
        System.out.println("----------------------------------------");
    }
}
