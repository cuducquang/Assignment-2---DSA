package Database;

import java.io.*;
import java.util.Iterator;

public class Map2D {
    private QuadTree quadTree;
    private final HashMap<String, Set<Place>> serviceIndex;

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
        Set<Place> places = serviceIndex.getOrDefault(serviceType, new Set<>());
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


    public void loadData(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            serviceIndex.clear();
            serviceIndex.putAll((HashMap<String, Set<Place>>) in.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveData(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(serviceIndex);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
