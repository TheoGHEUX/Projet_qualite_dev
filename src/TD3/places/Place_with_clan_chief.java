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

    public void setClanChief(Character character) {
        if(this.clan_chief == null){
            if(!character.isAClanChief()){
                this.clan_chief = character;
            }
            else{
                System.out.println("Ce personnage est déjà le chef de clan d'un autre lieu !");
            }
        }
        else{
            System.out.println("Ce lieu a déjà un chef de clan !");
        }
    }
}
