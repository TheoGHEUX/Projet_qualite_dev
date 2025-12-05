package TD3.characters.roman;

import TD3.characters.Character;
import TD3.enums.Sex;
import TD3.interfaces.Leader;

// Prefets
public class Prefect extends Roman implements Leader {

    // Constructeur personnalisé
    public Prefect(String name, Sex sex, int size, int age, int strength, int stamina, double health) {
        super(name, sex, size, age, strength, stamina, health);
        this.type = "Prefect";
    }

    // Constructeur avec des stats par défaut
    public Prefect(String name, Sex sex) {
        super(name, sex, randomBetween(175,185), randomBetween(35,50),15,60,100);
        this.type = "Prefect";
    }

    public void lead(Character follower) {
        System.out.println("Prefect" + this.name + " leads" + follower.getName());
    }
}
