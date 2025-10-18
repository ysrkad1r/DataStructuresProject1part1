package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class ClosestToIzmir {

    private static int NUM_OF_CITIES = DistanceReader.getNumOfCities();

    //When class initialize it fills Map from DistanceReader class
    private static Map<String, ArrayList<Integer>> citiesAndDistances;
    static {
        try {
            citiesAndDistances = DistanceReader.getCitiesAndDistances();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static String[] cities = DistanceReader.getCities();
    private static Map<String, Integer> cityPlateMap = DistanceReader.getCityPlateMap();
    private static Map<String, Integer> distancesToIzmir = new HashMap<>();

    private static Random random = new Random();

    private static NeighboringCities neighboringCities = new NeighboringCities();
    private static DistanceReader distanceReader = new DistanceReader();

    private static Map<String, ArrayList<String>> neighborsOfCities;
    static {
        try {
            neighborsOfCities = neighboringCities.neighborsOfCities();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static String filePath = "distances_to_izmir.txt";


    public static void main(String[] args) throws FileNotFoundException {
        DistanceReader.extractDataFromFile(DistanceReader.getFilePath());

        readDistancesToIzmir(filePath);
        findPathToIzmir();

    }

    public static void readDistancesToIzmir(String filePath){
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filePath)))) {
            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();
                String[] cityData = line.split(",");

                String city = cityData[0];
                int distanceToIzmir = Integer.parseInt(cityData[1]);

                distancesToIzmir.put(city, distanceToIzmir);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void findPathToIzmir() throws FileNotFoundException {
        readDistancesToIzmir(filePath);

        String currentCity = Helper.cityNormalizer(cities[random.nextInt(NUM_OF_CITIES) + 1]); // (0-80)+1
        while (currentCity.equals("İzmir")){
            currentCity = Helper.cityNormalizer(cities[random.nextInt(NUM_OF_CITIES) + 1]);
        }

        System.out.println("Start city: " + currentCity);

        ArrayList<String> path = new ArrayList<>();
        path.add(currentCity);
        int totalDistance = 0;

        while(!currentCity.equals("İzmir")){
            ArrayList<String> neighbors = neighboringCities.getNeigborsByCity(currentCity);

            if (neighbors == null || neighbors.isEmpty()){
                System.out.println(" Neighborhood can not found .");
                return;
            }

            String closestNeighbor = neighbors.getFirst();
            int minDistanceToIzmir = distancesToIzmir.get(closestNeighbor);

            for (int i = 1; i < neighbors.size(); i++) {
                String potentialNeighbor = neighbors.get(i);
                int potentialDistance = distancesToIzmir.get(potentialNeighbor);

                if(potentialDistance < minDistanceToIzmir){
                    minDistanceToIzmir = potentialDistance;
                    closestNeighbor = potentialNeighbor;
                }
            }

            int distanceToNextCity = DistanceReader.getDistance(currentCity, closestNeighbor);
            totalDistance += distanceToNextCity;

            System.out.println(currentCity + " -> " + closestNeighbor + " (" + distanceToNextCity + " km)");

            currentCity = closestNeighbor;
            path.add(currentCity);
        }

        System.out.println("\nReached to the İzmir!");
        System.out.println("Full path: " + String.join(" -> ", path));
        System.out.println("Total amount of distance: " + totalDistance + " km");
    }
}