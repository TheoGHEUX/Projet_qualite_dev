package TD3.characters.roman;

import TD3.characters.Character;
import TD3.enums.Sex;
import TD3.interfaces.Fighter;
import TD3.interfaces.Leader;

// Généraux
public class General extends Roman implements Leader, Fighter {

    // Constructeur personnalisé
    public General(String name, Sex sex, int size, int age, int strength, int stamina, double health) {
        super(name, sex, size, age, strength, stamina, health);
        this.type = "General";
    }

    // Constructeur avec des stats par défaut
    public General(String name, Sex sex) {
        super(name, sex, randomBetween(175,185), randomBetween(45,60),15,55,110);
        this.type = "General";
    }

    public void lead(Character follower) {
        System.out.println("General" + this.name + " leads" + follower.getName());
    }

    public void combat(Character enemy) {
        int cost = 45;
        if (this.stamina < cost) {
            System.out.println("General " + this.name + " n'a pas assez d'endurance pour activer Flammes vitales.");
            return;
        }
        System.out.println("General " + this.name + " utilise Flammes vitales.");
        this.stamina -= cost;
        this.stamina += 10;
        this.strength += 5;
        System.out.println("General " + this.name + " : stamina actuelle = " + this.stamina + ", force = " + this.strength + ".");
    }
}
