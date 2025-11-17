package com.example.TD3.characters.gaul;

import com.example.TD3.characters.Character;
import com.example.TD3.enums.Sex;

public abstract class Gaul extends Character {
    public Gaul(String name, Sex sex, int size, int age, int strength, int stamina, int health) {
        super(name, sex, size, age, strength, stamina, health);
    }
}
