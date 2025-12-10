package main;

import main.theatre. InvasionTheatre;
import main.ui.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== SIMULATION D'ENVAHISSEMENT DE L'ARMORIQUE ===\n");

        InvasionTheatre theatre = new InvasionTheatre("Armorique", 10);

        // Phase de setup
        SetupUI.createClanChiefsInteractively(theatre);

        // Initialisation des lieux sans chefs (champs de bataille et enclos)
        System.out.println("\n=== INITIALISATION DES LIEUX AUTONOMES ===");
        theatre.initializePlacesWithoutChiefs();

        // Initialisation des personnages et aliments dans les lieux avec chefs
        theatre. initializeRandomCharactersAndItems();

        // Boucle principale
        boolean continueSimulation = true;
        while (continueSimulation) {
            System. out.println("TOUR " + (theatre.getCurrentTurn() + 1));
            System.out.println("=".repeat(60));

            theatre.simulateTurn();
            GameUI.displayTheatreStatus(theatre);
            ChiefActionUI.manageClanChiefs(theatre);

            continueSimulation = GameUI.askContinue();
        }

        System.out.println("\nFin de la simulation.");
    }
}