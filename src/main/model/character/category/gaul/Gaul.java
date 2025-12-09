package main.model.character.category.gaul;

import main.model.character.Character;
import main.enums.Sex;

public abstract class Gaul extends Character {
    /**
     * Crée un nouveau gaulois.
     * @param name
     * @param sex
     * @param size
     * @param age
     * @param strength
     * @param stamina
     * @param health
     */
    public Gaul(String name, Sex sex, int size, int age, double strength, int stamina, double health) {
        super(name, sex, size, age, strength, stamina, health, null);
        this.nationality = "Gaul";
    }
}
