package TD3.Theatre;

import TD3.characters.Character;
import TD3.interfaces. Fighter;
import TD3.places.*;
import TD3.enums.FoodType;
import TD3.food.Food;
import TD3.food.FoodStats;

import java.util.*;

public class InvasionTheatre {

    private final String name;
    private final int maxPlaces;
    private final List<Place> places = new ArrayList<>();
    private final List<Character> clanChiefs = new ArrayList<>();
    private final Map<Character, Place> originalPlaces = new HashMap<>();
    private final Random random = new Random();

    private int currentTurn = 0;

    // -------------------------------------------------------
    // CONSTRUCTEUR
    // -------------------------------------------------------
    public InvasionTheatre(String name, int maxPlaces) {
        this.name = name;
        this.maxPlaces = maxPlaces;
    }

    // -------------------------------------------------------
    // INITIALISATION DU MONDE (à appeler depuis Main)
    // -------------------------------------------------------
    public void initializeWorld() {
        System.out.println("=== INITIALISATION DU MONDE ===\n");

        // 1. Créer les chefs de clan
        ClanChief abraracourcix = new ClanChief("Abraracourcix", Sex.MALE);
        ClanChief centurion = new ClanChief("Caius Bonus", Sex.MALE);
        ClanChief prefet = new ClanChief("Julius Pompus", Sex. MALE);

        // 2. Créer les lieux avec leurs chefs
        Place villageGaulois = new Gallic_village("Village Gaulois", 1000, abraracourcix);
        Place campRomain = new Roman_fortified_camp("Camp Babaorum", 1200, centurion);
        Place villeRomaine = new Roman_town("Lutèce", 2000, prefet);
        Place battlefield = new Battlefield("Plaine de combat", 1500);
        Place enclos = new Enclosure("Enclos des créatures", 500);

        // 3. Ajouter les lieux au théâtre
        addPlace(villageGaulois);
        addPlace(campRomain);
        addPlace(villeRomaine);
        addPlace(battlefield);
        addPlace(enclos);

        // 4. Créer des personnages initiaux
        // Village gaulois
        abraracourcix.createCharacter("Astérix", Sex.MALE, CharacterType.DRUID);
        abraracourcix.createCharacter("Obélix", Sex. MALE, CharacterType.BLACKSMITH);
        abraracourcix.createCharacter("Panoramix", Sex.MALE, CharacterType.DRUID);
        abraracourcix.createCharacter("Assurancetourix", Sex.MALE, CharacterType.MERCHANT);
        abraracourcix.createCharacter("Bonemine", Sex.FEMALE, CharacterType.INNKEEPER);

        // Camp romain
        centurion. createCharacter("Marcus", Sex.MALE, CharacterType.LEGIONARY);
        centurion.createCharacter("Brutus", Sex.MALE, CharacterType.LEGIONARY);
        centurion.createCharacter("Cesar", Sex. MALE, CharacterType.GENERAL);

        // Ville romaine
        prefet.createCharacter("Claudius", Sex.MALE, CharacterType.PREFECT);
        prefet.createCharacter("Gracchus", Sex.MALE, CharacterType.LEGIONARY);

        // 5. Ajouter des aliments initiaux
        villageGaulois.addFood(FoodStats. newFood(FoodType. BOAR));
        villageGaulois.addFood(FoodStats.newFood(FoodType.BOAR));
        villageGaulois.addFood(FoodStats.newFood(FoodType.WINE));
        villageGaulois.addFood(FoodStats.newFood(FoodType.PASSABLE_FRESH_FISH));

        campRomain.addFood(FoodStats.newFood(FoodType.BOAR));
        campRomain.addFood(FoodStats.newFood(FoodType.HONEY));
        campRomain.addFood(FoodStats.newFood(FoodType.MEAD));
        campRomain.addFood(FoodStats.newFood(FoodType.WINE));

        villeRomaine.addFood(FoodStats.newFood(FoodType.WINE));
        villeRomaine.addFood(FoodStats.newFood(FoodType.HONEY));
        villeRomaine.addFood(FoodStats.newFood(FoodType.MEAD));

        // 6. Ingrédients pour potion magique
        villageGaulois.addFood(FoodStats.newFood(FoodType.MISTLETOE));
        villageGaulois.addFood(FoodStats.newFood(FoodType.CARROTS));
        villageGaulois.addFood(FoodStats.newFood(FoodType.SALT));
        villageGaulois.addFood(FoodStats.newFood(FoodType.FRESH_FOUR_LEAF_CLOVER));
        villageGaulois. addFood(FoodStats.newFood(FoodType.ROCK_OIL));
        villageGaulois.addFood(FoodStats.newFood(FoodType.HONEY));
        villageGaulois.addFood(FoodStats.newFood(FoodType.MEAD));
        villageGaulois. addFood(FoodStats.newFood(FoodType.SECRET_INGREDIENT));

        System.out.println("\n=== MONDE INITIALISÉ ===");
        System.out.println("Lieux créés : " + places.size());
        System.out.println("Chefs de clan : " + clanChiefs.size());
        System.out.println("Personnages totaux : " + getTotalCharacterCount());
    }

