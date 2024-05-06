package Database;

import Database.DataStructure.Set;

import java.io.Serializable;
import java.util.Iterator;
import java.io.Serial;


public class Place implements Serializable{
    @Serial
    private static final long serialVersionUID = 2039612322674750625L;
    private final double x;
    private final double y;
    private String placeName;
    private int placeId;
    private Set<String> services;

    public Place(double x, double y, int placeId, Set<String> services) {
        this.x = x;
        this.y = y;
        this.placeId =placeId;
        this.services = services;
        this.placeName = null;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getPlaceName() {
        return placeName;
    }

    public Set<String> getServices() {
        return services;
    }

    public void setServices(Set<String> services) {
        this.services = services;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
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

    private String servicesToString() {
        StringBuilder sb = new StringBuilder("[");
        Iterator<String> iterator = services.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next());
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Place{" +
                "x=" + x +
                ", y=" + y +
                ", placeName='" + placeName + '\'' +
                ", services=" + services.toString() +
                '}';
    }

    public int getId() {
        return placeId;
    }
}
