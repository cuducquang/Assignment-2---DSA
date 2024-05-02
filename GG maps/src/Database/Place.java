package Database;

import java.io.Serial;
import java.io.Serializable;

import java.util.Set;

public class Place implements Serializable{
    @Serial
    private static final long serialVersionUID = 2039612322674750625L;
    private final double x;
    private final double y;
    private Set<String> services;

    public Place(double x, double y, Set<String> services) {
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

    public Set<String> getServices() {
        return services;
    }

    public void setServices(Set<String> services) {
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