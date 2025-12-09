package main.model.place.category.place_with_clan_chief;

import main.model.character.Character;
import main.model.character.category.roman.Roman;
import main.model.character.category.creature.Creature;
import main.model.clan_chief.ClanChief;
import main.model.place.category.PlaceWithClanChief;

public class RomanTown extends PlaceWithClanChief {

    public RomanTown(String name, int surface, ClanChief clan_chief) {
        super(name, surface, clan_chief);
        this.isGallo = false;
    }

    public boolean canAccept(Character character) {
        // Accepte seulement les romains et les créatures magiques
        return (character instanceof Roman) || (character instanceof Creature);
    }
}
