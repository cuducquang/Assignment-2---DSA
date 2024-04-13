public class QuadtreeNode {
    private int xMin, xMax, yMin, yMax;
    private List<Place> placeList;
    private Map<QuadtreeNode, List<Place>> children;
    private static final QuadtreeNode NW = new QuadtreeNode();
    private static final QuadtreeNode NE = new QuadtreeNode();
    private static final QuadtreeNode SW = new QuadtreeNode();
    private static final QuadtreeNode SE = new QuadtreeNode();
    private static final int MAX_PLACES_PER_NODE = 10;

    public QuadtreeNode() {
        this.xMin = 0;
        this.xMax = 0;
        this.yMin = 0;
        this.yMax = 0;
        this.placeList = new List<Place>();
        this.children = new Map<QuadtreeNode, List<Place>>();
    }

    public QuadtreeNode(int xMin, int xMax, int yMin, int yMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.placeList = new List<>();
        this.children = new Map<>();
    }

    public void addPlace(int x, int y, String serviceName) {
        if (children.size() == 0){
            placeList.add(new Place(x, y, serviceName));
            if (placeList.size() > MAX_PLACES_PER_NODE){
                split();
            }
        } else {
            QuadtreeNode child = chooseChild(x, y);
            child.addPlace(x, y, serviceName);
        }
    }

    private QuadtreeNode chooseChild(int x, int y) {
        int midX = (xMin + xMax) / 2;
        int midY = (yMin + yMax) / 2;
        if (x <= midX && y <= midY) {
            return children.getOrDefault(QuadtreeNode.NW, new QuadtreeNode(xMin, yMin, midX, midY));
        } else if (x <= midX && y > midY) {
            return children.getOrDefault(QuadtreeNode.SW, new QuadtreeNode(xMin, midY, midX, yMax));
        } else if (x > midX && y <= midY) {
            return children.getOrDefault(QuadtreeNode.NE, new QuadtreeNode(midX, yMin, xMax, midY));
        } else {
            return children.getOrDefault(QuadtreeNode.SE, new QuadtreeNode(midX, midY, xMax, yMax));
        }
    }

    private void split() {
        // Implement your logic to split the node and redistribute places to children
        // This is a simplified version, you may need to adjust it based on your requirements
        int midX = (xMin + xMax) / 2;
        int midY = (yMin + yMax) / 2;
        children.put(QuadtreeNode.NW, new QuadtreeNode(xMin, yMin, midX, midY));
        children.put(QuadtreeNode.SW, new QuadtreeNode(xMin, midY, midX, yMax));
        children.put(QuadtreeNode.NE, new QuadtreeNode(midX, yMin, xMax, midY));
        children.put(QuadtreeNode.SE, new QuadtreeNode(midX, midY, xMax, yMax));

        for (Place place : placeList) {
            chooseChild(place.getX(), place.getY()).addPlace(place.getX(), place.getY(), place.getServiceName());
        }
        placeList.clear();
    }

    public void search(int xMin, int yMin, int xMax, int yMax, String serviceName, CustomList<Place> result) {
        // Implement your search logic recursively
        // This is a simplified version, you may need to adjust it based on your requirements
        if (this.xMax < xMin || this.xMin > xMax || this.yMax < yMin || this.yMin > yMax) {
            return;
        }

        for (Place place : places) {
            if (place.getX() >= xMin && place.getX() <= xMax && place.getY() >= yMin && place.getY() <= yMax
                    && place.getServiceName().equals(serviceName)) {
                result.add(place);
            }
        }

        for (QuadtreeNode child : children.keys()) {
            child.search(xMin, yMin, xMax, yMax, serviceName, result);
        }
    }
}
