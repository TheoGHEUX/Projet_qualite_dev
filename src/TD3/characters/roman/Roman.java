package TD3.characters.roman;

import TD3.characters.Character;
import TD3.enums.Sex;

import java.util.Random;

public abstract class Roman extends Character {

    // Variable utile à la génération de l'âge et la taille aléatoire
    protected static final Random RANDOM = new Random();

    // Constructeur personnalisé pour un citoyen sans métier implémenté par une classe
    public Roman(String name, Sex sex, int size, int age, double strength, int stamina, double health) {
        super(name, sex, size, age, strength, stamina, health, null);
        this.nationality = "Roman";
    }

    // Méthode pour générer aléatoirement un nombre dans un intervalle (utilisée pour générer la taille et l'âge)
    protected static int randomBetween(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }
}
