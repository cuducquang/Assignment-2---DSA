package Database;

import java.util.Set;
import java.io.Serializable;

class Place implements Serializable{
    private final double x;
    private final double y;
    private HashSet<String> services;

    public Place(double x, double y, HashSet<String> services) {
        this.x = x;
        this.y = y;
        this.services = services;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public HashSet<String> getServices() {
        return services;
    }

    public void setServices(HashSet<String> services) {
        this.services = services;
    }

    @Override
    public String toString() {
        return "Place{" +
                "x=" + x +
                ", y=" + y +
                ", services=" + services +
                '}';
    }
}