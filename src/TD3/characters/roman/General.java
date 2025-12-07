package TD3.characters.roman;

import TD3.characters.Character;
import TD3.enums.Sex;
import TD3.interfaces.Fighter;
import TD3.interfaces.Leader;

import java.util.List;

// Généraux
public class General extends Roman implements Leader, Fighter {

    // Constructeur personnalisé
    public General(String name, Sex sex, int size, int age, double strength, int stamina, double health) {
        super(name, sex, size, age, strength, stamina, health);
        this.type = "General";
    }

    // Constructeur avec des stats par défaut
    public General(String name, Sex sex) {
        super(name, sex, randomBetween(175,185), randomBetween(45,60),15,55,110);
        this.type = "General";
    }

    @Override
    public void lead(List<Character> followers) {
        // Volonté : octroie +10 de force aux personnages présents dans le lieu pour lequel il est le chef de clan
        if(!this.isAClanChief){
            System.out.println("General " + this.name + " ne peut pas utiliser \"Volonté\" car il n'est pas un chef de clan !");
            return;
        }
        if (this.stamina < 35) {
            System.out.println("General " + this.name + " n'a pas assez d'énergie pour utiliser \"Volonté\" !");
            return;
        }
        for(Character c : followers){
            if(c != this) {
                c.gainStrength(10);
            }
        }
        System.out.println("General " + this.name + " utilise \"Volonté\" !");
        this.stamina -= 35;

    }

    @Override
    public void combat(Character enemy) {
        // Justice : Divise la vie actuelle de l'ennemi par 2
        if (this.stamina < 45) {
            System.out.println("General " + this.name + " n'a pas assez d'énergie pour utiliser \"Justice\" !");
            return;
        }
        System.out.println("General " + this.name + " utilise \"Justice\" sur " + enemy.getType() + " " + enemy.getName() + " !");
        enemy.updateHealth(-(enemy.getHealth()/2));
        this.stamina -= 45;
    }
}
