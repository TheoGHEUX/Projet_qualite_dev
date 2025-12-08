package TD3.places;

import TD3.characters.Character;
import TD3.characters.gaul.Gaul;
import TD3.characters.roman.Roman;
import TD3.clan_chief.ClanChief;

//bougarde galo-romaine
public class Gallo_Roman_settlement extends Place_with_clan_chief {

    public Gallo_Roman_settlement(String name, int surface, ClanChief clan_chief) {
        super(name, surface, clan_chief);
        this.isGallo = true;
    }

    public boolean canAccept(Character character) {
        // Accepte seulement les gaulois et les romains
        return (character instanceof Gaul) || (character instanceof Roman);
    }
}