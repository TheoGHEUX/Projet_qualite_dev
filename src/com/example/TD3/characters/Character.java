package com.example.TD3.characters;

import com.example.TD3.enums.Sex;
import com.example.TD3.food.Food;

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
    protected  int potion_level;
    protected  boolean last_was_vege;

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
        this.potion_level = 0;
        this.last_was_vege = false;
    }

    public void fight (Character enemy) {

    }

    public void beTreated (int sum){
        this.health += sum;
    }

    public void eat (Food food){

        // Gestion de la faim qui ne peut pas être supérieure à 100
        if((food.getHungerEffect() + hunger) >= 100){
            this.hunger = 100;
        }
        else{
            this.hunger += food.getHungerEffect();
        }

        // Gestion des végétaux : si deux végétaux d'affilée sont consommés, l'effet de vie est négatif
        if(food.isVege()){
            if(this.last_was_vege){
                this.health -= Math.abs(food.getHealthEffect());
                if(this.health <= 0){
                    this.decease();
                }
            }
            else{
                this.last_was_vege = true;
                if(this.health + food.getHealthEffect() > this.maxHealth){
                    this.health = this.maxHealth;
                }
                else{
                    this.health += food.getHealthEffect();
                }
            }
        }
        else{
            this.last_was_vege = false;
            if(this.health + food.getHealthEffect() > this.maxHealth){
                this.health = this.maxHealth;
            }
            else{
                this.health += food.getHealthEffect();
            }
        }

    }

    public void drinkMagicPotion (int doses){
        this.potion_level += doses;
    }

    public void decease (){
    }
}
