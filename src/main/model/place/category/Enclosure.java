package main.model.place.category;

import main.model.character.Character;
import main.model.character.category.creature.Creature;
import main.model.place.Place;

//enclos
public class Enclosure extends Place {

    public Enclosure(String name, int surface) {
        super(name, surface);
    }

    public boolean canAccept(Character character) {
        // Accepte seulement les créatures fantastiques
        return character instanceof Creature;
    }
}