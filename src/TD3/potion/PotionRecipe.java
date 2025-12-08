package TD3.potion;

import TD3.enums.FoodType;
import TD3.food.Food;

import java.util.ArrayList;
import java.util.List;

public class PotionRecipe {
    public List<FoodType> recipe = new ArrayList<>();

    /**
     * Recette minimale d'une potion magique.
     */
    public PotionRecipe() {
        recipe.add(FoodType.MISTLETOE);
        recipe.add(FoodType.CARROT);
        recipe.add(FoodType.SALT);
        recipe.add(FoodType.FRESH_FOUR_LEAF_CLOVER);
        recipe.add(FoodType.PASSABLE_FRESH_FISH);
        recipe.add(FoodType.ROCKFISH_OIL);
        recipe.add(FoodType.HONEY);
        recipe.add(FoodType.MEAD);
        recipe.add(FoodType.SECRET_INGREDIENT);
    }

    public List<FoodType> getRecipe() {
        return recipe;
    }
}
