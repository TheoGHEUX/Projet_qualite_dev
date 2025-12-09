package main.theatre;

import main.model.character.Character;
import main.model.clan_chief.ClanChief;
import main.enums.CharacterType;
import main.interfaces.Fighter;
import main.model.place.*;
import main.enums.FoodType;
import main.model.items.food.Food;
import main.model.items.food.FoodStats;
import main.enums.*;
import main.model.place.category.*;
import main.model.place. category.place_with_clan_chief.GallicVillage;
import main.model.place.category. PlaceWithClanChief;
import main.model.place.category.place_with_clan_chief.RomanFortifiedCamp;
import main.model.place.category.place_with_clan_chief.RomanTown;
import main.model.place.category.place_with_clan_chief.GalloRomanSettlement;

import java.util.*;

public class InvasionTheatre {

    private final String name;
    private final int maxPlaces;
    private final List<Place> places = new ArrayList<>();
    private final List<ClanChief> clanChiefs = new ArrayList<>();
    private final Map<Character, Place> originalPlaces = new HashMap<>();
    private final Random random = new Random();

    private int currentTurn = 0;

    //CONSTRUCTEUR
    public InvasionTheatre(String name, int maxPlaces) {
        this.name = name;
        this.maxPlaces = maxPlaces;
    }

    /**
     * Initialise automatiquement des personnages et aliments aléatoires
     * dans chaque lieu avec chef de clan.
     */
    public void initializeRandomCharactersAndItems() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("       INITIALISATION AUTOMATIQUE DU MONDE");
        System.out.println("=".repeat(60));

        for (Place place : places) {
            if (!(place instanceof PlaceWithClanChief placeWithChief)) continue;
            if (placeWithChief.getClanChief() == null) continue;

            ClanChief chief = placeWithChief.getClanChief();

            System.out. println("\n[Lieu] " + place.getName());
            System.out.println("  Chef : " + chief.getName());
            System.out.println("  " + "-".repeat(50));

            // Déterminer les types de personnages autorisés selon le lieu
            List<CharacterType> allowedTypes = getAllowedCharacterTypes(place);

            // Créer 3 à 5 personnages aléatoires
            int nbCharacters = random. nextInt(3) + 3;
            System.out.println("  [Personnages] Création de " + nbCharacters + " personnages :");

            // Désactiver temporairement les messages de Place. addCharacter()
            for (int i = 0; i < nbCharacters; i++) {
                CharacterType type = allowedTypes. get(random.nextInt(allowedTypes.size()));
                String name = generateRandomName(type) + (i > 0 ?  " " + (i+1) : "");
                Sex sex = random.nextBoolean() ? Sex.MALE : Sex.FEMALE;

                // Affichage propre
                System. out.print("     • " + name + " (" + type + ", " + sex + ")");

                // Créer le personnage
                java.io.ByteArrayOutputStream baos = new java. io.ByteArrayOutputStream();
                java.io.PrintStream oldOut = System.out;
                System.setOut(new java. io.PrintStream(baos));

                chief.createCharacter(name, sex, type);

                System.setOut(oldOut);
            }

            // Ajouter 5 à 10 aliments aléatoires
            int nbFood = random.nextInt(6) + 5;
            System. out.println("\n  [Aliments] Ajout de " + nbFood + " aliments :");

            // Compter les aliments par type pour avoir un affichage groupé
            Map<FoodType, Integer> foodCount = new HashMap<>();

            for (int i = 0; i < nbFood; i++) {
                FoodType foodType = getRandomFoodForPlace(place);
                foodCount. put(foodType, foodCount. getOrDefault(foodType, 0) + 1);

                // Ajouter sans affichage
                java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
                java.io. PrintStream oldOut = System.out;
                System.setOut(new java.io.PrintStream(baos));

                place.addFood(FoodStats. newFood(foodType));

                System.setOut(oldOut);
            }

            // Affichage groupé des aliments
            for (Map.Entry<FoodType, Integer> entry : foodCount.entrySet()) {
                String foodName = getFoodDisplayName(entry. getKey());
                System.out.println("     • " + foodName + " x " + entry.getValue());
            }
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("   INITIALISATION TERMINÉE");
        System.out.println("   Total : " + getTotalCharacterCount() + " personnages dans " + places.size() + " lieux");
        System.out.println("=".repeat(60) + "\n");
    }

