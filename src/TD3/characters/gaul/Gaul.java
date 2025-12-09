package TD3.characters.gaul;

import TD3.characters.Character;
import TD3.enums.Sex;
import java.util.Random;

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
