package TD3.characters.roman;

import TD3.characters.Character;
import TD3.enums.Sex;
import TD3.interfaces.Fighter;

// Romain légionnaires
public class Legionary extends Roman implements Fighter {

    // Constructeur personnalisé
    public Legionary(String name, Sex sex, int size, int age, double strength, int stamina, double health) {
        super(name, sex, size, age, strength, stamina, health);
        this.type = "Legionary";
    }

    // Constructeur avec des stats par défaut
    public Legionary(String name, Sex sex) {
        super(name, sex, randomBetween(170,180), randomBetween(20,35),20,75,110);
        this.type = "Legionary";
    }

    public void combat(Character enemy) {
        // Coup décisif : Inflige 30 dégâts à l'ennemi + 25% de la force du légionnaire
        if (this.stamina < 40) {
            System.out.println("Legionary " + this.name + " n'a pas assez d'énergie pour activer \"Coup décisif\" !");
            return;
        }
        System.out.println("Legionary " + this.name + " utilise \"Coup décisif\" sur " + enemy.getType() + " " + enemy.getName() + " !");
        enemy.updateHealth(-(30+(this.strength/4)));
        this.stamina -= 40;
    }
}
