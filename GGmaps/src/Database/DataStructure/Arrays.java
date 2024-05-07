package Database.DataStructure;

public class Arrays {
    @SuppressWarnings("unchecked")
    public static <T> List<T> asList(T... elements) {
        List<T> list = new List<>();
        for (T element : elements) {
            list.add(element);
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] copyOf(T[] original, int newLength) {
        T[] copy = (T[]) new Object[newLength];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }
}
