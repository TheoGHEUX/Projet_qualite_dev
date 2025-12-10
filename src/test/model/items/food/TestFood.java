package test.model.items.food;

import main.model.items.food.Food;
import main.enums.FoodType;
import main.model.items.food.FoodStats;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static main.model.items.food.Food.showFoodTypes;
import static main.model.items.food.FoodSort.foodSort;
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

class TestFoodSort {

    @Test
    void testFoodSort() {
        List<Food> notSorted = new ArrayList<Food>();
        notSorted.add(FoodStats.newFood(FoodType.MISTLETOE));
        notSorted.add(FoodStats.newFood(FoodType.WILD_BOAR));
        notSorted.add(FoodStats.newFood(FoodType.PASSABLE_FRESH_FISH));
        notSorted.add(FoodStats.newFood(FoodType.WILD_BOAR));
        notSorted.add(FoodStats.newFood(FoodType.BEET_JUICE));
        notSorted.add(FoodStats.newFood(FoodType.DOGMATIX_HAIR));
        notSorted.add(FoodStats.newFood(FoodType.CARROT));

        List<Food> sorted = new ArrayList<>();
        sorted.add(FoodStats.newFood(FoodType.DOGMATIX_HAIR));
        sorted.add(FoodStats.newFood(FoodType.MISTLETOE));
        sorted.add(FoodStats.newFood(FoodType.CARROT));
        sorted.add(FoodStats.newFood(FoodType.BEET_JUICE));
        sorted.add(FoodStats.newFood(FoodType.PASSABLE_FRESH_FISH));
        sorted.add(FoodStats.newFood(FoodType.WILD_BOAR));
        sorted.add(FoodStats.newFood(FoodType.WILD_BOAR));

        foodSort(notSorted);

        assertEquals(showFoodTypes(notSorted),showFoodTypes(sorted));

    }
}