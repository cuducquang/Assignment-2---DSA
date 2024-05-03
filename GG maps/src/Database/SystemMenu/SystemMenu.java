package Database.SystemMenu;

import Database.*;
import Database.DataStructure.Arrays;
import Database.DataStructure.List;
import Database.DataStructure.Set;

import java.util.Random;
import java.util.Scanner;

import java.util.Iterator;

public class SystemMenu {
    int mapWidth = 10000000;
    int mapHeight = 10000000;
    int numberOfPlaces = 1000;
    int capacity = 100;

    Map2D map = new Map2D(mapWidth, mapHeight, capacity);

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
//        List<String> serviceTypes = Arrays.asList("ATM", "Restaurant", "Hospital", "Gas Station", "Coffee Shop", "Pharmacy", "Park", "School", "Supermarket", "Library");
//        Random rand = new Random();
//        String placeName = "RMIT";
//        for (int i = 0; i < numberOfPlaces; i++) {
//            double x = rand.nextDouble() * mapWidth;
//            double y = rand.nextDouble() * mapHeight;
//            int numberOfServices = rand.nextInt(5) + 1;
//            Set<String> services = new Set<>();
//            for (int j = 0; j < numberOfServices; j++) {
//                int randomIndex = rand.nextInt(serviceTypes.size());
//                services.add(serviceTypes.get(randomIndex));
//            }
//
//            Place place = new Place(x, y, placeName, services);
//
//            map.add(place);
//        }
//        map.saveData("places_data.dat");
        map.loadData("places_data.dat");
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
                    editPlace(scanner);
                    break;
                case 3:
//                    removePlace();
                    break;
                case 4:
                    searchPlace();
                    break;
                case 5:
                    System.out.println("Exit the program");
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }

    public void addPlace(Scanner scanner) {
        System.out.println("Enter the coordinate (X) of the place: ");
        double x = scanner.nextDouble();
        System.out.println("Enter the coordinate (Y) of the place: ");
        double y = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter the name of the place: ");
        String placeName = scanner.nextLine();
        System.out.println("Enter the service types of the place (up to 5 types, separated by commas): ");
        System.out.println("Available service types:");
        List<String> serviceTypes = Arrays.asList("ATM", "Restaurant", "Hospital", "Gas Station", "Coffee Shop", "Pharmacy", "Park", "School", "Supermarket", "Library");
        for (int i = 0; i < serviceTypes.size(); i++) {
            System.out.println(serviceTypes.get(i));
        }
        String input = scanner.nextLine();
        String[] selectedServices = input.split(",\\s*");

        if (selectedServices.length > 5) {
            System.out.println("You can select up to 5 service types. Please try again.");
            return;
        }
        Set<String> services = new Set<>();
        for (String service : selectedServices) {
            services.add(service);
        }

        Place place = new Place(x, y, placeName, services);
        map.add(place);
        map.saveData("places_data.dat");
    }

    public void editPlace(Scanner scanner) {

    }

    public void searchPlace() {
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        while (choice != 4) {
            System.out.println("1. Search by place name");
            System.out.println("2. Search by service type");
            System.out.println("3. Search by current position");
            System.out.println("4. Return to menu");
            System.out.println("Please enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
//                    searchByPlaceName();
                    break;
                case 2:
                    searchByServiceType(sc);
                    break;
                case 3:
//                    searchByCurrentPosition();
                    break;
                case 4:
                    System.out.println("Return to menu");
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }

    public void searchByServiceType(Scanner sc) {
        System.out.println("Enter the service type you want to search: ");
        String serviceType = sc.nextLine();
        Set<Place> results = map.search(0, 0, mapWidth, mapHeight, serviceType, 50);
        int index = 1;

        Iterator<Place> iterator = results.iterator();
        while (iterator.hasNext()) {
            Place place = iterator.next();
            System.out.println("Place " + index + ": " + place.toString());
            index++;
        }
    }
}
