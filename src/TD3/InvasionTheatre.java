package TD3;

import TD3.characters.Character;
import TD3. enums.FoodType;
import TD3. food.Food;
import TD3. food.FoodStats;
import TD3.interfaces.Fighter;
import TD3.interfaces. Leader;
import TD3.places. Battlefield;
import TD3.places.Place;
import TD3. places.Place_with_clan_chief;

import TD3.characters.gaul.Druid;
import TD3.characters.gaul.Blacksmith;
import TD3.characters.roman.General;
import TD3. characters.roman.Legionary;
import TD3.enums.Sex;
import TD3.food.FoodStats;
import TD3.places.Gallic_village;
import TD3.places.Roman_town;
import TD3.places.Battlefield;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * InvasionTheatre - Simulation de l'univers d'Asterix
 * Gere l'aspect temporel et les interactions entre les lieux et personnages
 */
public class InvasionTheatre {
    private final String name;
    private final int maxPlaces;
    private final List<Place> places;
    private final List<Character> clanChiefs;
    private final Random random;
    private final Scanner scanner;
    private int currentTurn;

    // Stockage des lieux d'origine des personnages
    private final Map<Character, Place> originalPlaces;

    public InvasionTheatre(String name, int maxPlaces) {
        this.name = name;
        this.maxPlaces = maxPlaces;
        this. places = new ArrayList<>();
        this.clanChiefs = new ArrayList<>();
        this.random = new Random();
        this.scanner = new Scanner(System. in);
        this.currentTurn = 0;
        this.originalPlaces = new HashMap<>();
    }

    // ========== GESTION DES LIEUX ==========

    /**
     * Ajoute un lieu au theatre
     */
    public boolean addPlace(Place place) {
        if (places.size() >= maxPlaces) {
            System.out.println("Nombre maximal de lieux atteint !");
            return false;
        }

        if (places.contains(place)) {
            System.out.println("Ce lieu existe deja dans le theatre !");
            return false;
        }

        places. add(place);

        // Si c'est un lieu avec chef de clan, l'ajouter a la liste
        if (place instanceof Place_with_clan_chief) {
            Place_with_clan_chief placeWithChief = (Place_with_clan_chief) place;
            Character chief = placeWithChief. getClanChief();
            if (chief != null && ! clanChiefs.contains(chief)) {
                clanChiefs.add(chief);
            }
        }

        System.out.println("Lieu '" + place.getName() + "' ajoute au theatre");
        return true;
    }
    /**
     * Affiche tous les lieux du theatre
     */
    public void displayPlaces() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("LIEUX DU THEATRE: " + name);
        System. out.println("=".repeat(80));

