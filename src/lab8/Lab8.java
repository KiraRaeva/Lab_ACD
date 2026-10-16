package lab8;

import javax.swing.*;

/**
 * Главный класс лабораторной работы №8.
 * Отвечает за консольный ввод параметров и запуск графической оболочки.
 */
public class Lab8 {

    public static void main(String[] args) throws Exception {
        System.out.println("================================================");
        System.out.println("Задача 6 (ЛР №8): Рекурсивное рисование фигуры");
        System.out.println("================================================\n");

        int depth = 0;

        // Итеративный цикл валидации пользовательского ввода глубины
        while (true) {
            System.out.print("Введите глубину рекурсии фрактала (1..7): ");
            depth = InputReader.readInt(); // Безопасное чтение через наш посимвольный метод

            if (depth == Integer.MIN_VALUE) {
                System.out.println("Ошибка: введите корректное целое число.\n");
            } else if (depth < 1) {
                System.out.println("Ошибка: глубина должна быть не менее 1.\n");
            } else if (depth > 7) {
                System.out.println("Ошибка: технический лимит превышен (макс. 7).\n");
            } else {
                break; // Успешный ввод, прерываем цикл валидации
            }
        }

        System.out.println("\nОтрисовка фрактала запущена. Проверьте открывшееся окно графики.");

        // Безопасный запуск графического фрейма в потоке обработки событий (EDT)
        int finalDepth = depth;
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Лабораторная работа: Рекурсивный фрактал Т-Квадрат");

            // Создаем экземпляр панели из первого файла и передаем ей глубину
            FractalPanel fractalPanel = new FractalPanel(finalDepth);

            frame.add(fractalPanel);
            frame.setSize(700, 700); // Оптимальный размер окна
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null); // Центрирование на экране
            frame.setVisible(true);
        });
    }
}
