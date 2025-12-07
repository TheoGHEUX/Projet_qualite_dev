package TD3.characters.gaul;

import TD3.characters.Character;
import TD3.enums.FoodType;
import TD3.enums.Sex;
import TD3.food.Food;
import TD3.interfaces.Fighter;
import TD3.interfaces.Leader;
import TD3.interfaces.Worker;
import TD3.potion.Potion;
import TD3.potion.PotionRecipe;

import java.util.ArrayList;
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
        // Le druide prépare de la potion magique basique ! (sans effet de duplication et métamorphosis)
        if(this.currentPlace == null){
            System.out.println("Druid " + this.name + " ne peut pas préparer de potion magique car il n'est actuellement pas dans un lieu !");
            return;
        }
        if(this.getStamina() < 20){
            System.out.println("Druid " + this.name + " ne peut pas préparer de potion magique car il est fatigué !");
            return;
        }
        if(!this.getPlace().hasEnoughToMakeAMagicPotion()){
            System.out.println("Druid " + this.name + " ne peut pas préparer de potion magique car il n'y a pas assez d'ingrédients à " + this.getPlace().getName() + " !");
            return;
        }

        List<FoodType> availabletype = this.getPlace().showTheAlimentsPresent(); // types des aliments disponibles
        List<Food> available = this.getPlace().getThe_aliments_present(); // aliments disponibles
        PotionRecipe p_recipe = new PotionRecipe(); // Recette de la potion
        List<FoodType> recipe = p_recipe.getRecipe(); // Recette de la potion avec foodtype
        List<Food> used = new ArrayList<>(); // Aliments utilisés
        // Gestion de la suppression des aliments de base :
        if (availabletype.contains(FoodType.BEET_JUICE)){
            for(FoodType foodtype : recipe){
                if (foodtype != FoodType.ROCKFISH_OIL){
                    for(Food food : available){
                        if(food.getFoodType() == foodtype){
                            used.add(food);
                            this.getPlace().removeFoodWithoutPrint(food);
                            break;
                        }
                    }
                }
                else{
                    for(Food food : available){
                        if(food.getFoodType() == FoodType.BEET_JUICE){
                            used.add(food);
                            this.getPlace().removeFoodWithoutPrint(food);
                            break;
                        }
                    }
                }

            }
        }
        else {
            for (FoodType foodtype : recipe) {
                for (Food food : available) {
                    if (food.getFoodType() == foodtype) {
                        used.add(food);
                        this.getPlace().removeFoodWithoutPrint(food);
                        break;
                    }
                }
            }
        }
        // Gestion de la suppression des autres aliments nourrissant (s'il y en a, le druide les met automatiquement dans la potion)
        if (availabletype.contains(FoodType.STRAWBERRY)){
            for (Food food : available) {
                if (food.getFoodType() == FoodType.STRAWBERRY) {
                    used.add(food);
                    this.getPlace().removeFoodWithoutPrint(food);
                    break;
                }
            }
        }
        if (availabletype.contains(FoodType.LOBSTER)){
            for (Food food : available) {
                if (food.getFoodType() == FoodType.LOBSTER) {
                    used.add(food);
                    this.getPlace().removeFoodWithoutPrint(food);
                    break;
                }
            }
        }

        Potion potion = new Potion(used);

        System.out.println("Potion créé avec succès ! Aliments utilisés : " + potion.showIngredients());

        this.getPlace().addPotion(potion);
        this.stamina -= 20 ;
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