        if (places.isEmpty()) {
            System.out.println("Aucun lieu dans le theatre");
        } else {
            for (int i = 0; i < places.size(); i++) {
                Place place = places.get(i);
                String type = place instanceof Battlefield ? "[Champ de bataille]" : "[Lieu]";
                int charCount = place.getThe_characters_present().size();
                int foodCount = place.getThe_aliments_present().size();

                System.out.printf("[%d] %s %s | Personnages: %d | Aliments: %d\n",
                        i, type, place.getName(), charCount, foodCount);

                if (place instanceof Place_with_clan_chief) {
                    Character chief = ((Place_with_clan_chief) place).getClanChief();
                    if (chief != null) {
                        System.out.println("    Chef: " + chief.getName());
                    }
                }
            }
        }
        System.out.println("=".repeat(80));
    }

    /**
     * Compte le nombre total de personnages dans le theatre
     */
    public int countTotalCharacters() {
        int total = 0;
        for (Place place : places) {
            total += place.getThe_characters_present().size();
        }
        return total;
    }

    /**
     * Affiche tous les personnages de tous les lieux
     */
    public void displayAllCharacters() {
        System.out.println("\n" + "=". repeat(80));
        System.out.println("TOUS LES PERSONNAGES DU THEATRE (" + countTotalCharacters() + " total)");
        System.out. println("=".repeat(80));

        for (Place place : places) {
            List<Character> characters = place.getThe_characters_present();

            if (! characters.isEmpty()) {
                System.out.println("\n" + place.getName() + " (" + characters.size() + " personnages):");

                for (Character c : characters) {
                    String status = c.isAlive() ? "[Vivant]" : "[Mort]";
                    System.out.printf("   %s %s (%s) | Vie: %d/%d | Energie: %d/%d | Faim: %d\n",
                            status, c.getName(), c.getType(),
                            (int)c.getHealth(), (int)c.getMaxHealth(),
                            c.getStamina(), c.getMaxStamina(), c.getHunger());
                }
            }
        }
        System.out.println("=".repeat(80));
    }

    // ========== SIMULATION TEMPORELLE ==========

    /**
     * Demarre la simulation principale
     * Point d'entree de la gestion temporelle du theatre
     */
    public void startSimulation(int maxTurns) {
        System.out.println("\n" + "=".repeat(80));
        System.out. println("DEMARRAGE DE LA SIMULATION: " + name);
        System. out.println("=".repeat(80));

        while (currentTurn < maxTurns && !isGameOver()) {
            currentTurn++;

            System.out.println("\n\n" + "=".repeat(80));
            System.out. println("TOUR " + currentTurn + " / " + maxTurns);
            System.out.println("=".repeat(80));

            // 1. Faire combattre les belligerants sur les champs de bataille
            handleBattlefieldCombats();

            // 2. Renvoyer les survivants dans leur lieu d'origine
            returnSurvivorsToOrigin();

            // 3. Modifier aleatoirement l'etat des personnages
            randomlyModifyCharacters();

            // 4. Faire apparaitre des aliments
            spawnFoodInPlaces();

            // 5. Changer les aliments frais en pas frais
            ageFood();

            // 6. Afficher l'etat actuel du jeu
            displayGameState();

            // 7. Donner la main aux chefs de clan
            for (Character chief : new ArrayList<>(clanChiefs)) {
                if (chief. isAlive()) {
                    giveTurnToClanChief(chief);
                } else {
                    System.out.println("Le chef " + chief.getName() + " est mort !");
                }
            }

            // Pause entre les tours
            System.out.println("\nAppuyez sur Entree pour continuer...");
            scanner.nextLine();
        }

        // Fin de la simulation
        displayFinalResults();
    }

    /**
     * Gere les combats sur les champs de bataille
     */
    private void handleBattlefieldCombats() {
        System.out.println("\n=== PHASE DE COMBAT ===");

        for (Place place : places) {
            if (place instanceof Battlefield) {
                List<Character> fighters = place.getThe_characters_present();

                if (fighters.size() < 2) {
                    continue;
                }

                System.out.println("\nCombat sur: " + place.getName());

                // Separer les combattants par nationalite
                List<Character> gauls = new ArrayList<>();
                List<Character> romans = new ArrayList<>();
                List<Character> creatures = new ArrayList<>();

                for (Character c : fighters) {
                    if (! c.isAlive()) continue;

                    String nationality = c.getNationality();
                    if ("Gaul".equals(nationality)) {
                        gauls.add(c);
                    } else if ("Roman".equals(nationality)) {
                        romans.add(c);
                    } else {
                        creatures.add(c);
                    }
                }

                // Combat entre Gaulois et Romains
                conductCombatBetweenGroups(gauls, romans, place);

                // Combat avec les creatures (attaquent tout le monde)
                for (Character creature : new ArrayList<>(creatures)) {
                    if (! creature.isAlive()) continue;

                    List<Character> enemies = new ArrayList<>();
                    enemies.addAll(gauls);
                    enemies.addAll(romans);

                    if (! enemies.isEmpty() && creature instanceof Fighter) {
                        Character target = enemies.get(random.nextInt(enemies.size()));
                        if (target. isAlive()) {
                            creature.fight(target);
                        }
                    }
                }
            }
        }
    }

    /**
     * Fait combattre deux groupes de personnages
     */
    private void conductCombatBetweenGroups(List<Character> group1, List<Character> group2, Place battlefield) {
        if (group1.isEmpty() || group2.isEmpty()) {
            return;
        }

        // Chaque membre du groupe 1 attaque un membre aleatoire du groupe 2
        for (Character attacker : new ArrayList<>(group1)) {
            if (!attacker.isAlive()) continue;

            List<Character> aliveEnemies = new ArrayList<>();
            for (Character c : group2) {
                if (c.isAlive()) aliveEnemies.add(c);
            }

            if (! aliveEnemies.isEmpty() && attacker instanceof Fighter) {
                Character target = aliveEnemies.get(random.nextInt(aliveEnemies.size()));

                // Sauvegarder le lieu d'origine
                if (!originalPlaces.containsKey(attacker)) {
                    originalPlaces.put(attacker, attacker.getPlace());
                }
                if (!originalPlaces.containsKey(target)) {
                    originalPlaces.put(target, target.getPlace());
                }

                attacker.fight(target);
            }
        }
    }

    /**
     * Renvoie les survivants des champs de bataille vers leur lieu d'origine
     */
    private void returnSurvivorsToOrigin() {
        System.out.println("\n=== RETOUR DES SURVIVANTS ===");

        for (Place place : places) {
            if (place instanceof Battlefield) {
                List<Character> fighters = new ArrayList<>(place.getThe_characters_present());

                for (Character fighter : fighters) {
                    if (fighter.isAlive() && originalPlaces.containsKey(fighter)) {
                        Place origin = originalPlaces.get(fighter);

                        if (origin != null && origin != place) {
                            place.removeCharacter(fighter);
                            origin.addCharacter(fighter);
                            originalPlaces.remove(fighter);
                        }
                    } else if (! fighter.isAlive()) {
                        // Retirer les morts du champ de bataille
                        place.removeCharacter(fighter);
                        originalPlaces.remove(fighter);
                    }
                }
            }
        }
    }

    /**
     * Modifie aleatoirement l'etat de certains personnages
     */
    private void randomlyModifyCharacters() {
        System.out.println("\n=== EVENEMENTS ALEATOIRES ===");

        List<Character> allCharacters = new ArrayList<>();
        for (Place place : places) {
            allCharacters. addAll(place.getThe_characters_present());
        }

        for (Character c : allCharacters) {
            if (! c.isAlive()) continue;

            int event = random.nextInt(100);

            if (event < 20) {
                // 20% de chance: augmenter la faim
                int hungerIncrease = random.nextInt(20) + 10;
                c.updateHunger(-hungerIncrease);
                System.out.println(c.getName() + " a faim !  (Faim: " + c.getHunger() + ")");
            } else if (event < 35) {
                // 15% de chance: diminuer l'energie
                int staminaDecrease = random.nextInt(15) + 5;
                c.updateStamina(-staminaDecrease);
                System.out.println(c.getName() + " est fatigue ! (Energie: " + c.getStamina() + ")");
            }
        }
    }

    /**
     * Fait apparaitre des aliments dans les lieux (hormis les champs de bataille)
     */
    private void spawnFoodInPlaces() {
        System. out.println("\n=== APPARITION D'ALIMENTS ===");

        for (Place place : places) {
            // Pas d'aliments sur les champs de bataille
            if (place instanceof Battlefield) {
                continue;
            }

            // 40% de chance qu'un aliment apparaisse ( à changer : rareté différentes pour chaque aliment)
            if (random.nextInt(100) < 40) {
                FoodType[] foodTypes = FoodType.values();
                FoodType randomFoodType = foodTypes[random. nextInt(foodTypes.length)];

                Food newFood = FoodStats.newFood(randomFoodType);
                place.addFood(newFood);

                System.out.println(randomFoodType + " apparait dans " + place. getName());
            }
        }
    }

    /**
     * Fait vieillir les aliments (change frais en pas frais)
     */
    private void ageFood() {
        System.out. println("\n=== VIEILLISSEMENT DES ALIMENTS ===");

        for (Place place : places) {
            List<Food> foods = new ArrayList<>(place.getThe_aliments_present());

            for (Food food : foods) {
                // Si c'est un poisson frais, 30% de chance de devenir pas frais
                if (food.getFoodType() == FoodType. PASSABLE_FRESH_FISH && random.nextInt(100) < 30) {
                    place.removeFood(food);
                    Food rottenFish = FoodStats.newFood(FoodType.NOT_FRESH_FISH);
                    place.addFood(rottenFish);

                    System.out.println("Un poisson devient pas frais dans " + place.getName());
                }

                // Si c'est du trefle frais, 25% de chance de devenir pas frais
                if (food.getFoodType() == FoodType. FRESH_FOUR_LEAF_CLOVER && random.nextInt(100) < 25) {
                    place.removeFood(food);
                    Food oldClover = FoodStats.newFood(FoodType.NOT_FRESH_FOUR_LEAF_CLOVER);
                    place.addFood(oldClover);

                    System. out.println("Un trefle devient pas frais dans " + place.getName());
                }
            }
        }
    }

    /**
     * Donne la main a un chef de clan avec un nombre limite d'actions
     */
    private void giveTurnToClanChief(Character chief) {
        Place place = chief.getPlace();
        if (place == null) {
            return;
        }

        System.out.println("\n" + "=".repeat(80));
        System.out.println("TOUR DU CHEF " + chief.getName());
        System.out.println("=".repeat(80));

        int actionsRemaining = 3; // Nombre d'actions limite

        while (actionsRemaining > 0) {
            System.out.println("\nActions restantes: " + actionsRemaining);
            System.out. println("1. Examiner le lieu");
            System. out.println("2. Soigner un personnage");
            System.out.println("3. Nourrir un personnage");
            System.out.println("0. Terminer le tour");
            System.out.print("Votre choix: ");

            String input = scanner.nextLine(). trim();
            int choice;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out. println("Entree invalide !");
                continue;
            }

            switch (choice) {
                case 1:
                    examinePlace(place);
                    break;
                case 2:
                    if (healCharacterSimple(place)) {
                        actionsRemaining--;
                    }
                    break;
                case 3:
                    if (feedCharacterSimple(place)) {
                        actionsRemaining--;
                    }
                    break;
                case 0:
                    System.out.println(chief.getName() + " termine son tour.");
                    return;
                default:
                    System.out. println("Choix invalide !");
            }
        }

        System.out.println("\nPlus d'actions disponibles !");
    }

    /**
     * Examine un lieu (ne compte pas comme action)
     */
    /**
     * Examine un lieu (ne compte pas comme action)
     */
    private void examinePlace(Place place) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("LIEU: " + place. getName());
        System.out. println("=".repeat(60));

        List<Character> characters = place.getThe_characters_present();
        System.out.println("\nPersonnages presents (" + characters.size() + "):");
        if (characters.isEmpty()) {
            System.out.println("   Aucun personnage");
        } else {
            for (int i = 0; i < characters.size(); i++) {
                Character c = characters.get(i);
                String status = c.isAlive() ? "[Vivant]" : "[Mort]";
                System.out.printf("   [%d] %s %s | Type: %s | Vie: %d/%d | Energie: %d/%d\n",
                        i, status, c.getName(), c.getType(),
                        (int)c.getHealth(), (int)c.getMaxHealth(),
                        c.getStamina(), c.getMaxStamina());
            }
        }

        List<Food> foods = place.getThe_aliments_present();
        System. out.println("\nAliments disponibles (" + foods.size() + "):");
        if (foods.isEmpty()) {
            System. out.println("   Aucun aliment");
        } else {
            for (int i = 0; i < foods.size(); i++) {
                Food f = foods.get(i);
                System.out.printf("   [%d] %s | Vie: %+d | Faim: %+d | Energie: %+d\n",
                        i, f.getFoodType(), f.getHealthEffect(), f.getHungerEffect(), f.getStaminaEffect());
            }
        }
        System.out.println("=". repeat(60));
    }

    /**
     * Soigne un personnage (version simplifiee)
     */
    private boolean healCharacterSimple(Place place) {
        List<Character> characters = place.getThe_characters_present();

        if (characters.isEmpty()) {
            System.out.println("Aucun personnage a soigner !");
            return false;
        }

        System.out.println("\n--- Soigner un personnage ---");
        for (int i = 0; i < characters.size(); i++) {
            Character c = characters.get(i);
            System.out.printf("[%d] %s - Vie: %d/%d\n",
                    i, c.getName(), (int)c.getHealth(), (int)c.getMaxHealth());
        }

        System.out.print("Choisir un personnage (numero): ");
        String input = scanner.nextLine(). trim();
        int index;

        try {
            index = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out. println("Entree invalide !");
            return false;
        }

        if (index < 0 || index >= characters.size()) {
            System.out. println("Index invalide !");
            return false;
        }

        System.out.print("Quantite de soin: ");
        input = scanner.nextLine().trim();
        int healAmount;

        try {
            healAmount = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Entree invalide !");
            return false;
        }

        return place.healCharacter(characters.get(index), healAmount);
    }


    /**
     * Nourrit un personnage (version simplifiee)
     */
    private boolean feedCharacterSimple(Place place) {
        List<Character> characters = place.getThe_characters_present();
        List<Food> foods = place.getThe_aliments_present();

        if (characters.isEmpty()) {
            System.out.println("Aucun personnage a nourrir !");
            return false;
        }

        if (foods.isEmpty()) {
            System.out.println("Aucun aliment disponible !");
            return false;
        }

        System.out.println("\n--- Nourrir un personnage ---");
        System.out.println("Personnages:");
        for (int i = 0; i < characters.size(); i++) {
            Character c = characters.get(i);
            System.out.printf("[%d] %s - Faim: %d\n", i, c.getName(), c.getHunger());
        }

        System. out.print("Choisir un personnage: ");
        String input = scanner. nextLine().trim();
        int charIndex;

        try {
            charIndex = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Entree invalide !");
            return false;
        }

        if (charIndex < 0 || charIndex >= characters. size()) {
            System.out.println("Index invalide !");
            return false;
        }

        System.out.println("\nAliments:");
        for (int i = 0; i < foods.size(); i++) {
            Food f = foods.get(i);
            System.out.printf("[%d] %s\n", i, f.getFoodType());
        }

        System.out.print("Choisir un aliment: ");
        input = scanner.nextLine().trim();
        int foodIndex;

        try {
            foodIndex = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Entree invalide !");
            return false;
        }

        if (foodIndex < 0 || foodIndex >= foods.size()) {
            System.out. println("Index invalide !");
            return false;
        }

        return place.feedCharacter(characters. get(charIndex), foodIndex);
    }

    /**
     * Affiche l'etat actuel du jeu
     */
    private void displayGameState() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ETAT DU MONDE");
        System.out.println("=".repeat(80));

        int totalCharacters = countTotalCharacters();
        int aliveCharacters = 0;
        int deadCharacters = 0;

        for (Place place : places) {
            for (Character c : place.getThe_characters_present()) {
                if (c.isAlive()) {
                    aliveCharacters++;
                } else {
                    deadCharacters++;
                }
            }
        }

        System.out.println("Personnages: " + totalCharacters + " (Vivants: " + aliveCharacters + ", Morts: " + deadCharacters + ")");
        System.out.println("Lieux: " + places.size());
        System.out.println("Chefs de clan: " + clanChiefs.size());
        System. out.println("=".repeat(80));
    }

    /**
     * Verifie si la partie est terminee
     */
    private boolean isGameOver() {
        // La partie se termine si tous les chefs de clan sont morts
        for (Character chief : clanChiefs) {
            if (chief.isAlive()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Affiche les resultats finaux
     */
    private void displayFinalResults() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("FIN DE LA SIMULATION");
        System.out.println("=".repeat(80));

        System.out.println("\nRESULTATS FINAUX:");
        System.out. println("Tours joues: " + currentTurn);

        displayAllCharacters();

        System.out.println("\n" + "=".repeat(80));
        System.out.println("Merci d'avoir joue !");
        System.out.println("=".repeat(80));
    }

    // ========== GETTERS ==========

    public String getName() {
        return name;
    }

    public int getMaxPlaces() {
        return maxPlaces;
    }

    public List<Place> getPlaces() {
        return new ArrayList<>(places);
    }

    public List<Character> getClanChiefs() {
        return new ArrayList<>(clanChiefs);
    }

    public int getCurrentTurn() {
        return currentTurn;
    }
    /**
     * Point d'entree principal de l'application - VERSION TEST
     * Demarre la simulation du theatre d'envahissement
     */
    public static void main(String[] args) {
        System.out.println("=== TEST DE LA CLASSE INVASIONTHEATRE ===\n");

        // Creer le theatre
        InvasionTheatre theatre = new InvasionTheatre("Theatre de Test", 5);

        // Test 1: Creer des personnages
        System.out. println("--- Creation des personnages ---");
        Druid panoramix = new Druid("Panoramix", Sex. MASCULIN);
        Blacksmith asterix = new Blacksmith("Asterix", Sex.MASCULIN);
        Blacksmith obelix = new Blacksmith("Obelix", Sex.MASCULIN);

        General cesar = new General("Jules Cesar", Sex.MASCULIN);
        Legionary marcus = new Legionary("Marcus", Sex.MASCULIN);
        Legionary brutus = new Legionary("Brutus", Sex.MASCULIN);

        System.out.println("Personnages crees avec succes !\n");

        // Test 2: Creer des lieux
        System.out.println("--- Creation des lieux ---");
        Gallic_village village = new Gallic_village("Village Gaulois", 1000, panoramix);
        Roman_town camp = new Roman_town("Camp Romain", 2000, cesar);
        Battlefield battlefield = new Battlefield("Plaine de Combat", 1500);

        System.out. println("Lieux crees avec succes !\n");

        // Test 3: Ajouter les lieux au theatre
        System.out.println("--- Ajout des lieux au theatre ---");
        theatre.addPlace(village);
        theatre.addPlace(camp);
        theatre.addPlace(battlefield);
        System.out.println();

        // Test 4: Ajouter des personnages dans les lieux
        System.out. println("--- Ajout des personnages dans les lieux ---");
        village.addCharacter(asterix);
        village.addCharacter(obelix);

        camp.addCharacter(marcus);
        camp.addCharacter(brutus);
        System.out.println();

        // Test 5: Ajouter quelques aliments
        System.out.println("--- Ajout d'aliments ---");
        village.addFood(FoodStats.newFood(FoodType.WILD_BOAR));
        village.addFood(FoodStats.newFood(FoodType.PASSABLE_FRESH_FISH));
        camp.addFood(FoodStats.newFood(FoodType.CARROT));
        System.out. println();

        // Test 6: Afficher l'etat initial
        System.out.println("=== ETAT INITIAL ===");
        theatre.displayPlaces();
        theatre.displayAllCharacters();

        // Test 7: Envoyer des personnages au combat
        System. out.println("\n--- Envoi de personnages sur le champ de bataille ---");
        village.removeCharacter(asterix);
        battlefield.addCharacter(asterix);

        camp.removeCharacter(marcus);
        battlefield.addCharacter(marcus);
        System.out.println();

        // Test 8: Afficher l'etat apres les deplacements
        System.out. println("=== ETAT APRES DEPLACEMENTS ===");
        theatre.displayPlaces();
        theatre.displayAllCharacters();

        // Test 9: Lancer une simulation courte (2 tours)
        System.out.println("\n\n=== LANCEMENT DE LA SIMULATION (2 TOURS) ===");
        System.out.println("IMPORTANT: Tapez '0' puis Entree pour terminer rapidement chaque tour de chef");
        System.out.println("Appuyez sur Entree pour commencer...");

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        theatre.startSimulation(2);

        System.out.println("\n=== TEST TERMINE ===");
    }
}