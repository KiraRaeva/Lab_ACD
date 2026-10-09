package course;

/**
 * Собственный список ходов на массиве.
 * Использование коллекций Java запрещено правилами курсовой.
 */
public class MoveList {

    private Move[] data;
    private int size;

    public MoveList() {
        data = new Move[64];
        size = 0;
    }

    public void add(Move m) {
        if (size == data.length) {
            // Расширяем массив вручную
            Move[] bigger = new Move[data.length * 2];
            for (int i = 0; i < data.length; i++) {
                bigger[i] = data[i];
            }
            data = bigger;
        }
        data[size] = m;
        size++;
    }

    public Move get(int index) {
        return data[index];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}