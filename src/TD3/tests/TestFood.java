package TD3.tests;

import TD3.food.Food;
import TD3.enums.FoodType;
import TD3.food.FoodStats;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FoodTest {

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
}