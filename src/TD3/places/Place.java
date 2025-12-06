package TD3.places;

import TD3.characters.Character;
import TD3.food.Food;
import java.util.ArrayList;
import java.util.List;

//lieu
public abstract class Place {
    protected String name;
    protected int surface; //superficie
    protected List<Character> the_characters_present;
    protected List<Food> the_aliments_present;

    public Place(String name, int surface) {
        this.name = name;
        this.surface = surface;
        this.the_characters_present = new ArrayList<>();
        this.the_aliments_present = new ArrayList<>();
    }

    public abstract boolean canAccept(Character character);

    //GESTION DES PERSONNAGES

    /**
     * Ajoute un personnage au lieu s'il peut y entrer
     * @param character le personnage à ajouter
     * @return true si ajouté avec succès, false sinon
     */
    public boolean addCharacter(Character character) {
        if (character == null) {
            System.out.println("Erreur : personnage null");
            return false;
        }

        if (! canAccept(character)) {
            System.out.println(character.getName() + " ne peut pas entrer dans " + this.name);
            return false;
        }

        the_characters_present.add(character);
        character.modifyCurrentPlace(this);
        System.out.println(character.getName() + " entre dans " + this.name);
        return true;
    }

    /**
     * Retire un personnage du lieu
     * @param character le personnage à retirer
     * @return true si retiré, false sinon
     */
    public boolean removeCharacter(Character character) {
        if (the_characters_present.remove(character)) {
            System.out. println(character.getName() + " quitte " + this.name);
            character.modifyCurrentPlace(null);
            return true;
        }
        System.out.println(character.getName() + " n'est pas dans " + this.name);
        return false;
    }

    /**
     * Vérifie si un personnage est présent dans le lieu
     */
    public boolean containsCharacter(Character character) {
        return the_characters_present.contains(character);
    }

    //GESTION DU SOINS

    /**
     * Soigne un personnage spécifique d'un montant donné
     * @param character le personnage à soigner
     * @param healAmount la quantité de vie à restaurer
     * @return true si soigné, false si le personnage n'est pas présent
     */
    public boolean healCharacter(Character character, int healAmount) {
        if (! containsCharacter(character)) {
            System.out.println(character.getName() + " n'est pas dans " + this.name);
            return false;
        }

        if (! character.isAlive()) {
            System.out.println(character. getName() + " est mort, impossible de le soigner");
            return false;
        }

        double oldHealth = character.getHealth();
        character.updateHealth(healAmount);
        double newHealth = character.getHealth();

        System.out.println(character.getName() + " a été soigné de " + (newHealth - oldHealth) + " PV dans " + this.name);
        return true;
    }


    //GESTION DU NOURRICEMENT

    /**
     * Nourrit un personnage avec un aliment spécifique du lieu
     * @param character le personnage à nourrir
     * @param foodIndex l'index de l'aliment dans la liste
     * @return true si nourri avec succès, false sinon
     */
    public boolean feedCharacter(Character character, int foodIndex) {
        // Vérifier que le personnage est présent
        if (!containsCharacter(character)) {
            System.out.println(character.getName() + " n'est pas dans " + this.name);
            return false;
        }

        // Vérifier que le personnage est vivant
        if (!character.isAlive()) {
            System.out.println(character.getName() + " est mort, impossible de le nourrir");
            return false;
        }

        // Vérifier qu'il y a de la nourriture
        if (the_aliments_present.isEmpty()) {
            System.out.println("Aucune nourriture disponible dans " + this.name);
            return false;
        }

        // Vérifier l'index
        if (foodIndex < 0 || foodIndex >= the_aliments_present.size()) {
            System.out.println("Index de nourriture invalide");
            return false;
        }

        // Nourrir le personnage
        Food food = the_aliments_present.get(foodIndex);
        System.out.println(character.getName() + " mange " + food.getFoodType() + " dans " + this.name);
        character.eat(food);

        // Retirer l'aliment consommé
        the_aliments_present.remove(foodIndex);

        return true;
    }




    /**
     * Vérifie s'il y a des personnages ayant faim (< 100)
     */
    private boolean hasHungryCharacters() {
        for (Character c : the_characters_present) {
            if (c.isAlive() && c.getHunger() < 100) {
                return true;
            }
        }
        return false;
    }

    //GESTION DES ALIMENTS

    /**
     * Ajoute un aliment au lieu
     */
    public boolean addFood(Food food) {
        if (food == null) {
            System. out.println("Erreur : aliment null");
            return false;
        }
        the_aliments_present.add(food);
        System.out. println(food.getFoodType() + " ajouté à " + this.name);
        return true;
    }

    /**
     * Retire un aliment du lieu
     */
    public boolean removeFood(int index) {
        if (index < 0 || index >= the_aliments_present.size()) {
            System.out.println("Index invalide");
            return false;
        }
        Food removed = the_aliments_present. remove(index);
        System. out.println(removed.getFoodType() + " retiré de " + this.name);
        return true;
    }

    //GETTERS

    public String getName() {
        return name;
    }

    public int getSurface() {
        return surface;
    }

    public List<Character> getThe_characters_present() { return the_characters_present;
    }

    public List<Food> getThe_aliments_present() {
        return the_aliments_present;
    }

    public int getNumberOfCharacters() {
        return the_characters_present.size();
    }

    public int getNumberOfFood() {
        return the_aliments_present.size();
    }

    // AFFICHAGE

    public void showPlaceInfos() {
        System.out.println("=== " + name + " ===");
        System.out.println("Surface: " + surface + " m² | Personnages: " + getNumberOfCharacters() + " | Aliments: " + getNumberOfFood());

        if (this instanceof Place_with_clan_chief) {
            if(((Place_with_clan_chief) this).getClanChief() != null) {
                System.out. println("Chef: " + ((Place_with_clan_chief) this).getClanChief().getName());
            }
            else{
                System.out.println("Chef: pas de chef");
            }
        }

        System.out.println("\nPersonnages (" + getNumberOfCharacters() + ") :");
        if (the_characters_present.isEmpty()) {
            System.out. println("  (aucun)");
        } else {
            for (Character c : the_characters_present) {
                System.out.println("  - " + c.getName() + " (" + c.getType() + ") - "
                        + c.getHealth() + "/" + c.getMaxHealth() + " PV | Faim: " + c.getHunger() + "%");
            }
        }

        System.out.println("\nAliments (" + getNumberOfFood() + ") :");
        if (the_aliments_present.isEmpty()) {
            System.out.println("  (aucun)");
        } else {
            for (Food f : the_aliments_present) {
                System.out.println("  - " + f.getFoodType() + " (+" + f.getHealthEffect() + " PV, +"
                        + f.getHungerEffect() + " faim)");
            }
        }
        System.out.println();
    }
}