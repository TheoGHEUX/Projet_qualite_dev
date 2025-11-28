package TD3.places;

import TD3.characters.Character;
//champ de bataille
public class Battlefield extends Place {

    public Battlefield(String name, int surface) {
        super(name, surface);
    }

    public boolean canAccept(Character character) {
        // Accepte tous types de personnages
        return true;
    }


}