package TD3.places;

import TD3.characters.Character;
import TD3.characters.creature.Creature;
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