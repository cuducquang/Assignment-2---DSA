package Database;
import java.util.Iterator;
import java.util.NoSuchElementException;

class Set<E> {
    private HashMap<E, Object> map;
    private static final Object PRESENT = new Object();

    public Set() {
        map = new HashMap<>();
    }

    public void add(E element) {
        map.put(element, PRESENT);
    }

    public boolean contains(E element) {
        return map.containsKey(element);
    }

    public void remove(E element) {
        map.remove(element);
    }

    public int size() {
        return map.size();
    }

    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Iterator<HashMap.Entry<E, Object>> mapIterator = map.iterator();

            @Override
            public boolean hasNext() {
                return mapIterator.hasNext();
            }

            @Override
            public E next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                return mapIterator.next().getKey();
            }

            @Override
            public void remove() {
                mapIterator.remove();
            }
        };
    }
}

