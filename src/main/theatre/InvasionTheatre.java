package main.theatre;

import main.interfaces.Leader;
import main.model.character.Character;
import main.model.clan_chief.ClanChief;
import main.interfaces.Fighter;
import main.model.place.*;
import main.model.items.food.Food;
import main.model.items.food.FoodStats;
import main.enums.*;
import main.model.place.category.*;
import main.model.place.category. PlaceWithClanChief;
import main.model.place.category.place_with_clan_chief.*;
import main.model.place.category.Battlefield;
import main. model.place.category.Enclosure;

import main.enums.Sex;
import java.util.Random;

import java.util.*;

public class InvasionTheatre {

    private final String name;
    private final int maxPlaces;
    private final List<Place> places = new ArrayList<>();
    private final List<ClanChief> clanChiefs = new ArrayList<>();
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
    /**
     * Initialise aléatoirement des personnages et des aliments dans les lieux avec chefs.
     */
    public void initializeRandomCharactersAndItems() {
        Random random = new Random();

        System.out.println("\n" + "=". repeat(60));
        System.out.println("       INITIALISATION AUTOMATIQUE DU MONDE");
        System.out.println("=".repeat(60));

        for (Place place : places) {
            if (place instanceof PlaceWithClanChief) {
                PlaceWithClanChief placeWithChief = (PlaceWithClanChief) place;
                ClanChief chief = placeWithChief.getClanChief();

                System.out.println("\n[Lieu] " + place.getName());
                System.out.println("  Chef :  " + (chief != null ? chief.getName() : "Aucun"));
                System.out.println("  " + "-".repeat(50));

                // Génération des personnages
                int charCount = random.nextInt(3) + 3; // Entre 3 et 5
                System.out.println("  [Personnages] Creation de " + charCount + " personnages :");

                List<Character> characters = new ArrayList<>();
                for (int i = 0; i < charCount; i++) {
                    Character character = generateRandomCharacter(place, i + 1);
                    if (place. canAccept(character)) {
                        place.getThe_characters_present().add(character);
                        character.modifyCurrentPlace(place);
                        characters.add(character);
                    }
                }

                // Affichage des personnages
                for (Character c : characters) {
                    System.out.println("     - " + c.getName() + " (" + c.getType() + ", " + c.getSex() + ")");
                }

                // Génération des aliments
                int foodCount = random.nextInt(14) + 12; // Entre 12 et 25
                System. out.println("  [Aliments] Ajout de " + foodCount + " aliments :");

                Map<FoodType, Integer> foodCounts = new LinkedHashMap<>();
                for (int i = 0; i < foodCount; i++) {
                    Food food = createRandomFood();
                    place.getThe_aliments_present().add(food);
                    foodCounts.put(food.getFoodType(), foodCounts.getOrDefault(food.getFoodType(), 0) + 1);
                }

                // Affichage des aliments groupés
                for (Map.Entry<FoodType, Integer> entry : foodCounts.entrySet()) {
                    System.out. println("     - " + entry. getKey() + " x " + entry.getValue());
                }
            }
        }

        System.out.println("\n" + "=".repeat(60));
    }

