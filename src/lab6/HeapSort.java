package lab6;

/**
 * Пирамидальная сортировка (heapsort).
 * Задача 18:
 * Отсортировать последовательность так, чтобы вначале шли
 * отсортированные чётные элементы, а затем отсортированные нечётные.
 */
public class HeapSort {

    /**
     * arr - массив
     * n - размер кучи
     * i - индекс корня поддерева
     */
    public static void heapify(int[] arr, int n, int i) {
        while (true) {
            int largest = i;
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            // ищем наибольшего среди корня, левого и правого
            if (left < n && arr[left] > arr[largest]) {
                largest = left;
            }
            if (right < n && arr[right] > arr[largest]) {
                largest = right;
            }


            if (largest == i) {
                break;
            }

            // меняем корень с наибольшим
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;

            // продолжаем просеивать вниз
            i = largest;
        }
    }


    public static void buildHeap(int[] arr, int n) {
        // Вторая половина элементов массива — это самые нижние «листья» дерева, у которых физически нет детей.
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
    }


    /**
     * Сортирует массив по возрастанию пирамидальной сортировкой.
     */
    public static void heapSort(int[] arr, int n) {

        buildHeap(arr, n);

        //  по одному извлекаем максимум
        for (int i = n - 1; i > 0; i--) {
            // меняем корень (максимум) с последним
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // восстанавливаем кучу для оставшихся
            heapify(arr, i, 0);
        }
    }



    public static int[] solveTask18(int[] arr) {
        int n = arr.length;

        // 1. считаем чётные и нечётные
        int evenCount = 0;
        int oddCount = 0;
        for (int i = 0; i < n; i++) {
            if (arr[i] % 2 == 0) {
                evenCount++;
            } else {
                oddCount++;
            }
        }

        // 2. создаём два массива
        int[] even = new int[evenCount];
        int[] odd = new int[oddCount];

        // 3. заполняем
        int eIdx = 0;
        int oIdx = 0;
        for (int i = 0; i < n; i++) {
            if (arr[i] % 2 == 0) {
                even[eIdx++] = arr[i];
            } else {
                odd[oIdx++] = arr[i];
            }
        }

        // 4. сортируем каждый
        heapSort(even, evenCount);
        heapSort(odd, oddCount);

        // 5. склеиваем
        int[] result = new int[n];
        for (int i = 0; i < evenCount; i++) {
            result[i] = even[i];
        }
        for (int i = 0; i < oddCount; i++) {
            result[evenCount + i] = odd[i];
        }

        return result;
    }
}