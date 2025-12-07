package TD3.clan_chief;

import TD3.enums.Sex;
import TD3.places.Place_with_clan_chief;

import java.util.Random;

public class ClanChief {
    private final String name;
    private int age;
    private final Sex sex;
    private Place_with_clan_chief place;

    public ClanChief(String name, Sex sex) {
        this.name = name;
        this.age = new Random().nextInt(61) + 30;
        this.place = null ;
        this.sex = sex ;
    }

    public Place_with_clan_chief getPlace() {
        return place;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Sex getSex() {
        return sex;
    }

    public void setPlace(Place_with_clan_chief place) {
        this.place = place;
    }

    public void clearPlace() {
        this.place = null;
    }

    public void showInfos() {
        if(this.getPlace() != null) {
            System.out.println("[INFOS] Chef de clan: " + name + " | Age: " + age + " | Sex: " + sex + " | Place: " + place.getName());
            return;
        }
        System.out.println("[INFOS] Chef de clan: " + name + " | Age: " + age + " | Sex: " + sex + " | Place: aucune");

    }

}

