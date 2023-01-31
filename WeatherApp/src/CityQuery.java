import java.util.Scanner;

public class CityQuery {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the city: ");
        String city = scanner.nextLine(); // jak tutaj wrzucić pole city z klasy City ?
        System.out.print("Enter the name of the state: ");
        String state = scanner.nextLine(); // jak tutaj wrzucić pole state z klasy City ?
        System.out.print("Enter the name of the country: ");
        String country = scanner.nextLine(); // jak tutaj wrzucić pole country z klasy City ?



        City chosenCity = new City(city, state, country);
        System.out.println(chosenCity);

    }
}

