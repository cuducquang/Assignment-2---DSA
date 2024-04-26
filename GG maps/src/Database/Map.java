package Database;

public class Map<K, V> {
    private List<K> keys;
    private List<V> values;

    public Map() {
        this.keys = new List<>();
        this.values = new List<>();
    }

    public void put(K key, V value) {
        keys.add(key);
        values.add(value);
    }

    public V get(K key) {
        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i).equals(key)) {
                return values.get(i);
            }
        }
        return null;
    }

    public int size() {
        return keys.size();
    }

    public V getOrDefault(K key, V defaultValue) {
        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i).equals(key)) {
                return values.get(i);
            }
        }
        return defaultValue;
    }
}
