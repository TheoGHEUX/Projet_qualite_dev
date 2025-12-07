package TD3.characters.roman;

import TD3.characters.Character;
import TD3.enums.Sex;
import TD3.interfaces.Leader;

import java.util.List;

// Prefets
public class Prefect extends Roman implements Leader {

    // Constructeur personnalisé
    public Prefect(String name, Sex sex, int size, int age, double strength, int stamina, double health) {
        super(name, sex, size, age, strength, stamina, health);
        this.type = "Prefect";
    }

    // Constructeur avec des stats par défaut
    public Prefect(String name, Sex sex) {
        super(name, sex, randomBetween(175,185), randomBetween(35,50),15,60,100);
        this.type = "Prefect";
    }

    @Override
    public void lead(List<Character> followers) {
        // Clarté : Régénère entièrement l'énergie des personnages présents dans le lieu pour lequel il est le chef de clan
        if(!this.isAClanChief){
            System.out.println("Prefect " + this.name + " ne peut pas utiliser \"Clarté\" car il n'est pas un chef de clan !");
            return;
        }
        if (this.stamina < 45) {
            System.out.println("Prefect " + this.name + " n'a pas assez d'énergie pour utiliser \"Clarté\" !");
            return;
        }
        for(Character c : followers){
            if(c != this) {
                c.updateStamina(c.getMaxStamina());
            }
        }
        System.out.println("Prefect " + this.name + " utilise \"Clarté\" !");
        this.stamina -= 45;
    }
}
