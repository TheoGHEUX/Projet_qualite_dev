package TD3.places;

import TD3.characters.Character;
import TD3.food.Food;

//champ de bataille
public class Battlefield extends Place {

    public Battlefield(String name, int surface) {
        super(name, surface);
    }

    public boolean canAccept(Character character) {
        return true;
    }

}