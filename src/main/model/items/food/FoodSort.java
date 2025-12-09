package main.model.items.food;

import java.util.List;

// Classe qui implémente un tri à bulles pour ranger les aliments d'une List<Food> du moins au plus nourrissant
public class FoodSort {

    /**
     * Méthode de tri à bulles qui tri les aliments d'une liste du moins au plus nourrissant.
     * @param foods
     */
    public static void foodSort(List<Food> foods) {
        int n = foods.size();

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < n; j++) {
                if (foods.get(j).getHungerEffect() < foods.get(minIndex).getHungerEffect()) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                Food temp = foods.get(i);
                foods.set(i, foods.get(minIndex));
                foods.set(minIndex, temp);
            }
        }
    }
}