    /**
     * Pour avoir un joli affichage des items
     */
    private String getFoodDisplayName(FoodType foodType) {
        return switch (foodType) {
            case WILD_BOAR -> "Sanglier";
            case PASSABLE_FRESH_FISH -> "Poisson frais";
            case NOT_FRESH_FISH -> "Poisson pas frais";
            case WINE -> "Vin";
            case HONEY -> "Miel";
            case MEAD -> "Hydromel";
            case CARROT -> "Carotte";
            case STRAWBERRY -> "Fraise";
            case LOBSTER -> "Homard";
            case MISTLETOE -> "Gui";
            case SALT -> "Sel";
            case FRESH_FOUR_LEAF_CLOVER -> "Trèfle frais";
            case NOT_FRESH_FOUR_LEAF_CLOVER -> "Trèfle pas frais";
            case ROCKFISH_OIL -> "Huile de roche";
            case BEET_JUICE -> "Jus de betterave";
            case TWO_HEADS_UNICORN_MILK -> "Lait de licorne";
            case DOGMATIX_HAIR -> "Poils d'Idéfix";
            case SECRET_INGREDIENT -> "Ingrédient secret";
            default -> foodType.toString();
        };
    }

    /**
     * Retourne les types de personnages autorisés selon le type de lieu.
     */
    private List<CharacterType> getAllowedCharacterTypes(Place place) {
        List<CharacterType> types = new ArrayList<>();

        if (place instanceof GallicVillage) {
            types.addAll(List.of(CharacterType. DRUID, CharacterType. BLACKSMITH,
                    CharacterType.MERCHANT, CharacterType.INNKEEPER));
        } else if (place instanceof RomanFortifiedCamp) {
            types.addAll(List.of(CharacterType. LEGIONARY, CharacterType. GENERAL));
        } else if (place instanceof RomanTown) {
            types.addAll(List.of(CharacterType. PREFECT, CharacterType. LEGIONARY,
                    CharacterType.GENERAL));
        } else if (place instanceof GalloRomanSettlement) {
            types.addAll(List.of(CharacterType. MERCHANT, CharacterType. INNKEEPER,
                    CharacterType.BLACKSMITH, CharacterType.PREFECT,
                    CharacterType. LEGIONARY));
        }

        return types;
    }

    /**
     * Génère un nom aléatoire selon le type de personnage.
     */
    private String generateRandomName(CharacterType type) {
        String[] gaulNames = {"Astérix", "Obélix", "Panoramix", "Assurancetourix",
                "Abraracourcix", "Bonemine", "Agecanonix", "Cétautomatix",
                "Ordralfabétix", "Falbala", "Ielosubmarine", "Plaintcontrix"};
        String[] romanNames = {"Marcus", "Brutus", "Caesar", "Claudius", "Gracchus",
                "Julius", "Titus", "Lucius", "Gaius", "Publius",
                "Quintus", "Decimus", "Septimus", "Octavius"};

        return switch (type) {
            case DRUID, BLACKSMITH, MERCHANT, INNKEEPER ->
                    gaulNames[random.nextInt(gaulNames.length)];
            case LEGIONARY, GENERAL, PREFECT ->
                    romanNames[random.nextInt(romanNames.length)];
            case WEREWOLF ->
                    "Loup" + random.nextInt(100);
        };
    }

    /**
     * Retourne un aliment aléatoire adapté au type de lieu.
     */
    private FoodType getRandomFoodForPlace(Place place) {
        FoodType[] gaulFood = {FoodType. WILD_BOAR, FoodType.WILD_BOAR, // Plus de sanglier
                FoodType. PASSABLE_FRESH_FISH, FoodType.WINE,
                FoodType. CARROT, FoodType. STRAWBERRY};
        FoodType[] romanFood = {FoodType.WILD_BOAR, FoodType.HONEY,
                FoodType. MEAD, FoodType. WINE, FoodType.WINE, // Plus de vin
                FoodType.LOBSTER};

        if (place instanceof GallicVillage || place instanceof GalloRomanSettlement) {
            return gaulFood[random.nextInt(gaulFood.length)];
        } else {
            return romanFood[random.nextInt(romanFood.length)];
        }
    }

