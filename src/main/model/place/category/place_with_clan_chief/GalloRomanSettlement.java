package main.model.place.category.place_with_clan_chief;

import main.model.character.Character;
import main.model.character.category.gaul.Gaul;
import main.model.character.category.roman.Roman;
import main.model.clan_chief.ClanChief;
import main.model.place.category.PlaceWithClanChief;

//bougarde galo-romaine
public class GalloRomanSettlement extends PlaceWithClanChief {

    public GalloRomanSettlement(String name, int surface, ClanChief clan_chief) {
        super(name, surface, clan_chief);
        this.isGallo = true;
    }

    public boolean canAccept(Character character) {
        // Accepte seulement les gaulois et les romains
        return (character instanceof Gaul) || (character instanceof Roman);
    }
}