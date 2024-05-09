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
}
