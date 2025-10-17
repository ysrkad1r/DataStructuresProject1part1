package org.example;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {
        NeighboringCities neighbors = new NeighboringCities();
        try {
            Map<String, ArrayList<String>> neighborMap=neighbors.neighborsOfCities();
            neighbors.printNeighbours(neighborMap);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}