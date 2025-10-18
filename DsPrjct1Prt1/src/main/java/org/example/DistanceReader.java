package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DistanceReader {

    private static final int NUM_OF_CITIES = 81;

    private static final String filePath = "cityDistance.csv";
    // MAIN DATA STRUCTURES :
    // It consists key value pair which key as a String name of city and ArrayList consists of distances as Integers
    private static Map<String, ArrayList<Integer>> citiesAndDistances = new HashMap<>();

    // City name list their plate codes equal their indices
    private static String[] cities = new String[NUM_OF_CITIES + 1]; // 82 elemanlı

    // it is pairs of cityName plateCode
    private static Map<String, Integer> cityPlateMap = new HashMap<>();

    private static Random random = new Random();


    public static void main(String[] args) throws FileNotFoundException {

        extractDataFromFile(filePath);
        visit10cityAndCalculateDistance();

    }

    // it fills citiesAndDistances, cities and cityPlateMap data structures.
    public static void extractDataFromFile(String filePath) throws FileNotFoundException {
        //Basic control for efficiency
        if (!citiesAndDistances.isEmpty()) {
            return;
        }

        String row;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {

            // Skip unneccessary rows
            bufferedReader.readLine();

            // read title row and skip
            if ((row = bufferedReader.readLine()) != null){
                String[] headerParts = row.split(";"); // "İL PLAKA NO"; "İL ADI"; "ADANA"; ...

                cities[0] = ""; // set cities' first element as 0 because it must be all cities' plate code equal to its indices

                for (int i = 2; i < headerParts.length; i++) {
                    int plaka = i - 1; // "ADANA" (index 2) -> plate 1
                    String cityName = Helper.cityNormalizer(headerParts[i].trim());

                    cities[plaka] = cityName; // cities[1] = "ADANA"
                    cityPlateMap.put(cityName, plaka); // "ADANA" -> 1
                    citiesAndDistances.put(cityName, new ArrayList<>()); // set free map for after operations
                }
            }

            // Read rows and fill map with datas
            while ((row = bufferedReader.readLine()) != null){

                if (row.trim().isEmpty()) {
                    continue;
                }

                String[] values = row.split(";"); // ["01", "ADANA", "", "337", ...]

                if (values.length < 1) {
                    continue;
                }

                int plakaNo = Integer.parseInt(values[0].trim());
                String currentCityKey = cities[plakaNo]; // cities[1] -> "ADANA"
                ArrayList<Integer> currentDistances = citiesAndDistances.get(currentCityKey);

                for (int i = 2; i < values.length; i++){
                    String distanceValue = values[i].trim();
                    if (distanceValue.isEmpty()){
                        currentDistances.add(0); // if field do not contain any value (distance to itself of city) set as 0
                    } else {
                        currentDistances.add(Integer.parseInt(distanceValue));
                    }
                }
            }

        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("An error has occured during file reading: " + e.getMessage(), e);
        }
    }


     // İki şehir arasındaki mesafeyi getirir.
    public static int getDistance(String city1, String city2) throws FileNotFoundException {
        extractDataFromFile(filePath);

        Integer plaka2 = cityPlateMap.get(Helper.cityNormalizer(city1));
        ArrayList<Integer> distancesOfCity1 = citiesAndDistances.get(Helper.cityNormalizer(city2));


        if (plaka2 == null || distancesOfCity1 == null) {
            System.err.println("Error one or more city can not found  -> " + city1 + ", " + city2);
            return -1; // Error code
        }

        return distancesOfCity1.get(plaka2 - 1);
    }

    public static void visit10cityAndCalculateDistance() throws FileNotFoundException {
        extractDataFromFile(filePath);

        int totalDistance = 0;
        String[] randomCities = new String[10];

        ArrayList<Integer> pickedNumbers = new ArrayList<>();
        int pointer = 0;
        while (pickedNumbers.size() != 10){
            int randomCityPlate = random.nextInt(81) + 1;

            if (!pickedNumbers.contains(randomCityPlate)){
                pickedNumbers.add(randomCityPlate);
                randomCities[pointer++] = cities[randomCityPlate];
            }
        }

        for (int k = 0; k < randomCities.length ; k++){
            System.out.print((k+1)+". city which visited is : "+ randomCities[k] + " ");
            if (k < randomCities.length - 1){
                int distance = getDistance(randomCities[k],randomCities[k+1]);
                System.out.println("| distance between "+ randomCities[k] + " and "+ randomCities[k+1] +" is : " + distance);
                totalDistance += distance;
            }
        }

        System.out.println("Total distance : " + totalDistance);
    }

    // NUM_OF_CITIES citiesAndDistances cities cityPlateMap
    public static int getNumOfCities(){
        return NUM_OF_CITIES;
    }

    public static Map<String, ArrayList<Integer>> getCitiesAndDistances() throws FileNotFoundException {
        return citiesAndDistances;
    }

    public static String[] getCities(){
        return cities;
    }

    public static Map<String, Integer> getCityPlateMap() {
        return cityPlateMap;
    }

    public static String getFilePath() {
        return filePath;
    }

}