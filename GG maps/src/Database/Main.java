package Database;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.*;

public class Main {
    private Map2D map;

    public Main(double width, double height, int capacity) {
        this.map = new Map2D(width, height, capacity);
    }

    public void displayMenu() {
        System.out.println("-------------Welcome to the Map 2D-------------------");
        System.out.println("1. Add a place");
        System.out.println("2. Edit a place");
        System.out.println("3. Remove a place");
        System.out.println("4. Search for places");
        System.out.println("5. Exit");
        System.out.println("Please enter your choice: ");
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while (choice != 5) {
            displayMenu();
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addPlace(scanner);
                    break;
                case 2:
//                    editPlace(scanner);
                    break;
                case 3:
//                    removePlace(scanner);
                    break;
                case 4:
//                    searchPlace(scanner);
                    break;
                case 5:
                    System.out.println("Exit the program");
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }

        scanner.close();
    }

    private void addPlace(Scanner scanner) {
        System.out.println("Enter the Coordinator of the place: ");
//        String
        System.out.println("Enter the name of the place: ");
        String name = scanner.nextLine();
    }


    public static void main(String[] args) {

        Map2D map = new Map2D(10000000, 10000000, 1000);
//        Set<String> types = new Set<>();
//        types.add("ATM");
//        Place place = new Place(500000, 500000, types);
//        map.add(place);
//        map.saveData("places_data.dat");
        map.loadData("places_data.dat");
        Set<Place> results = map.search(500000, 500000, 10000, 10000, "ATM", 5);
        Iterator<Place> iterator = results.iterator();
        while (iterator.hasNext()) {
            Place result = iterator.next();
            System.out.println(result.toString());
        }
    }
}