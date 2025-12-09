package main.model.items.food;

// Créer un objet Food : Food food = new FoodStats.newFood(FoodType.<type dans l'enum>);

import main.enums.FoodType;

public class Food {
    private final FoodType foodType;
    private final int healthEffect;
    private final int hungerEffect;
    private final int staminaEffect;
    private final boolean isVege;


    /**
     * Crée un nouvel aliment.
     * @param foodtype
     * @param healthEffect
     * @param hungerEffect
     * @param staminaEffect
     * @param isVege
     */
    public Food(FoodType foodtype, int healthEffect, int hungerEffect, int staminaEffect, boolean isVege) {
        this.foodType = foodtype;
        this.healthEffect = healthEffect;
        this.hungerEffect = hungerEffect;
        this.staminaEffect = staminaEffect;
        this.isVege = isVege;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public int getHealthEffect() {
        return healthEffect;
    }

    public int getHungerEffect() {
        return hungerEffect;
    }

    public boolean isVege() {
        return isVege;
    }

    public int getStaminaEffect() {
        return staminaEffect;
    }
}
