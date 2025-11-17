package com.example.TD3.food;

public class FoodStats {
    public static Food newFood(FoodType foodType){
        switch (foodType) {
            case WILD_BOAR:
                return new Food(foodType, 10, 20, true, false);
            case PASSABLE_FRESH_FISH:
                return new Food(foodType, 10, 20, true, false);
            case NOT_FRESH_FISH:
                return new Food(foodType, 10, 20, true, false);
            case MISTLETOE:
                return new Food(foodType, 10, 20, true, false);
            case LOBSTER:
                return new Food(foodType, 10, 20, true, false);
            case STRAWBERRY:
                return new Food(foodType, 10, 20, true, false);
            case CARROT:
                return new Food(foodType, 10, 20, true, false);
            case SALT:
                return new Food(foodType, 10, 20, true, false);
            case FRESH_FOUR_LEAF_CLOVER:
                return new Food(foodType, 10, 20, true, false);
            case NOT_FRESH_FOUR_LEAF_CLOVER:
                return new Food(foodType, 10, 20, true, false);
            case ROCKFISH_OIL:
                return new Food(foodType, 10, 20, true, false);
            case BEET_JUICE:
                return new Food(foodType, 10, 20, true, false);
            case HONEY:
                return new Food(foodType, 10, 20, true, false);
            case WINE:
                return new Food(foodType, 10, 20, true, false);
            case MEAD:
                return new Food(foodType, 10, 20, true, false);
            case TWO_HEADS_UNICORN_MILK:
                return new Food(foodType, 10, 20, true, false);
            case DOGMATIX_HAIR:
                return new Food(foodType, 10, 20, true, false);
            case SECRET_INGREDIENT:
                return new Food(foodType, 10, 20, true, false);
            default:
                return new Food(foodType, 10, 20, true, false);
        }

    }
}