    //GESTION DES LIEUX
    public boolean addPlace(Place place) {
        if (places.size() >= maxPlaces) return false;
        places.add(place);

        if (place instanceof PlaceWithClanChief p && p.getClanChief() != null) {
            clanChiefs.add(p.getClanChief());
        }
        return true;
    }

    public List<Place> getPlaces() { return places; }
    public List<ClanChief> getClanChiefs() { return clanChiefs; }

    public int getTotalCharacterCount() {
        int total = 0;
        for (Place p : places) {
            total += p.getThe_characters_present().size();
        }
        return total;
    }

    public String getName() { return name; }
    public int getCurrentTurn() { return currentTurn; }


    // SIMULATION
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


    //COMBATS

    private void fight() {
        for (Place place : places) {
            if (!(place instanceof Battlefield)) continue;

            List<Character> fighters = place.getThe_characters_present();
            if (fighters.size() < 2) continue;

            // Séparer groupes
            List<Character> gauls = new ArrayList<>();
            List<Character> romans = new ArrayList<>();

            for (Character c : fighters) {
                if (! c.isAlive()) continue;
                if ("Gaul".equals(c. getNationality())) gauls.add(c);
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
            Character target = g2.get(random. nextInt(g2.size()));
            originalPlaces.putIfAbsent(attacker, attacker.getPlace());
            originalPlaces.putIfAbsent(target, target.getPlace());
            attacker.fight(target);
        }
    }

    //RETOUR AU LIEU D'ORIGINE
    private void returnToOriginalPlaces() {
        for (Place battlefield : places) {
            if (!(battlefield instanceof Battlefield)) continue;

            for (Character c : new ArrayList<>(battlefield.getThe_characters_present())) {
                if (! c.isAlive()) {
                    battlefield.removeCharacter(c);
                    continue;
                }

                if (originalPlaces.containsKey(c)) {
                    Place origin = originalPlaces.remove(c);
                    battlefield. removeCharacter(c);
                    origin.addCharacter(c);
                }
            }
        }
    }

    //MODIFICATION ALÉATOIRE

    private void modifyCharactersState() {
        for (Place p : places) {
            for (Character c : p.getThe_characters_present()) {
                if (! c.isAlive()) continue;

                int roll = random.nextInt(100);

                if (roll < 25) c.updateHunger(-10);
                else if (roll < 40) c.updateStamina(-5);
            }
        }
    }

    //APPARITION ALIMENTS
    private void spawnFood() {
        for (Place p :  places) {
            if (p instanceof Battlefield) continue;

            if (random.nextInt(100) < 30) {
                FoodType t = FoodType.values()[random.nextInt(FoodType.values().length)];
                p.addFood(FoodStats. newFood(t));
            }
        }
    }

    //VIEILLISSEMENT DES ALIMENTS

    private void ageFood() {
        System.out.println("\n=== VIEILLISSEMENT DES ALIMENTS ===");

        for (Place place : places) {
            List<Food> foods = new ArrayList<>(place.getThe_aliments_present());

            for (Food food : foods) {
                // Si c'est un poisson frais, 30% de chance de devenir pas frais
                if (food. getFoodType() == FoodType.PASSABLE_FRESH_FISH && random.nextInt(100) < 30) {
                    place.removeFood(food);
                    Food rottenFish = FoodStats.newFood(FoodType.NOT_FRESH_FISH);
                    place.addFood(rottenFish);

                    System.out.println("Un poisson devient pas frais dans " + place.getName());
                }

                // Si c'est du trefle frais, 25% de chance de devenir pas frais
                if (food.getFoodType() == FoodType.FRESH_FOUR_LEAF_CLOVER && random.nextInt(100) < 25) {
                    place. removeFood(food);
                    Food oldClover = FoodStats.newFood(FoodType.NOT_FRESH_FOUR_LEAF_CLOVER);
                    place.addFood(oldClover);

                    System.out.println("Un trefle devient pas frais dans " + place.getName());
                }
            }
        }
    }
}