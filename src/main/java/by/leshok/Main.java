package by.leshok;

import by.leshok.model.Animal;
import by.leshok.model.Car;
import by.leshok.model.Flower;
import by.leshok.model.House;
import by.leshok.model.Person;
import by.leshok.util.CarUtil;
import by.leshok.util.Util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.CheckedOutputStream;

public class Main {


    public static void main(String[] args) throws IOException {
//        task1();
//        task2();
//        task3();
//        task4();
//        task5();
//        task6();
//        task7();
//        task8();
//        task9();
//        task10();
//        task11();
//        task12();
//        task13();
//        task14();
        task15();
    }

    private static void task1() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getAge() > 10 && animal.getAge() < 20)
                .sorted(Comparator.comparingInt(Animal::getAge))
                .skip(14)
                .limit(7)
                .forEach(System.out::println);
    }

    private static void task2() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> "Japanese".contains(animal.getOrigin())
                        && "Female".contains(animal.getGender()))
                .map(animal -> animal.getBread().toUpperCase())
                .forEach(System.out::println);

    }

    private static void task3() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getAge() > 30 && 'A' == animal.getOrigin().charAt(0))
                .map(animal -> animal.getOrigin())
                .distinct()
                .forEach(System.out::println);
    }

    private static void task4() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .filter(animal -> "Female".equals(animal.getGender()))
                .count());
    }

    private static void task5() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .filter(animal -> animal.getAge() > 20 && animal.getAge() < 30)
                .anyMatch(animal -> "Hungarian".equals(animal.getOrigin())));
    }

    private static void task6() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .allMatch(animal -> "Male".equals(animal.getGender())
                        || "Female".equals(animal.getGender())));
    }

    private static void task7() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .noneMatch(animal -> "Oceania".equals(animal.getOrigin())));
    }

    private static void task8() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .sorted(Comparator.comparing(Animal::getBread))
                .limit(100)
                .mapToInt(animal -> animal.getAge())
                .max().orElse(-1));

    }

    private static void task9() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .mapToInt(animal -> animal.getBread().toCharArray().length)
                .max().orElse(-1));
    }

    private static void task10() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .mapToInt(Animal::getAge)
                .sum());
    }

    private static void task11() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .filter(animal -> "Indonesian".equals(animal.getOrigin()))
                .mapToInt(Animal::getAge)
                .average().orElse(-1));
    }

    private static void task12() throws IOException {
        LocalDate currentDate = LocalDate.now();
        List<Person> people = Util.getPersons();
        people.stream()
                .filter(person -> "Male".equals(person.getGender())
                        && person.getDateOfBirth().isBefore(currentDate.minusYears(18))
                        && person.getDateOfBirth().isAfter(currentDate.minusYears(27)))
                .sorted(Comparator.comparingInt(Person::getRecruitmentGroup))
                .limit(200)
                .forEach(System.out::println);

    }

    private static void task13() throws IOException {
        final String EVACUATE_FROM = "hospital";
        final String MALE = "Male";
        final String FEMALE = "Female";
        final int MALE_RETIREMENT_AGE = 65;
        final int FEMALE_RETIREMENT_AGE = 58;
        final int CHILDREN_AGE = 18;

        LocalDate currentDate = LocalDate.now();
        StringBuilder stringBuilder = new StringBuilder();      //stringBuilder for ordered output
        HashSet<Person> addedPersons = new HashSet<>();         //a list for evacuation 500 people

        List<House> houses = Util.getHouses();
        houses.stream()
                .filter(house -> EVACUATE_FROM.equalsIgnoreCase(house.getBuildingType()))
                .flatMap(house -> house.getPersonList().stream())
                .limit(500)
                .peek(person -> {
                    stringBuilder.append(String.format("%s\n", person));
                    addedPersons.add(person);
                })
                .count();

        houses.stream()
                .flatMap(house -> house.getPersonList().stream())
                .filter(person -> !addedPersons.contains(person))
                .filter(person -> person.getDateOfBirth().isAfter(currentDate.minusYears(CHILDREN_AGE))
                        || (MALE.equalsIgnoreCase(person.getGender()) &&
                        person.getDateOfBirth().isBefore(currentDate.minusYears(MALE_RETIREMENT_AGE)))
                        || (FEMALE.equalsIgnoreCase(person.getGender()) &&
                        person.getDateOfBirth().isBefore(currentDate.minusYears(FEMALE_RETIREMENT_AGE))))
                .peek(person -> {
                    if (addedPersons.size() < 500) {
                        stringBuilder.append(String.format("%s\n", person));
                        addedPersons.add(person);
                    }
                })
                .filter(person -> !addedPersons.contains(person))
                .forEach(person -> {
                    if (addedPersons.size() < 500) {
                        stringBuilder.append(String.format("%s\n", person));
                        addedPersons.add(person);
                    }
                });

        System.out.println(stringBuilder);
    }

    private static void task14() throws IOException {
        final String COUNTRY_TURKMENISTAN = "Туркменистан";
        final String COUNTRY_UZBEKISTAN = "Узбекистан";
        final String COUNTRY_KAZAKHSTAN = "Казахстан";
        final String COUNTRY_KYRGYZSTAN = "Кыргызстан";
        final String COUNTRY_RUSSIA = "Россия";
        final String COUNTRY_MONGOLIA = "Монголия";

        final Map<String, List<Car>> list = new HashMap<>();
        list.put(COUNTRY_TURKMENISTAN, new ArrayList<>());
        list.put(COUNTRY_UZBEKISTAN, new ArrayList<>());
        list.put(COUNTRY_KAZAKHSTAN, new ArrayList<>());
        list.put(COUNTRY_KYRGYZSTAN, new ArrayList<>());
        list.put(COUNTRY_RUSSIA, new ArrayList<>());
        list.put(COUNTRY_MONGOLIA, new ArrayList<>());

        List<Car> cars = Util.getCars();

        list.get(COUNTRY_TURKMENISTAN).addAll(cars.stream()
                .filter(CarUtil::isQualifiedForTurkmenistan)
                .toList());

        list.get(COUNTRY_UZBEKISTAN).addAll(cars.stream()
                .filter(CarUtil::isQualifiedForTurkmenistan)
                .filter(CarUtil::isQualifiedForUzbekistan)
                .toList());

        list.get(COUNTRY_KAZAKHSTAN).addAll(cars.stream()
                .filter(CarUtil::isQualifiedForTurkmenistan)
                .filter(CarUtil::isQualifiedForUzbekistan)
                .filter(CarUtil::isQualifiedForKazakhstan)
                .toList());

        list.get(COUNTRY_KYRGYZSTAN).addAll(cars.stream()
                .filter(CarUtil::isQualifiedForTurkmenistan)
                .filter(CarUtil::isQualifiedForUzbekistan)
                .filter(CarUtil::isQualifiedForKazakhstan)
                .filter(CarUtil::isQualifiedForKyrgyzstan)
                .toList());

        list.get(COUNTRY_RUSSIA).addAll(cars.stream()
                .filter(CarUtil::isQualifiedForTurkmenistan)
                .filter(CarUtil::isQualifiedForUzbekistan)
                .filter(CarUtil::isQualifiedForKazakhstan)
                .filter(CarUtil::isQualifiedForKyrgyzstan)
                .filter(CarUtil::isQualifiedForRussia)
                .toList());

        list.get(COUNTRY_MONGOLIA).addAll(cars.stream()
                .filter(CarUtil::isQualifiedForTurkmenistan)
                .filter(CarUtil::isQualifiedForUzbekistan)
                .filter(CarUtil::isQualifiedForKazakhstan)
                .filter(CarUtil::isQualifiedForKyrgyzstan)
                .filter(CarUtil::isQualifiedForRussia)
                .filter(CarUtil::isQualifiedForMongolia)
                .toList());

        double turSum = CarUtil.getCountryExpense(COUNTRY_TURKMENISTAN, list);
        double uzbSum = CarUtil.getCountryExpense(COUNTRY_UZBEKISTAN, list);
        double kazSum = CarUtil.getCountryExpense(COUNTRY_KAZAKHSTAN, list);
        double kyrSum = CarUtil.getCountryExpense(COUNTRY_KYRGYZSTAN, list);
        double rusSum = CarUtil.getCountryExpense(COUNTRY_RUSSIA, list);
        double monSum = CarUtil.getCountryExpense(COUNTRY_MONGOLIA, list);

        CarUtil.printCountryEspense(COUNTRY_TURKMENISTAN, turSum);
        CarUtil.printCountryEspense(COUNTRY_UZBEKISTAN, uzbSum);
        CarUtil.printCountryEspense(COUNTRY_KAZAKHSTAN, kazSum);
        CarUtil.printCountryEspense(COUNTRY_KYRGYZSTAN, kyrSum);
        CarUtil.printCountryEspense(COUNTRY_RUSSIA, rusSum);
        CarUtil.printCountryEspense(COUNTRY_MONGOLIA, monSum);

        CarUtil.printTotalSum(turSum, uzbSum, kazSum, kyrSum, rusSum, monSum);
    }
    private static void task15() throws IOException {
        List<Flower> flowers = Util.getFlowers();
        //        Продолжить...
    }
}