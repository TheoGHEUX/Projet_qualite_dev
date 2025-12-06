package TD3.characters.gaul;

import TD3.enums.Sex;
import TD3.interfaces.Worker;

// Gaulois marchants
public class Merchant extends Gaul implements Worker {

    // Constructeur personnalisé
    public Merchant(String name, Sex sex, int size, int age, double strength, int stamina, double health) {
        super(name, sex, size, age, strength, stamina, health);
        this.type = "Merchant";
    }

    // Constructeur avec des stats par défaut
    public Merchant(String name, Sex sex) {
        super(name, sex, randomBetween(160,175), randomBetween(30,60),10,60,100);
        this.type = "Merchant";
    }

    public void work() {
        System.out.println("Merchant " + this.name + " works");
    }
}
