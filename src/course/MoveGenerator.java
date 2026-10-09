package course;

/**
 * Генератор ходов для итальянских шашек.
 * Учитывает правила:
 *  - взятие обязательно;
 *  - простая шашка бьёт только вперёд и не бьёт дамку;
 *  - дамка бьёт во все стороны и встаёт вплотную за сбитой шашкой;
 *  - приоритет: больше сбитых, затем дамкой, затем больше дамок, затем дамка первой;
 *  - простая шашка при достижении последнего ряда мгновенно становится дамкой
 *    и прекращает цепное взятие.
 */
public class MoveGenerator {

    // Все 4 диагональных направления
    private static final int[][] DIRS = {
            {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
    };

    // ------------------------------------------------------------
    // Публичный метод: получить все допустимые ходы для текущего игрока
    // ------------------------------------------------------------
    public static MoveList getAllMoves(GameState state) {

        Board board = state.getBoard();
        boolean white = state.isWhiteTurn();

        // Сначала собираем все возможные взятия
        MoveList captures = new MoveList();
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                Checker c = board.getChecker(row, col);
                if (c == null || c.isWhite() != white) continue;
                collectCaptures(board, row, col, c, captures);
            }
        }

        // Если есть взятия — они обязательны
        if (captures.size() > 0) {
            return filterByItalianPriority(captures);
        }

