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
        Set<String> services = place.getServices();
        Iterator<String> serviceIterator = services.iterator();
        while (serviceIterator.hasNext()) {
            String service = serviceIterator.next();
            if (!serviceIndex.containsKey(service)) {
                serviceIndex.put(service, new Set<>());
            }
            serviceIndex.get(service).add(place);
        }
    }

    public void edit(Place place, Set<String> newServices) {
        // Remove the place from serviceIndex
        Iterator<String> serviceIterator = place.getServices().iterator();
        while (serviceIterator.hasNext()) {
            String service = serviceIterator.next();
            Iterator<Place> iterator = serviceIndex.get(service).iterator();
            while (iterator.hasNext()) {
                Place currentPlace = iterator.next();
                if (currentPlace.equals(place)) {
                    iterator.remove();
                    break; // No need to continue iterating
                }
            }
        }

        // Update the place with new services
        place.setServices(newServices);

        // Add the place back to serviceIndex
        while (serviceIterator.hasNext()) {
            String service = serviceIterator.next();
            if (!serviceIndex.containsKey(service)) {
                serviceIndex.put(service, new Set<>());
            }
            serviceIndex.get(service).add(place);
        }

    }

    public void remove(Place place) {
        quadTree.remove(place);
        Iterator<String> serviceIterator = place.getServices().iterator();
        while (serviceIterator.hasNext()) {
            String service = serviceIterator.next();
            Iterator<Place> iterator = serviceIndex.get(service).iterator();
            while (iterator.hasNext()) {
                Place currentPlace = iterator.next();
                if (currentPlace.equals(place)) {
                    iterator.remove();
                    break; // No need to continue iterating
                }
            }
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
        Iterator<Set<Place>> setIterator = serviceIndex.values().iterator();
        while (setIterator.hasNext()) {
            Iterator<Place> placeIterator = setIterator.next().iterator();
            while (placeIterator.hasNext()) {
                int currentPlaceId = placeIterator.next().getId(); // Assuming Place has an ID attribute
                if (currentPlaceId > highestPlaceId) {
                    highestPlaceId = currentPlaceId;
                }
            }
        }
        return highestPlaceId;
    }



    // Load all data
    public void loadData() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            QuadTree loadedQuadTree = (QuadTree) in.readObject();
            this.quadTree = loadedQuadTree;
        } catch (EOFException eofException) {
            // Handle EOFException separately
            System.err.println("Unexpected end of file: " + filename);
            eofException.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            // Handle other exceptions
            e.printStackTrace();
        }
    }

    // Load all places containing a service
    public void loadPlacesForService(String service) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            HashMap<String, Set<Place>> data = (HashMap<String, Set<Place>>) in.readObject();
            Set<Place> values = data.getOrDefault(service, new Set<>());
            serviceIndex.put(service, values);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Load services of a place
    public void loadServicesForPlace(int placeId) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            HashMap<String, Set<Place>> data = (HashMap<String, Set<Place>>) in.readObject();
            Iterator<Set<Place>> iterator = data.values().iterator();
            while (iterator.hasNext()) {
                Set<Place> places = iterator.next();
                Iterator<Place> placeIterator = places.iterator();
                while (placeIterator.hasNext()) {
                    Place place = placeIterator.next();
                    if (place.getId() == placeId) {
                        Iterator<String> serviceIterator = place.getServices().iterator();
                        while (serviceIterator.hasNext()) {
                            String service = serviceIterator.next();
                            serviceIndex.put(service, new Set<Place>());
                            serviceIndex.get(service).add(place);
                        }
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
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


}
