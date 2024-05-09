package Database.Methods;

import Database.DataStructure.List;
import Database.Place;

public class PlaceQuickSort {
    public static void quickSort(List<Place> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        quickSort(list, 0, list.size() - 1);
    }

    // A utility function to swap two elements
    static void swap(List<Place> list, int i, int j)
    {
        Place temp = list.get(i);
        list.set(i,list.get(j));
        list.set(j,temp);
    }

    // This function takes last element as pivot,
    // places the pivot element at its correct position
    // in sorted list, and places all smaller to left
    // of pivot and all greater elements to right of pivot
    static int partition(List<Place> list, int low, int high)
    {
        // Choosing the pivot
        Place pivot = list.get(high);

        // Index of smaller element and indicates
        // the right position of pivot found so far
        int i = (low - 1);

        for (int j = low; j <= high - 1; j++) {

            // If current element is smaller than the pivot
            if (list.get(j).getX() < pivot.getX()) {

                // Increment index of smaller element
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i + 1, high);
        return (i + 1);
    }

    // The main function that implements QuickSort
    // list[] --> list to be sorted,
    // low --> Starting index,
    // high --> Ending index
    static void quickSort(List<Place> list, int low, int high)
    {
        if (low < high) {

            // pi is partitioning index, list[p]
            // is now at right place
            int pi = partition(list, low, high);

            // Separately sort elements before
            // partition and after partition
            quickSort(list, low, pi - 1);
            quickSort(list, pi + 1, high);
        }
    }
}