package datastructure.heap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * 自定义的堆
 * - 拥有反向索引表
 */

public class MyHeap<T> {

    private ArrayList<T> heap;
    private int heapSize;
    // 反向索引表
    private HashMap<T, Integer> map;
    private Comparator<? super T> comparator;

    public MyHeap(Comparator<T> comparator) {
        this.heap = new ArrayList<>();
        this.heapSize = 0;
        this.map = new HashMap<>();
        this.comparator = comparator;
    }

    public boolean contains(T obj) {
        return map.containsKey(obj);
    }

    public void push(T obj) {
        heap.add(obj);
        map.put(obj, heapSize);
        heapInsert(heapSize++);
    }

    public T pop() {
        if (heapSize == 0) {
            return null;
        }
        T ans = heap.get(0);
        swap(0, --heapSize);
        heapify(0);
        return ans;
    }

    public void remove(T obj) {
        if (!map.containsKey(obj)) {
            return;
        }
        T replace = heap.get(heapSize - 1);
        int index = map.get(obj);
        map.remove(obj);
        heap.remove(--heapSize);
        if (obj != replace) {
            heap.set(index, replace);
            map.put(replace, index);
            resign(replace);
        }
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }

    public void resign(T obj) {
        heapInsert(map.get(obj));
        heapify(map.get(obj));
    }

    private void heapInsert(int index) {
        int parent = (index - 1) >> 1;
        if (comparator.compare(heap.get(index), heap.get(parent)) > 0) {
            swap(index, parent);
            index = parent;
            parent = (index - 1) >> 1;
        }
    }

    private void heapify(int index) {
        int left = (index << 1) + 1;
        while (left < heapSize) {
            int best = left + 1 < heapSize && comparator.compare(heap.get(left), heap.get(left + 1)) < 0 ? left + 1 : left;
            swap(index, best);
            index = best;
            left = (index << 1) + 1;
        }
    }

    private void swap(int i, int j) {
        T o1 = heap.get(i);
        T o2 = heap.get(j);
        heap.set(i, o2);
        heap.set(j, o1);
        map.put(o1, j);
        map.put(o2, i);
    }
}
