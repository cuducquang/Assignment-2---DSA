package Database;

import Database.DataStructure.HashMap;
import Database.DataStructure.QuadTree;
import Database.DataStructure.Set;

import java.io.*;
import java.util.Iterator;

public class Map2D {
    private QuadTree quadTree;
    private final HashMap<String, Set<Place>> serviceIndex;

    private final String filename = "places_data.dat";

    public Map2D(double width, double height, int capacity) {
        this.serviceIndex = new HashMap<>();
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
        boolean removed = quadTree.remove(place);
        if (removed) {
            System.out.println("Place removed successfully.");
        } else {
            System.out.println("Failed to remove place. Place not found.");
        }
    }

    public Set<Place> search(double x, double y, double width, double height, String serviceType, int maxResults) {
        Set<Place> result = new Set<>();
        Set<Place> places = searchWithQuadTree(x, y, width, height, serviceType, maxResults);
        Iterator<Place> iterator = places.iterator();
        while (iterator.hasNext()) {
            Place place = iterator.next();
            if (place.getX() >= x && place.getX() < x + width && place.getY() >= y && place.getY() < y + height) {
                result.add(place);
                if (result.size() == maxResults) {
                    break;
                }
            }
        }

        return result;
    }

    public Set<Place> searchWithQuadTree(double x, double y, double width, double height, String serviceType, int maxResults) {
        return quadTree.search(x, y, width, height, serviceType, maxResults);
    }

    public int getHighestPlaceId() {
        int highestPlaceId = 0;
        Iterator<Place> placeIterator = quadTree.getAllPlaces().iterator();
        while (placeIterator.hasNext()) {
            int currentPlaceId = placeIterator.next().getId(); // Assuming Place has an ID attribute
            if (currentPlaceId > highestPlaceId) {
                highestPlaceId = currentPlaceId;
            }
        }
        return highestPlaceId;
    }

    public Set<Place> getAllPlaces() {
        return quadTree.getAllPlaces();
    }

    public void reArrange(){
        quadTree.reArrangePlaces();
    }


    // Load all data
    public void loadData() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            QuadTree loadedQuadTree = (QuadTree) in.readObject();
            this.quadTree = loadedQuadTree;
            quadTree.reArrangePlaces();
        } catch (EOFException eofException) {
            // Handle EOFException separately
            System.err.println("Unexpected end of file: " + filename);
            eofException.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            // Handle other exceptions
            e.printStackTrace();
        }
    }



    public void saveData(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(quadTree);
            System.out.println("Data saved successfully to: " + filename);
        } catch (IOException e) {
            System.err.println("Error saving data to file: " + filename);
            e.printStackTrace();
        }
    }

    public void searchByCurrentPosition(double centerX, double centerY, double radius, String serviceType) {
        // Calculate the bounding rectangle based on the given center (X, Y) and radius

        double x1 = centerX + Math.sqrt(2*radius*radius);
        double y1 = centerY + Math.sqrt(2*radius*radius);
        double width = 2 * radius;
        double height = 2 * radius;

        // Use the QuadTree to search for nodes that intersect this bounding rectangle
        Set<QuadTree.Node> boundedNodes = quadTree.getPartiallyContainedNodes(x1, y1, width, height);

        int validLocations = 0;
        int maxResults = 50; // Define the maximum number of results

        Iterator<QuadTree.Node> iterator = boundedNodes.iterator();


        while (iterator.hasNext()) {
            QuadTree.Node node = iterator.next();
            Set<Place> places = node.getPlaces();
            if (places == null) {
                continue;
            }

            Iterator<Place> placeIterator = places.iterator();
            while(placeIterator.hasNext()) {
                Place place = placeIterator.next();
                if (place.getX() >= x1 && place.getX() <= x1 + width &&
                        place.getY() >= y1 && place.getY() <= y1 + height &&
                        place.haveService(serviceType)) {
                    validLocations++;
                    System.out.println(place);
                    if (validLocations >= maxResults) {
                        break; // Stop if max results reached
                    }
                }
            }
            if (validLocations == maxResults) {
                break;
            }
        }

        System.out.println("Number of valid locations found: " + validLocations);
    }

}
