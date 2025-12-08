package TD3.places;

import TD3.characters.Character;
import TD3.characters.roman.Roman;
import TD3.characters.creature.Creature;
import TD3.clan_chief.ClanChief;

public class Roman_town extends Place_with_clan_chief{

    public Roman_town(String name, int surface, ClanChief clan_chief) {
        super(name, surface, clan_chief);
        this.isGallo = false;
    }

    public boolean canAccept(Character character) {
        // Accepte seulement les romains et les créatures magiques
        return (character instanceof Roman) || (character instanceof Creature);
    }
}
