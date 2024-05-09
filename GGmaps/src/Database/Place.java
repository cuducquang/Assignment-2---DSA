package Database;

import Database.DataStructure.Set;
import java.util.Iterator;

public class Place{
    private final double x;
    private final double y;
    private int placeId;
    private Set<String> services;

    public Place(double x, double y, int placeId, Set<String> services) {
        this.x = x;
        this.y = y;
        this.placeId =placeId;
        this.services = services;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public void setServices(Set<String> services) {
        this.services = services;
    }

    public boolean haveService(String serviceType) {
        Iterator<String> iterator = services.iterator();
        while (iterator.hasNext()) {
            String service = iterator.next();
            if (service.equals(serviceType)) {
                return true;
            }
        }
        return false;
    }

    public int getId() {
        return placeId;
    }

    @Override
    public String toString() {
        return "Place{" +
                "x=" + x +
                ", y=" + y +
                ", placeId=" + placeId +
                ", services=" + services +
                '}';
    }
}
