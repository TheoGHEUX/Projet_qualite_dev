package test.model.items.food;

import main.enums.FoodType;
import main.model.items.food.Food;
import main.model.items.food.FoodStats;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static main.model.items.food.Food.showFoodTypes;
import static main.model.items.food.FoodSort.foodSort;
import static org.junit.jupiter.api.Assertions.*;

public class TestFoodSort {

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
