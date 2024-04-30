package Database;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.*;

public class Main {
    public static void main(String[] args) {

        Map2D map = new Map2D(10000000, 10000000, 1000);
//        Set<String> types = new HashSet<>();
//        types.add("ATM");
//        Place place = new Place(500000, 500000, types);
//        map.add(place);
//        map.saveData("places_data.dat");
        map.loadData("places_data.dat");
        Set<Place> results = map.search(500000, 500000, 10000, 10000, "ATM", 5);
        for (Place result : results) {
            System.out.println(result.toString());
        }
    }
}