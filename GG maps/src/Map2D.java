public class Map2D {
    private QuadtreeNode root;

    public Map2D() {
        root = null;
    }

    public Map2D(int xMin, int yMin, int xMax, int yMax) {
        this.root = new QuadtreeNode(xMin, yMin, xMax, yMax);
    }

    public void addPlace(int x, int y, String serviceName) {
        root.addPlace(x, y, serviceName);
    }

}
