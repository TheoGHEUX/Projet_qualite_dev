package TD3.places;

import TD3.characters.Character;
import TD3.clan_chief.ClanChief;
import TD3.interfaces.Leader;

public abstract class Place_with_clan_chief extends Place {
    protected ClanChief clan_chief;

    public Place_with_clan_chief(String name, int surface, ClanChief clan_chief) {
        super(name, surface);
        if (clan_chief != null) {
            if (clan_chief.getPlace() != null) {
                System.out.println("Le chef " + clan_chief.getName() + " est déjà chef d'un autre lieu ! Le lieu" + name + " a été créé mais n'a pas encore de chef !");
                this.clan_chief = null;
            } else {
                this.clan_chief = clan_chief;
                clan_chief.setPlace(this);
            }
        } else {
            this.clan_chief = null;
            System.out.println("Le lieu " + name + " a été créé mais n'a pas encore de chef !");
        }
    }

    public void assignClanChief(ClanChief clan_chief) {
        if (clan_chief == null) {
            System.out.println("Chef fourni invalide.");
            return;
        }
        if(this.clan_chief != null) {
            System.out.println("Le lieu " + name + " a déjà un chef !");
            return;
        }
        if(clan_chief.getPlace() != null) {
            System.out.println("Le chef de clan " + clan_chief.getName() + " est déjà associé à un lieu !");
            return;
        }
        this.clan_chief = clan_chief;
        clan_chief.setPlace(this);
        System.out.println(clan_chief.getName() + " est le nouveau chef de " + name + " !");
    }

    public void removeClanChief() {
        if(this.clan_chief == null) {
            System.out.println("Le lieu " + name + " est déjà sans chef !");
            return;
        }
        this.clan_chief.clearPlace();
        System.out.println(clan_chief.getName() + " n'est plus le chef de " + name + " !");
        this.clan_chief = null;
    }

    public ClanChief getClanChief() {
        return clan_chief;
    }

    public void showInfos() {
        if(clan_chief != null) {
            System.out.println("[INFOS] Lieu: " + name + " | Chef de clan: " + clan_chief.getName());
            return;
        }
        System.out.println("[INFOS] Lieu: " + name + " | Chef de clan: aucun");
    }
}