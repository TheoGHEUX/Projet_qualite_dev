package main.model.character.category.gaul.job;

import main.enums.Sex;
import main.interfaces.Worker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import main.model.character.Character;
import main.model.character.category.gaul.Gaul;

// Forgerons
public class Blacksmith extends Gaul implements Worker {

    /**
     * Crée un nouveau forgeron avec plus de choix de paramètres.
     * @param name
     * @param sex
     * @param size
     * @param age
     * @param strength
     * @param stamina
     * @param health
     */
    public Blacksmith(String name, Sex sex, int size, int age, double strength, int stamina, double health) {
        super(name, sex, size, age, strength, stamina, health);
        this.type = "Blacksmith";
    }

    /**
     * Crée un nouveau forgeron.
     * @param name
     * @param sex
     */
    public Blacksmith(String name, Sex sex) {
        super(name, sex,randomBetween(170,185),randomBetween(30,50),15,65,120);
        this.type = "Blacksmith";
    }

    /**
     * Le forgeron travaille.
     * Le forgeron fabrique une arme pour augmenter la force d'un membre actuellement présent dans le même lieu que lui
     * Coûte 10 d'énergie.
     */
    @Override
    public void work() {
        if(this.currentPlace == null){
            System.out.println("Blacksmith " + this.name + " ne peut pas forger une arme car il n'est actuellement pas dans un lieu !");
            return;
        }
        if(this.getStamina() < 10){
            System.out.println("Blacksmith " + this.name + " ne peut pas forger une arme car il est fatigué !");
            return;
        }

        List<Character> others = new ArrayList<>();

        for (Character c : this.getPlace().getThe_characters_present()) {
            if (c != this) {
                others.add(c);
            }
        }

        if (others.isEmpty()) {
            System.out.println("Blacksmith " + this.name + " ne peut pas forger une arme car il n'y a pas de personnages dans son lieu !");
            return;
        }
        Character chosen = others.get(new Random().nextInt(others.size()));
        chosen.gainStrength(5);
        System.out.println("Blacksmith " + this.name + " a forgé une arme pour " + chosen.getName() + " ce qui lui a augmenté sa force !");
        this.stamina -= 10 ;
    }
}
