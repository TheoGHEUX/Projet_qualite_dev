package com.example.TD3.food;

public class Food {
    private FoodType foodType;
    private int healtEffect;
    private int hungerEffect;
    private boolean isVege;

    public Food(FoodType foodtype, int healtEffect, int hungerEffect, boolean isVege) {
        this.foodType = foodtype;
        this.healtEffect = healtEffect;
        this.hungerEffect = hungerEffect;
        this.isVege = isVege;
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

    public boolean isVege() {
        return isVege;
    }
}
