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
import TD3.food.Food;
import TD3.places.Battlefield;
import TD3.places.Enclosure;
import TD3.places.Place;
import TD3.places.Place_with_clan_chief;
import TD3.potion.Potion;

import java.util.Random;

public class ClanChief {
    private final String name;
    private int age;
    private final Sex sex;
    private Place_with_clan_chief place;

    /**
     * Crée un nouveau chef de clan.
     * @param name
     * @param sex
     */
    public ClanChief(String name, Sex sex) {
        this.name = name;
        this.age = new Random().nextInt(61) + 30;
        this.place = null ;
        this.sex = sex ;
    }

    /**
     * Le chef de clan affiche les informations de son lieu s'il en a un.
     */
    public void examinePlace(){
        if(place == null){
            System.out.println(name + " n'a pas de lieu à examiner");
            return;
        }
        place.showInfos();
    }

    /**
     * Le chef de clan crée un personnage dans son lieu s'il en a un.
     * @param name
     * @param sex
     * @param type
     */
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

    /**
     * Le chef de clan nourrit les personnes dans son lieu s'il en a un, si possible.
     */
    public void feedCharacters(){
        if (this.place == null) {
            System.out.println("Le chef " + name + " n'est associé à aucun lieu.");
            return;
        }

        if (place.getThe_characters_present().isEmpty()) {
            System.out.println("Il n'y a aucun personnage à nourrir dans " + place.getName() + ".");
            return;
        }

        if (place.getThe_aliments_present().isEmpty()) {
            System.out.println("Il n'y a aucune nourriture disponible dans " + place.getName() + ".");
            return;
        }

        for (Character character : place.getThe_characters_present()) {

            if (!character.isAlive()) {
                System.out.println(character.getName() + " est mort, il ne peut pas manger.");
                continue;
            }

            if (place.getThe_aliments_present().isEmpty()) {
                System.out.println("Il n'y a plus de nourriture disponible.");
                return;
            }

            Food food = place.getThe_aliments_present().removeFirst(); // prend le premier aliment disponible
            character.eat(food);

            System.out.println(character.getName() + " a mangé " + food.getFoodType());
        }

    }

    /**
     * Le chef de clan demande à un druide de préparer une potion magique pour son lieu s'il en a un, si le lieu accepte les gaulois et possède un druide.
     */
    public void askDruidToMakePotion(){
        if (place == null) {
            System.out.println("Le chef " + name + " n'est associé à aucun lieu !");
            return;
        }
        if(!place.isGallo()){
            System.out.println(place.getName() + " n'autorise pas l'accès aux gaulois, il ne peut donc pas y avoir de druide !");
            return;
        }

        Druid druid = null;

        for (Character character : place.getThe_characters_present()) {
            if (character instanceof Druid) {
                druid = (Druid) character;
                break;
            }
        }

        if (druid == null) {
            System.out.println("Aucun druide présent dans " + place.getName() + ".");
            return;
        }

        if (!place.hasEnoughToMakeAMagicPotion()) {
            System.out.println("Il n'y a pas assez d'ingrédients pour produire une potion magique dans " + place.getName() + ".");
            return;
        }

        druid.work(); // préparation de la potion

        System.out.println("Le chef " + name + " a demandé au druide " + druid.getName() + " de préparer une potion magique dans " + place.getName() + ".");
    }

    /**
     * Le chef de clan fait boire de la potion magique aux personnes de son lieu s'il en a un, si possible.
     * @param potion
     */
    public void makeCharactersDrinkMagicPotion(Potion potion) {
        if (place == null) {
            System.out.println("Le chef " + name + " n'est associé à aucun lieu, impossible de donner une potion.");
            return;
        }

        if (potion == null || potion.isEmpty()) {
            System.out.println("La potion est vide ou inexistante !");
            return;
        }

        for (Character character : place.getThe_characters_present()) {
            if (!character.isAlive()) {
                System.out.println(character.getName() + " est mort, il ne peut pas boire la potion.");
                continue;
            }

            System.out.println("Le chef " + name + " fait boire une potion magique à " + character.getName() + " !");
            character.drinkMagicPotion(potion);
        }
    }

    /**
     * Le chef de clan transfert un personnage de son lieu s'il en a un, vers un champ de bataille ou un enclos.
     * @param character
     * @param newPlace
     */
    public void transferCharacter(Character character, Place newPlace) {
        if (this.place == null) {
            System.out.println("Le chef " + this.name + " n'est associé à aucun lieu !");
            return;
        }

        if (character == null || !this.place.containsCharacter(character)) {
            System.out.println(character.getName() + " n'est pas dans " + this.place.getName());
            return;
        }

        if (!(newPlace instanceof Battlefield || newPlace instanceof Enclosure)) {
            System.out.println("Le lieu de destination doit être un champ de bataille ou un enclos !");
            return;
        }

        if (!newPlace.canAccept(character)) {
            System.out.println(character.getName() + " ne peut pas entrer dans " + newPlace.getName());
            return;
        }

        // Retirer du lieu actuel
        this.place.removeCharacter(character);

        // Ajouter au nouveau lieu
        if (newPlace.addCharacter(character)) {
            System.out.println(character.getName() + " a été transféré de " + this.place.getName() + " vers " + newPlace.getName());
        }
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

