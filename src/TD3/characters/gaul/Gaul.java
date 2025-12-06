package TD3.characters.gaul;

import TD3.characters.Character;
import TD3.enums.Sex;
import java.util.Random;

public abstract class Gaul extends Character {

    // Variable utile à la génération de l'âge et la taille aléatoire
    protected static final Random RANDOM = new Random();

    // Constructeur personnalisé pour un citoyen sans métier implémenté par une classe
    public Gaul(String name, Sex sex, int size, int age, int strength, int stamina, double health) {
        super(name, sex, size, age, strength, stamina, health, null);
        this.nationality = "Gaul";
    }

    // Constructeur avec des stats par défaut pour un citoyen sans métier implémenté par une classe
    public Gaul(String name, Sex sex) {
        super(name, sex,randomBetween(150,200),randomBetween(20,90),10,40,100, null);
        this.nationality = "Gaul";
    }

    // Méthode pour générer aléatoirement un nombre dans un intervalle (utilisée pour générer la taille et l'âge)
    protected static int randomBetween(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }
}
