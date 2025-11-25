package TD3.characters.gaul;

import TD3.enums.Sex;

// Gaulois marchants
public class Merchant extends Gaul{

    // Constructeur personnalisé
    public Merchant(String name, Sex sex, int size, int age, int strength, int stamina, int health) {
        super(name, sex, size, age, strength, stamina, health);
    }

    // Constructeur avec des stats par défaut
    public Merchant(String name, Sex sex) {
        super(name, sex, randomBetween(160,175), randomBetween(30,60),40,60,100);
    }
}
