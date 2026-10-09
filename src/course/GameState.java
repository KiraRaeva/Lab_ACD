package course;

/**
 * Класс, описывающий состояние игры:
 * поле + чей сейчас ход + счётчик полуходов для ничьей.
 */
public class GameState {

    private Board board;

    // true — ход белых, false — ход чёрных
    private boolean whiteTurn;

    // Счётчик полуходов без взятий и превращений (для ничьей)
    private int halfMoveCounter;

    public GameState() {
        this.board = new Board();
        this.whiteTurn = true; // белые ходят первыми
        this.halfMoveCounter = 0;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    public int getHalfMoveCounter() {
        return halfMoveCounter;
    }

    // Сменить ход
    public void switchTurn() {
        whiteTurn = !whiteTurn;
    }

    // Увеличить счётчик полуходов
    public void incrementHalfMove() {
        halfMoveCounter++;
    }

    // Сбросить счётчик полуходов (при взятии или превращении)
    public void resetHalfMove() {
        halfMoveCounter = 0;
    }

    // Копирование состояния (для MiniMax)
    public GameState copy() {
        GameState newState = new GameState();
        newState.board = this.board.copy();
        newState.whiteTurn = this.whiteTurn;
        newState.halfMoveCounter = this.halfMoveCounter;
        return newState;
    }

    public void print() {
        board.print();
        System.out.println("Ход: " + (whiteTurn ? "белые" : "чёрные"));
    }
}