package test.model.items.food;

import main.model.items.food.Food;
import main.enums.FoodType;
import main.model.items.food.FoodStats;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestFood {

    @Test
    void testConstructorAndGetters() {
        Food food = new Food(FoodType.WILD_BOAR, 10, 20, 30,false);

        assertEquals(FoodType.WILD_BOAR, food.getFoodType());
        assertEquals(10, food.getHealthEffect());
        assertEquals(20, food.getHungerEffect());
        assertFalse(food.isVege());
    }

    @Test
    void testFoodStatsCreation() {
        Food wildBoar = FoodStats.newFood(FoodType.WILD_BOAR);

        assertEquals(FoodType.WILD_BOAR, wildBoar.getFoodType());
        assertEquals(10, wildBoar.getHealthEffect());
        assertEquals(20, wildBoar.getHungerEffect());
    }

    @Test
    void testVegeDetection() {
        Food carrot = FoodStats.newFood(FoodType.CARROT);
        assertTrue(carrot.isVege());

        Food lobster = FoodStats.newFood(FoodType.LOBSTER);
        assertFalse(lobster.isVege());
    }

    @Test
    void testHungerEffect() {
        Food carrot = FoodStats.newFood(FoodType.CARROT);
        assertEquals(10, carrot.getHungerEffect());

        Food salt = FoodStats.newFood(FoodType.SALT);
        assertEquals(1, salt.getHungerEffect());
    }

    @Test
    void testHealthEffect() {
        Food mistletoe = FoodStats.newFood(FoodType.MISTLETOE);
        assertEquals(5, mistletoe.getHealthEffect());

        Food mead = FoodStats.newFood(FoodType.MEAD);
        assertEquals(3, mead.getHealthEffect());
    }

    @Test
    void testStaminaEffect() {
        Food unicornMilk = FoodStats.newFood(FoodType.TWO_HEADS_UNICORN_MILK);
        assertEquals(20, unicornMilk.getStaminaEffect());

        Food dogmatixHair = FoodStats.newFood(FoodType.DOGMATIX_HAIR);
        assertEquals(0,dogmatixHair.getStaminaEffect());
    }
}