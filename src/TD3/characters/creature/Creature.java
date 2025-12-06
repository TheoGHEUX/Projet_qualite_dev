package TD3.characters.creature;

import TD3.characters.Character;
import TD3.enums.Sex;

import java.util.Random;

public class Creature extends Character {

    // Variable utile à la génération de l'âge et la taille aléatoire
    protected static final Random RANDOM = new Random();

    // Constructeur personnalisé pour une créature sans espèce implémentée par une classe
    public Creature(String name, Sex sex, int size, int age, int strength, int stamina, double health) {
        super(name, sex, size, age, strength, stamina, health);
        this.nationality = "Creature";
    }

    // Constructeur avec des stats par défaut pour une créature sans espèce implémentée par une classe
    public Creature(String name, Sex sex) {
        super(name, sex,randomBetween(150,200),randomBetween(20,90),10,40,100);
        this.nationality = "Creature";
    }

    // Méthode pour générer aléatoirement un nombre dans un intervalle (utilisée pour générer la taille et l'âge)
    protected static int randomBetween(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }
}
