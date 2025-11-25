package com.example.TD3.characters.gaul;

import com.example.TD3.enums.Sex;

// Druides
public class Druid extends Gaul {

    // Constructeur personnalisé
    public Druid(String name, Sex sex, int size, int age, int strength, int stamina, int health) {
        super(name, sex, size, age, strength, stamina, health);
    }

    // Constructeur avec des stats par défaut
    public Druid(String name, Sex sex) {
        super(name, sex, randomBetween(165,180), randomBetween(50,90),35,65,100);
    }
}
