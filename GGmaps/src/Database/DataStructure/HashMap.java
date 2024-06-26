package Database.DataStructure;


import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashMap<K, V> {

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

    public Entry<K, V> has(K key) {
        if (key == null) {
            return null; // Null keys not supported
        }
        int index = Math.abs(getIndex(key));
        Entry<K, V> entry = table[index];;
        while (entry != null) {
            if (entry.key.equals(key)) {
                return entry;
            }
            entry = entry.next;
        }
        return null;
    }

    public boolean containsKey(K key) {
        return has(key) != null;
    }

    public List<K> getKeys() {

        List<K> keys = new List<K>();
        for (Entry<K, V> entry : table) {
            while (entry != null) {
                keys.add(entry.key);
                entry = entry.next;
            }
        }
        return keys;
    }

    public int size() {
        return size;
    }

    public void rehash() {
        HashMap<K, V> newMap = new HashMap<>();
        // Re-insert all elements into the new map
        Iterator<Entry<K, V>> entryIterator = entrySet().iterator();
        while (entryIterator.hasNext()) {
            Entry<K, V> entry = entryIterator.next();
            newMap.put(entry.getKey(), entry.getValue());
        }

        // Replace the original map with the new one
        this.table = newMap.table;
        this.size = newMap.size;
    }

    public V remove(K key) {
        if (key == null)
            return null; // Null keys not supported
        int index = getIndex(key);
        Entry<K, V> entry = table[index];
        Entry<K, V> prev = null;
        while (entry != null) {
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



    public static class Entry<K, V>{
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

