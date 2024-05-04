package Database.DataStructure;

import Database.Place;

public class MinHeap {
    private Place[] heap;
    private int size;
    private int capacity;

    public MinHeap(int capacity) {
        this.capacity = capacity;
        this.heap = new Place[capacity];
        this.size = 0;
    }

    private int parent(int i) { return (i - 1) / 2; }
    private int leftChild(int i) { return 2 * i + 1; }
    private int rightChild(int i) { return 2 * i + 2; }

    public void add(Place place) {
        if (size == capacity) throw new IllegalStateException("Heap is full");
        heap[size] = place;
        int current = size++;
        while (heap[current].compareTo(heap[parent(current)]) < 0) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    public Place extractMin() {
        Place min = heap[0];
        heap[0] = heap[--size];
        minHeapify(0);
        return min;
    }

    private void minHeapify(int i) {
        int left = leftChild(i);
        int right = rightChild(i);
        int smallest = i;
        if (left < size && heap[left].compareTo(heap[i]) < 0)
            smallest = left;
        if (right < size && heap[right].compareTo(heap[smallest]) < 0)
            smallest = right;
        if (smallest != i) {
            swap(i, smallest);
            minHeapify(smallest);
        }
    }

    private void swap(int i, int j) {
        Place temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
}

