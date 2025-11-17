package com.example.TD3.places;

public class Place_with_clan_chief extends Place {
    protected String clan_chief;

    public Place_with_clan_chief(String name, int surface, String clan_chief) {
        super(name, surface);
        this.clan_chief = clan_chief;
    }

    public String getClanChief() {
        return clan_chief;
    }

    public void setClanChief(String clan_chief) {
        this.clan_chief = clan_chief;
    }


}
