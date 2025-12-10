package main.ui;

import main. model.clan_chief.ClanChief;
import main.theatre.InvasionTheatre;

/**
 * Gère les actions des chefs de clan.
 */
public class ChiefActionUI {
    private static final int MAX_ACTIONS_PER_TURN = 3;

    public static void manageClanChiefs(InvasionTheatre theatre) {
        System.out.println("\n--- TOUR DES CHEFS DE CLAN ---");

        for (ClanChief chief : theatre.getClanChiefs()) {
            if (chief == null || chief. getPlace() == null) continue;

            System.out. println("\nTour de " + chief.getName() + " (" + chief.getPlace().getName() + ")");
            manageChief(chief, theatre);
        }
    }

    private static void manageChief(ClanChief chief, InvasionTheatre theatre) {
        int actionsRemaining = MAX_ACTIONS_PER_TURN;

        while (actionsRemaining > 0) {
            System.out.println("\nActions restantes : " + actionsRemaining);
            displayChiefMenu();

            int choice = GameUI.getIntInput();

            // Cas spécial pour terminer le tour
            if (choice == 0) {
                System.out.println("Tour terminé pour " + chief.getName());
                return;
            }

            boolean actionPerformed = switch (choice) {
                case 1 -> {
                    chief.examinePlace();
                    yield false; // Pour ne pas consommer d'action
                }
                case 2 -> chief.feedCharactersInteractive();
                case 3 -> chief.healCharacters();
                case 4 -> chief.createCharacterInteractive();
                case 5 -> chief. askDruidToMakePotion();
                case 6 -> chief.givePotionInteractive();
                case 7 -> chief.transferCharacterInteractive(theatre);
                default -> {
                    System.out.println(" Choix invalide.");
                    yield false; // Pour ne pas consommer d'action
                }
            };

            // Décrémenter seulement si l'action a été effectuée avec succès
            if (actionPerformed) {
                actionsRemaining--;
            }
        }
        System.out.println("Limite d'actions atteinte pour " + chief.getName());
    }

    private static void displayChiefMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("          MENU DU CHEF");
        System.out.println("=".repeat(40));
        System.out.println("1. Examiner le lieu (gratuit)");
        System.out.println("2. Nourrir les personnages");
        System.out.println("3. Soigner les personnages");
        System.out.println("4. Créer un personnage");
        System.out.println("5. Faire de la potion magique");
        System.out. println("6. Donner la potion magique");
        System.out.println("7. Transférer un personnage");
        System.out.println("0. Terminer le tour");
        System.out.println("=".repeat(40));
        System.out.print("Votre choix : ");
    }
}