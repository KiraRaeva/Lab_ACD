package lab5;

/**
 * Главный класс лабораторной работы №5.
 * Хэш-таблица с разрешением коллизий через BST.
 *
 * Меню:
 * 1 — Добавить элемент (СНИЛС + ФИО)
 * 2 — Найти элемент по СНИЛС
 * 3 — Удалить элемент по СНИЛС
 * 4 — Вывести всю таблицу
 * 5 — Рехэшировать (принудительно)
 * 6 — Показать статистику
 * 7 — Выход
 */
public class Lab5 {

    public static void main(String[] args) throws Exception {
        System.out.println("================================================");
        System.out.println("ЛР №5: Хэш-таблица (коллизии через BST)");
        System.out.println("================================================\n");

        System.out.println("Ключ: СНИЛС (11 цифр).");
        System.out.println("Значение: ФИО.");
        System.out.println("Хэш-функция: деление (key % size).");
        System.out.println("Коллизии: BST в каждой ячейке.");
        System.out.println("Коэффициент заполнения: 0.75.\n");

        HashTable table = new HashTable();

        // ============================================================
        // ТЕСТОВЫЕ ДАННЫЕ (9 элементов)
        // После добавления 10-го сработает рехэширование
        // ============================================================
        table.insert("12345678900", "Кира Сергеевна Раева");
        table.insert("12345678901", "Лариса Васильевна Уварова");
        table.insert("12345678902", "Владос Константинович Потаенков");
        table.insert("12345678903", "К К К");
        table.insert("12345678904", "С С С");
        table.insert("12345678905", "Уилл Байерс");
        table.insert("12345678906", "Стив Харингтон");
        table.insert("12345678907", "Лукас");
        table.insert("12345678908", "Дастин");

        System.out.println("Загружено 9 тестовых элементов.");
        System.out.println("При добавлении 10-го сработает рехэширование.\n");

        while (true) {
            printMenu();
            int choice = InputReader.readMenuChoice();

            if (choice == -1) {
                System.out.println("\nКонец ввода. Выход.");
                break;
            }

            System.out.println();

            if (choice == 1) {
                // добавить
                String snils = InputReader.readSnils();
                String fio = InputReader.readFio();
                table.insert(snils, fio);
                System.out.println("Элемент добавлен.\n");

            } else if (choice == 2) {
                // найти
                String snils = InputReader.readSnils();
                String fio = table.search(snils);
                if (fio == null) {
                    System.out.println("Элемент не найден.\n");
                } else {
                    System.out.println("Найдено: " + snils + " → " + fio + "\n");
                }

            } else if (choice == 3) {
                // удалить
                String snils = InputReader.readSnils();
                boolean removed = table.remove(snils);
                if (removed) {
                    System.out.println("Элемент удалён.\n");
                } else {
                    System.out.println("Элемент не найден.\n");
                }

            } else if (choice == 4) {
                // вывести таблицу
                table.print();
                System.out.println();

            } else if (choice == 5) {
                // рехэшировать
                table.rehash();
                System.out.println("Рехэширование выполнено. Новый размер: " + table.getSize() + "\n");

            } else if (choice == 6) {
                // статистика
                System.out.println("Размер таблицы: " + table.getSize());
                System.out.println("Количество элементов: " + table.getCount());
                System.out.printf("Коэффициент заполнения: %.2f%n%n", table.getLoadFactor());

            } else if (choice == 7) {
                // выход
                System.out.println("Выход.");
                break;
            }
        }
    }

    /**
     * Выводит меню.
     */
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