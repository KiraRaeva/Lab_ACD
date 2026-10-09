package course;

/**
 * Класс, описывающий одну шашку.
 * Хранит цвет и признак дамки.
 */
public class Checker {

    // Цвет: true — белая, false — чёрная
    private boolean isWhite;

    // Признак дамки
    private boolean isKing;

    // Конструктор
    public Checker(boolean isWhite) {
        this.isWhite = isWhite;
        this.isKing = false;
    }

    // Получить цвет
    public boolean isWhite() {
        return isWhite;
    }

    // Является ли дамкой
    public boolean isKing() {
        return isKing;
    }

    // Превратить в дамку
    public void makeKing() {
        this.isKing = true;
    }

    // Строковое представление для отладки
    // W — белая дамка, w — белая простая
    // B — чёрная дамка, b — чёрная простая
    @Override
    public String toString() {
        if (isWhite) {
            return isKing ? "W" : "w";
        } else {
            return isKing ? "B" : "b";
        }
    }
}