    // -------------------------------------------------------
    // GESTION DES LIEUX
    // -------------------------------------------------------
    public boolean addPlace(Place place) {
        if (places.size() >= maxPlaces) return false;
        places.add(place);

        if (place instanceof Place_with_clan_chief p && p.getClanChief() != null) {
            clanChiefs.add(p.getClanChief());
        }
        return true;
    }

    public List<Place> getPlaces() { return places; }
    public List<Character> getClanChiefs() { return clanChiefs; }

    // -------------------------------------------------------
    // SIMULATION
    // -------------------------------------------------------
    public void simulateTurn() {
        currentTurn++;

        fight();
        returnToOriginalPlaces();
        modifyCharactersState();
        spawnFood();
        ageFood();
    }

    public void simulate(int maxTurns) {
        for (int i = 0; i < maxTurns; i++) {
            simulateTurn();
        }
    }

    // -------------------------------------------------------
    // LOGIQUE : COMBATS
    // -------------------------------------------------------
    private void fight() {
        for (Place place : places) {
            if (!(place instanceof Battlefield)) continue;

            List<Character> fighters = place.getThe_characters_present();
            if (fighters.size() < 2) continue;

            // Séparer groupes
            List<Character> gauls = new ArrayList<>();
            List<Character> romans = new ArrayList<>();

            for (Character c : fighters) {
                if (!c. isAlive()) continue;
                if ("Gaul".equals(c.getNationality())) gauls.add(c);
                else if ("Roman".equals(c.getNationality())) romans.add(c);
            }

            // Combat
            duelGroups(gauls, romans, place);
        }
    }

    private void duelGroups(List<Character> g1, List<Character> g2, Place battlefield) {
        if (g1.isEmpty() || g2.isEmpty()) return;

        for (Character attacker : g1) {
            if (!(attacker instanceof Fighter)) continue;
            Character target = g2.get(random.nextInt(g2.size()));
            originalPlaces.putIfAbsent(attacker, attacker.getPlace());
            originalPlaces.putIfAbsent(target, target.getPlace());
            attacker.fight(target);
        }
    }

    // -------------------------------------------------------
    // LOGIQUE : RETOUR AU LIEU D'ORIGINE
    // -------------------------------------------------------
    private void returnToOriginalPlaces() {
        for (Place battlefield : places) {
            if (!(battlefield instanceof Battlefield)) continue;

            for (Character c : new ArrayList<>(battlefield. getThe_characters_present())) {
                if (!c.isAlive()) {
                    battlefield.removeCharacter(c);
                    continue;
                }

                if (originalPlaces.containsKey(c)) {
                    Place origin = originalPlaces. remove(c);
                    battlefield.removeCharacter(c);
                    origin.addCharacter(c);
                }
            }
        }
    }

    // -------------------------------------------------------
    // LOGIQUE : MODIFICATION ALÉATOIRE
    // -------------------------------------------------------
    private void modifyCharactersState() {
        for (Place p : places) {
            for (Character c : p.getThe_characters_present()) {
                if (!c.isAlive()) continue;

                int roll = random.nextInt(100);

                if (roll < 25) c.updateHunger(-10);
                else if (roll < 40) c.updateStamina(-5);
            }
        }
    }

    // -------------------------------------------------------
    // LOGIQUE : APPARITION ALIMENTS
    // -------------------------------------------------------
    private void spawnFood() {
        for (Place p : places) {
            if (p instanceof Battlefield) continue;

            if (random.nextInt(100) < 30) {
                FoodType t = FoodType. values()[random.nextInt(FoodType.values().length)];
                p.addFood(FoodStats.newFood(t));
            }
        }
    }

    // -------------------------------------------------------
    // LOGIQUE : VIEILLISSEMENT ALIMENTS
    // -------------------------------------------------------
    /**
     * Fait vieillir les aliments (change frais en pas frais)
     */
    private void ageFood() {
        System.out.println("\n=== VIEILLISSEMENT DES ALIMENTS ===");

        for (Place place : places) {
            List<Food> foods = new ArrayList<>(place.getThe_aliments_present());

            for (Food food : foods) {
                // Si c'est un poisson frais, 30% de chance de devenir pas frais
                if (food.getFoodType() == FoodType.PASSABLE_FRESH_FISH && random.nextInt(100) < 30) {
                    place.removeFood(food);
                    Food rottenFish = FoodStats.newFood(FoodType.NOT_FRESH_FISH);
                    place.addFood(rottenFish);

                    System.out.println("Un poisson devient pas frais dans " + place. getName());
                }

                // Si c'est du trefle frais, 25% de chance de devenir pas frais
                if (food.getFoodType() == FoodType.FRESH_FOUR_LEAF_CLOVER && random.nextInt(100) < 25) {
                    place.removeFood(food);
                    Food oldClover = FoodStats.newFood(FoodType.NOT_FRESH_FOUR_LEAF_CLOVER);
                    place. addFood(oldClover);

                    System.out.println("Un trefle devient pas frais dans " + place.getName());
                }
            }
        }
    }
}