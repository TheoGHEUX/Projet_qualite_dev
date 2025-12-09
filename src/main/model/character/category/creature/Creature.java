package main.model.character.category.creature;

import main.model.character.Character;
import main.enums.Sex;

public class Creature extends Character {

    /**
     * Crée une nouvelle créature.
     * @param name
     * @param sex
     * @param size
     * @param age
     * @param strength
     * @param stamina
     * @param health
     */
    public Creature(String name, Sex sex, int size, int age, int strength, int stamina, double health) {
        super(name, sex, size, age, strength, stamina, health, null);
        this.nationality = "Creature";
    }


}
