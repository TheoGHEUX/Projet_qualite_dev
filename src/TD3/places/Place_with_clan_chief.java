package TD3.places;

import TD3.characters.Character;
import TD3.interfaces.Leader;

public abstract class Place_with_clan_chief extends Place {
    protected Character clan_chief;

    public Place_with_clan_chief(String name, int surface, Character clan_chief) {
        super(name, surface);
        if(clan_chief != null){
            if(clan_chief instanceof Leader){
                if(this.canAccept(clan_chief)){
                    this.clan_chief = clan_chief;
                    clan_chief.modifyCurrentPlace(this);
                    clan_chief.modifyIsAClanChief(true);
                    this.the_characters_present.add(clan_chief);
                }
                else{
                    this.clan_chief = null;
                    System.out.println("Ce personnage n'est pas autorisé à diriger ce lieu ! Le lieu a été créé mais n'a pas encore de chef !");
                }
            }
            else{
                this.clan_chief = null;
                System.out.println("Ce personnage ne peut pas diriger ! Le lieu a été créé mais n'a pas encore de chef !");
            }
        }
        else{
            this.clan_chief = null;
            System.out.println("Le lieu a été créé mais n'a pas encore de chef !");
        }


    }

    public Character getClanChief() {
        return clan_chief;
    }

    public void setClanChief(Character character) {

        if(this.clan_chief == null){
            if(character instanceof Leader){
                if(!character.isAClanChief()){
                    this.clan_chief = character;
                    character.modifyCurrentPlace(this);
                    character.modifyIsAClanChief(true);
                    this.the_characters_present.add(clan_chief);
                }
                else{
                    System.out.println("Ce personnage est déjà le chef de clan d'un autre lieu !");
                }
            }
            else{
                System.out.println("Ce personnage n'est pas autorisé à diriger !");
            }
        }
        else{
            System.out.println("Ce lieu a déjà un chef de clan !");
        }
    }
}
