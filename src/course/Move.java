package course;

/**
 * Класс, описывающий один ход.
 * Хранит координаты откуда и куда, а также список сбитых шашек.
 * Максимум можно сбить 12 шашек за один ход.
 */
public class Move {

    private int fromRow;
    private int fromCol;
    private int toRow;
    private int toCol;

    // Координаты сбитых шашек
    private int[] capturedRows;
    private int[] capturedCols;
    private int capturedCount;

    // Был ли ход выполнен дамкой (нужно для приоритета взятия)
    private boolean byKing;

    public Move(int fromRow, int fromCol, int toRow, int toCol, boolean byKing) {
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
        this.byKing = byKing;
        this.capturedRows = new int[12];
        this.capturedCols = new int[12];
        this.capturedCount = 0;
    }

    // Добавить сбитую шашку
    public void addCapture(int row, int col) {
        if (capturedCount < 12) {
            capturedRows[capturedCount] = row;
            capturedCols[capturedCount] = col;
            capturedCount++;
        }
    }

    // Геттеры
    public int getFromRow() { return fromRow; }
    public int getFromCol() { return fromCol; }
    public int getToRow() { return toRow; }
    public int getToCol() { return toCol; }
    public int getCapturedCount() { return capturedCount; }
    public int getCapturedRow(int index) { return capturedRows[index]; }
    public int getCapturedCol(int index) { return capturedCols[index]; }
    public boolean isByKing() { return byKing; }

    // Есть ли взятия в этом ходе
    public boolean isCapture() {
        return capturedCount > 0;
    }

    @Override
    public String toString() {
        return "(" + fromRow + "," + fromCol + ") -> (" + toRow + "," + toCol
                + "), сбито: " + capturedCount
                + (byKing ? " [дамка]" : " [простая]");
    }
}