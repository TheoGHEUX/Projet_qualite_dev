package main.model.character.category.gaul.job;

import main.enums.Sex;
import main.interfaces.Worker;
import main.model.character.category.gaul.Gaul;

import static main.model.items.food.FoodSort.foodSort;

// Aubergistes
public class Innkeeper extends Gaul implements Worker {

    /**
     * Crée un nouvel aubergiste avec plus de choix de paramètres.
     * @param name
     * @param sex
     * @param size
     * @param age
     * @param strength
     * @param stamina
     * @param health
     */
    public Innkeeper(String name, Sex sex, int size, int age, double strength, int stamina, double health) {
        super(name, sex, size, age, strength, stamina, health);
        this.type = "Innkeeper";
    }

    /**
     * Crée un nouvel aubergiste.
     * @param name
     * @param sex
     */
    public Innkeeper(String name, Sex sex) {
        super(name, sex, randomBetween(165,185), randomBetween(30,50),10,70,110);
        this.type = "Innkeeper";
    }

    /**
     * L'aubergiste travaille.
     * L'aubergiste range l'inventaire des aliments du lieu dans lequel il est du plus au moins nourrissant.
     * Coûte 20 d'énergie.
     */
    @Override
    public void work() {
        //
        if(this.currentPlace == null){
            System.out.println("Innkeeper " + this.name + " ne peut pas trier des aliments car il n'est actuellement pas dans un lieu !");
            return;
        }
        if(this.getStamina() < 20){
            System.out.println("Innkeeper " + this.name + " ne peut pas trier les aliments car il est fatigué !");
            return;
        }

        foodSort(this.getPlace().getThe_aliments_present());
        System.out.println("Inkeeper " + this.name + " a trié l'inventaire des aliments de " + this.getPlace().getName() + " !");
        this.stamina -= 20 ;
    }
}
