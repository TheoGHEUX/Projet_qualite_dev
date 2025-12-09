package main.model.character.category.gaul.job;

import main.enums.FoodType;
import main.enums.Sex;
import main.model.character.category.gaul.Gaul;
import main.model.items.food.Food;
import main.model.items.food.FoodStats;
import main.interfaces.Worker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Gaulois marchants
public class Merchant extends Gaul implements Worker {

    /**
     * Crée un nouveau marchand avec plus de choix de paramètres.
     * @param name
     * @param sex
     * @param size
     * @param age
     * @param strength
     * @param stamina
     * @param health
     */
    public Merchant(String name, Sex sex, int size, int age, double strength, int stamina, double health) {
        super(name, sex, size, age, strength, stamina, health);
        this.type = "Merchant";
    }

    /**
     * Crée un nouveau marchand
     * @param name
     * @param sex
     */
    public Merchant(String name, Sex sex) {
        super(name, sex, randomBetween(160,175), randomBetween(30,60),10,60,100);
        this.type = "Merchant";
    }

    /**
     * Le marchand travaille.
     * Le marchand vend 6 aliments aléatoires au lieu où il se trouve
     * Coûte 30 d'énergie.
     */
    @Override
    public void work() {
        if(this.currentPlace == null){
            System.out.println("Merchant " + this.name + " doit être dans un lieu pour vendre ses produits !");
            return;
        }
        if(this.getStamina() < 30){
            System.out.println("Merchant " + this.name + " est trop fatigué pour vendre ses produits !");
            return;
        }

        List<Food> foods = new ArrayList<>();
        Random random = new Random();
        FoodType[] foodTypes = FoodType.values();

        for (int i = 0; i < 6; i++) {
            FoodType randomType = foodTypes[random.nextInt(foodTypes.length)];
            foods.add(FoodStats.newFood(randomType));
        }

        this.getPlace().addFood(foods);
        System.out.println("Merchant " + this.name + " a vendu 6 produits à " + this.getPlace().getName() + " : ");
        for (Food food : foods) {
            System.out.println(food.getFoodType());
        }
        System.out.println("\n");
        this.stamina -= 30 ;

    }
}
