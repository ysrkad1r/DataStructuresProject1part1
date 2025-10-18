package org.example;

public class Helper {

    // This is a helper method for city names like ANKARA to Ankara or AnKarA to Ankara.
    // The first letter will be Uppercase
    // Others will be lowercase
    public static String cityNormalizer(String cityName){
        String firstLetter = cityName.substring(0,1).toUpperCase();
        String restOf = cityName.substring(1).toLowerCase();

        return firstLetter + restOf;
    }

}
