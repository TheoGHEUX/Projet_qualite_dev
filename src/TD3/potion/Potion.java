package TD3.potion;

import TD3.enums.FoodType;
import TD3.enums.PotionEffect;
import TD3.food.Food;
import TD3.food.FoodStats;

import java.util.*;

public class Potion {
    private int remainingDoses ;
    private final List<Food> ingredients ;
    private final Set<PotionEffect> effects ;
    private final boolean isNourishing ;
    private final boolean hasUnicornMilk ;
    private final boolean hasDogmatixHair ;
    private final int healthEffect ;
    private final int hungerEffect ;
    private final int staminaEffect ;

    public Potion(List<Food> ingredients) {
        this.ingredients = Collections.unmodifiableList(ingredients);

        boolean hasUnicornMilk = false;
        boolean hasDogmatixHair = false;

        int healthEffect = 0;
        int hungerEffect = 0;
        int staminaEffect = 0;

        for (Food ingredient : ingredients) {
            FoodType type = ingredient.getFoodType();
            switch (type) {
                case LOBSTER, STRAWBERRY, BEET_JUICE:
                    healthEffect += ingredient.getHealthEffect();
                    hungerEffect += ingredient.getHungerEffect();
                    staminaEffect += ingredient.getStaminaEffect();
                    break;
                case TWO_HEADS_UNICORN_MILK:
                    hasUnicornMilk = true;
                case DOGMATIX_HAIR:
                    hasDogmatixHair = true;
                default: {}
            }
        }

        this.isNourishing = healthEffect > 0;
        this.healthEffect = healthEffect;
        this.hungerEffect = hungerEffect;
        this.staminaEffect = staminaEffect;
        this.hasUnicornMilk = hasUnicornMilk;
        this.hasDogmatixHair = hasDogmatixHair;

        Set<PotionEffect> potionEffects = EnumSet.of(PotionEffect.SUPER_STRENGTH, PotionEffect.INVINCIBILITY);
        if (hasUnicornMilk) {
            potionEffects.add(PotionEffect.DUPLICATION);
        }
        if (hasDogmatixHair && hasUnicornMilk) {
            potionEffects.add(PotionEffect.METAMORPHOSIS);
        }

        this.effects = potionEffects;
        this.remainingDoses = 4 ;
    }

    public void showPotionInfos(){
        System.out.println("=== Infos potion === \nDoses restantes: " + this.getRemainingDoses() + "\nRégénération de vie: " + this.getHealthEffect() + " \nRégénération d'énergie: " + this.getStaminaEffect() + " \nEffet de faim: " + this.getHungerEffect() + " \nPouvoirs: " + this.getEffects() + " \nIngrédients: " + this.showIngredients());
    }

    public List<FoodType> showIngredients() {
        List<FoodType> ingredients = new ArrayList<>();
        for (Food food : this.ingredients) {
            ingredients.add(food.getFoodType());
        }
        return ingredients;
    }

    public boolean isEmpty(){
        return this.getRemainingDoses() <= 0;
    }

    public boolean isMagicPotion(){
        List<FoodType> recipe = new ArrayList<>();
        recipe.add(FoodType.MISTLETOE);
        recipe.add(FoodType.CARROT);
        recipe.add(FoodType.SALT);
        recipe.add(FoodType.FRESH_FOUR_LEAF_CLOVER);
        recipe.add(FoodType.PASSABLE_FRESH_FISH);
        recipe.add(FoodType.ROCKFISH_OIL);
        recipe.add(FoodType.HONEY);
        recipe.add(FoodType.MEAD);
        recipe.add(FoodType.SECRET_INGREDIENT);


        List<FoodType> ingredients = new ArrayList<>();
        for (Food food : this.ingredients) {
            ingredients.add(food.getFoodType());
        }

        boolean contains = true;

        for (FoodType food : recipe){
            if(food == FoodType.ROCKFISH_OIL) {
                if(!ingredients.contains(FoodType.ROCKFISH_OIL) && !ingredients.contains(FoodType.BEET_JUICE)) {
                    contains = false;
                    break;
                }
            }
            else{
                if(!ingredients.contains(food)){
                    contains = false;
                    break;
                }
            }
        }
        return contains;
    }

    public void takeOneDose() {
        if(!this.isEmpty()){
            this.remainingDoses--;
        }
    }

    // getters
    public boolean isNourishing() {
        return isNourishing;
    }

    public boolean hasUnicornMilk() {
        return hasUnicornMilk;
    }

    public boolean hasDogmatixHair() {
        return hasDogmatixHair;
    }

    public List<Food> getIngredients() {
        return ingredients;
    }

    public int getHealthEffect() {
        return healthEffect;
    }

    public int getHungerEffect() {
        return hungerEffect;
    }

    public int getStaminaEffect() {
        return staminaEffect;
    }

    public int getRemainingDoses() {
        return remainingDoses;
    }

    public Set<PotionEffect> getEffects() {
        return effects;
    }

}


