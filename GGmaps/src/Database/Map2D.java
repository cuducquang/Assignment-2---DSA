package Database;

import Database.DataStructure.HashMap;
import Database.DataStructure.QuadTree;
import Database.DataStructure.Set;
import Database.DataStructure.List;
import Database.Methods.PlaceBinarySearch;
import Database.Methods.PlaceQuickSort;

import java.util.Iterator;

public class Map2D {
    private QuadTree quadTree;
    public Map2D(double width, double height, int capacity) {
        this.quadTree = new QuadTree(0, 0, width, height, capacity);
    }



    public void add(Place place) {
        quadTree.insert(place);
    }

    public void edit(Place place, Set<String> newServices) {
        quadTree.remove(place);
        // Update the place with new services
        place.setServices(newServices);
        // Add the place back to quadTree
        quadTree.insert(place);
    }

    public void remove(Place place) {
        quadTree.remove(place);
    }

    public Set<Place> search(double x, double y, double width, double height, String serviceType, int maxResults) {
        Set<Place> places = quadTree.search(x, y, width, height, serviceType, maxResults);
        return places;
    }


    public int getHighestPlaceId() {
        return quadTree.getPlaceNum();
    }


    public Place getPlaceById(int id) {
        Iterator<Place> placeIterator = quadTree.getPlaceById(id).iterator();
        while (placeIterator.hasNext()) {
            Place place = placeIterator.next();
            if (place.getId() == id) {
                return place;
            }
        }
        return null;
    }


    public Set<Place> searchByCurrentPosition(double centerX, double centerY, double width, double height, String serviceType, int maxResults) {
        // Calculate the bounding rectangle based on the given center (X, Y) and radius
        Set<Place> result = new Set<>();
        double topLeftX = centerX - width / 2;
        double topLeftY = centerY + height / 2;
        double mapMaxRange = 100000000;
        System.out.println("topLeftX: " + topLeftX + " topLeftY: " + topLeftY + " width: " + width + " height: " + height);

        if (topLeftX < 0 || topLeftY < 0 || topLeftX > mapMaxRange || topLeftY > mapMaxRange || topLeftX + width > mapMaxRange || topLeftY - height < 0) {
            System.out.println("Error: The selected area are out of bounds. Please try again within the maximum bound of 1e8 x 1e8.");
            return result;
        }

        // Use the QuadTree to search for nodes that intersect this bounding rectangle
        Set<QuadTree.Node> boundedNodes = quadTree.getPartiallyContainedNodes(topLeftX, topLeftY, width, height);
        int validLocations = 0;
        int numOfLocationsProcessed = 0;
        Iterator<QuadTree.Node> iterator = boundedNodes.iterator();
        while (iterator.hasNext()) {

            if (validLocations == maxResults) {
                break;
            }
            QuadTree.Node node = iterator.next();
            Set<Place> places = node.getPlaces();
            if (places == null) {
                continue;
            }


//          Metric for evaluation
            numOfLocationsProcessed += places.size();

//          Create a list of places and sort by x coordinates
            List<Place> sortedList = places.toList();
            PlaceQuickSort.quickSort(sortedList);
//          Print list for testing
//          PlaceQuickSort.printList(sortedList);

//            Find the starting point in the list by X
            int start = PlaceBinarySearch.binarySearchStart(sortedList, topLeftX);
            int end = PlaceBinarySearch.binarySearchEnd(sortedList, topLeftX +width);

//            If there are no nodes in the rectangle
            if (start == -1 || end == -1) {
                continue;
            }

            for (int i = start; i <= end; i++) {
                if (validLocations >= maxResults) {
                    break; // Stop if max results reached
                }
                Place place = sortedList.get(i);
                if (place.getX() >= topLeftX && place.getX() <= topLeftX + width &&
                        place.getY() <= topLeftY && place.getY() >= topLeftY - height &&
                        place.haveService(serviceType)) {
                    validLocations++;
                    result.add(place);
                }
            }
        }
        System.out.println("Processed " + numOfLocationsProcessed + " places.");
        return result;
    }
}
