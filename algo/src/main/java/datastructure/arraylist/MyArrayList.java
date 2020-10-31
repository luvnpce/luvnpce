package datastructure.arraylist;

public class MyArrayList {

    public Object[] data;
    public int size;
    public int capacity;

    public MyArrayList(int capacity) {
        this.data = new Object[capacity];
        this.size = 0;
        this.capacity = capacity;
    }

    public boolean add(Object o) {
        if (size == capacity) {
//            expand();
        }
        this.data[size++] = o;
        return true;
    }
}
