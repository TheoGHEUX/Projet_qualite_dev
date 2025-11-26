package TD3.characters.roman;

import TD3.enums.Sex;

// Généraux
public class General extends Roman {

    // Constructeur personnalisé
    public General(String name, Sex sex, int size, int age, int strength, int stamina, int health) {
        super(name, sex, size, age, strength, stamina, health);
        this.type = "General";
    }

    // Constructeur avec des stats par défaut
    public General(String name, Sex sex) {
        super(name, sex, randomBetween(175,185), randomBetween(45,60),50,55,110);
        this.type = "General";
    }
}
