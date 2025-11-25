package TD3.food;

public class Food {
    private FoodType foodType;
    private int healthEffect;
    private int hungerEffect;
    private boolean isVege;

    public Food(FoodType foodtype, int healthEffect, int hungerEffect, boolean isVege) {
        this.foodType = foodtype;
        this.healthEffect = healthEffect;
        this.hungerEffect = hungerEffect;
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
}
