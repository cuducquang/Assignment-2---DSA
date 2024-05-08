package Database.Methods;

import Database.DataStructure.List;
import Database.Place;


public class PlaceBinarySearch {
    public static int binarySearchStart(List<Place> list, double target) {
        int left = 0;
        int right = list.size() - 1;
        int result = -1; // Initialize result to -1


        while (left <= right) {
            int mid = left + (right - left) / 2;

            // Check if target is present at mid
            if (list.get(mid).getX() == target) {
                return mid;
            }

            // If target is greater, ignore left half
            if (list.get(mid).getX() < target) {
                left = mid + 1;
            }
            // If target is smaller, ignore right half
            else {
                result = mid;
                right = mid - 1;
            }
        }

        // If target is not found, return index of the first element higher than the target
        return result;
    }

    public static int binarySearchEnd(List<Place> list, double target) {
        int left = 0;
        int right = list.size() - 1;
        int result = -1; // Initialize result to -1

        while (left <= right) {
            int mid = left + (right - left) / 2;

            // If target is present at mid, return mid
            if (list.get(mid).getX() == target) {
                return mid;
            }

            // If target is greater than current element, move to the right half
            if (list.get(mid).getX() < target) {
                result = mid;
                left = mid + 1;
            }
            // If target is smaller than current element, update result and move to the left half
            else {
                right = mid - 1;
            }
        }

        // If target is not found, return the index of the first element lower than the target
        return result;
    }

//    public static void main(String[] args) {
//        Place place1 = new Place(2, 0,3234,null);
//        Place place2 = new Place(3, 0,3234,null);
//        Place place3 = new Place(9, 0,3234,null);
//        Place place4 = new Place(12, 0,3234,null);
//        Place place5 = new Place(4, 0,3234,null);
//        Place place6 = new Place(0, 0,3234,null);
//
//        List<Place> list = new List<>();
//        list.add(place1);
//        list.add(place2);
//        list.add(place3);
//        list.add(place4);
//        list.add(place5);
//        list.add(place6);
//
//
//        // Function call
//        PlaceQuickSort.quickSort(list);
//        int result = binarySearchStart(list, -1);
//        int result2 = binarySearchEnd(list, 3.9);
//        System.out.println(result);
//        System.out.println(result2);
//        System.out.println();
////        System.out.println(list.get(result).getX());
////        System.out.println(list.get(result2).getX());
//        for (int i = result; i <= result2; i++) {
//            System.out.println(list.get(i).getX());
//        }


//    }
}

