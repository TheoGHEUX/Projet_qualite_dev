package main. model. clan_chief;

import main.model.character.Character;
import main.model.character.category.creature.species.Werewolf;
import main.model.character.category.gaul.job.Blacksmith;
import main.model. character.category.gaul.job.Druid;
import main.model.character.category.gaul.job.Innkeeper;
import main.model. character.category.gaul.job.Merchant;
import main. model.character.category.roman.job.General;
import main.model.character.category.roman.job. Legionary;
import main.model.character.category.roman.job. Prefect;
import main.enums.CharacterType;
import main.enums. Sex;
import main.enums.FoodType;
import main.model.items.food.Food;
import main.model.place.category. Battlefield;
import main.model. place.category.Enclosure;
import main.model.place. Place;
import main.model.place.category.PlaceWithClanChief;
import main.model.items.potion.Potion;
import main.theatre.InvasionTheatre;

import java.util.*;

public class ClanChief {
    private final String name;
    private int age;
    private final Sex sex;
    private PlaceWithClanChief place;
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Crée un nouveau chef de clan.
     * @param name
     * @param sex
     */
    public ClanChief(String name, Sex sex) {
        this.name = name;
        this.age = new Random().nextInt(61) + 30;
        this.place = null;
        this.sex = sex;
    }

    /**
     * Le chef de clan affiche les informations de son lieu s'il en a un.
     */
    public void examinePlace() {
        if (place == null) {
            System. out.println(name + " n'a pas de lieu à examiner");
            return;
        }
        place.showInfos();
    }

    /**
     * Le chef de clan crée un personnage dans son lieu s'il en a un.
     * @param name
     * @param sex
     * @param type
     */
    public void createCharacter(String name, Sex sex, CharacterType type) {
        if (place == null) {
            System.out.println("Le chef " + this.name + " n'est relié à aucun lieu :  impossible de créer un personnage.");
            return;
        }
        Character newCharacter;
        switch (type) {
            case WEREWOLF:
                newCharacter = new Werewolf(name, sex);
                break;
            case BLACKSMITH:
                newCharacter = new Blacksmith(name, sex);
                break;
            case DRUID:
                newCharacter = new Druid(name, sex);
                break;
            case INNKEEPER:
                newCharacter = new Innkeeper(name, sex);
                break;
            case MERCHANT:
                newCharacter = new Merchant(name, sex);
                break;
            case GENERAL:
                newCharacter = new General(name, sex);
                break;
            case LEGIONARY:
                newCharacter = new Legionary(name, sex);
                break;
            case PREFECT:
                newCharacter = new Prefect(name, sex);
                break;
            default:
                System.out.println("Type de personnage inconnu :  " + type);
                return;
        }

        if (! place.canAccept(newCharacter)) {
            System.out.println("Le nouveau personnage " + newCharacter.getName() + " a été créé mais n'est pas autorisé à entrer dans votre lieu !  Il est parti :(");
            return;
        }
        place.addCharacter(newCharacter);
    }

    /**
     * Menu interactif pour créer un personnage.
     * @return true si l'action a été effectuée avec succès
     */
    public boolean createCharacterInteractive() {
        if (place == null) {
            System.out.println("Le chef " + this.name + " n'est relié à aucun lieu.");
            return false;
        }

        // Choix du nom avec validation
        String name = "";
        while (name.trim().isEmpty()) {
            System. out.print("Nom du personnage : ");
            name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("[ERREUR] Le nom ne peut pas être vide !  Veuillez entrer un nom.");
            }
        }

        // Choix du sexe avec validation
        Sex sex = null;
        while (sex == null) {
            System.out.println("Sexe (1:  MALE, 2: FEMALE) : ");
            int sexChoice = getIntInput();

            if (sexChoice == 1) {
                sex = Sex. MALE;
            } else if (sexChoice == 2) {
                sex = Sex.FEMALE;
            } else {
                System.out.println("[ERREUR] Choix invalide !  Veuillez choisir 1 ou 2.");
            }
        }

