package TD3.characters;

import TD3.enums.Sex;
import TD3.food.Food;

public abstract class Character {
    protected String name;
    protected Sex sex;
    protected  int size;
    protected  int age;
    protected  int strength;
    protected  int stamina;
    protected  int health;
    protected int maxHealth;
    protected  int hunger;
    protected  int belligerence ;
    protected  int potionLevel;
    protected  boolean lastWasVegetal;

    public Character(String name, Sex sex, int size, int age, int strength, int stamina, int health) {
        this.name = name;
        this.sex = sex;
        this.size = size;
        this.age = age;
        this.strength = strength;
        this.stamina = stamina;
        this.health = health;
        this.maxHealth = health;
        this.hunger = 100;
        this.belligerence = 0;
        this.potionLevel = 0;
        this.lastWasVegetal = false;
    }

    // Méthodes

    public void fight (Character enemy) {

    }

    public void beTreated (int sum){
        this.health += sum;
    }

    public void updateHealth(int health) {
        if(this.health + health >= maxHealth){
            this.health = maxHealth;
        }
        else{
            this.health += health;
        }
        if(this.health <= 0){
            this.decease();
        }
    }

    public void updateHunger(int hunger) {
        if(this.hunger + hunger >= 100){
            this.hunger = 100 ;
        }
        else{
            this.hunger += hunger;
        }
        if(this.hunger <= 0){
            this.decease();
        }
    }

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

        // Gestion des végétaux
        lastWasVegetal = food.isVege();
    }

    public void drinkMagicPotion (int doses){
        this.potionLevel += doses;
    }

    public void decease (){
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

    public int getStrength() {
        return strength;
    }

    public int getStamina() {
        return stamina;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getHunger() {
        return hunger;
    }

    public int getBelligerence() {
        return belligerence;
    }

    public int getPotion_level() {
        return potionLevel;
    }

    public boolean getLast_was_vege() {
        return lastWasVegetal;
    }

}
