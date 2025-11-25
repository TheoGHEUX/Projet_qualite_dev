package com.example.TD3.food;

public class Food {
    private FoodType foodType;
    private int healtEffect;
    private int hungerEffect;
    private boolean isFeeding;
    private boolean isHarmful;

    public Food(FoodType foodtype, int healtEffect, int hungerEffect, boolean isFeeding, boolean isHarmful) {
        this.foodType = foodtype;
        this.healtEffect = healtEffect;
        this.hungerEffect = hungerEffect;
        this.isFeeding = isFeeding;
        this.isHarmful = isHarmful;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public int getHealtEffect() {
        return healtEffect;
    }

    public int getHungerEffect() {
        return hungerEffect;
    }

    public boolean isFeeding() {
        return isFeeding;
    }

    public boolean isHarmful() {
        return isHarmful;
    }
}