        // Choix du type de personnage avec validation
        CharacterType type = null;
        while (type == null) {
            System.out.println("Type de personnage :");
            System.out.println("1. DRUID");
            System.out. println("2. BLACKSMITH");
            System.out. println("3. MERCHANT");
            System.out.println("4. INNKEEPER");
            System.out. println("5. LEGIONARY");
            System.out. println("6. PREFECT");
            System.out.println("7. GENERAL");
            System.out.println("8. WEREWOLF");
            System.out.print("Votre choix : ");

            int typeChoice = getIntInput();

            type = switch (typeChoice) {
                case 1 -> CharacterType. DRUID;
                case 2 -> CharacterType. BLACKSMITH;
                case 3 -> CharacterType.MERCHANT;
                case 4 -> CharacterType.INNKEEPER;
                case 5 -> CharacterType.LEGIONARY;
                case 6 -> CharacterType.PREFECT;
                case 7 -> CharacterType.GENERAL;
                case 8 -> CharacterType. WEREWOLF;
                default -> {
                    System.out.println("[ERREUR] Choix invalide ! Veuillez choisir un nombre entre 1 et 8.");
                    yield null;
                }
            };
        }

        createCharacter(name, sex, type);
        return true;
    }

    /**
     * Le chef de clan nourrit les personnes dans son lieu s'il en a un, si possible.
     */
    public void feedCharacters() {
        if (this.place == null) {
            System.out.println("Le chef " + name + " n'est associé à aucun lieu.");
            return;
        }

        if (place.getThe_characters_present().isEmpty()) {
            System.out.println("Il n'y a aucun personnage à nourrir dans " + place. getName() + ".");
            return;
        }

        if (place.getThe_aliments_present().isEmpty()) {
            System.out.println("Il n'y a aucune nourriture disponible dans " + place.getName() + ".");
            return;
        }

        for (Character character : place.getThe_characters_present()) {

            if (! character.isAlive()) {
                System.out.println(character.getName() + " est mort, il ne peut pas manger.");
                continue;
            }

            if (place.getThe_aliments_present().isEmpty()) {
                System.out. println("Il n'y a plus de nourriture disponible.");
                return;
            }

            Food food = place.getThe_aliments_present().removeFirst();
            character.eat(food);

            System.out.println(character.getName() + " a mangé " + food.getFoodType());
        }
    }

    /**
     * Menu interactif pour nourrir les personnages.
     * @return true si l'action a été effectuée avec succès
     */
    public boolean feedCharactersInteractive() {
        if (place == null) {
            System.out.println("Le chef n'a pas de lieu.");
            return false;
        }

        List<Character> characters = place.getThe_characters_present();
        if (characters.isEmpty()) {
            System.out.println("Aucun personnage à nourrir.");
            return false;
        }

        if (place.getThe_aliments_present().isEmpty()) {
            System. out.println("Aucun aliment disponible pour nourrir.");
            return false;
        }

        // Menu de choix du mode
        System.out.println("\n=== MODE DE NOURRISSAGE ===");
        System.out.println("1. Nourrir automatiquement (tous les personnages consomment 1 aliment aléatoirement)");
        System.out.println("2. Nourrir manuellement (choisir personnages et aliments)");
        System.out.println("0. Annuler");
        System.out.print("Votre choix : ");

        int mode = getIntInput();

        if (mode == 1) {
            feedCharacters();
            return true;
        } else if (mode == 2) {
            return feedManually();
        } else {
            System.out.println("Nourrissage annulé.");
            return false;
        }
    }

    /**
     * Nourrit manuellement les personnages.
     * @return true si au moins un personnage a été nourri
     */
    private boolean feedManually() {
        boolean atLeastOneFed = false;
        boolean continueFeeding = true;

        while (continueFeeding) {
            List<Character> characters = place.getThe_characters_present();
            List<Food> foods = place.getThe_aliments_present();

            if (foods.isEmpty()) {
                System. out.println("\nPlus d'aliments disponibles !");
                break;
            }

            displayCharactersWithStats(characters);

            System.out.println("\n0. Terminer le nourrissage manuel");
            System.out.print("Choisir un personnage à nourrir : ");
            int charIndex = getIntInput();

            if (charIndex == 0) {
                continueFeeding = false;
                System.out.println("Nourrissage manuel terminé.");
                continue;
            }

            int realCharIndex = charIndex - 1;

            if (realCharIndex < 0 || realCharIndex >= characters.size()) {
                System. out. println("[ERREUR] Index invalide.");
                continue;
            }

            Character selectedChar = characters.get(realCharIndex);

            if (! selectedChar.isAlive()) {
                System.out.println("[ERREUR] " + selectedChar.getName() + " est mort, impossible de le nourrir.");
                continue;
            }

            int realFoodIndex = displayAndSelectFood(foods);
            if (realFoodIndex == -1) continue;

            Food selectedFood = foods.get(realFoodIndex);
            selectedChar.eat(selectedFood);
            foods.remove(realFoodIndex);

            System.out.println("\n[OK] " + selectedChar.getName() + " a mangé " + selectedFood.getFoodType());
            System.out.println("  Nouvel état : PV:  " + (int) selectedChar.getHealth() +
                    " | Energie: " + selectedChar.getStamina() +
                    " | Faim: " + selectedChar.getHunger());

            atLeastOneFed = true;
        }

        return atLeastOneFed;
    }

    /**
     * Le chef de clan soigne tous les personnages de son lieu au maximum de leurs points de vie.
     * @return true si au moins un personnage a été soigné
     */
    public boolean healCharacters() {
        if (this. place == null) {
            System.out.println("Le chef " + name + " n'est associé à aucun lieu.");
            return false;
        }

        if (place.getThe_characters_present().isEmpty()) {
            System.out.println("Il n'y a aucun personnage à soigner dans " + place.getName() + ".");
            return false;
        }

        System.out.println("\n=== SOINS DES PERSONNAGES ===");
        boolean atLeastOneHealed = false;

        for (Character character :  place.getThe_characters_present()) {
            if (! character.isAlive()) {
                System.out.println(character.getName() + " est mort, impossible de le soigner.");
                continue;
            }

            double currentHealth = character.getHealth();
            double maxHealth = character.getMaxHealth();

            if (currentHealth >= maxHealth) {
                System. out.println(character.getName() + " a déjà tous ses points de vie (PV:  " + (int)currentHealth + "/" + (int)maxHealth + ")");
            } else {
                double healthToRestore = maxHealth - currentHealth;
                character.updateHealth(healthToRestore);
                System.out.println("[OK] " + character.getName() + " a été soigné :  PV " + (int)currentHealth + " → " + (int)character.getHealth());
                atLeastOneHealed = true;
            }
        }

        return atLeastOneHealed;
    }

    /**
     * Affiche les personnages avec leurs statistiques.
     */
    private void displayCharactersWithStats(List<Character> characters) {
        System.out. println("\n" + "=". repeat(60));
        System.out.println("PERSONNAGES DISPONIBLES");
        System.out.println("=".repeat(60));

        for (int i = 0; i < characters.size(); i++) {
            Character c = characters.get(i);
            String status = c.isAlive() ? "Vivant" : "Mort";
            System.out.println((i + 1) + ". " + c.getName() +
                    " | PV:  " + (int) c.getHealth() + "/" + (int) c.getMaxHealth() +
                    " | Energie: " + c.getStamina() + "/" + c.getMaxStamina() +
                    " | Faim: " + c.getHunger() + "/100" +
                    " | " + status);
        }
    }

    /**
     * Affiche les aliments et permet d'en sélectionner un.
     * @return l'index réel de l'aliment sélectionné, ou -1 si invalide
     */
    private int displayAndSelectFood(List<Food> foods) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ALIMENTS DISPONIBLES");
        System.out.println("=".repeat(60));

        // Grouper les aliments par type
        Map<FoodType, List<Integer>> foodByType = new HashMap<>();
        for (int i = 0; i < foods.size(); i++) {
            FoodType type = foods.get(i).getFoodType();
            foodByType.computeIfAbsent(type, k -> new ArrayList<>()).add(i);
        }

        int displayIndex = 1;
        Map<Integer, Integer> displayToRealIndex = new HashMap<>();

        for (Map.Entry<FoodType, List<Integer>> entry : foodByType.entrySet()) {
            FoodType foodType = entry.getKey();
            int count = entry.getValue().size();
            int firstIndex = entry.getValue().get(0);

            Food sampleFood = foods.get(firstIndex);
            System.out.println(displayIndex + ". " + foodType + " (x" + count + ")" +
                    " | Vie: +" + sampleFood.getHealthEffect() +
                    " | Faim: +" + sampleFood.getHungerEffect() +
                    " | Energie: +" + sampleFood.getStaminaEffect());

            displayToRealIndex.put(displayIndex, firstIndex);
            displayIndex++;
        }

        System.out. print("\nChoisir un aliment : ");
        int foodChoice = getIntInput();

        if (! displayToRealIndex.containsKey(foodChoice)) {
            System.out.println("[ERREUR] Choix invalide.");
            return -1;
        }

        return displayToRealIndex.get(foodChoice);
    }

    /**
     * Le chef de clan demande à un druide de préparer une potion magique pour son lieu s'il en a un, si le lieu accepte les gaulois et possède un druide.
     * @return true si l'action a été effectuée avec succès
     */
    public boolean askDruidToMakePotion() {
        if (place == null) {
            System.out.println("Le chef " + name + " n'est associé à aucun lieu !");
            return false;
        }
        if (! place.isGallo()) {
            System.out.println(place.getName() + " n'autorise pas l'accès aux gaulois, il ne peut donc pas y avoir de druide !");
            return false;
        }

        Druid druid = null;

        for (Character character : place.getThe_characters_present()) {
            if (character instanceof Druid) {
                druid = (Druid) character;
                break;
            }
        }

        if (druid == null) {
            System.out.println("Aucun druide présent dans " + place.getName() + ".");
            return false;
        }

        if (!place.hasEnoughToMakeAMagicPotion()) {
            System.out.println("Il n'y a pas assez d'ingrédients pour produire une potion magique dans " + place.getName() + ".");
            return false;
        }

        druid.work();

        System.out.println("Le chef " + name + " a demandé au druide " + druid.getName() + " de préparer une potion magique dans " + place.getName() + ".");
        return true;
    }

    /**
     * Le chef de clan fait boire de la potion magique aux personnes de son lieu s'il en a un, si possible.
     * @param potion
     */
    public void makeCharactersDrinkMagicPotion(Potion potion) {
        if (place == null) {
            System.out.println("Le chef " + name + " n'est associé à aucun lieu, impossible de donner une potion.");
            return;
        }

        if (potion == null || potion.isEmpty()) {
            System.out. println("La potion est vide ou inexistante !");
            return;
        }

        for (Character character : place.getThe_characters_present()) {
            if (! character.isAlive()) {
                System.out.println(character.getName() + " est mort, il ne peut pas boire la potion.");
                continue;
            }

            System.out.println("Le chef " + name + " fait boire une potion magique à " + character.getName() + " !");
            character.drinkMagicPotion(potion);
        }
    }

    /**
     * Menu interactif pour donner une potion.
     * @return true si l'action a été effectuée avec succès
     */
    public boolean givePotionInteractive() {
        if (place == null || place.getPotion_present().isEmpty()) {
            System.out.println("Aucune potion disponible.");
            return false;
        }

        makeCharactersDrinkMagicPotion(place.getPotion_present().get(0));
        return true;
    }

    /**
     * Le chef de clan transfert un personnage de son lieu s'il en a un, vers un champ de bataille ou un enclos.
     * @param character
     * @param newPlace
     */
    public void transferCharacter(Character character, Place newPlace) {
        if (this.place == null) {
            System.out.println("Le chef " + this.name + " n'est associé à aucun lieu !");
            return;
        }

        if (character == null || ! this.place.containsCharacter(character)) {
            System.out.println(character.getName() + " n'est pas dans " + this.place.getName());
            return;
        }

        if (!(newPlace instanceof Battlefield || newPlace instanceof Enclosure)) {
            System.out. println("Le lieu de destination doit être un champ de bataille ou un enclos !");
            return;
        }

        if (! newPlace.canAccept(character)) {
            System. out.println(character.getName() + " ne peut pas entrer dans " + newPlace.getName());
            return;
        }

        // IMPORTANT : Sauvegarder le lieu d'origine
        character.setOriginPlace(this.place);

        // Retirer sans affichage
        this.place.getThe_characters_present().remove(character);

        // Ajouter sans affichage
        newPlace. getThe_characters_present().add(character);
        character.modifyCurrentPlace(newPlace);

        System.out.println(character.getName() + " a été transféré de " + this. place.getName() + " vers " + newPlace.getName());
    }

    /**
     * Menu interactif pour transférer un personnage.
     * @return true si l'action a été effectuée avec succès
     */
    public boolean transferCharacterInteractive(InvasionTheatre theatre) {
        if (place == null) {
            System.out.println("Le chef n'a pas de lieu.");
            return false;
        }

        List<Character> characters = place.getThe_characters_present();
        if (characters.isEmpty()) {
            System.out.println("Aucun personnage à transférer.");
            return false;
        }

        // Affichage des personnages
        System.out.println("\nPersonnages :");
        for (int i = 0; i < characters.size(); i++) {
            System.out.println((i + 1) + ". " + characters.get(i).getName());
        }
        System.out.println("0. Annuler");
        System.out.print("Choisir un personnage : ");
        int charIndex = getIntInput();

        if (charIndex == 0) {
            System.out.println("Transfert annulé.");
            return false;
        }

        if (charIndex < 1 || charIndex > characters.size()) {
            System. out.println("[ERREUR] Index invalide.");
            return false;
        }

        // Affichage des destinations
        System.out.println("\nDestinations possibles :");
        List<Place> destinations = theatre.getPlaces().stream()
                .filter(p -> p instanceof Battlefield || p instanceof Enclosure)
                .toList();

        for (int i = 0; i < destinations. size(); i++) {
            System.out.println((i + 1) + ". " + destinations.get(i).getName());
        }
        System.out. println("0. Annuler");
        System.out.print("Choisir une destination :  ");
        int destIndex = getIntInput();

        if (destIndex == 0) {
            System.out.println("Transfert annulé.");
            return false;
        }

        if (destIndex >= 1 && destIndex <= destinations. size()) {
            transferCharacter(characters.get(charIndex - 1), destinations.get(destIndex - 1));
            return true;
        } else {
            System.out. println("[ERREUR] Index invalide.");
            return false;
        }
    }

    /**
     * Lit et valide une entrée entière.
     */
    private int getIntInput() {
        while (! scanner.hasNextInt()) {
            System.out.print("Entrez un nombre valide : ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    // Getters et Setters
    public PlaceWithClanChief getPlace() {
        return place;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Sex getSex() {
        return sex;
    }

    public void setPlace(PlaceWithClanChief place) {
        this.place = place;
    }

    public void clearPlace() {
        this.place = null;
    }

    public void showInfos() {
        if (this.getPlace() != null) {
            System.out.println("[INFOS] Chef de clan:  " + name + " | Age: " + age + " | Sex: " + sex + " | Place: " + place.getName());
            return;
        }
        System. out.println("[INFOS] Chef de clan: " + name + " | Age: " + age + " | Sex: " + sex + " | Place: aucune");
    }
}