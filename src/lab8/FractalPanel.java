package lab8;



import javax.swing.*;
import java.awt.*;

/**
 * Графическая панель для рекурсивного рисования фрактала Т-Квадрат.
 */
public class FractalPanel extends JPanel {

    private final int maxDepth;

    public FractalPanel(int depth) {
        this.maxDepth = depth;
        setBackground(Color.WHITE); // Белый фон для графического окна
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Активация сглаживания (anti-aliasing) для четких линий контуров
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);

        // Динамический расчет центра окна приложения
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // Стартовая длина стороны центрального квадрата
        int initialSize = 200;

        // Запуск рекурсивного алгоритма от центральной точки (direction = 0)
        drawFractal(g2d, centerX, centerY, initialSize, maxDepth, 0);
    }

    /**
     * Рекурсивный метод геометрического построения фрактала Т-Квадрат.
     */
    private void drawFractal(Graphics2D g, int cx, int cy, int size, int depth, int direction) {
        // Базовый случай: остановка рекурсии при достижении нулевого уровня
        if (depth == 0) {
            return;
        }

        // Преобразование центральных координат в координаты верхнего левого угла для drawRect
        int x = cx - size / 2;
        int y = cy - size / 2;

        // Маскирование: закрашиваем внутреннюю область белым, чтобы стереть пересечения
        g.setColor(Color.WHITE);
        g.fillRect(x, y, size, size);

        // Отрисовка внешней черной рамки квадрата
        g.setColor(Color.BLACK);
        g.drawRect(x, y, size, size);

        // Масштабирование: шаг уменьшения стороны элементов следующего уровня в 2 раза
        int nextSize = size / 2;
        int half = size / 2;

        // Ветвление рекурсии по углам с фильтрацией обратного хода
        if (direction == 0 || direction == 1 || direction == 2 || direction == 3) {
            drawFractal(g, cx - half, cy - half, nextSize, depth - 1, 1);
        }
        if (direction == 0 || direction == 1 || direction == 2 || direction == 4) {
            drawFractal(g, cx + half, cy - half, nextSize, depth - 1, 2);
        }
        if (direction == 0 || direction == 1 || direction == 3 || direction == 4) {
            drawFractal(g, cx - half, cy + half, nextSize, depth - 1, 3);
        }
        if (direction == 0 || direction == 2 || direction == 3 || direction == 4) {
            drawFractal(g, cx + half, cy + half, nextSize, depth - 1, 4);
        }
    }
}
