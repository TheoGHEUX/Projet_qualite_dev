package TD3.places;

import TD3.characters.Character;

public abstract class Place_with_clan_chief extends Place {
    protected Character clan_chief;

    public Place_with_clan_chief(String name, int surface, Character clan_chief) {
        super(name, surface);
        this.clan_chief = clan_chief;
    }

    public Character getClanChief() {
        return clan_chief;
    }

    public void setClanChief(Character clan_chief) {
        this.clan_chief = clan_chief;
    }


}