    /**
     * Génère un personnage aléatoire adapté au type de lieu.
     */
    private Character generateRandomCharacter(Place place, int index) {
        Random random = new Random();
        Sex sex = random.nextBoolean() ? Sex.MALE : Sex. FEMALE;

        // Noms aléatoires
        String[] gaulNames = {"Astérix", "Obélix", "Agecanonix", "Assurancetourix", "Abraracourcix",
                "Bonemine", "Falbala", "Panoramix", "Ordralfabétix", "Cétautomatix"};
        String[] romanNames = {"Julius", "Marcus", "Caius", "Brutus", "Claudius",
                "Tullius", "Priscus", "Maximus", "Aurelius", "Flavius"};

        String baseName;
        CharacterType type;

        if (place instanceof GallicVillage || place instanceof GalloRomanSettlement) {
            // Lieu gaulois ou mixte
            baseName = gaulNames[random.nextInt(gaulNames.length)];
            if (index > 1) baseName += " " + index;

            CharacterType[] gaulTypes = {CharacterType. DRUID, CharacterType.BLACKSMITH,
                    CharacterType.MERCHANT, CharacterType.INNKEEPER};
            type = gaulTypes[random.nextInt(gaulTypes.length)];

            return switch (type) {
                case DRUID -> new main.model.character.category.gaul.job.Druid(baseName, sex);
                case BLACKSMITH -> new main.model.character. category.gaul.job. Blacksmith(baseName, sex);
                case MERCHANT -> new main.model.character.category.gaul.job.Merchant(baseName, sex);
                case INNKEEPER -> new main.model. character.category.gaul.job.Innkeeper(baseName, sex);
                default -> new main.model.character.category.gaul.job.Druid(baseName, sex);
            };
        } else {
            // Lieu romain
            baseName = romanNames[random.nextInt(romanNames.length)];
            if (index > 1) baseName += " " + index;

            CharacterType[] romanTypes = {CharacterType.LEGIONARY, CharacterType.GENERAL, CharacterType.PREFECT};
            type = romanTypes[random.nextInt(romanTypes.length)];

            return switch (type) {
                case LEGIONARY -> new main. model.character.category.roman. job.Legionary(baseName, sex);
                case GENERAL -> new main.model.character. category.roman.job.General(baseName, sex);
                case PREFECT -> new main.model.character.category.roman.job.Prefect(baseName, sex);
                default -> new main.model.character.category.roman.job.Legionary(baseName, sex);
            };
        }
    }


    /**
     * Initialise des lieux sans chefs (champs de bataille et enclos) avec des personnages et objets.
     */
    public void initializePlacesWithoutChiefs() {
        Random random = new Random();

        System.out.println("\n" + "=". repeat(60));
        System.out.println("GENERATION DES LIEUX AUTONOMES");
        System.out.println("=".repeat(60));

        // Créer 2-3 champs de bataille (VIDES au départ)
        int battlefieldCount = random.nextInt(2) + 2;
        for (int i = 1; i <= battlefieldCount; i++) {
            System.out.println("\n[Lieu] Champ de bataille " + i);
            System.out.println("  Type :  Champ de bataille ");
            System.out.println("  " + "-".repeat(50));
            System.out.println("  [Statut] Vide");

            Battlefield battlefield = new Battlefield("Champ de bataille " + i, random.nextInt(500) + 1000);
            this.addPlace(battlefield);
        }

        // Créer 1-2 enclos
        int enclosureCount = random.nextInt(2) + 1;
        for (int i = 1; i <= enclosureCount; i++) {
            System.out.println("\n[Lieu] Enclos " + i);
            System.out. println("  Type : Enclos (accepte uniquement les creatures fantastiques)");
            System.out.println("  " + "-".repeat(50));

            Enclosure enclosure = new Enclosure("Enclos " + i, random.nextInt(300) + 500);

            // Ajouter des personnages (3-5 créatures uniquement)
            int charCount = random.nextInt(3) + 3;
            System. out.println("  [Personnages] Creation de " + charCount + " creatures :");

            List<Character> creatures = new ArrayList<>();
            for (int j = 1; j <= charCount; j++) {
                Character character = createCreatureCharacter("Creature-" + i + "-" + j, enclosure, random);
                if (enclosure.canAccept(character)) {
                    // Ajouter sans affichage
                    enclosure.getThe_characters_present().add(character);
                    character.modifyCurrentPlace(enclosure);
                    creatures. add(character);
                }
            }

            // Afficher toutes les créatures proprement
            for (Character c : creatures) {
                System.out.println("     - " + c.getName() + " (" + c.getType() + ", " + c.getSex() + ")");
            }

            // Ajouter des aliments pour les enclos (5-10 aliments)
            int foodCount = random.nextInt(6) + 5;
            System.out. println("  [Aliments] Ajout de " + foodCount + " aliments :");

            Map<FoodType, Integer> foodCounts = new LinkedHashMap<>();
            for (int j = 0; j < foodCount; j++) {
                Food food = createRandomFood();
                // Ajouter sans affichage
                enclosure.getThe_aliments_present().add(food);
                foodCounts. put(food.getFoodType(), foodCounts.getOrDefault(food.getFoodType(), 0) + 1);
            }

            // Afficher les aliments groupés proprement
            for (Map.Entry<FoodType, Integer> entry : foodCounts.entrySet()) {
                System.out. println("     - " + entry. getKey() + " x " + entry.getValue());
            }

            this.addPlace(enclosure);
        }

        System.out.println("\n Generation terminee : " + battlefieldCount + " champ(s) de bataille et " + enclosureCount + " enclos cree(s)");
    }

