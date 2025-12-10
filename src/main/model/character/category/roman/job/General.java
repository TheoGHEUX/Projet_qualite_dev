package main.model.character.category.roman.job;

import main.model.character.Character;
import main.enums.Sex;
import main. interfaces.Fighter;
import main. interfaces.Leader;
import main. model.character.category.roman. Roman;

import java.util. List;

// Généraux
public class General extends Roman implements Leader, Fighter {

    /**
     * Crée un nouveau général avec plus de choix de paramètres.
     * @param name
     * @param sex
     * @param size
     * @param age
     * @param strength
     * @param stamina
     * @param health
     */
    public General(String name, Sex sex, int size, int age, double strength, int stamina, double health) {
        super(name, sex, size, age, strength, stamina, health);
        this.type = "General";
    }

    /**
     * Crée un nouveau général.
     * @param name
     * @param sex
     */
    public General(String name, Sex sex) {
        super(name, sex, randomBetween(175,185), randomBetween(45,60),15,55,110);
        this.type = "General";
    }

    /**
     * Le général dirige.
     * Volonté :  octroie +10 de force aux personnages présents dans le lieu pour lequel il est le chef de clan
     * Coûte 35 d'énergie.
     * @param followers
     */
    @Override
    public void lead(List<Character> followers) {
        if(this.currentPlace == null){
            System.out.println("General " + this.name + " ne peut pas utiliser \"Volonté\" car il n'est pas dans un lieu!");
            return;
        }
        if (this. stamina < 35) {
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

    /**
     * Le général combat un ennemi.
     * Justice : Divise la vie actuelle de l'ennemi par 2.
     * Coûte 45 d'énergie.
     * @param enemy
     */
    @Override
    public void combat(Character enemy) {
        if (this.stamina < 45) {
            System.out. println("General " + this.name + " n'a pas assez d'énergie pour utiliser \"Justice\" !");
            return;
        }

        double damage = enemy.getHealth() / 2;
        double remainingHealth = enemy.getHealth() - damage;
        int remainingStamina = this. stamina - 45;

        System.out.println("General " + this.name + " utilise \"Justice\" sur " + enemy.getType() + " " + enemy.getName() + " | Dégâts: " + damage + " | Vie restante: " + remainingHealth );

        enemy.updateHealth(-damage);
        this.stamina -= 45;
    }
}