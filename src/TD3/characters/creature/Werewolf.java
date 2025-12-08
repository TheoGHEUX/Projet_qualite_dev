package TD3.characters.creature;

import TD3.characters.Character;
import TD3.enums.Sex;
import TD3.interfaces.Fighter;

// Loups-garous
public class Werewolf extends Creature implements Fighter {

    /**
     * Crée un nouveau loup avec plus de choix de paramètres.
     * @param name
     * @param sex
     * @param size
     * @param age
     * @param strength
     * @param stamina
     * @param health
     */
    public Werewolf(String name, Sex sex, int size, int age, int strength, int stamina, double health) {
        super(name, sex, size, age, strength, stamina, health);
        this.type = "Werewolf";
    }

    /**
     * Crée un nouveau loup
     * @param name
     * @param sex
     */
    public Werewolf(String name, Sex sex) {
        super(name, sex, randomBetween(165,180), randomBetween(50,90),25,65,100);
        this.type = "Werewolf";
    }

    /**
     * Le loup combar un ennemi
     * Dents de la bête : Inflige 50 dégâts à l'ennemi contre 50 de faim et se soigne de 20
     * Coûte 50 de faim.
     * @param enemy
     */
    @Override
    public void combat(Character enemy) {
        int currentHunger = this.getHunger();
        if (currentHunger <= 50) {
            System.out.println("Werewolf " + this.name + " n'a pas assez faim pour activer \"Dents de la bête\" !");
            return;
        }
        System.out.println("Werewolf " + this.name + " utilise \"Dents de la bête\" sur " + enemy.getType() + " " + enemy.getName() + " !");
        int hungerCost = 50;
        enemy.updateHealth(-50);
        this.updateHunger(-hungerCost);
        this.updateHealth(20);
    }
}