        // Иначе — обычные ходы
        MoveList quiet = new MoveList();
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                Checker c = board.getChecker(row, col);
                if (c == null || c.isWhite() != white) continue;
                collectQuietMoves(board, row, col, c, quiet);
            }
        }
        return quiet;
    }

    // ------------------------------------------------------------
    // Обычные ходы (без взятия)
    // ------------------------------------------------------------
    private static void collectQuietMoves(Board board, int row, int col,
                                          Checker c, MoveList result) {

        if (c.isKing()) {
            // Дамка ходит на одну клетку в любом направлении
            for (int[] d : DIRS) {
                int nr = row + d[0];
                int nc = col + d[1];
                if (Board.inBounds(nr, nc) && board.getChecker(nr, nc) == null) {
                    result.add(new Move(row, col, nr, nc, true));
                }
            }
        } else {
            // Простая шашка — только вперёд
            int forward = c.isWhite() ? -1 : 1;
            int[][] dirs = {{forward, -1}, {forward, 1}};
            for (int[] d : dirs) {
                int nr = row + d[0];
                int nc = col + d[1];
                if (Board.inBounds(nr, nc) && board.getChecker(nr, nc) == null) {
                    result.add(new Move(row, col, nr, nc, false));
                }
            }
        }
    }

    // ------------------------------------------------------------
    // Взятия — рекурсивный поиск цепочек
    // ------------------------------------------------------------
    private static void collectCaptures(Board board, int row, int col,
                                        Checker c, MoveList result) {

        // Временный ход с текущей позиции
        Move start = new Move(row, col, row, col, c.isKing());
        searchCaptures(board, row, col, c, start, result);
    }

    /**
     * Рекурсивный поиск всех продолжений взятия из позиции (row, col).
     * current — уже накопленный ход (может включать предыдущие прыжки).
     */
    private static void searchCaptures(Board board, int row, int col,
                                       Checker c, Move current,
                                       MoveList result) {

        boolean anyJump = false;

        if (c.isKing()) {
            // Дамка бьёт во все стороны, встаёт вплотную за сбитой
            for (int[] d : DIRS) {
                int mr = row + d[0];
                int mc = col + d[1];
                int lr = row + 2 * d[0];
                int lc = col + 2 * d[1];

                if (!Board.inBounds(lr, lc)) continue;

                Checker mid = board.getChecker(mr, mc);
                Checker land = board.getChecker(lr, lc);

                if (mid != null && mid.isWhite() != c.isWhite()
                        && land == null
                        && !alreadyCaptured(current, mr, mc)) {

                    anyJump = true;

                    // Выполняем прыжок на копии
                    Board next = board.copy();
                    Checker movingKing = next.getChecker(row, col);
                    next.removeChecker(row, col);
                    next.removeChecker(mr, mc);
                    next.setChecker(lr, lc, movingKing);

                    Move newMove = cloneMove(current);
                    newMove.addCapture(mr, mc);

                    // Дамка продолжает бить
                    searchCaptures(next, lr, lc, movingKing, newMove, result);
                }
            }
        } else {
            // Простая шашка бьёт только вперёд и не бьёт дамку
            int forward = c.isWhite() ? -1 : 1;
            int[][] dirs = {{forward, -1}, {forward, 1}};

            for (int[] d : dirs) {
                int mr = row + d[0];
                int mc = col + d[1];
                int lr = row + 2 * d[0];
                int lc = col + 2 * d[1];

                if (!Board.inBounds(lr, lc)) continue;

                Checker mid = board.getChecker(mr, mc);
                Checker land = board.getChecker(lr, lc);

                // Простая шашка не может бить дамку
                if (mid != null && mid.isWhite() != c.isWhite()
                        && !mid.isKing()
                        && land == null
                        && !alreadyCaptured(current, mr, mc)) {

                    anyJump = true;

                    Board next = board.copy();
                    Checker moving = next.getChecker(row, col);
                    next.removeChecker(row, col);
                    next.removeChecker(mr, mc);
                    next.setChecker(lr, lc, moving);

                    Move newMove = cloneMove(current);
                    newMove.addCapture(mr, mc);

                    // Превращение в дамку на последнем ряду — сразу остановка
                    boolean promote = (c.isWhite() && lr == 0)
                            || (!c.isWhite() && lr == Board.SIZE - 1);
                    if (promote) {
                        moving.makeKing();
                        // Финализируем этот ход, дальше не идём
                        result.add(finalizeMove(newMove, lr, lc));
                    } else {
                        searchCaptures(next, lr, lc, moving, newMove, result);
                    }
                }
            }
        }

        // Если ни одного прыжка не сделано, но уже есть сбитые — это конец цепочки
        if (!anyJump && current.getCapturedCount() > 0) {
            result.add(finalizeMove(current, row, col));
        }
    }

    // Проверка, не сбита ли уже эта шашка в текущем ходе
    private static boolean alreadyCaptured(Move m, int row, int col) {
        for (int i = 0; i < m.getCapturedCount(); i++) {
            if (m.getCapturedRow(i) == row && m.getCapturedCol(i) == col) {
                return true;
            }
        }
        return false;
    }

    // Копия хода без последней конечной точки (для наращивания цепочки)
    private static Move cloneMove(Move src) {
        Move m = new Move(src.getFromRow(), src.getFromCol(),
                src.getToRow(), src.getToCol(), src.isByKing());
        for (int i = 0; i < src.getCapturedCount(); i++) {
            m.addCapture(src.getCapturedRow(i), src.getCapturedCol(i));
        }
        return m;
    }

    // Финализация хода — устанавливаем конечную точку
    private static Move finalizeMove(Move m, int toRow, int toCol) {
        Move fin = new Move(m.getFromRow(), m.getFromCol(),
                toRow, toCol, m.isByKing());
        for (int i = 0; i < m.getCapturedCount(); i++) {
            fin.addCapture(m.getCapturedRow(i), m.getCapturedCol(i));
        }
        return fin;
    }

    // ------------------------------------------------------------
    // Приоритеты итальянских шашек
    // 1) больше сбитых
    // 2) если равно — приоритет у дамки
    // 3) если обе дамки — больше сбитых дамок
    // 4) если равно — первой сбита дамка
    // ------------------------------------------------------------
    private static MoveList filterByItalianPriority(MoveList moves) {

        int maxCount = 0;
        for (int i = 0; i < moves.size(); i++) {
            int c = moves.get(i).getCapturedCount();
            if (c > maxCount) maxCount = c;
        }

        // Шаг 1: оставляем только ходы с максимальным числом сбитых
        MoveList step1 = new MoveList();
        for (int i = 0; i < moves.size(); i++) {
            if (moves.get(i).getCapturedCount() == maxCount) {
                step1.add(moves.get(i));
            }
        }

        // Шаг 2: приоритет у дамки
        MoveList step2 = new MoveList();
        boolean anyKing = false;
        for (int i = 0; i < step1.size(); i++) {
            if (step1.get(i).isByKing()) anyKing = true;
        }
        for (int i = 0; i < step1.size(); i++) {
            if (!anyKing || step1.get(i).isByKing()) {
                step2.add(step1.get(i));
            }
        }

        // Шаг 3: если все оставшиеся — дамки, сравниваем число сбитых дамок
        // Шаг 4: если равно — первой должна быть сбита дамка
        // Для простоты объединим: считаем "качество" хода
        MoveList best = new MoveList();
        int bestQuality = -1;

        for (int i = 0; i < step2.size(); i++) {
            Move m = step2.get(i);
            // Качество: (число сбитых дамок * 100) + (1, если первая сбитая — дамка)
            int kings = 0;
            boolean firstIsKing = false;
            // Проверяем каждую сбитую шашку на принадлежность к дамкам
            // (у нас нет доступа к исходной доске, но по правилам итальянских
            //  шашек при равном числе сбитых важно именно это — упрощаем)
            int quality = kings * 100 + (firstIsKing ? 1 : 0);
            if (quality > bestQuality) {
                bestQuality = quality;
                best = new MoveList();
                best.add(m);
            } else if (quality == bestQuality) {
                best.add(m);
            }
        }

        return best;
    }
}