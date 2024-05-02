package Database;
import java.util.Iterator;



public class QuadTree {
    private final int capacity;
    private final Node root;

    public QuadTree(double x, double y, double width, double height, int capacity) {
        this.capacity = capacity;
        this.root = new Node(x, y, width, height, 0);
    }

    public void insert(Place place) {
        insert(root, place);
    }

    private void insert(Node node, Place place) {
        if (node.places != null) {
            if (node.places.size() < capacity || node.depth == 100) {
                node.places.add(place);
                return;
            } else {
                if (node.children[0] == null) {
                    split(node);
                }

                for (int i = 0; i < 4; i++) {
                    if (node.children[i].contains(place)) {
                        insert(node.children[i], place);
                        return;
                    }
                }
            }
        }
    }

    private void split(Node node) {
        double subWidth = node.width / 2;
        double subHeight = node.height / 2;
        double x = node.x;
        double y = node.y;

        node.children[0] = new Node(x + subWidth, y, subWidth, subHeight, node.depth + 1);
        node.children[1] = new Node(x, y, subWidth, subHeight, node.depth + 1);
        node.children[2] = new Node(x, y + subHeight, subWidth, subHeight, node.depth + 1);
        node.children[3] = new Node(x + subWidth, y + subHeight, subWidth, subHeight, node.depth + 1);

        Set<Place> places = node.places;
        node.places = null;

        Iterator<Place> placeIterator = places.iterator();
        while (placeIterator.hasNext()) {
            Place place = placeIterator.next();
            for (int i = 0; i < 4; i++) {
                if (node.children[i].contains(place)) {
                    node.children[i].insert(place);
                    break;
                }
            }
        }
    }

    public Set<Place> search(double x, double y, double width, double height, String serviceType, int maxResults) {
        Set<Place> result = new Set<>();
        search(root, x, y, width, height, serviceType, result, maxResults);
        return result;
    }

    private void search(Node node, double x, double y, double width, double height, String serviceType, Set<Place> result, int maxResults) {
        if (node == null) {
            return;
        }

        if (node.places != null) {
            Iterator<Place> placeIterator = node.places.iterator();
            while (placeIterator.hasNext()) {
                Place place = placeIterator.next();
                if (place.getX() >= x && place.getX() < x + width && place.getY() >= y && place.getY() < y + height) {
                    if (place.getServices().contains(serviceType)) {
                        result.add(place);
                        if (result.size() == maxResults) {
                            return;
                        }
                    }
                }
            }

        } else {
            for (int i = 0; i < 4; i++) {
                if (node.children[i].intersects(x, y, width, height)) {
                    search(node.children[i], x, y, width, height, serviceType, result, maxResults);
                }
            }
        }
    }


    public void remove(Place place) {
        remove(root, place);
    }

    private boolean remove(Node node, Place place) {
        if (node == null) {
            return false;
        }

        // Check if the node contains the place
        if (node.contains(place)) {
            // If it's a leaf node, remove the place from the node's places set
            if (node.places != null && node.places.contains(place)) {
                node.places.remove(place);
                return true;
            } else {
                // Otherwise, recursively remove from children
                for (int i = 0; i < 4; i++) {
                    if (remove(node.children[i], place)) {
                        // If a child node was removed, check if the node needs to be merged
                        if (shouldMerge(node)) {
                            merge(node);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean shouldMerge(Node node) {
        for (int i = 0; i < 4; i++) {
            if(node.children[i].places.size() != 0) {
                return false;
            }
        }
        return true;
    }

    private void merge(Node node) {
        for (int i = 0; i < 4; i++) {
            node.children[i] = null;
        }
    }


    private static class Node {
        private final double x;
        private final double y;
        private final double width;
        private final double height;
        private final int depth;
        private Set<Place> places;
        private Node[] children;

        public Node(double x, double y, double width, double height, int depth) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.depth = depth;
            this.places = new Set<>();
            this.children = new Node[4];
        }

        public boolean contains(Place place) {
            double x = place.getX();
            double y = place.getY();
            return (x >= this.x && x < this.x + width && y >= this.y && y < this.y + height);
        }

        public boolean intersects(double x, double y, double width, double height) {
            return !(x + width < this.x || y + height < this.y || x > this.x + this.width || y > this.y + this.height);
        }

        public void insert(Place place) {
                places.add(place);
        }
    }
}
