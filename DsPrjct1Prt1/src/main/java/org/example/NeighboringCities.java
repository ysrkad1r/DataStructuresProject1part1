package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class NeighboringCities {
    Map<String, ArrayList<String>> cityNeighboursMap = new HashMap<String, ArrayList<String>>();

    public Map<String, ArrayList<String>> neighborsOfCities() throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader("NeighborsOfCites.txt")))) {
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

    public void printNeighbours(Map<String, ArrayList<String>> neighborsMap) {
        for (Map.Entry<String, ArrayList<String>> entry : neighborsMap.entrySet()) {
            System.out.println("City: " + entry.getKey() + " | Neighbours: " + String.join(", ", entry.getValue()));
        }
    }

    public void

}

