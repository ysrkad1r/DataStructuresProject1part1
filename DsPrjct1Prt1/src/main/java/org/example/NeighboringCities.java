package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class NeighboringCities {
    private static Map<String, ArrayList<String>> cityNeighboursMap = new HashMap<String, ArrayList<String>>();

    private static String filePath = "NeighborsOfCites.txt";
    private static String filePathForDistances = DistanceReader.getFilePath();

    public static void main(String[] args) throws FileNotFoundException {
        neighborsOfCities();
        DistanceReader.extractDataFromFile(filePathForDistances);
        findFarthestNeighbour();
    }

    public static Map<String, ArrayList<String>> neighborsOfCities() throws FileNotFoundException {
        if (!cityNeighboursMap.isEmpty()) {
            return cityNeighboursMap;
        }

        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filePath)))) {
            while (scanner.hasNextLine()) {
                String cities = scanner.nextLine();
                String[] cityNames = cities.split(",");
                ArrayList<String> neighbors = new ArrayList<>();
                String city = cityNames[0];
                for (int i = 1; i < cityNames.length; i++) {
                    neighbors.add(cityNames[i]);
                }
                cityNeighboursMap.put(city, neighbors);

            }


        } catch (Exception e) {
            e.printStackTrace();

        }
        return cityNeighboursMap;
    }

    public static void printNeighbours(Map<String, ArrayList<String>> neighborsMap) {
        for (Map.Entry<String, ArrayList<String>> entry : neighborsMap.entrySet()) {
            System.out.println("City: " + entry.getKey() + " | Neighbours: " + String.join(", ", entry.getValue()));
        }
    }

    public static void findFarthestNeighbour() throws FileNotFoundException {
        neighborsOfCities();

        String cityA=null;
        String cityB=null;

        int distance=0;

        for (Map.Entry<String, ArrayList<String>> entry : cityNeighboursMap.entrySet()) {
            String city1= entry.getKey();
            ArrayList<String> neighbours = entry.getValue();
            for (String neighbour : neighbours) {
                String city2 = neighbour;

                int distance12= DistanceReader.getDistance(city1, city2);

                if (distance12>distance) {
                    cityA = city1;
                    cityB = city2;
                    distance = distance12;
                }

            }
        }
        System.out.println("The most distant neighboring couple :"+cityA+" - "+cityB+" | Distance :"+distance+"km");
    }

    public static ArrayList<String> getNeigborsByCity(String cityName) throws FileNotFoundException {
        neighborsOfCities();
        return cityNeighboursMap.get(Helper.cityNormalizer(cityName));
    }

}

