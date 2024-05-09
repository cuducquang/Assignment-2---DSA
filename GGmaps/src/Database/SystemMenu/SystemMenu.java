package Database.SystemMenu;

import Database.*;
import Database.DataStructure.Arrays;
import Database.DataStructure.List;
import Database.DataStructure.Set;

import java.util.Random;
import java.util.Scanner;

import java.util.Iterator;

public class SystemMenu {
    final int mapWidth = 10000000;
    final int mapHeight = 10000000;
    final int capacity = 100;
    final Map2D map = new Map2D(mapWidth, mapHeight, capacity);

    public void displayMenu() {
        System.out.println("-------------Welcome to the Map 2D-------------------");
        System.out.println("1. Add a place");
        System.out.println("2. Edit a place");
        System.out.println("3. Remove a place");
        System.out.println("4. Search for places");
        System.out.println("5. Exit");
        System.out.println("Please enter your choice: ");
    }

    public List<Place> generateRandomData(int numberOfPlaces) {
        List<Place> places = new List<Place>();
        List<String> serviceTypes = Arrays.asList("ATM", "Restaurant", "Hospital", "Gas Station", "Coffee Shop", "Pharmacy", "Park", "School", "Supermarket", "Library");
        Random rand = new Random();
        for (int i = 0; i < numberOfPlaces; i++) {
            double x = rand.nextDouble() * mapWidth;
            double y = rand.nextDouble() * mapHeight;
            int numberOfServices = rand.nextInt(5) + 1;
            Set<String> services = new Set<>();
            for (int j = 0; j < numberOfServices; j++) {
                int randomIndex = rand.nextInt(serviceTypes.size());
                services.add(serviceTypes.get(randomIndex));
            }

            Place place = new Place(x, y, 0, services);
            places.add(place);
        }
        return places;
    }

    public void start() {
        System.out.print("Loading");
        int multiplier = 1000;
        for (int i = 0; i < multiplier; i++) {
            if (i == multiplier /5  || i == multiplier * 2 /5  || i == multiplier * 3 /5 || i == multiplier * 4 /5 || i == multiplier * 5 /5 - 1) {
                System.out.print(".");
            }
            List<Place> places = generateRandomData(10000000);
            Iterator<Place> placesIterator = places.iterator();
            while (placesIterator.hasNext()) {
                Place place = placesIterator.next();
                map.add(place);
            }
        }

        Set<String> services = new Set<>();
        services.add("ATM");

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
                    removePlace(scanner);
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
        int placeId = map.getHighestPlaceId() + 1;
        System.out.println("Enter the coordinate (X) of the place: ");
        double x = scanner.nextDouble();
        System.out.println("Enter the coordinate (Y) of the place: ");
        double y = scanner.nextDouble();
        scanner.nextLine();
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

        Place place = new Place(x, y, placeId, services);
        map.add(place);
        System.out.println(place.toString());
        System.out.println("Add place successfully");
    }

    public void editPlace(Scanner scanner) {
        System.out.println("Enter the ID of the place you want to edit: ");
        int placeId = scanner.nextInt();
        scanner.nextLine();
        Place placeToEdit = map.getPlaceById(placeId);

        if (placeToEdit == null) {
            System.out.println("Place with ID " + placeId + " not found.");
            return;
        }

        System.out.println("Editing place: " + placeToEdit);
        System.out.println("Enter new service types (up to 5 types, separated by commas): ");
        System.out.println("Available service types:");
        List<String> serviceTypes = Arrays.asList("ATM", "Restaurant", "Hospital", "Gas Station", "Coffee Shop", "Pharmacy", "Park", "School", "Supermarket", "Library");
        for (String service : serviceTypes) {
            System.out.println(service);
        }

        String input = scanner.nextLine();
        String[] selectedServices = input.split(",\\s*");

        if (selectedServices.length > 5) {
            System.out.println("You can only select up to 5 service types. Please try again.");
            return;
        }

        Set<String> newServices = new Set<>();
        for (String service : selectedServices) {
            newServices.add(service);
        }

        map.edit(placeToEdit, newServices);
        System.out.println(placeToEdit.toString());
        System.out.println("Place edited successfully.");
    }

    public void removePlace(Scanner scanner) {
        System.out.println("Enter the ID of the place you want to remove: ");
        int placeId = scanner.nextInt();
        scanner.nextLine();
        Place placeToRemove = map.getPlaceById(placeId);

        if (placeToRemove == null) {
            System.out.println("Place with ID " + placeId + " not found.");
            return;
        }

        System.out.println("Do you want to remove the following place?");
        System.out.println(placeToRemove.toString());
        System.out.println("Enter 'yes' to confirm, or any other key to cancel: ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes")) {
            map.remove(placeToRemove);
        } else {
            System.out.println("Operation canceled.");
        }

    }


    public void searchPlace() {
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        while (choice != 4) {
            System.out.println("1. Search by place ID");
            System.out.println("2. Search by service type");
            System.out.println("3. Search by current position");
            System.out.println("4. Return to menu");
            System.out.println("Please enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    searchByPlaceID(sc);
                    break;
                case 2:
                    searchByServiceType(sc);
                    break;
                case 3:
                    searchByCurrentPosition(sc);
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

    public void searchByPlaceID(Scanner sc) {
        System.out.println("Enter the ID of the place you want to search: ");
        int placeId = sc.nextInt();
        sc.nextLine();
        Place placeToFind = map.getPlaceById(placeId);
        if (placeToFind == null) {
            System.out.println("Place with ID " + placeId + " not found.");
            return;
        }

        System.out.println("The place you want to find: " + placeToFind);


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

    public void searchByCurrentPosition(Scanner sc) {
        System.out.println("Enter your current position by x: ");
        double centerX = sc.nextDouble();
        System.out.println("Enter your current position by y: ");
        double centerY = sc.nextDouble();
        System.out.println("Enter the width of the area you want to search: ");
        double width = sc.nextDouble();
        System.out.println("Enter the height of the area you want to search: ");
        double height = sc.nextDouble();
        sc.nextLine();  // Consume the remaining newline after numbers
        System.out.println("Enter the service type you want to search: ");
        List<String> serviceTypes = Arrays.asList("ATM", "Restaurant", "Hospital", "Gas Station", "Coffee Shop", "Pharmacy", "Park", "School", "Supermarket", "Library");
        for (int i = 0; i < serviceTypes.size(); i++) {
            System.out.println(serviceTypes.get(i));
        }
        String serviceType = sc.nextLine();
//        System.out.println(centerX + " " +  centerY+ " " + width+ " " + height+ " " + serviceType+ " " + 50);
        Set<Place> results = map.searchByCurrentPosition(centerX, centerY, width, height, serviceType, 50);
        int index = 1;

        if (results.size() == 0) {
            System.out.println("There are no results found.");
        } else {
            Iterator<Place> iterator = results.iterator();
            System.out.println("The place you want to find: ");
            while (iterator.hasNext()) {
                Place place = iterator.next();
                System.out.println("Place " + index + ": " + place.toString());
                index++;
            }
        }
    }
}
