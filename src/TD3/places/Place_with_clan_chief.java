package TD3.places;

import TD3.characters.Character;
import TD3.interfaces.Leader;

public abstract class Place_with_clan_chief extends Place {
    protected Character clan_chief;

    public Place_with_clan_chief(String name, int surface, Character clan_chief) {
        super(name, surface);
        if(clan_chief instanceof Leader){
            this.clan_chief = clan_chief;
            clan_chief.modifyCurrentPlace(this);
            this.numberOfPeople = 1 ;
        }
        else{
            this.clan_chief = null;
            this.numberOfPeople = 0 ;
            System.out.println("Ce personnage n'est pas autorisé à diriger ! Le lieu a été créé mais n'a pas encore de chef !");
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
                    this.numberOfPeople++ ;
                    character.modifyCurrentPlace(this);
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
