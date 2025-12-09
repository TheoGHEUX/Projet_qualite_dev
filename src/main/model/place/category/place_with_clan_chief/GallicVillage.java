package main.model.place.category.place_with_clan_chief;

import main.model.character.Character;
import main.model.character.category.gaul.Gaul;
import main.model.character.category.creature.Creature;
import main.model.clan_chief.ClanChief;
import main.model.place.category.PlaceWithClanChief;

//village gaulois
public class GallicVillage extends PlaceWithClanChief {

    public GallicVillage(String name, int surface, ClanChief clan_chief) {
        super(name, surface, clan_chief);
        this.isGallo = true;
    }

    public boolean canAccept(Character character) {
        // Accepte les gaulois et les créatures magiques
        return (character instanceof Gaul) || (character instanceof Creature);
    }
}
