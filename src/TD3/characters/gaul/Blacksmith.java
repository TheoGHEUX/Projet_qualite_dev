package TD3.characters.gaul;

import TD3.enums.Sex;
import TD3.interfaces.Worker;

// Forgerons
public class Blacksmith extends Gaul implements Worker {

    // Constructeur personnalisé
    public Blacksmith(String name, Sex sex, int size, int age, int strength, int stamina, double health) {
        super(name, sex, size, age, strength, stamina, health);
        this.type = "Blacksmith";
    }

    // Constructeur avec des stats par défaut
    public Blacksmith(String name, Sex sex) {
        super(name, sex,randomBetween(170,185),randomBetween(30,50),15,65,120);
        this.type = "Blacksmith";
    }

    public void work() {
        System.out.println("Blacksmith" + this.name + " works");
    }
}
