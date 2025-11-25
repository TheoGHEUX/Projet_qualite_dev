package com.example.TD3.characters.gaul;

import com.example.TD3.enums.Sex;

// Forgerons
public class Blacksmith extends Gaul {

    // Constructeur personnalisé
    public Blacksmith(String name, Sex sex, int size, int age, int strength, int stamina, int health) {
        super(name, sex, size, age, strength, stamina, health);
    }

    // Constructeur avec des stats par défaut
    public Blacksmith(String name, Sex sex) {
        super(name, sex,randomBetween(170,185),randomBetween(30,50),75,65,120);
    }
}
