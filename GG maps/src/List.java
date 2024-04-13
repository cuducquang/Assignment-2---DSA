public class List<T> {
    private Object[] elements;
    private int size;
    private static final int INITIAL_CAPACITY = 16;

    public List() {
        this.elements = new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    public void add(T element) {
        ensureCapacity();
        elements[size++] = element;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return (T) elements[index];
    }

    public int size() {
        return size;
    }

    private void ensureCapacity() {
        if (size == elements.length) {
            Object[] newElements = new Object[2 * elements.length];
            System.arraycopy(elements, 0, newElements, 0, size);
            elements = newElements;
        }
    }

    public void clear(){
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }
}
