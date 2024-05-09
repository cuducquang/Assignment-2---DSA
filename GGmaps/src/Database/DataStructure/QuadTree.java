package Database.DataStructure;

import Database.Place;
import java.util.Iterator;

public class QuadTree {
    private final int capacity;
    private final Node root;
    public Set<Place> searchResult;
    public Set<Node> nodeResult;
    private int placeNum;

    public QuadTree(double x, double y, double width, double height, int capacity) {
        this.capacity = capacity;
        this.root = new Node(x, y, width, height, 0);
        this.placeNum = 0;
    }

    public int getPlaceNum() {
        return placeNum;
    }


    public int calculatePosition(Place place, Node node) {
        // North
        if (Math.abs(node.getY() - place.getY()) < node.getHeight()/2) {
            // West
            if (Math.abs(node.getX() - place.getX()) < node.getWidth()) {
                return 0;
            } else {
                // East
                return 1;
            }
        } else {
            // South
            if (Math.abs(node.getX() - place.getX()) < node.getWidth()) {
                return 2;
            } else {
                // East
                return 3;
            }
        }
    }

    public void insert(Place place) {
        insert(root, place);
    }

    private void insert(Node node, Place place) {
        if (node == null) {
            return;
        }
        if (node.places != null) {
            if (node.places.size() < capacity ) {
                if (place.getId() == 0) {
                    place.setPlaceId(placeNum + 1);
                    placeNum++;
                }
                node.places.add(place);

            }
            if (node.places.size() >= capacity) {
                split(node);
            }
        } else {
            insert(node.children[calculatePosition(place, node)], place);
        }
    }

    private void split(Node node) {
        double subWidth = node.width / 2;
        double subHeight = node.height / 2;
        double x = node.x;
        double y = node.y;

        node.children[0] = new Node(x, y, subWidth, subHeight, node.depth + 1);
        node.children[1] = new Node(x + subWidth, y, subWidth, subHeight, node.depth + 1);
        node.children[2] = new Node(x, y + subHeight, subWidth, subHeight, node.depth + 1);
        node.children[3] = new Node(x + subWidth, y + subHeight, subWidth, subHeight, node.depth + 1);

        // Transfer place to children
        Set<Place> places = node.places;
        node.places = null;

        Iterator<Place> placeIterator = places.iterator();
        while (placeIterator.hasNext()) {
            Place place = placeIterator.next();
            node.children[calculatePosition(place, node)].insert(place);
        }
    }


    public Set<Node> getPartiallyContainedNodes(double x, double y, double width, double height) {
        this.nodeResult = new Set<>();
        findPartiallyContainedNodes(root, x, y, width, height);
        return this.nodeResult;
    }

    private void findPartiallyContainedNodes(Node node, double x, double y, double width, double height) {
        if (node.isPartiallyContained(x, y, width, height)) {
            nodeResult.add(node);
        }
        if (node.children != null) {
            for (Node child : node.children) {
                if (child != null) {
                    findPartiallyContainedNodes(child, x, y, width, height);
                }
            }
        }
    }


    public Set<Place> getPlaceById(int id) {
        searchResult = new Set<>();
        getPlaceById(root, id);
        return searchResult;
    }

    public void getPlaceById(Node node, int id) {
        if (node == null) {
            return;
        }

        if (node.places != null) {
            Iterator<Place> placeIterator = node.places.iterator();
            while (placeIterator.hasNext()) {
                Place place = placeIterator.next();
                if (place.getId() == id) {
                    searchResult.add(place);
                    return;
                }
            }
        } else {
            for (int i = 0; i < 4; i++) {
                getPlaceById(node.children[i], id);
            }
        }
    }



    public Set<Place> search(double x, double y, double width, double height, String serviceType, int maxResults) {
        this.searchResult = new Set<>();
        search(root, x, y, width, height, serviceType, maxResults);
        return this.searchResult;
    }



    private void search(Node node, double x, double y, double width, double height, String serviceType, int maxResults) {
        if (node.places != null) {
            Iterator<Place> placeIterator = node.places.iterator();
            while (placeIterator.hasNext()) {
                Place place = placeIterator.next();
                if (place.getX() >= x && place.getX() < x + width && place.getY() >= y && place.getY() < y + height) {
                    if (place.haveService(serviceType)) {
                        this.searchResult.add(place);
                        if (this.searchResult.size() == maxResults) {
                            return;
                        }
                    }
                }
            }

        } else {
            for (int i = 0; i < 4; i++) {
                if (node.children[i].intersects(x, y, width, height)) {
                    search(node.children[i], x, y, width, height, serviceType, maxResults);
                }
                if (this.searchResult.size() == maxResults) {
                    return;
                }
            }
        }
    }


    public void remove(Place place) {
        remove(root, place);
    }

    private void remove(Node node, Place place) {
        if (node == null) {
            return;
        }
        // Check if the node contains the place
        // If it's a leaf node, remove the place from the node's places set
        if (node.places != null) {
            if (node.places.contains(place)) {
                node.places.remove(place);
                System.out.println("Successfully removed");
            }
        } else {
            // Otherwise, recursively remove from children
            remove(node.children[calculatePosition(place, node)], place);
            // If a child node was removed, check if the node needs to be merged
            if (node.places != null) {
                if (shouldMerge(node)) {
                    merge(node);
                }
            }
        }

    }

    // If total number of places returns to the capacity, merge
    private boolean shouldMerge(Node node) {
        int totalSize = 0;
        for (int i = 0; i < 4; i++) {
            totalSize += node.children[i].places.size();
        }
        if (totalSize <= capacity) {
            return true;
        }
        return false;
    }
    // Merge the child nodes to the parents
    private void merge(Node node) {
        for (int i = 0; i < 4; i++) {
            node.children[i] = null;
            Iterator<Place> placeIterator = node.places.iterator();
            while (placeIterator.hasNext()) {
                Place place = placeIterator.next();
                node.places.add(place);
            }
        }
    }


    public static class Node{
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

        public double getX(){
            return x;
        }

        public double getY(){
            return y;
        }

        public double getWidth() {
            return width;
        }

        public double getHeight() {
            return height;
        }

        public boolean intersects(double x, double y, double width, double height) {
            return !(x + width < this.x || y + height < this.y || x > this.x + this.width || y > this.y + this.height);
        }
        // Check if this node is only partially contained within a rectangle
        public boolean isPartiallyContained(double x, double y, double width, double height) {
            boolean intersects = intersects(x, y, width, height);
            boolean fullyContained = (this.x >= x && this.x + this.width <= x + width &&
                    this.y >= y && this.y + this.height <= y + height);
            return intersects || fullyContained;
        }

        public void insert(Place place) {
                places.add(place);
        }
        public Set<Place> getPlaces() {
            return places;
        }
    }
}
