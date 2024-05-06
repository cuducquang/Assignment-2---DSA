package Database.DataStructure;

import Database.Place;

import java.io.Serial;
import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashMap<K, V> implements Serializable {

    @Serial
    private static final long serialVersionUID = -1969132081001806847L;
    private static final int CAPACITY = 16;
    private Entry<K, V>[] table;
    private int size;

//    @SuppressWarnings("unchecked")
    public HashMap() {
        table = new Entry[CAPACITY];
        size = 0;
    }

    public void put(K key, V value) {
        if (key == null)
            return; // Null keys not supported
        int index = Math.abs(getIndex(key));
        if (table[index] == null) {
            table[index] = new Entry<>(key, value);
        } else {
            Entry<K, V> entry = table[index];
            while (entry != null) {
                if (entry.key.equals(key)) {
                    entry.value = value;
                    return;
                }
                if (entry.next == null) {
                    break;
                }
                entry = entry.next;
            }
            entry.next = new Entry<>(key, value);
        }
        size++;

    }

    public V get(K key) {
        if (key == null)
            return null; // Null keys not supported
        int index = Math.abs(getIndex(key));
        Entry<K, V> entry = table[index];;
        while (entry != null) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
            entry = entry.next;
        }
        return null;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public int size() {
        return size;
    }

    public V getOrDefault(K key, V defaultValue) {
        V value = get(key);
        return value != null ? value : defaultValue;
    }

    public V remove(K key) {
        if (key == null)
            return null; // Null keys not supported
        int index = Math.abs(getIndex(key));
        Entry<K, V> entry = table[index];
        Entry<K, V> prev = null;
        while (entry != null) {
            System.out.println(entry.key.toString());
            if (entry.key.equals(key)) {
                if (prev == null) {
                    table[index] = entry.next;
                } else {
                    prev.next = entry.next;
                }
                size--;
                return entry.value;
            }
            prev = entry;
            entry = entry.getNext();
        }
        return null;
    }


    private int getIndex(K key) {
        return Math.abs(key.hashCode() % table.length);
    }

    public void clear() {
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        size = 0;
    }

    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entrySet = new Set<>();
        for (Entry<K, V> entry : table) {
            while (entry != null) {
                entrySet.add(entry);
                entry = entry.next;
            }
        }
        return entrySet;
    }


    public Set<V> values() {
        Set<V> values = new Set<>();
        for (Entry<K, V> entry : table) {
            while (entry != null) {
                values.add(entry.value);
                entry = entry.next;
            }
        }
        return values;
    }

    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<Entry<K, V>>() {
            private int index = 0;
            private Entry<K, V> currentEntry = null;

            @Override
            public boolean hasNext() {
                // Check if there's a next entry in the table or in the current entry's chain
                if (currentEntry != null && currentEntry.next != null) {
                    return true;
                }
                while (index < table.length) {
                    if (table[index] != null) {
                        return true;
                    }
                    index++;
                }
                return false;
            }

            @Override
            public Entry<K, V> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                if (currentEntry != null && currentEntry.next != null) {
                    currentEntry = currentEntry.next;
                } else {
                    while (table[index] == null) {
                        index++;
                    }
                    currentEntry = table[index++];
                }
                return currentEntry;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("remove() method is not supported.");
            }
        };
    }



    public static class Entry<K, V> implements Serializable{
        @Serial
        private static final long serialVersionUID = -8520682291953982672L;

        K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public Entry<K, V> getNext() {return next;}
    }
}

