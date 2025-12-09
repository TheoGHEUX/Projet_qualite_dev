package main;

import main.model.place.category.place_with_clan_chief.*;
import main.theatre.InvasionTheatre;
import main.model.clan_chief.ClanChief;
import main.model.character.Character;
import main.model.place.Place;
import main.model.place.category.PlaceWithClanChief;
import main.model.place.category. Battlefield;
import main.model. place.category. Enclosure;
import main.enums.CharacterType;
import main.enums.Sex;

import java.util. Scanner;
import java.util.List;

/**
 * Classe principale de l'application de simulation d'envahissement de l'Armorique.
 * Gere la boucle de jeu, l'interaction utilisateur et l'orchestration de la simulation.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final int MAX_ACTIONS_PER_TURN = 3; // Nombre d'actions maximum par chef et par tour

    /**
     * Point d'entree de l'application.
     * Cree le theatre, initialise le monde et lance la boucle de simulation.
     */
    public static void main(String[] args) {
        System.out.println("=== SIMULATION D'ENVAHISSEMENT DE L'ARMORIQUE ===\n");

        // Creation du theatre d'envahissement
        InvasionTheatre theatre = new InvasionTheatre("Armorique", 10);

        // Creation des chefs de clan de manière interactive
        createClanChiefsInteractively(theatre);

        // Initialisation automatique des personnages et aliments
        theatre.initializeRandomCharactersAndItems();


        // Boucle principale de simulation
        boolean continueSimulation = true;
        while (continueSimulation) {
            System.out.println("\n" + "=". repeat(60));
            System.out.println("TOUR " + (theatre.getCurrentTurn() + 1));
            System.out. println("=".repeat(60));

            // Execution de la simulation automatique (combats, aliments, etc.)
            theatre. simulateTurn();

            // Affichage de l'etat actuel du monde
            displayTheatreStatus(theatre);

            // Phase interactive :  les chefs de clan agissent
            manageClanChiefs(theatre);

            // Demander si l'utilisateur veut continuer
            continueSimulation = askContinue();
        }

        System.out.println("\nFin de la simulation.");
        scanner.close();
    }

    /**
     * Cree plusieurs chefs de clan de manière interactive.
     * Demande à l'utilisateur le nombre de chefs, puis pour chacun :
     * - son nom,
     * - son sexe,
     * - le type de lieu qu'il dirigera.
     * Génère ensuite automatiquement le chef, son lieu associé
     * et l'ajoute au théâtre d'envahissement.
     */
    /**
     * Cree plusieurs chefs de clan de manière interactive.
     * Demande à l'utilisateur le nombre de chefs, puis pour chacun :
     * - son nom,
     * - son sexe,
     * - le type de lieu qu'il dirigera.
     * Génère ensuite automatiquement le chef, son lieu associé
     * et l'ajoute au théâtre d'envahissement.
     */
    private static void createClanChiefsInteractively(InvasionTheatre theatre) {

        System.out.print("Combien de chefs de clan souhaitez-vous créer ?   ");
        int nb = getIntInput();

        for (int i = 1; i <= nb; i++) {
            System.out. println("\n=== Création du chef de clan " + i + " ===");

            // Choix du nom avec validation
            String name = "";
            while (name.trim().isEmpty()) {
                System.out. print("Nom du chef : ");
                name = scanner.nextLine().trim();

                if (name.isEmpty()) {
                    System.out.println(" Le nom ne peut pas être vide !   Veuillez entrer un nom.");
                }
            }

            // Choix du sexe avec validation
            Sex sex = null;
            while (sex == null) {
                System.out.println("Sexe (1 = MALE, 2 = FEMALE) : ");
                int sexChoice = getIntInput();

                if (sexChoice == 1) {
                    sex = Sex. MALE;
                } else if (sexChoice == 2) {
                    sex = Sex.FEMALE;
                } else {
                    System.out.println(" Choix invalide !  Veuillez choisir 1 ou 2.");
                }
            }

            // Création du chef (AVANT le lieu)
            ClanChief chief = new ClanChief(name, sex);

            // Choix du type de lieu avec validation
            Place place = null;
            while (place == null) {
                System.out.println("\nChoisir un type de lieu :");
                System.out. println("1. Village gaulois");
                System.out.println("2. Camp romain");
                System.out.println("3. Ville romaine");
                System.out.println("4. Bourgade gallo-romaine");
                System.out. println("5. Enclos");
                System.out.println("6. Champ de bataille");
                System.out.print("Votre choix :   ");

                int choice = getIntInput();

                place = switch (choice) {
                    case 1 -> new GallicVillage("Village de " + name, 1000, chief);
                    case 2 -> new RomanFortifiedCamp("Camp de " + name, 1200, chief);
                    case 3 -> new RomanTown("Ville de " + name, 1500, chief);
                    case 4 -> new GalloRomanSettlement("Bourgade de " + name, 1300, chief);
                    case 5 -> new Enclosure("Enclos " + name, 500);
                    case 6 -> new Battlefield("Champ de bataille " + name, 1500);
                    default -> {
                        System.out.println(" Choix invalide !  Veuillez choisir un nombre entre 1 et 6.");
                        yield null; // On reste dans la boucle
                    }
                };
            }

            // Ajout au théâtre APRÈS validation complète
            theatre.addPlace(place);

            System.out. println(" Chef " + name + " créé avec son lieu :   " + place. getName());
        }
    }

    /**
     * Affiche l'etat general du theatre d'envahissement.
     * Montre le nombre de lieux, personnages vivants et un resume par lieu.
     * @param theatre le theatre a afficher
     */
    private static void displayTheatreStatus(InvasionTheatre theatre) {
        System.out.println("\n--- ETAT DU THEATRE " + theatre.getName() + " ---");
        System.out.println("Lieux :  " + theatre.getPlaces().size());
        System.out.println("Personnages vivants : " + theatre.getTotalCharacterCount());
        System.out.println("Chefs de clan : " + theatre. getClanChiefs().size());

        // Affichage du resume de chaque lieu
        System.out.println("\nResume des lieux :");
        for (Place place : theatre. getPlaces()) {
            int charCount = place.getThe_characters_present().size();
            int foodCount = place.getThe_aliments_present().size();
            System.out.println("  - " + place.getName() + " : " + charCount + " personnages, " + foodCount + " aliments");
        }
    }

    /**
     * Passe la main a chaque chef de clan pour qu'il gere son lieu.
     * Chaque chef dispose d'un nombre limite d'actions par tour.
     * @param theatre le theatre contenant les chefs de clan
     */
    private static void manageClanChiefs(InvasionTheatre theatre) {
        System.out.println("\n--- TOUR DES CHEFS DE CLAN ---");

        for (ClanChief chief : theatre.getClanChiefs()) {
            if (chief == null || chief.getPlace() == null) continue;

            System.out.println("\nTour de " + chief.getName() + " (" + chief.getPlace().getName() + ")");
            manageChief(chief, theatre);
        }
    }

    /**
     * Gere le tour d'un chef de clan specifique.
     * Affiche un menu et execute les actions choisies jusqu'a epuisement des actions.
     * @param chief le chef de clan a gerer
     * @param theatre le theatre (pour certaines actions comme les transferts)
     */
    private static void manageChief(ClanChief chief, InvasionTheatre theatre) {
        int actionsRemaining = MAX_ACTIONS_PER_TURN;

        while (actionsRemaining > 0) {
            System.out. println("\nActions restantes : " + actionsRemaining);
            displayChiefMenu();

            int choice = getIntInput();

            switch (choice) {
                case 1: // Examiner le lieu
                    chief.examinePlace();
                    actionsRemaining--;
                    break;
                case 2: // Nourrir les personnages
                    chief.feedCharacters();
                    actionsRemaining--;
                    break;
                case 3: // Soigner les personnages
                    healCharactersMenu(chief);
                    actionsRemaining--;
                    break;
                case 4: // Creer un personnage
                    createCharacterMenu(chief);
                    actionsRemaining--;
                    break;
                case 5: // Faire de la potion magique
                    chief.askDruidToMakePotion();
                    actionsRemaining--;
                    break;
                case 6: // Donner la potion magique
                    givePotionMenu(chief);
                    actionsRemaining--;
                    break;
                case 7: // Transferer un personnage
                    transferCharacterMenu(chief, theatre);
                    actionsRemaining--;
                    break;
                case 0: // Terminer le tour
                    return;
                default:
                    System.out.println("Choix invalide.");
            }
        }
        System.out.println("Limite d'actions atteinte pour " + chief.getName());
    }

    /**
     * Affiche le menu des actions disponibles pour un chef de clan.
     */
    private static void displayChiefMenu() {
        System.out.println("1. Examiner le lieu");
        System.out.println("2. Nourrir les personnages");
        System.out.println("3. Soigner les personnages");
        System.out. println("4. Creer un personnage");
        System.out.println("5. Faire de la potion magique");
        System.out.println("6. Donner la potion magique");
        System.out.println("7. Transferer un personnage");
        System.out. println("0. Terminer le tour");
        System.out.print("Votre choix : ");
    }

    /**
     * Menu permettant au chef de soigner un personnage de son lieu.
     * L'utilisateur choisit le personnage et la quantite de soin.
     * @param chief le chef de clan effectuant l'action
     */
    private static void healCharactersMenu(ClanChief chief) {
        PlaceWithClanChief place = chief.getPlace();
        if (place == null) {
            System.out.println("Le chef n'a pas de lieu.");
            return;
        }

        List<Character> characters = place.getThe_characters_present();
        if (characters.isEmpty()) {
            System.out.println("Aucun personnage a soigner.");
            return;
        }

        // Affichage des personnages disponibles
        System.out.println("\nPersonnages dans " + place.getName() + " :");
        for (int i = 0; i < characters.size(); i++) {
            Character c = characters.get(i);
            System.out.println(i + ". " + c.getName() + " (Sante: " + c.getHealth() + ")");
        }

        // Choix du personnage
        System.out.print("Choisir un personnage (numero) : ");
        int index = getIntInput();

        if (index >= 0 && index < characters.size()) {
            Character character = characters.get(index);
            System.out.print("Quantite de soin :  ");
            int amount = getIntInput();
            place.healCharacter(character, amount);
        } else {
            System.out. println("Index invalide.");
        }
    }

    /**
     * Menu permettant au chef de creer un nouveau personnage dans son lieu.
     * L'utilisateur choisit le nom, le sexe et le type du personnage.
     * @param chief le chef de clan effectuant l'action
     */
    private static void createCharacterMenu(ClanChief chief) {
        System.out.print("Nom du personnage : ");
        String name = scanner.nextLine();

        // Choix du sexe
        System.out.println("Sexe (1: MALE, 2: FEMALE) : ");
        int sexChoice = getIntInput();
        Sex sex = (sexChoice == 1) ? Sex.MALE : Sex.FEMALE;

        // Choix du type de personnage
        System.out.println("Type de personnage :");
        System.out.println("1. DRUID");
        System.out. println("2. BLACKSMITH");
        System.out. println("3. MERCHANT");
        System.out.println("4. INNKEEPER");
        System.out. println("5. LEGIONARY");
        System.out.println("6. PREFECT");
        System.out. println("7. GENERAL");
        System.out.println("8. WEREWOLF");
        System.out.print("Votre choix : ");

        int typeChoice = getIntInput();
        CharacterType type = switch (typeChoice) {
            case 1 -> CharacterType. DRUID;
            case 2 -> CharacterType. BLACKSMITH;
            case 3 -> CharacterType.MERCHANT;
            case 4 -> CharacterType.INNKEEPER;
            case 5 -> CharacterType.LEGIONARY;
            case 6 -> CharacterType.PREFECT;
            case 7 -> CharacterType.GENERAL;
            case 8 -> CharacterType. WEREWOLF;
            default -> null;
        };

        if (type != null) {
            chief.createCharacter(name, sex, type);
        } else {
            System.out.println("Type invalide.");
        }
    }

    /**
     * Menu permettant au chef de donner une potion magique aux personnages de son lieu.
     * Utilise la premiere potion disponible dans le lieu.
     * @param chief le chef de clan effectuant l'action
     */
    private static void givePotionMenu(ClanChief chief) {
        PlaceWithClanChief place = chief.getPlace();
        if (place == null || place.getPotion_present().isEmpty()) {
            System.out.println("Aucune potion disponible.");
            return;
        }

        chief.makeCharactersDrinkMagicPotion(place.getPotion_present().get(0));
    }

    /**
     * Menu permettant au chef de transferer un personnage vers un champ de bataille ou un enclos.
     * L'utilisateur choisit le personnage et la destination.
     * @param chief le chef de clan effectuant l'action
     * @param theatre le theatre contenant les destinations possibles
     */
    private static void transferCharacterMenu(ClanChief chief, InvasionTheatre theatre) {
        PlaceWithClanChief place = chief.getPlace();
        if (place == null) return;

        List<Character> characters = place.getThe_characters_present();
        if (characters.isEmpty()) {
            System.out.println("Aucun personnage a transferer.");
            return;
        }

        // Affichage des personnages disponibles
        System.out.println("\nPersonnages :");
        for (int i = 0; i < characters.size(); i++) {
            System.out.println(i + ". " + characters.get(i).getName());
        }
        System.out.print("Choisir un personnage :  ");
        int charIndex = getIntInput();

        if (charIndex < 0 || charIndex >= characters.size()) {
            System. out.println("Index invalide.");
            return;
        }

        // Affichage des destinations possibles (champ de bataille ou enclos uniquement)
        System.out. println("\nDestinations possibles :");
        List<Place> destinations = theatre.getPlaces().stream()
                .filter(p -> p instanceof Battlefield || p instanceof Enclosure)
                .toList();

        for (int i = 0; i < destinations.size(); i++) {
            System.out.println(i + ". " + destinations. get(i).getName());
        }
        System.out.print("Choisir une destination : ");
        int destIndex = getIntInput();

        if (destIndex >= 0 && destIndex < destinations.size()) {
            chief.transferCharacter(characters.get(charIndex), destinations.get(destIndex));
        } else {
            System.out.println("Index invalide.");
        }
    }

    /**
     * Demande a l'utilisateur s'il souhaite continuer la simulation.
     * @return true si l'utilisateur veut continuer, false sinon
     */
    private static boolean askContinue() {
        System.out.print("\nContinuer la simulation ? (o/n) : ");
        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("o") || response.equals("oui");
    }

    /**
     * Lit et valide une entree entiere de l'utilisateur.
     * Redemande une saisie tant que l'entree n'est pas un entier valide.
     * @return l'entier saisi par l'utilisateur
     */
    private static int getIntInput() {
        while (! scanner.hasNextInt()) {
            System.out.print("Entrez un nombre valide : ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // Consommer le retour a la ligne
        return value;
    }
}