    /**
     * Crée une créature fantastique pour un enclos (uniquement créatures).
     */
    private Character createCreatureCharacter(String baseName, Place place, Random random) {
        Sex sex = random.nextBoolean() ? Sex.MALE : Sex.FEMALE;
        return new main.model.character.category. creature.species.Werewolf(baseName, sex);
    }

    /**
     * Crée un aliment aléatoire.
     */
    private Food createRandomFood() {
        Random random = new Random();
        FoodType[] foodTypes = FoodType. values();
        FoodType randomType = foodTypes[random.nextInt(foodTypes.length)];
        return FoodStats.newFood(randomType);
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

        // Compter les combattants vivants
        int aliveG1 = 0;
        int aliveG2 = 0;
        for (Character c :  g1) if (c.isAlive()) aliveG1++;
        for (Character c : g2) if (c.isAlive()) aliveG2++;

        if (aliveG1 == 0 || aliveG2 == 0) return;

        System.out. println("\n[COMBAT] Dans " + battlefield.getName());
        System.out.println("  Gaulois :  " + aliveG1 + " | Romains : " + aliveG2);
        System.out.println("  " + "-".repeat(50));

        // ===== PHASE 1 : Les Gaulois attaquent =====
        for (Character attacker : g1) {
            if (! attacker.isAlive()) continue;

            Character target = null;
            for (Character potential :  g2) {
                if (potential.isAlive()) {
                    target = potential;
                    break;
                }
            }

            if (target != null) {
                if(attacker instanceof Fighter) {
                    ((Fighter) attacker).combat(target);
                } else if(attacker instanceof Leader){
                    ((Leader) attacker).lead(g1);
                    attacker.fight(target);
                } else {
                    attacker.fight(target);
                }
            }
        }

        System.out.println();

        // ===== PHASE 2 : Les Romains ripostent =====
        for (Character attacker : g2) {
            if (!attacker.isAlive()) continue;

            Character target = null;
            for (Character potential : g1) {
                if (potential. isAlive()) {
                    target = potential;
                    break;
                }
            }

            if (target != null) {
                if(attacker instanceof Fighter) {
                    ((Fighter) attacker).combat(target);
                } else if(attacker instanceof Leader){
                    ((Leader) attacker).lead(g2);
                    attacker.fight(target);
                } else {
                    attacker.fight(target);
                }
            }
        }

        System.out.println("  " + "-".repeat(50));
    }

    //retour au lieu d'origine
    private void returnToOriginalPlaces() {
        System.out.println("\n=== RETOUR DES COMBATTANTS ===");

        for (Place battlefield : places) {
            if (!(battlefield instanceof Battlefield)) continue;

            List<Character> toReturn = new ArrayList<>(battlefield.getThe_characters_present());

            for (Character c : toReturn) {
                // Si le personnage est mort, le retirer du champ de bataille
                if (! c.isAlive()) {
                    battlefield.getThe_characters_present().remove(c);
                    System.out.println(c.getName() + " est mort et reste sur le champ de bataille");
                    continue;
                }

                // Récupérer le lieu d'origine
                Place origin = c.getOriginPlace();

                if (origin != null) {
                    // Retirer du champ de bataille
                    battlefield.getThe_characters_present().remove(c);

                    // Remettre dans le lieu d'origine
                    origin.getThe_characters_present().add(c);
                    c. modifyCurrentPlace(origin);
                    c.setOriginPlace(null); // Réinitialiser

                    System.out.println(c.getName() + " retourne à " + origin.getName());
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

    //VIEILLISSEMENT ET MODIFICATIONS ALEATOIRES DES ALIMENTS

    private void ageFood() {
        System.out.println("\n=== VIEILLISSEMENT ET MODIFICATIONS ALEATOIRES DES ALIMENTS ===");

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