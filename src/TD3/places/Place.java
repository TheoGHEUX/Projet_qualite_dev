package TD3.places;

import TD3.characters.Character;
import TD3.food.Food;
import java.util.ArrayList;

//lieu
public abstract class Place {
    protected String name;
    protected int surface; //superficie
    protected ArrayList<Character> the_characters_present; // à modifier
    protected ArrayList<Food> the_aliments_present; // à modifier

    public Place(String name, int surface) {
        this.name = name;
        this.surface = surface;
        this.the_characters_present = new ArrayList<>();
        this.the_aliments_present = new ArrayList<>();
    }

    public abstract boolean canAccept(Character character);

    //Getters
    public String getName() {
        return name;
    }

    public int getSurface() {
        return surface;
    }

    //maxCapacity a implementer

    public ArrayList<Character> getThe_characters_present() {
        return the_characters_present;
    }
    public ArrayList<Food> getThe_aliments_present() {
        return the_aliments_present;
    }

    public void showPlaceInfos() {
        System.out.println("=== " + name + " ===");
        System. out.println("Surface: " + surface + " m² | Capacité: " + getNumberOfCharacters());

        if (this instanceof Place_with_clan_chief) {
            System.out.println("Chef: " + ((Place_with_clan_chief) this).getClanChief());
        }

        System.out.println("\nPersonnages (" + getNumberOfCharacters() + ") :");
        if (the_characters_present.isEmpty()) {
            System.out.println("  (aucun)");
        } else {
            for (Character c : the_characters_present) {
                System.out.println("  - " + c.getName() + " (" + c.getType() + ") - " + c.getHealth() + " PV"); //pour affichage clair
            }
        }

        System.out.println("\nAliments (" + getNumberOfFood() + ") :");
        if (the_aliments_present.isEmpty()) {
            System.out.println("  (aucun)");
        } else {
            for (Food f : the_aliments_present) {
                System.out.println("  - " + f.getFoodType() + " (+" + f.getHealthEffect() + " PV)"); //pour affichage clair
            }
        }
        System.out.println();
    }


    public int getNumberOfCharacters() {
        return the_characters_present.size();
    }

    public int getNumberOfFood() {
        return the_aliments_present.size();
    }

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
        System.out.println(character. getName() + " entre dans " + this.name);
        return true;
    }

    public boolean removeCharacter(Character character) {
        if (the_characters_present.remove(character)) {
            System.out.println(character.getName() + " quitte " + this.name);
            return true;
        }
        System.out.println(character.getName() + " n'est pas dans " + this.name);
        return false;
    }

    public boolean containsCharacter(Character character) {
        return the_characters_present. contains(character);
    }






}
