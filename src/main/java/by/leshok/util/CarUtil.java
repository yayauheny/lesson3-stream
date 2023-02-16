package by.leshok.util;

import by.leshok.model.Car;

import java.util.List;
import java.util.Map;

public class CarUtil {
    public static final String MODEL_JAGUAR = "Jaguar";
    public static final String MODEL_BMV = "BMW";
    public static final String MODEL_LEXUS = "Lexus";
    public static final String MODEL_TOYOTA = "Toyota";
    public static final String MODEL_CHRYSLER = "Chrysler";
    public static final String MODEL_GMC = "GMC";
    public static final String MODEL_DODGE = "Dodge";
    public static final String MODEL_CIVIC = "Civic";
    public static final String MODEL_CHEROKEE = "Cherokee";

    public static final String COLOR_WHITE = "White";
    public static final String COLOR_BLACK = "Black";
    public static final String COLOR_YELLOW = "Yellow";
    public static final String COLOR_RED = "Red";
    public static final String COLOR_GREEN = "Green";
    public static final String COLOR_BLUE = "Blue";

    public static final int TON = 1000;
    public static final double COST_PER_TON = 7.14;


    public static boolean isQualifiedForTurkmenistan(Car car) {
        return MODEL_JAGUAR.equalsIgnoreCase(car.getCarModel())
                || COLOR_WHITE.equalsIgnoreCase(car.getColor());
    }

    public static boolean isQualifiedForUzbekistan(Car car) {
        return car.getMass() <= 1500
                || MODEL_BMV.equalsIgnoreCase(car.getCarModel())
                || MODEL_LEXUS.equalsIgnoreCase(car.getCarModel())
                || MODEL_CHRYSLER.equalsIgnoreCase(car.getCarModel())
                || MODEL_TOYOTA.equalsIgnoreCase(car.getCarModel());
    }

    public static boolean isQualifiedForKazakhstan(Car car) {
        return car.getMass() > 4000 && COLOR_BLACK.equalsIgnoreCase(car.getColor())
                || MODEL_GMC.equalsIgnoreCase(car.getCarModel())
                || MODEL_DODGE.equalsIgnoreCase(car.getCarModel());
    }

    public static boolean isQualifiedForKyrgyzstan(Car car) {
        return car.getReleaseYear() < 1982
                || MODEL_CIVIC.equalsIgnoreCase(car.getCarModel())
                || MODEL_CHEROKEE.equalsIgnoreCase(car.getCarModel());
    }

    public static boolean isQualifiedForRussia(Car car) {
        return car.getPrice() > 40000
                || !COLOR_YELLOW.equalsIgnoreCase(car.getColor())
                && !COLOR_RED.equalsIgnoreCase(car.getColor())
                && !COLOR_GREEN.equalsIgnoreCase(car.getColor())
                && !COLOR_BLUE.equalsIgnoreCase(car.getColor());
    }

    public static boolean isQualifiedForMongolia(Car car) {
        return car.getVin().contains("59");
    }

    public static double getCountryExpense(String country, Map<String, List<Car>> list) {
        return list.get(country).stream()
                .map(Car::getMass)
                .mapToDouble(mass -> (double) mass / TON * COST_PER_TON)
                .sum();
    }

    public static void printCountryEspense(String country, double value) {
        System.out.println(String.format("%s: %.2f", country, value));
    }

    public static void printTotalSum(double... values) {
        double sum = 0;
        for (double value : values) {
            sum += value;
        }
        System.out.println(String.format("Total sum: %.2f", sum));
    }
}
