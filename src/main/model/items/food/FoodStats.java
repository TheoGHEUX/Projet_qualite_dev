package main.model.items.food;

import main.enums.FoodType;

public class FoodStats {
    /**
     * Crée un nouvel aliment uniquement avec son type.
     * @param foodType
     * @return
     */
    public static Food newFood(FoodType foodType){
        switch (foodType) {
            case WILD_BOAR:
                return new Food(foodType, 10, 20, 30, false);
            case PASSABLE_FRESH_FISH:
                return new Food(foodType, 8, 15, 25,false);
            case NOT_FRESH_FISH:
                return new Food(foodType, -5, 6, 5, false);
            case MISTLETOE:
                return new Food(foodType, 5, 8, 20,true);
            case LOBSTER:
                return new Food(foodType, 12, 18, 15, false);
            case STRAWBERRY, BEET_JUICE:
                return new Food(foodType, 6, 12, 10,true);
            case CARROT:
                return new Food(foodType, 5, 10, 10,true);
            case SALT:
                return new Food(foodType, 0, 1, 0,false);
            case FRESH_FOUR_LEAF_CLOVER:
                return new Food(foodType, 7, 10, 20,true);
            case NOT_FRESH_FOUR_LEAF_CLOVER:
                return new Food(foodType, 3, 5, 2,true);
            case ROCKFISH_OIL:
                return new Food(foodType, 10, 8, 15,false);
            case HONEY:
                return new Food(foodType, 8, 5, 15,false);
            case WINE:
                return new Food(foodType, 2, 5, 15,false);
            case MEAD:
                return new Food(foodType, 3, 5, 30,false);
            case TWO_HEADS_UNICORN_MILK:
                return new Food(foodType, 15, 20, 20,false);
            case DOGMATIX_HAIR:
                return new Food(foodType, 1, 1, 0, false);
            case SECRET_INGREDIENT:
                return new Food(foodType, 20, 25, 15,false);
            default:
                return new Food(foodType, 5, 10, 10, false);
        }
    }
}
