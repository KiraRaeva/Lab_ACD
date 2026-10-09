package course;

import java.util.Scanner;

/**
 * Главный класс курсовой работы.
 * Этап 1: консольная игра в итальянские шашки для двух игроков.
 */
public class LabCourse {

    // Сканер для ввода с клавиатуры
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("=========================================");
        System.out.println("   Итальянские шашки (2 игрока)");
        System.out.println("=========================================");
        System.out.println("Формат хода: <откуда> <куда>, например: c3 d4");
        System.out.println("Команды: moves — список ходов, board — доска,");
        System.out.println("         help — справка, quit — выход");
        System.out.println();

        GameState state = new GameState();
        gameLoop(state);

        System.out.println("Игра завершена.");
        scanner.close();
    }

    // ------------------------------------------------------------
    // Главный цикл игры
    // ------------------------------------------------------------
    private static void gameLoop(GameState state) {

        while (true) {

            // Проверяем окончание игры
            int result = Rules.checkGameOver(state);
            if (result != 0) {
                printGameResult(result, state);
                return;
            }

            // Показываем доску и чей ход
            System.out.println();
            state.print();
            System.out.println();

            boolean white = state.isWhiteTurn();
            System.out.println("Ход: " + (white ? "белые (w)" : "чёрные (b)"));

            // Показываем взятия, если они обязательны
            MoveList moves = MoveGenerator.getAllMoves(state);
            boolean mustCapture = moves.size() > 0 && moves.get(0).isCapture();
            if (mustCapture) {
                System.out.println("ВНИМАНИЕ: взятие обязательно!");
                System.out.println("Доступные взятия:");
                for (int i = 0; i < moves.size(); i++) {
                    System.out.println("  " + formatMove(moves.get(i)));
                }
            }

            // Читаем ввод
            System.out.print("Ваш ход: ");
            String line = scanner.nextLine().trim();

            if (line.isEmpty()) continue;

            // Обработка команд
            String lower = line.toLowerCase();

            if (lower.equals("quit") || lower.equals("exit")) {
                System.out.println("Выход из игры.");
                return;
            }
            if (lower.equals("help")) {
                printHelp();
                continue;
            }
            if (lower.equals("board")) {
                state.print();
                continue;
            }
            if (lower.equals("moves")) {
                System.out.println("Всего ходов: " + moves.size());
                for (int i = 0; i < moves.size(); i++) {
                    System.out.println("  " + formatMove(moves.get(i)));
                }
                continue;
            }

            // Парсим ход
            Move move = parseUserMove(line, state, moves);
            if (move == null) {
                continue; // сообщение уже выведено
            }

            // Применяем ход
            GameState next = Rules.applyMove(state, move);

            // Копируем результат в текущее состояние
            // (у нас нет set-методов, поэтому проще передать next дальше)
            state = next;

            // Сообщения о ходе
            if (move.isCapture()) {
                System.out.println("Сбито шашек: " + move.getCapturedCount());
            }

            // Проверка превращения
            Checker moved = state.getBoard().getChecker(move.getToRow(),
                    move.getToCol());
            if (moved != null && moved.isKing()) {
                // Проверяем: была ли это простая, ставшая дамкой
                // (по логике applyMove — если стала дамкой, значит превратилась)
                // Можно отследить, но для простоты сообщаем всегда,
                // когда дамка оказалась на последнем ряду
                if ((moved.isWhite() && move.getToRow() == 0)
                        || (!moved.isWhite() && move.getToRow() == Board.SIZE - 1)) {
                    System.out.println("Превращение в дамку!");
                }
            }
        }
    }

    // ------------------------------------------------------------
    // Парсинг хода пользователя
    // Возвращает Move, если ход корректен и допустим.
    // Иначе — null (с выводом сообщения об ошибке).
    // ------------------------------------------------------------
    private static Move parseUserMove(String line, GameState state, MoveList moves) {

        // Разбиваем строку по пробелам — должно быть два токена
        String[] parts = splitBySpace(line);
        if (parts.length != 2) {
            System.out.println("Ошибка: нужно два поля через пробел, например: c3 d4");
            return null;
        }

        int[] from = Coord.parse(parts[0]);
        int[] to = Coord.parse(parts[1]);

        if (from == null) {
            System.out.println("Ошибка: неверная начальная клетка '" + parts[0] + "'");
            return null;
        }
        if (to == null) {
            System.out.println("Ошибка: неверная конечная клетка '" + parts[1] + "'");
            return null;
        }

        int fr = from[0], fc = from[1];
        int tr = to[0], tc = to[1];

        // Ищем среди допустимых ходов
        for (int i = 0; i < moves.size(); i++) {
            Move m = moves.get(i);
            if (m.getFromRow() == fr && m.getFromCol() == fc
                    && m.getToRow() == tr && m.getToCol() == tc) {
                return m;
            }
        }

        // Не нашли — подсказка
        System.out.println("Недопустимый ход.");

        // Проверяем: возможно, есть взятие, и пользователь его не сделал
        MoveList all = moves;
        boolean anyCapture = false;
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).isCapture()) anyCapture = true;
        }
        if (anyCapture) {
            System.out.println("Взятие обязательно! Доступные ходы:");
            for (int i = 0; i < all.size(); i++) {
                System.out.println("  " + formatMove(all.get(i)));
            }
        } else {
            System.out.println("Возможные ходы для вашей шашки:");
            boolean found = false;
            for (int i = 0; i < all.size(); i++) {
                Move m = all.get(i);
                if (m.getFromRow() == fr && m.getFromCol() == fc) {
                    System.out.println("  " + formatMove(m));
                    found = true;
                }
            }
            if (!found) {
                System.out.println("  (у этой шашки нет ходов)");
            }
        }

        return null;
    }

    // ------------------------------------------------------------
    // Разбиение строки по пробелам (без String.split — вручную)
    // ------------------------------------------------------------
    private static String[] splitBySpace(String line) {
        // Считаем количество токенов
        int count = 0;
        boolean inToken = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == ' ' || c == '\t') {
                inToken = false;
            } else {
                if (!inToken) {
                    count++;
                    inToken = true;
                }
            }
        }

        String[] result = new String[count];
        int idx = 0;
        int start = -1;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == ' ' || c == '\t') {
                if (start != -1) {
                    result[idx++] = line.substring(start, i);
                    start = -1;
                }
            } else {
                if (start == -1) start = i;
            }
        }
        if (start != -1) {
            result[idx] = line.substring(start);
        }

        return result;
    }

    // ------------------------------------------------------------
    // Формат хода для вывода: "c3-d4" или "c3-d4 (сбито: 2)"
    // ------------------------------------------------------------
    private static String formatMove(Move m) {
        String s = Coord.format(m.getFromRow(), m.getFromCol())
                + "-"
                + Coord.format(m.getToRow(), m.getToCol());
        if (m.isCapture()) {
            s += " (сбито: " + m.getCapturedCount() + ")";
        }
        return s;
    }

    // ------------------------------------------------------------
    // Вывод результата игры
    // ------------------------------------------------------------
    private static void printGameResult(int result, GameState state) {
        System.out.println();
        state.print();
        System.out.println();
        if (result == 1) {
            System.out.println("Победили БЕЛЫЕ!");
        } else if (result == -1) {
            System.out.println("Победили ЧЁРНЫЕ!");
        } else if (result == 2) {
            System.out.println("НИЧЬЯ (40 полуходов без изменений).");
        }
    }

    // ------------------------------------------------------------
    // Справка
    // ------------------------------------------------------------
    private static void printHelp() {
        System.out.println("Справка:");
        System.out.println("  Ход вводится двумя полями: <откуда> <куда>");
        System.out.println("  Клетки обозначаются буквой (a-h) и цифрой (1-8).");
        System.out.println("  Пример: c3 d4");
        System.out.println();
        System.out.println("  Команды:");
        System.out.println("    moves — показать все допустимые ходы");
        System.out.println("    board — перерисовать доску");
        System.out.println("    help  — эта справка");
        System.out.println("    quit  — выход из игры");
    }
}