public class Place {
    private int x;
    private int y;
    private String serviceName;

    public Place() {
        this.x = 0;
        this.y = 0;
        this.serviceName = "default";
    }

    public Place(int x, int y, String serviceName) {
        this.x = x;
        this.y = y;
        this.serviceName = serviceName;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getServiceName() {
        return serviceName;
    }
}
