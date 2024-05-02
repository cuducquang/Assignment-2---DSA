package  Database;
public class HashSet<T> {
    private static final int CAPACITY = 16;

    private Entry<T>[] items;
    private int size;

    public HashSet() {
        items = new Entry[CAPACITY];
        size = 0;
    }

    public void add(Place place) {
        int index = getIndex((T) place);
        Entry<T> entry = items[index];
        while (entry != null) {
            if (entry.value.equals(place)) {
                return; // Element already exists
            }
            entry = entry.next;
        }
        Entry<T> newEntry = new Entry<>((T) place);
        newEntry.next = items[index];
        items[index] = newEntry;
        size++;
        if (size == items.length) {
            resize();
        }
    }

    public boolean contains(T element) {
        int index = getIndex(element);
        Entry<T> entry = items[index];
        while (entry != null) {
            if (entry.value.equals(element)) {
                return true; // Element found
            }
            entry = entry.next;
        }
        return false; // Element not found
    }

    public void remove(Place element) {
        int index = getIndex((T) element);
        Entry<T> entry = items[index];
        Entry<T> prev = null;
        while (entry != null) {
            if (entry.value.equals(element)) {
                if (prev == null) {
                    items[index] = entry.next;
                } else {
                    prev.next = entry.next;
                }
                size--;
                return;
            }
            prev = entry;
            entry = entry.next;
        }
    }

    public int size() {
        return size;
    }

    private int getIndex(T element) {
        return Math.abs(element.hashCode()) % items.length;
    }

    private void resize() {
        Entry<T>[] oldBuckets = items;
        items = new Entry[oldBuckets.length * 2];
        size = 0;
        for (Entry<T> entry : oldBuckets) {
            while (entry != null) {
                add((Place) entry.value);
                entry = entry.next;
            }
        }
    }

    public T[] toArray() {
        // Create a new array of the same type as the elements in the HashSet
        T[] array = (T[]) new Object[size];

        // Copy elements from the HashSet to the array
        int index = 0;
        for (Entry<T> entry : items) {
            while (entry != null) {
                array[index++] = entry.value;
                entry = entry.next;
            }
        }

        return array;
    }


    private static class Entry<T> {
        T value;
        Entry<T> next;

        Entry(T value) {
            this.value = value;
        }
    }
}
