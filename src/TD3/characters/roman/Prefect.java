package TD3.characters.roman;

import TD3.enums.Sex;

// Prefets
public class Prefect extends Roman {

    // Constructeur personnalisé
    public Prefect(String name, Sex sex, int size, int age, int strength, int stamina, int health) {
        super(name, sex, size, age, strength, stamina, health);
        this.type = "Prefect";
    }

    // Constructeur avec des stats par défaut
    public Prefect(String name, Sex sex) {
        super(name, sex, randomBetween(175,185), randomBetween(35,50),70,60,100);
        this.type = "Prefect";
    }
}
