package TD3.clan_chief;

import TD3.characters.Character;
import TD3.characters.creature.Werewolf;
import TD3.characters.gaul.Blacksmith;
import TD3.characters.gaul.Druid;
import TD3.characters.gaul.Innkeeper;
import TD3.characters.gaul.Merchant;
import TD3.characters.roman.General;
import TD3.characters.roman.Legionary;
import TD3.characters.roman.Prefect;
import TD3.enums.CharacterType;
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

    public void examinePlace(){
        if(place == null){
            System.out.println(name + " n'a pas de lieu à examiner");
            return;
        }
        place.showInfos();
    }

    public Place_with_clan_chief getPlace() {
        return place;
    }

    public void createCharacter(String name, Sex sex, CharacterType type){
        if(place == null){
            System.out.println("Le chef " + this.name + " n'est relié à aucun lieu : impossible de créer un personnage.");
            return;
        }
        Character newCharacter;
         switch(type){
             case WEREWOLF:
                 newCharacter = new Werewolf(name, sex);
                 break;
             case BLACKSMITH:
                 newCharacter = new Blacksmith(name, sex);
                 break;
             case DRUID:
                 newCharacter = new Druid(name, sex);
                 break;
             case INNKEEPER:
                 newCharacter = new Innkeeper(name, sex);
                 break;
             case MERCHANT:
                 newCharacter = new Merchant(name, sex);
                 break;
             case GENERAL:
                 newCharacter = new General(name,sex);
                 break;
             case LEGIONARY:
                 newCharacter = new Legionary(name,sex);
                 break;
             case PREFECT:
                 newCharacter = new Prefect(name,sex);
                 break;
             default:
                 System.out.println("Type de personnage inconnu : " + type);
                 return;
         }

         if(!place.canAccept(newCharacter)){
             System.out.println("Le nouveau personnage " + newCharacter.getName() + " a été créé mais n'est pas autorisé à entrer dans votre lieu ! Il est parti :(  ");
             return;
         }
         place.addCharacter(newCharacter);
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

