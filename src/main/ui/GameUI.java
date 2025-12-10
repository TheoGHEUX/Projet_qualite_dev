package main.ui;

import main.theatre.InvasionTheatre;
import main.model.place.Place;

import java.util.Scanner;

/**
 * Gère l'affichage et les interactions utilisateur générales.
 */
public class GameUI {
    private static final Scanner scanner = new Scanner(System. in);

    /**
     * Affiche l'état général du théâtre.
     */
    public static void displayTheatreStatus(InvasionTheatre theatre) {
        System.out. println("\n--- ETAT DU THEATRE " + theatre.getName() + " ---");
        System.out.println("Lieux :  " + theatre.getPlaces().size());
        System.out.println("Personnages vivants : " + theatre.getTotalCharacterCount());
        System.out. println("Chefs de clan : " + theatre.getClanChiefs().size());

        System.out.println("\nResume des lieux :");
        for (Place place : theatre.getPlaces()) {
            int charCount = place.getThe_characters_present().size();
            int foodCount = place.getThe_aliments_present().size();
            System.out.println("  - " + place.getName() + " :  " + charCount + " personnages, " + foodCount + " aliments");
        }
    }

    /**
     * Demande si l'utilisateur veut continuer.
     */
    public static boolean askContinue() {
        System.out.print("\nVoulez-vous continuer la simulation ?  (o/n) : ");

        String response = "";
        while (true) {
            response = getScanner().nextLine().trim().toLowerCase();

            if (response.equals("o")) {
                return true;
            } else if (response.equals("n")) {
                return false;
            } else {
                System.out.print(" Veuillez entrer 'o' (oui) ou 'n' (non) : ");
            }
        }
    }

    /**
     * Lit et valide une entrée entière.
     */
    public static int getIntInput() {
        while (! scanner.hasNextInt()) {
            System.out.print("Entrez un nombre valide : ");
            scanner.next();
        }
        int value = scanner. nextInt();
        scanner.nextLine();
        return value;
    }

    public static Scanner getScanner() {
        return scanner;
    }
}