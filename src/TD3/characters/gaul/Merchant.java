package TD3.characters.gaul;

import TD3.enums.FoodType;
import TD3.enums.Sex;
import TD3.food.Food;
import TD3.food.FoodStats;
import TD3.interfaces.Worker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Gaulois marchants
public class Merchant extends Gaul implements Worker {

    // Constructeur personnalisé
    public Merchant(String name, Sex sex, int size, int age, double strength, int stamina, double health) {
        super(name, sex, size, age, strength, stamina, health);
        this.type = "Merchant";
    }

    // Constructeur avec des stats par défaut
    public Merchant(String name, Sex sex) {
        super(name, sex, randomBetween(160,175), randomBetween(30,60),10,60,100);
        this.type = "Merchant";
    }

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
