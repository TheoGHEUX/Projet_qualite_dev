package main.model.character;

import main.enums.PotionEffect;
import main.enums.Sex;
import main.model.items.food.Food;
import main.model.place.Place;
import main.model.items.potion.Potion;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class Character {
    // Informations personnelles
    protected String name; // nom
    protected Sex sex; // sexe
    protected int size; // taille (en cm)
    protected int age; // âge
    protected String nationality; // Gaul, Roman ou Creature
    protected String type; // correspond au métier d'un humain ou à l'espèce d'une créature
    protected Place currentPlace; // lieu qu'il occupe
    // Informations sur les statistiques vitales
    protected double strength; // Dégâts d'une attaque (methode fight())
    protected double baseStrength; // Dégâts de base
    protected int stamina; // énergie
    protected int maxStamina; // énergie max
    protected double health; // vie
    protected double maxHealth; // vie max
    protected int hunger; // faim
    protected boolean isAlive; // est vivant
    protected Place originPlace;
    // Informations concernant les potions
    protected int potionLevel; // potionLevel >= 4 signifie qu'il possède les effets de force et invincibilité en permanence
    protected boolean isInvincible; // est invincible => sa vie ne peut pas diminuer
    protected List<PotionEffect> characterPotionEffectsTemp = new ArrayList<PotionEffect>(); // effets de potions temporaires
    protected List<PotionEffect> characterPotionEffectsPerm = new ArrayList<PotionEffect>(); // effets de potions permanents
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(); // Pour la gestion de drinkMagicPotion()
    protected boolean isStatue ; // est une statue (a bu trop de potion), implique qu'il est mort
    // Autres
    protected boolean lastWasVegetal; // le dernier aliment qu'il a mangé est végétal
    // Variable utile à la génération de l'âge et la taille aléatoire
    protected static final Random RANDOM = new Random();

    /**
     * Crée un nouveau personnage.
     * @param name
     * @param sex
     * @param size
     * @param age
     * @param strength
     * @param stamina
     * @param health
     * @param place
     */
    public Character(String name, Sex sex, int size, int age, double strength, int stamina, double health, Place place) {
        this.name = name;
        this.sex = sex;
        this.size = size;
        this.age = age;
        this.strength = strength;
        this.baseStrength = strength;
        this.stamina = stamina;
        this.maxStamina = stamina;
        this.health = health;
        this.maxHealth = health;
        this.hunger = 100;
        this.potionLevel = 0;
        this.lastWasVegetal = false;
        this.isAlive = true;
        this.nationality = "Undefined";
        this.type = "Undefined";
        this.isInvincible = false;
        this.isStatue = false;
        this.currentPlace = place;
    }

    // Méthodes

    /**
     * Attaque un ennemi.
     * Diminue la vie de l'ennemi à hauteur de la force du lanceur de l'attaque.
     * Coûte 10 d'énergie
     * @param enemy
     */
    public void fight (Character enemy) {
        if((this.stamina - 10) < 0){
            System.out.println(this.name + " ne peut pas attaquer " + enemy.name + " | Pas assez d'énergie !" );
        }
        else {
            System.out.println(this.name + " attaque " + enemy.name + " | Dégâts: " + this.strength + " | Vie restante: " + (enemy.getHealth()-this.strength));
            enemy.updateHealth(-this.strength);
            this.stamina -= 10 ;
        }
    }

    /**
     * Met à jour la vie du personnage.
     * Il meurt si sa vie descend en dessous de 1.
     * Ses points de vies ne peuvent pas dépasser le maximum de vie du personnage.
     * @param health
     */
    public void updateHealth(double health) {
        if(this.isInvincible){
            if(health <=  0){
                return ;
            }
            this.health = Math.min(maxHealth, this.health + health);
            return;
        }

        this.health = Math.min(maxHealth, Math.max(0, this.health + health));

        if(this.health <= 0){
            this.decease();
        }
    }

    /**
     * Met à jour la faim du personnage.
     * Il meurt si sa faim descend en dessous de 1.
     * Sa barre de faim ne peut pas dépasser 100%.
     * @param hunger
     */
    public void updateHunger(int hunger) {

        this.hunger = Math.min(100, Math.max(0, this.hunger + hunger));

        if(this.hunger <= 0){
            this.decease();
        }
    }

    /**
     * Met à jour l'énergie du personnage.
     * Sa barre d'énergie ne peut pas dépasser l'énergie max du personnage.
     * @param stamina
     */
    public void updateStamina(int stamina) {
        this.stamina = Math.min(this.maxStamina, Math.max(0, this.stamina + stamina));
    }

    /**
     * Le persoonage mange un aliment.
     * Met à jour sa vie, sa faim et son énergie.
     * S'il mange deux végétaux d'affilée, il perd des points de vie.
     * @param food
     */
    public void eat(Food food){
        // Gestion de la faim
        if(hunger + food.getHungerEffect() >= 100){
            hunger = 100;
        } else {
            updateHunger(food.getHungerEffect());
        }

        // Gestion de la santé
        int effect = food.getHealthEffect();
        if(food.isVege() && lastWasVegetal){
            effect = -Math.abs(effect);
        } else if(food.isVege()){
            lastWasVegetal = true;
        } else {
            lastWasVegetal = false;
        }

        if(effect > 0){
            if(health + effect > maxHealth){
                health = maxHealth;
            } else {
                updateHealth(effect);
            }
        } else {
            updateHealth(effect);
        }

        // Gestion de l'énergie
        if(stamina + food.getStaminaEffect() >= maxStamina){
            stamina = maxStamina;
        } else {
            stamina += food.getStaminaEffect();
        }


        // Gestion des végétaux
        lastWasVegetal = food.isVege();
    }

    /**
     * Le personnage boit une potion.
     * Met à jour sa vie, sa faim et son énergie.
     * S'il s'agit d'une potion magique, il gagne des effets temporaires.
     * S'il boit beaucoup de potion magique, les effets deviennent permanents.
     * S'il boit trop de potion magique, il se transforme en statue.
     * @param potion
     */
    public void drinkMagicPotion (Potion potion){
        if (potion.isEmpty()){
            System.out.println("La potion est vide ! ");
            return;
        }

        potion.takeOneDose();
        if (potion.isNourishing()){
            this.updateHealth(potion.getHealthEffect());
            this.updateHunger(potion.getHungerEffect());
            this.updateStamina(potion.getStaminaEffect());
        }

        // Si la potion n'est pas magique :
        if(!potion.isMagic()){
            return;
        }

        // Si la potion est magique :

        // Si le personnage n'a pas les effets permanents:
        if(!this.characterPotionEffectsPerm.contains(PotionEffect.SUPER_STRENGTH)){
            // Si le personnage n'a pas les effets temporaires :
            if(!this.characterPotionEffectsTemp.contains(PotionEffect.SUPER_STRENGTH)){
                this.strength = baseStrength * 3 ;
                this.isInvincible = true;
                this.characterPotionEffectsTemp.add(PotionEffect.SUPER_STRENGTH);
                this.characterPotionEffectsTemp.add(PotionEffect.INVINCIBILITY);
            }
            // Si le personnage n'atteint pas le seuil pour passer d'effet temporaire à permanent :
            if(this.potionLevel + 1 < 4){
                this.potionLevel += 1 ;
                scheduler.schedule(() -> {
                    synchronized (this) {
                        if (this.potionLevel < 4) {
                            this.potionLevel -= 1;
                            if (this.potionLevel <= 0) {
                                this.strength = baseStrength;
                                this.isInvincible = false;
                                this.characterPotionEffectsTemp.remove(PotionEffect.SUPER_STRENGTH);
                                this.characterPotionEffectsTemp.remove(PotionEffect.INVINCIBILITY);
                            }
                        }
                    }
                }, 5, TimeUnit.MINUTES);
            }
            // Si les effets deviennent permanents :
            else {
                this.potionLevel = 4 ;
                this.characterPotionEffectsPerm.add(PotionEffect.SUPER_STRENGTH);
                this.characterPotionEffectsTemp.remove(PotionEffect.SUPER_STRENGTH);
                this.characterPotionEffectsPerm.add(PotionEffect.INVINCIBILITY);
                this.characterPotionEffectsTemp.remove(PotionEffect.INVINCIBILITY);
            }

        }
        // Si la personne avait déjà des effets permanents, il faut s'assurer qu'elle ne boit pas une deuxième marmite (sinon elle se transforme en statue)
        else {
            this.potionLevel += 1 ;
            scheduler.schedule(() -> {
                synchronized (this) {
                    if (this.potionLevel < 8) {
                        this.potionLevel -= 1;
                    }

                    if (this.potionLevel >= 8) {
                        if (!this.isStatue) {
                            this.transformStatue();
                            this.isStatue = true;
                        }
                    }
                }
            }, 5, TimeUnit.MINUTES);
        }
        // Gestion des autres effets

        if(!this.characterPotionEffectsPerm.contains(PotionEffect.DUPLICATION)){
            if(potion.getEffects().contains(PotionEffect.DUPLICATION)){
                this.characterPotionEffectsPerm.add(PotionEffect.DUPLICATION);
            }
        }

        if(!this.characterPotionEffectsPerm.contains(PotionEffect.METAMORPHOSIS)){
            if(potion.getEffects().contains(PotionEffect.METAMORPHOSIS)){
                this.characterPotionEffectsPerm.add(PotionEffect.METAMORPHOSIS);
            }
        }

    }

    /**
     * Le personnage se transforme en statue et meurt.
     */
    public void transformStatue(){
        this.isStatue = true;
        this.decease();
    }

    /**
     * Le personnage meurt.
     */
    public void decease (){
        System.out.println(this.name + " est mort !");
        this.isAlive = false;
    }

    /**
     * Met à jour le lieu actuel du personnage.
     * @param place
     */
    public void modifyCurrentPlace(Place place){
        this.currentPlace = place;
    }

    /**
     * Modifie la force du personnage.
     * @param strength
     */
    public void gainStrength(int strength){
        this.strength += strength;
    }

    /**
     *
     * @return
     */
    public static List<String> showCharactersNames(List<Character> characters){
        List<String> noms = new ArrayList<>();
        for (Character character : characters){
            noms.add(character.getName());
        }
        return noms;
    }
    // Getters

    public String getName() {
        return name;
    }

    public Sex getSex(){
        return sex;
    }

    public int getSize() {
        return size;
    }

    public int getAge() {
        return age;
    }

    public double getStrength() {
        return strength;
    }

    public int getStamina() {
        return stamina;
    }

    public int getMaxStamina() {
        return maxStamina;
    }

    public double getHealth() {
        return health;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public int getHunger() {
        return hunger;
    }

    public int getPotionLevel() {
        return potionLevel;
    }

    public boolean getLastWasVegetal() {
        return lastWasVegetal;
    }

    public Place getPlace() {
        return currentPlace;
    }

    public String getNationality() {
        return nationality;
    }

    public String getType() {
        return type;
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    public boolean isStatue() {
        return isStatue;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public Place getOriginPlace() {return originPlace;}

    public void setOriginPlace(Place place) {this.originPlace = place;}

    public List<PotionEffect> getCharacterPotionEffectsTemp() {
        return characterPotionEffectsTemp;
    }
    public List<PotionEffect> getCharacterPotionEffectsPerm() {
        return characterPotionEffectsPerm;
    }

    /**
     * Génère aléatoirement un nombre dans un intervalle
     * @param min
     * @param max
     * @return
     */
    protected static int randomBetween(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }

    public void showCharacterVitalData(){
        System.out.println(this.getName() + " | Vie: " + this.getHealth() + "/" + this.maxHealth + " | Faim: " + this.getHunger() + "%" + " | Energie: " + this.getStamina() + " | Vivant: " + this.isAlive());
    }

    public void showCharacterInfos(){
        System.out.println(this.getName() + " | Nationalité: " + this.getNationality() + " | Type: " + this.getType() +" | Age: " + this.getAge() + " | Taille: " + this.getSize() + "cm" + " | Force: " + this.getStrength());
    }


}
