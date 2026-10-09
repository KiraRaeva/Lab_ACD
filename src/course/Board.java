package course;

/**
 * Класс, описывающий игровое поле 8x8.
 * Игра ведётся только на чёрных клетках.
 * Белые шашки внизу (ряды 5-7), чёрные вверху (ряды 0-2).
 */
public class Board {

    public static final int SIZE = 8;

    // null — пустая клетка
    private Checker[][] cells;

    public Board() {
        cells = new Checker[SIZE][SIZE];
        initStartPosition();
    }

    // Начальная расстановка
    private void initStartPosition() {
        // Чёрные — ряды 0, 1, 2
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (isBlackCell(row, col)) {
                    cells[row][col] = new Checker(false);
                }
            }
        }
        // Белые — ряды 5, 6, 7
        for (int row = 5; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (isBlackCell(row, col)) {
                    cells[row][col] = new Checker(true);
                }
            }
        }
    }

    // Чёрная ли клетка (сумма координат нечётная)
    public static boolean isBlackCell(int row, int col) {
        return (row + col) % 2 == 1;
    }

    // Проверка границ
    public static boolean inBounds(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }

    // Получить шашку
    public Checker getChecker(int row, int col) {
        if (!inBounds(row, col)) return null;
        return cells[row][col];
    }

    // Поставить шашку
    public void setChecker(int row, int col, Checker checker) {
        if (inBounds(row, col)) {
            cells[row][col] = checker;
        }
    }

    // Убрать шашку
    public void removeChecker(int row, int col) {
        if (inBounds(row, col)) {
            cells[row][col] = null;
        }
    }

    // Копирование поля (для поиска ИИ)
    public Board copy() {
        Board newBoard = new Board();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (cells[row][col] != null) {
                    Checker original = cells[row][col];
                    Checker copy = new Checker(original.isWhite());
                    if (original.isKing()) {
                        copy.makeKing();
                    }
                    newBoard.cells[row][col] = copy;
                } else {
                    newBoard.cells[row][col] = null;
                }
            }
        }
        return newBoard;
    }

    // Подсчёт количества шашек заданного цвета
    public int countCheckers(boolean white) {
        int count = 0;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (cells[row][col] != null
                        && cells[row][col].isWhite() == white) {
                    count++;
                }
            }
        }
        return count;
    }

    // Вывод поля в консоль
    // Вывод поля в консоль с шашечной нотацией
    public void print() {
        System.out.println("    a b c d e f g h");
        for (int row = 0; row < SIZE; row++) {
            System.out.print((8 - row) + "   ");
            for (int col = 0; col < SIZE; col++) {
                if (cells[row][col] == null) {
                    System.out.print(". ");
                } else {
                    System.out.print(cells[row][col] + " ");
                }
            }
            System.out.println("  " + (8 - row));
        }
        System.out.println("    a b c d e f g h");
    }
}