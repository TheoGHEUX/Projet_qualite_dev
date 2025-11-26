package TD3.characters.roman;

import TD3.enums.Sex;

// Romain légionnaires
public class Legionary extends Roman{

    // Constructeur personnalisé
    public Legionary(String name, Sex sex, int size, int age, int strength, int stamina, int health) {
        super(name, sex, size, age, strength, stamina, health);
        this.type = "Legionary";
    }

    // Constructeur avec des stats par défaut
    public Legionary(String name, Sex sex) {
        super(name, sex, randomBetween(170,180), randomBetween(20,35),60,75,110);
        this.type = "Legionary";
    }
}
