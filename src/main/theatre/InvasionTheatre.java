package main.theatre;

import main.model.character.Character;
import main.model.clan_chief.ClanChief;
import main.enums.CharacterType;
import main.interfaces. Fighter;
import main.model.place.*;
import main.enums.FoodType;
import main.model.items.food.Food;
import main.model.items.food.FoodStats;
import main.enums.*;
import main.model.place.category.*;
import main.model.place.category.place_with_clan_chief.GallicVillage;
import main.model.place.category.PlaceWithClanChief;
import main.model.place.category.place_with_clan_chief.RomanFortifiedCamp;
import main.model.place.category.place_with_clan_chief.RomanTown;

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

    //INITIALISATION DU MONDE (a appeler dans le main par la suite)
    public void initializeWorld() {
        System.out.println("=== INITIALISATION DU MONDE ===\n");

        // 1. Créer les chefs de clan
        ClanChief abraracourcix = new ClanChief("Abraracourcix", Sex.MALE);
        ClanChief centurion = new ClanChief("Caius Bonus", Sex.MALE);
        ClanChief prefet = new ClanChief("Julius Pompus", Sex.MALE);

        // 2. Créer les lieux avec leurs chefs
        Place villageGaulois = new GallicVillage("Village Gaulois", 1000, abraracourcix);
        Place campRomain = new RomanFortifiedCamp("Camp Babaorum", 1200, centurion);
        Place villeRomaine = new RomanTown("Lutèce", 2000, prefet);
        Place battlefield = new Battlefield("Plaine de combat", 1500);
        Place enclos = new Enclosure("Enclos des créatures", 500);

        // 3. Ajouter les lieux
        addPlace(villageGaulois);
        addPlace(campRomain);
        addPlace(villeRomaine);
        addPlace(battlefield);
        addPlace(enclos);

        // 4. Créer des personnages
        // Village gaulois
        abraracourcix.createCharacter("Astérix", Sex.MALE, CharacterType.DRUID);
        abraracourcix.createCharacter("Obélix", Sex.MALE, CharacterType.BLACKSMITH);
        abraracourcix.createCharacter("Panoramix", Sex.MALE, CharacterType.DRUID);
        abraracourcix.createCharacter("Assurancetourix", Sex.MALE, CharacterType.MERCHANT);
        abraracourcix.createCharacter("Bonemine", Sex.FEMALE, CharacterType.INNKEEPER);

        // Camp romain
        centurion.createCharacter("Marcus", Sex.MALE, CharacterType.LEGIONARY);
        centurion.createCharacter("Brutus", Sex.MALE, CharacterType.LEGIONARY);
        centurion.createCharacter("Cesar", Sex.MALE, CharacterType.GENERAL);

        // Ville romaine
        prefet.createCharacter("Claudius", Sex.MALE, CharacterType.PREFECT);
        prefet.createCharacter("Gracchus", Sex.MALE, CharacterType.LEGIONARY);

        // 5. Ajouter des aliments
        villageGaulois.addFood(FoodStats.newFood(FoodType.WILD_BOAR));
        villageGaulois.addFood(FoodStats.newFood(FoodType.WILD_BOAR));
        villageGaulois.addFood(FoodStats.newFood(FoodType.WINE));
        villageGaulois.addFood(FoodStats.newFood(FoodType.PASSABLE_FRESH_FISH));

        campRomain.addFood(FoodStats.newFood(FoodType.WILD_BOAR));
        campRomain.addFood(FoodStats.newFood(FoodType.HONEY));
        campRomain.addFood(FoodStats.newFood(FoodType.MEAD));
        campRomain.addFood(FoodStats.newFood(FoodType.WINE));

        villeRomaine.addFood(FoodStats.newFood(FoodType.WINE));
        villeRomaine.addFood(FoodStats.newFood(FoodType.HONEY));
        villeRomaine.addFood(FoodStats.newFood(FoodType.MEAD));

        // 6. Ingrédients pour potion magique
        villageGaulois.addFood(FoodStats.newFood(FoodType.MISTLETOE));
        villageGaulois.addFood(FoodStats.newFood(FoodType.CARROT));
        villageGaulois.addFood(FoodStats.newFood(FoodType.SALT));
        villageGaulois.addFood(FoodStats.newFood(FoodType.FRESH_FOUR_LEAF_CLOVER));
        villageGaulois.addFood(FoodStats.newFood(FoodType.ROCKFISH_OIL));
        villageGaulois.addFood(FoodStats.newFood(FoodType.HONEY));
        villageGaulois.addFood(FoodStats.newFood(FoodType.MEAD));
        villageGaulois.addFood(FoodStats.newFood(FoodType.SECRET_INGREDIENT));

        System.out.println("\n=== MONDE INITIALISÉ ===");
        System.out.println("Lieux créés : " + places.size());
        System.out.println("Chefs de clan : " + clanChiefs.size());
        System.out.println("Personnages totaux : " + getTotalCharacterCount());
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

    //RETOUR AU LIEU D'ORIGINE
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

    //MODIFICATION ALÉATOIRE

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

    //APPARITION ALIMENTS
    private void spawnFood() {
        for (Place p : places) {
            if (p instanceof Battlefield) continue;

            if (random.nextInt(100) < 30) {
                FoodType t = FoodType. values()[random.nextInt(FoodType.values().length)];
                p.addFood(FoodStats.newFood(t));
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