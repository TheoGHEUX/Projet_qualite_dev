package TD3.characters.gaul;

import TD3.enums.Sex;

// Aubergistes
public class Innkeeper extends Gaul{

    // Constructeur personnalisé
    public Innkeeper(String name, Sex sex, int size, int age, int strength, int stamina, int health) {
        super(name, sex, size, age, strength, stamina, health);
        this.type = "Innkeeper";
    }

    // Constructeur avec des stats par défaut
    public Innkeeper(String name, Sex sex) {
        super(name, sex, randomBetween(165,185), randomBetween(30,50),50,70,110);
        this.type = "Innkeeper";
    }
}
