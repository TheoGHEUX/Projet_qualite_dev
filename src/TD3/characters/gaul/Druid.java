package TD3.characters.gaul;

import TD3.characters.Character;
import TD3.enums.Sex;
import TD3.interfaces.Fighter;
import TD3.interfaces.Leader;
import TD3.interfaces.Worker;

import java.util.List;


// Druides
public class Druid extends Gaul implements Worker, Leader, Fighter {

    // Constructeur personnalisé
    public Druid(String name, Sex sex, int size, int age, double strength, int stamina, double health) {
        super(name, sex, size, age, strength, stamina, health);
        this.type = "Druid";
    }

    // Constructeur avec des stats par défaut
    public Druid(String name, Sex sex) {
        super(name, sex, randomBetween(165,180), randomBetween(50,90),10,65,100);
        this.type = "Druid";
    }

    public void work() {
        // Le druide prépare de la potion magique !


    }

    public void lead(List<Character> followers) {
        // Appel de l'étoile : Soigne 75 de vie à tous les membres présents dans le lieu pour lequel il est le chef de clan
        if(!this.isAClanChief){
            System.out.println("Druid " + this.name + " ne peut pas utiliser \"Appel de l'étoile\" car il n'est pas un chef de clan !");
            return;
        }
        if (this.stamina < 50) {
            System.out.println("Druid " + this.name + " n'a pas assez d'énergie pour activer \"Appel de l'étoile\" !");
            return;
        }
        for (Character c : followers) {
            if(c != this){
                c.updateHealth(75);
            }
        }
        System.out.println("Druid " + this.name + " utilise \"Appel de l'étoile\" !");
        this.stamina -= 50;

    }

    public void combat(Character enemy) {
        // Transfusion : Inflige au hasard de 5 à 35 dégâts à l'ennemi et le druide se régénère le montant de ces dégâts
        if (this.stamina < 35) {
            System.out.println("Druid " + this.name + " n'a pas assez d'énergie pour activer \"Transfusion\" !");
            return;
        }
        System.out.println("Druid " + this.name + " utilise \"Transfusion\" sur " + enemy.getType() + " " + enemy.getName() + " !");
        int stealRequested = randomBetween(5, 35);
        double stealActual = Math.min(stealRequested, enemy.getHealth());
        System.out.println("Druid " + this.name + " vole " + stealActual + " PV.");
        enemy.updateHealth(-stealActual);
        this.updateHealth(stealActual);
        this.stamina -= 35;
    }
}