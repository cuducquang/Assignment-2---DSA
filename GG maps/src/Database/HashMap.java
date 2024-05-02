package Database;

public class HashMap<K, V> {
    private static final int CAPACITY = 16;

    private Entry<K, V>[] items;
    private int size;

    public HashMap() {
        items = new Entry[CAPACITY];
        size = 0;
    }

    public void put(K key, V value) {
        int index = getIndex(key);
        Entry<K, V> entry = items[index];
        while (entry != null) {
            if (entry.key.equals(key)) {
                entry.values.add(value); // Update existing key
                return;
            }
            entry = entry.next;
        }
        // Insert new key-value pair
        Entry<K, V> newEntry = new Entry<>(key, new HashSet<>());
        newEntry.values.add(value);
        newEntry.next = items[index];
        items[index] = newEntry;
        size++;
        if ((double) size == items.length) {
            resize();
        }
    }

    public HashSet<V> get(K key) {
        int index = getIndex(key);
        Entry<K, V> entry = items[index];
        while (entry != null) {
            if (entry.key.equals(key)) {
                return entry.values;
            }
            entry = entry.next;
        }
        return null; // Key not found
    }

    public int size() {
        return size;
    }

    private int getIndex(K key) {
        return Math.abs(key.hashCode()) % items.length;
    }

    private void resize() {
        Entry<K, V>[] oldBuckets = items;
        items = new Entry[oldBuckets.length * 2];
        size = 0;
        for (Entry<K, V> entry : oldBuckets) {
            while (entry != null) {
                for (V value : entry.values.toArray()) {
                    put(entry.key, value); // Reinsert each value with rehashed key
                }
                entry = entry.next;
            }
        }
    }

    private static class Entry<K, V> {
        K key;
        HashSet<V> values;
        Entry<K, V> next;

        Entry(K key, HashSet<V> values) {
            this.key = key;
            this.values = values;
        }
    }
}
