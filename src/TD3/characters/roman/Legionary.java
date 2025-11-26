package TD3.characters.roman;

import TD3.characters.Character;
import TD3.enums.Sex;
import TD3.interfaces.Fighter;

// Romain légionnaires
public class Legionary extends Roman implements Fighter {

    // Constructeur personnalisé
    public Legionary(String name, Sex sex, int size, int age, int strength, int stamina, int health) {
        super(name, sex, size, age, strength, stamina, health);
        this.type = "Legionary";
    }

    // Constructeur avec des stats par défaut
    public Legionary(String name, Sex sex) {
        super(name, sex, randomBetween(170,180), randomBetween(20,35),20,75,110);
        this.type = "Legionary";
    }

    public void combat(Character enemy) {
        System.out.println("Legionary" + this.name + " combats" + enemy.getName());
    }
}
