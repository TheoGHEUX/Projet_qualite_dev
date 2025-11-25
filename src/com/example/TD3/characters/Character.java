package com.example.TD3.characters;

import com.example.TD3.enums.Sex;

public abstract class Character {
    protected String name;
    protected Sex sex;
    protected  int size;
    protected  int age;
    protected  int strength;
    protected  int stamina;
    protected  int health;
    protected  int hunger;
    protected  int belligerence ;
    protected  int potion_level;

    public Character(String name, Sex sex, int size, int age, int strength, int stamina, int health) {
        this.name = name;
        this.sex = sex;
        this.size = size;
        this.age = age;
        this.strength = strength;
        this.stamina = stamina;
        this.health = health;
        this.hunger = 100;
        this.belligerence = 0;
        this.potion_level = 0;
    }

    public void fight (Character enemy) {

    }

    public void beTreated (int sum){
        this.health += sum;
    }

    public void eat (int sum){
        this.hunger += sum;
    }

    public void drinkMagicPotion (int doses){
        this.potion_level += doses;
    }

    public void decease (){

    }
}
