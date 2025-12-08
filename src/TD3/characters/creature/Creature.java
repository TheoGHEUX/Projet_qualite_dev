package TD3.characters.creature;

import TD3.characters.Character;
import TD3.enums.Sex;
import TD3.places.Place;

import java.util.Random;

public class Creature extends Character {

    // Variable utile à la génération de l'âge et la taille aléatoire
    protected static final Random RANDOM = new Random();

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

    /**
     * Génère aléatoirement un nombre dans un intervalle
     * @param min
     * @param max
     * @return
     */
    protected static int randomBetween(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }
}
