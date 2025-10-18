package org.example;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("### APPLICATION TEST STARTED ###\n");

        try {
            // --- Test 1: DistanceReader visit10cityAndCalculateDistance
            System.out.println("--- Test 1: visit 10 random cities and calcualte distances between them and total");
            DistanceReader.visit10cityAndCalculateDistance();

            System.out.println();
            System.out.println("==================================================================================");
            System.out.println();

            // --- Test 2: NeigboringCities printNeighbours(Map<String, ArrayList<String>> neighborsMap)
            System.out.println("--- Test 2: print all cities' neighbours ");
            NeighboringCities.printNeighbours(NeighboringCities.neighborsOfCities());

            System.out.println();
            System.out.println("==================================================================================");
            System.out.println();

            // --- Test 2: Testing NeighboringCities class ---
            System.out.println("--- Test 3: FarthestNeighbour pair ---");
            // Let's find the farthest neighbor. This method will load its own data.
            NeighboringCities.findFarthestNeighbour();

            System.out.println();
            System.out.println("==================================================================================");
            System.out.println();

            // --- Test 3: Testing ClosestToIzmir class ---
            System.out.println("--- Test 4: Finding a Route to İzmir from a Random City ---");
            ClosestToIzmir.findPathToIzmir();


        } catch (FileNotFoundException e) {
            System.err.println("Error: A required file was not found! Please check the file paths.");
            e.printStackTrace();
        }

        System.out.println("\n### APPLICATION TEST COMPLETED ###");
    }
}