package TD3.characters.gaul;

import TD3.enums.Sex;
import TD3.interfaces.Worker;

import static TD3.food.FoodSort.foodSort;

// Aubergistes
public class Innkeeper extends Gaul implements Worker {

    // Constructeur personnalisé
    public Innkeeper(String name, Sex sex, int size, int age, double strength, int stamina, double health) {
        super(name, sex, size, age, strength, stamina, health);
        this.type = "Innkeeper";
    }

    // Constructeur avec des stats par défaut
    public Innkeeper(String name, Sex sex) {
        super(name, sex, randomBetween(165,185), randomBetween(30,50),10,70,110);
        this.type = "Innkeeper";
    }

    @Override
    public void work() {
        // L'aubgergiste range l'inventaire des aliments du lieu dans lequel il est du plus au moins nourrissant
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
