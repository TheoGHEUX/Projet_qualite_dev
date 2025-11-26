package TD3.characters.creature;

import TD3.characters.Character;
import TD3.enums.Sex;
import TD3.interfaces.Fighter;

// Loups-garous
public class Werewolf extends Creature implements Fighter {

    // Constructeur personnalisé
    public Werewolf(String name, Sex sex, int size, int age, int strength, int stamina, int health) {
        super(name, sex, size, age, strength, stamina, health);
        this.type = "Werewolf";
    }

    // Constructeur avec des stats par défaut
    public Werewolf(String name, Sex sex) {
        super(name, sex, randomBetween(165,180), randomBetween(50,90),25,65,100);
        this.type = "Werewolf";
    }

    public void combat(Character enemy) {
        System.out.println("Druid" + this.name + " combats" + enemy.getName());
    }
}
