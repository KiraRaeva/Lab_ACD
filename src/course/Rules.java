package course;

/**
 * Проверка правил и применение хода к состоянию игры.
 */
public class Rules {

    /**
     * Проверяет, является ли ход допустимым для текущего состояния.
     * Ход должен присутствовать в списке сгенерированных ходов.
     */
    public static boolean isValidMove(GameState state, Move move) {
        MoveList legal = MoveGenerator.getAllMoves(state);
        for (int i = 0; i < legal.size(); i++) {
            Move m = legal.get(i);
            if (sameMove(m, move)) return true;
        }
        return false;
    }

    // Сравнение ходов по началу/концу и числу сбитых
    private static boolean sameMove(Move a, Move b) {
        if (a.getFromRow() != b.getFromRow()) return false;
        if (a.getFromCol() != b.getFromCol()) return false;
        if (a.getToRow() != b.getToRow()) return false;
        if (a.getToCol() != b.getToCol()) return false;
        if (a.getCapturedCount() != b.getCapturedCount()) return false;

        // Сравниваем множества сбитых (порядок может отличаться)
        for (int i = 0; i < a.getCapturedCount(); i++) {
            boolean found = false;
            for (int j = 0; j < b.getCapturedCount(); j++) {
                if (a.getCapturedRow(i) == b.getCapturedRow(j)
                        && a.getCapturedCol(i) == b.getCapturedCol(j)) {
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }
        return true;
    }

    /**
     * Применить ход к состоянию игры.
     * Возвращает новое состояние (не изменяет исходное).
     */
    public static GameState applyMove(GameState state, Move move) {

        GameState next = state.copy();
        Board board = next.getBoard();

        Checker moving = board.getChecker(move.getFromRow(), move.getFromCol());

        // Убираем сбитые шашки
        for (int i = 0; i < move.getCapturedCount(); i++) {
            board.removeChecker(move.getCapturedRow(i), move.getCapturedCol(i));
        }

        // Переносим шашку
        board.removeChecker(move.getFromRow(), move.getFromCol());
        board.setChecker(move.getToRow(), move.getToCol(), moving);

        // Превращение в дамку при достижении последнего ряда
        if (!moving.isKing()) {
            if (moving.isWhite() && move.getToRow() == 0) {
                moving.makeKing();
            } else if (!moving.isWhite() && move.getToRow() == Board.SIZE - 1) {
                moving.makeKing();
            }
        }

        // Счётчик полуходов
        if (move.isCapture()) {
            next.resetHalfMove();
        } else {
            next.incrementHalfMove();
        }

        // Передаём ход сопернику
        next.switchTurn();

        return next;
    }

    /**
     * Проверка окончания игры.
     * Возвращает:
     *   1  — победили белые
     *  -1  — победили чёрные
     *   0  — игра продолжается
     *   2  — ничья (40 полуходов без изменений)
     */
    public static int checkGameOver(GameState state) {

        // Ничья по счётчику полуходов
        if (state.getHalfMoveCounter() >= 40) {
            return 2;
        }

        // Проверка: у противника нет ходов
        MoveList moves = MoveGenerator.getAllMoves(state);

        boolean white = state.isWhiteTurn();
        int ownCount = state.getBoard().countCheckers(white);
        int enemyCount = state.getBoard().countCheckers(!white);

        // Если у соперника не осталось шашек — победа
        if (enemyCount == 0) {
            return white ? 1 : -1;
        }
        // Если у текущего игрока не осталось шашек — поражение
        if (ownCount == 0) {
            return white ? -1 : 1;
        }
        // Если нет ходов — поражение текущего игрока
        if (moves.isEmpty()) {
            return white ? -1 : 1;
        }

        return 0;
    }
}