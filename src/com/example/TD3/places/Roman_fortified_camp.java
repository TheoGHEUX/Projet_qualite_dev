package com.example.TD3.places;

import com.example.TD3.characters.Character;
import com.example.TD3.characters.roman.Roman;
import com.example.TD3.characters.creature.Creature;
import com.example.TD3.interfaces.Fighter;

//camp retranché romain
public class Roman_fortified_camp extends Place_with_clan_chief {

    public Roman_fortified_camp(String name, int surface, String clan_chief) {
        super(name, surface, clan_chief);
    }

    public boolean canAccept(Character character) {
        // Accepte seulement les combattants romains et les créatures magiques
        return ((character instanceof Roman))        // ############# conditions à rajouter est combattants
                || (character instanceof Creature);
    }
}