package main.model.character.category.roman.job;

import main.model.character.Character;
import main.enums.Sex;
import main.interfaces.Fighter;
import main.model.character.category.roman.Roman;

// Romain légionnaires
public class Legionary extends Roman implements Fighter {

    /**
     * Crée un nouveau légionnaire avec plus de choix de paramètres.
     * @param name
     * @param sex
     * @param size
     * @param age
     * @param strength
     * @param stamina
     * @param health
     */
    public Legionary(String name, Sex sex, int size, int age, double strength, int stamina, double health) {
        super(name, sex, size, age, strength, stamina, health);
        this.type = "Legionary";
    }

    /**
     * Crée un nouveau légionnaire.
     * @param name
     * @param sex
     */
    public Legionary(String name, Sex sex) {
        super(name, sex, randomBetween(170,180), randomBetween(20,35),20,75,110);
        this.type = "Legionary";
    }

    /**
     * Le légionnaire combat un ennemi.
     * Coup décisif : Inflige 30 dégâts à l'ennemi + 25% de la force du légionnaire
     * Coûte 40 d'énergie.
     * @param enemy
     */
    @Override
    public void combat(Character enemy) {
        if (this.stamina < 40) {
            System.out.println("Legionary " + this.name + " n'a pas assez d'énergie pour activer \"Coup décisif\" !");
            return;
        }
        double damage = 30+(this.strength/4);
        enemy.updateHealth(-damage);
        this.stamina -= 40;
        System.out.println("Legionary " + this.name + " utilise \"Coup décisif\" sur " + enemy.getType() + " " + enemy.getName() + " | Dégâts: " + damage + " | Vie restante: " + enemy.getHealth());
    }
}
