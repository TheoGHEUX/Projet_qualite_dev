package main.model.place.category;

import main.model.character.Character;
import main.model.place.Place;

//champ de bataille
public class Battlefield extends Place {

    public Battlefield(String name, int surface) {
        super(name, surface);
    }

    public boolean canAccept(Character character) {
        return true;
    }

}