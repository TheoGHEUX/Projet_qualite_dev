package TD3.characters.gaul;

import TD3.characters.Character;
import TD3.enums.Sex;
import TD3.interfaces.Fighter;
import TD3.interfaces.Leader;
import TD3.interfaces.Worker;

import java.awt.*;

// Druides
public class Druid extends Gaul implements Worker, Leader, Fighter {

    // Constructeur personnalisé
    public Druid(String name, Sex sex, int size, int age, int strength, int stamina, int health) {
        super(name, sex, size, age, strength, stamina, health);
        this.type = "Druid";
    }

    // Constructeur avec des stats par défaut
    public Druid(String name, Sex sex) {
        super(name, sex, randomBetween(165,180), randomBetween(50,90),10,65,100);
        this.type = "Druid";
    }

    public void work() {
        System.out.println("Druid" + this.name + " works");
    }

    public void lead(Character follower) {
        System.out.println("Druid" + this.name + " leads" + follower.getName());
    }

    public void combat(Character enemy) {
        System.out.println("Druid" + this.name + " combats" + enemy.getName());
    }

}
