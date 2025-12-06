package TD3.characters.gaul;

import TD3.enums.Sex;
import TD3.interfaces.Worker;

// Aubergistes
public class Innkeeper extends Gaul implements Worker {

    // Constructeur personnalisé
    public Innkeeper(String name, Sex sex, int size, int age, int strength, int stamina, double health) {
        super(name, sex, size, age, strength, stamina, health);
        this.type = "Innkeeper";
    }

    // Constructeur avec des stats par défaut
    public Innkeeper(String name, Sex sex) {
        super(name, sex, randomBetween(165,185), randomBetween(30,50),10,70,110);
        this.type = "Innkeeper";
    }

    public void work() {
        System.out.println("Innkeeper " + this.name + " works");
    }
}
