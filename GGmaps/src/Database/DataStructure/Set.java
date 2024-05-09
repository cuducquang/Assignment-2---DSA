package Database.DataStructure;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Set<E> {
    private HashMap<E, Object> map;

    public Set() {
        map = new HashMap<>();
    }

    public List<E> toList() {
        List<E> list = new List<E>();
        for (E element : map.getKeys()) {
            list.add(element);
        }
        return list;
    }

    public void add(E element) {
        map.put(element, null);
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

    public void reArrange() {
        HashMap<E, Object> newMap = this.map;
        newMap.rehash();
        this.map = newMap;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator<E> iterator = iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next());
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
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

