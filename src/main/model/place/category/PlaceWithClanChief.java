package main.model.place.category;

import main.model.clan_chief.ClanChief;
import main.enums.FoodType;
import main.model.items.food.Food;
import main.model.place.Place;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class PlaceWithClanChief extends Place {
    protected ClanChief clan_chief;
    protected boolean isGallo;

    public PlaceWithClanChief(String name, int surface, ClanChief clan_chief) {
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

    public boolean isGallo() {
        return isGallo;
    }

    public void showInfos() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("[Lieu] " + name);

        // Chef
        if(clan_chief != null) {
            System.out.println("  Chef :  " + clan_chief.getName());
        } else {
            System.out.println("  Chef :  Aucun");
        }
        System.out.println("  " + "-". repeat(50));

        // Personnages
        System. out.println("  [Personnages] " + the_characters_present.size() + " personnage(s) :");
        if (the_characters_present.isEmpty()) {
            System.out. println("     • Aucun");
        } else {
            for (main.model.character.Character c :  the_characters_present) {
                System.out.println("     • " + c.getName() + " (" + c.getType() + ", " + c.getSex() +
                        ") - Vie: " + (int)c.getHealth() + ", Energie: " + c.getStamina());
            }
        }

        // Aliments
        System. out.println("\n  [Aliments] " + the_aliments_present.size() + " aliment(s) :");
        if (the_aliments_present.isEmpty()) {
            System.out.println("     • Aucun");
        } else {
            // Compter les aliments
            Map<FoodType, Integer> foodCount = new HashMap<>();
            for (Food food : the_aliments_present) {
                FoodType type = food.getFoodType();
                foodCount.put(type, foodCount.getOrDefault(type, 0) + 1);
            }

            // Afficher
            for (Map.Entry<FoodType, Integer> entry : foodCount.entrySet()) {
                System.out.println("     • " + entry.getKey() + " x " + entry.getValue());
            }
        }

        // Potions
        if (! potion_present.isEmpty()) {
            System.out.println("\n  [Potions] " + potion_present.size() + " potion(s)");
        }

        System.out.println("=".repeat(60) + "\n");
    }
}