package main.ui;

import main.theatre.InvasionTheatre;
import main.model.clan_chief.ClanChief;
import main.model.place.Place;
import main.model.place.category.place_with_clan_chief.*;
import main.enums.Sex;

/**
 * Gère l'initialisation interactive du jeu.
 */
public class SetupUI {

    public static void createClanChiefsInteractively(InvasionTheatre theatre) {
        System.out.print("Combien de chefs de clan souhaitez-vous créer ?  ");
        int nb = main.ui.GameUI.getIntInput();

        for (int i = 1; i <= nb; i++) {
            System.out.println("\n=== Création du chef de clan " + i + " ===");

            // Nom
            String name = "";
            while (name.trim().isEmpty()) {
                System.out.print("Nom du chef : ");
                name = main.ui.GameUI.getScanner().nextLine().trim();
                if (name.isEmpty()) {
                    System.out.println(" Le nom ne peut pas être vide !");
                }
            }

            // Sexe
            Sex sex = null;
            while (sex == null) {
                System.out.println("Sexe (1 = MALE, 2 = FEMALE) : ");
                int sexChoice = main.ui.GameUI.getIntInput();
                if (sexChoice == 1) {
                    sex = Sex. MALE;
                } else if (sexChoice == 2) {
                    sex = Sex.FEMALE;
                } else {
                    System.out.println(" Choix invalide !");
                }
            }

            ClanChief chief = new ClanChief(name, sex);

            // Type de lieu
            Place place = null;
            while (place == null) {
                System. out.println("\nChoisir un type de lieu :");
                System. out.println("1. Village gaulois");
                System.out. println("2. Camp romain");
                System.out. println("3. Ville romaine");
                System.out. println("4. Bourgade gallo-romaine");
                System.out. print("Votre choix : ");

                int choice = main.ui.GameUI.getIntInput();

                place = switch (choice) {
                    case 1 -> new GallicVillage("Village de " + name, 1000, chief);
                    case 2 -> new RomanFortifiedCamp("Camp de " + name, 1200, chief);
                    case 3 -> new RomanTown("Ville de " + name, 1500, chief);
                    case 4 -> new GalloRomanSettlement("Bourgade de " + name, 1300, chief);
                    default -> {
                        System.out.println(" Choix invalide !");
                        yield null;
                    }
                };
            }

            theatre.addPlace(place);
            System.out.println(" Chef " + name + " créé avec son lieu :  " + place.getName());
        }
    }
}