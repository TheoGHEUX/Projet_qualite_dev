package com.example.TD3.places;

import com.example.TD3.characters.Character;
import com.example.TD3.characters.gaul.Gaul;
import com.example.TD3.characters.creature.Creature;

//village gaulois
public class Gallic_village extends Place_with_clan_chief {

    public Gallic_village(String name, int surface, String clan_chief) {
        super(name, surface, clan_chief);
    }

    public boolean canAccept(Character character) {
        // Accepte les gaulois et les créatures magiques
        return (character instanceof Gaul) || (character instanceof Creature);
    }
}
