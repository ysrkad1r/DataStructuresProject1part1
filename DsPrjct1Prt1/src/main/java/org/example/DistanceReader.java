package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DistanceReader {

    private static final int NUM_OF_CITIES = 81;

    private static int[][] DISTANCES_OF_CITIES = new int[NUM_OF_CITIES][NUM_OF_CITIES];

    private static Map<String, Integer> citiesIndexMap = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        String filePath = "cityDistance.csv";

        extractDataFromFile(filePath);
    }

    public static void extractDataFromFile(String filePath) throws FileNotFoundException {
        String row;
        int rowCount = 3;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {

            bufferedReader.readLine();
            if ((row = bufferedReader.readLine()) != null){
                String[] cities = row.split(";");

                for (int i = 2; i < cities.length ; i++){
                    citiesIndexMap.put(cities[i-2],i-1);
                }
            }

            while ((row = bufferedReader.readLine()) != null){
                String[] values = row.split(";");
                for (int i = 1; i < values.length ; i++){
                    if (rowCount-2 != i){
                        DISTANCES_OF_CITIES[rowCount-2][i-1] = Integer.parseInt(values[i-1]);
                    }else {
                        DISTANCES_OF_CITIES[rowCount-2][i] = 0;
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
