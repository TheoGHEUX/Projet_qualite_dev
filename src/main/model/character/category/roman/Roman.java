package main.model.character.category.roman;

import main.model.character.Character;
import main.enums.Sex;

public abstract class Roman extends Character {
    /**
     * Crée un nouveau romain.
     * @param name
     * @param sex
     * @param size
     * @param age
     * @param strength
     * @param stamina
     * @param health
     */
    public Roman(String name, Sex sex, int size, int age, double strength, int stamina, double health) {
        super(name, sex, size, age, strength, stamina, health, null);
        this.nationality = "Roman";
    }
}
