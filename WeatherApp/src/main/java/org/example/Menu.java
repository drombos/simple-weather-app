package main.java.org.example;

import java.util.Scanner;

public class Menu {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String userOptions = "a";

        while (!userOptions.equals("d")) {
            System.out.println("""
                    Wybierz opcję:
                    a. Dodaj lokalizację.
                    b. Wyświetl wszystkie lokalizacje.
                    c. Pobierz prognozę pogody.
                    d. Zakończ program.       
                    """);
            userOptions = scanner.nextLine();

            switch (userOptions) {
                case "a":
                    System.out.println("Wybrano opcję 1.");
                    System.out.println();
                    break;
                case "b":
                    System.out.println("Wybrano opcję 2.");
                    System.out.println();
                    break;
                case "c":
                    System.out.println("Wybrano opcję 3.");
                    System.out.println();
                    break;
                case "d":
                    System.out.println("Koniec programu.");
                    break;
                default:
                    System.out.println("Wprowadź litery a, b, c lub d: ");
                    System.out.println();
            }
        }
    }


}
